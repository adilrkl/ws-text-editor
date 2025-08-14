package com.example.wsTextEditor.crdt;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * CRDT Document that maintains a conflict-free replicated text document
 * Uses RGA (Replicated Growable Array) algorithm
 */
public class CRDTDocument {
    private final Map<String, CRDTCharacter> characters;
    private final String siteId;
    private long localClock;

    public CRDTDocument(String siteId) {
        this.characters = new ConcurrentHashMap<>();
        this.siteId = siteId;
        this.localClock = 0;
    }

    /**
     * Insert a character at the specified position
     * @param content The character to insert
     * @param afterId The ID of the character this should come after (null for beginning)
     * @return The operation that was performed
     */
    public synchronized CRDTOperation insertCharacter(char content, String afterId) {
        long timestamp = System.currentTimeMillis() + (++localClock); // Ensure uniqueness
        CRDTCharacter newChar = new CRDTCharacter(content, timestamp, siteId, afterId);
        characters.put(newChar.getId(), newChar);
        return new CRDTOperation(CRDTOperation.Type.INSERT, newChar, null, siteId, System.currentTimeMillis());
    }

    /**
     * Delete a character by its ID
     * @param characterId The ID of the character to delete
     * @return The operation that was performed, or null if character not found
     */
    public synchronized CRDTOperation deleteCharacter(String characterId) {
        CRDTCharacter charToDelete = characters.get(characterId);
        if (charToDelete != null && !charToDelete.isDeleted()) {
            charToDelete.delete();
            return new CRDTOperation(CRDTOperation.Type.DELETE, charToDelete, null, siteId, System.currentTimeMillis());
        }
        return null;
    }

    /**
     * Apply a remote operation to this document
     * This method is idempotent and commutative
     */
    public synchronized List<CRDTOperation> applyOperation(CRDTOperation operation) {
        List<CRDTOperation> resultingOperations = new ArrayList<>();
        if (operation == null || operation.getCharacter() == null) return resultingOperations;

        CRDTCharacter character = operation.getCharacter();

        switch (operation.getType()) {
            case INSERT:
                if (characters.containsKey(character.getId())) {
                    break; // Character already exists, do nothing
                }

                characters.put(character.getId(), character);
                resultingOperations.add(operation);

                // Find the character that was previously after the new character's predecessor
                // and update its afterId to point to the new character.
                for (CRDTCharacter oldNextChar : characters.values()) {
                    if (!oldNextChar.getId().equals(character.getId()) && java.util.Objects.equals(oldNextChar.getAfterId(), character.getAfterId())) {
                        oldNextChar.setAfterId(character.getId());
                        CRDTOperation updateOperation = new CRDTOperation(CRDTOperation.Type.UPDATE, oldNextChar, null, this.siteId, System.currentTimeMillis());
                        resultingOperations.add(updateOperation);
                        break;
                    }
                }
                break;

            case DELETE:
                CRDTCharacter charToDelete = characters.get(character.getId());
                if (charToDelete != null && !charToDelete.isDeleted()) {
                    charToDelete.setDeleted(true);
                    resultingOperations.add(operation);
                }
                break;
            case UPDATE:
                CRDTCharacter charToUpdate = characters.get(character.getId());
                if (charToUpdate != null) {
                    charToUpdate.setAfterId(character.getAfterId());
                    resultingOperations.add(operation);
                }
                break;
        }
        return resultingOperations;
    }

    /**
     * Get the current text content of the document
     * Characters are ordered based on their causal relationships
     */
    public synchronized String getText() {
        List<CRDTCharacter> orderedChars = getOrderedCharacters();
        StringBuilder text = new StringBuilder();
        
        for (CRDTCharacter character : orderedChars) {
            if (!character.isDeleted()) {
                text.append(character.getContent());
            }
        }
        
        return text.toString();
    }

    /**
     * Get characters ordered by their causal relationships
     * This implements the RGA ordering algorithm
     */
    private List<CRDTCharacter> getOrderedCharacters() {
        List<CRDTCharacter> result = new ArrayList<>();
        Map<String, List<CRDTCharacter>> childrenMap = new HashMap<>();
        
        // Build parent-child relationships
        for (CRDTCharacter character : characters.values()) {
            String afterId = character.getAfterId();
            childrenMap.computeIfAbsent(afterId, k -> new ArrayList<>()).add(character);
        }
        
        // Sort children by timestamp for deterministic ordering
        for (List<CRDTCharacter> children : childrenMap.values()) {
            children.sort(CRDTCharacter::compareTo);
        }
        
        // Build the ordered list using DFS
        buildOrderedList(null, childrenMap, result);
        
        return result;
    }

    private void buildOrderedList(String parentId, Map<String, List<CRDTCharacter>> childrenMap, List<CRDTCharacter> result) {
        List<CRDTCharacter> children = childrenMap.get(parentId);
        if (children != null) {
            for (CRDTCharacter child : children) {
                result.add(child);
                buildOrderedList(child.getId(), childrenMap, result);
            }
        }
    }

    /**
     * Get character ID at a specific position in the visible text
     */
    public synchronized String getCharacterIdAtPosition(int position) {
        List<CRDTCharacter> orderedChars = getOrderedCharacters();
        int currentPos = 0;
        
        for (CRDTCharacter character : orderedChars) {
            if (!character.isDeleted()) {
                if (currentPos == position) {
                    return character.getId();
                }
                currentPos++;
            }
        }
        
        return null; // Position is at the end
    }

    /**
     * Get the ID of the character that should come before a new insertion at the given position
     */
    public synchronized String getAfterIdForPosition(int position) {
        if (position == 0) {
            return null; // Insert at beginning
        }
        
        List<CRDTCharacter> orderedChars = getOrderedCharacters();
        int currentPos = 0;
        
        for (CRDTCharacter character : orderedChars) {
            if (!character.isDeleted()) {
                if (currentPos == position - 1) {
                    return character.getId();
                }
                currentPos++;
            }
        }
        
        // If position is beyond the end, return the last character's ID
        for (int i = orderedChars.size() - 1; i >= 0; i--) {
            if (!orderedChars.get(i).isDeleted()) {
                return orderedChars.get(i).getId();
            }
        }
        
        return null;
    }

    // Getters
    public String getSiteId() { return siteId; }
    public int getCharacterCount() { return (int) characters.values().stream().filter(c -> !c.isDeleted()).count(); }
    public List<CRDTCharacter> getCharacters() {
        return new ArrayList<>(characters.values());
    }
    
    @Override
    public String toString() {
        return "CRDTDocument{" +
                "siteId='" + siteId + '\'' +
                ", text='" + getText() + '\'' +
                ", characterCount=" + getCharacterCount() +
                '}';
    }
}
