# --- AŞAMA 1: Projeyi Build Etme (Derleme) ---
# Maven ve JDK içeren bir imaj kullanarak projeyi derle.
FROM maven:3.8-openjdk-17 AS builder

# Çalışma dizini oluştur
WORKDIR /app

# Önce sadece pom.xml'i kopyala. Bu sayede bağımlılıklar sadece gerektiğinde indirilir.
COPY pom.xml .
RUN mvn dependency:go-offline

# Tüm kaynak kodunu kopyala
COPY src ./src

# Projeyi paketle (testleri atlayarak süreci hızlandırabilirsin)
RUN mvn clean package -DskipTests


# --- AŞAMA 2: Çalıştırma İmajını Oluşturma ---
# Sadece Java Runtime (JRE) içeren daha küçük bir imaj kullan.
FROM eclipse-temurin:17-jre-alpine

# Çalışma dizini
WORKDIR /app

# Önceki aşamada derlenen JAR dosyasını bu yeni imaja kopyala
# JAR adının doğru olduğundan emin ol (pom.xml'den kontrol et).
COPY --from=builder /app/target/*.jar app.jar

# Spring Boot uygulamasının çalışacağı port (docker-compose'da 9090 olarak maplendi)
EXPOSE 9090

# Uygulamayı başlat
ENTRYPOINT ["java","-jar","/app/app.jar"]