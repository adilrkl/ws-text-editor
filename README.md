# 🚀 Code Share - Real-time Collaborative Code Editor

A modern, real-time collaborative code editor built with Spring Boot and Y.js that allows multiple developers to edit code simultaneously with live cursor tracking and instant synchronization.

## ✨ Features

- **🔄 Real-time Collaboration**: Multiple users can edit the same document simultaneously
- **👥 Live Cursor Tracking**: See other users' cursors and selections in real-time
- **🎨 Syntax Highlighting**: Support for multiple programming languages (JavaScript, Java, Python, HTML, CSS, JSON, Markdown)
- **📱 Mobile Responsive**: Optimized for both desktop and mobile devices
- **🔐 User Authentication**: Secure user registration and login system
- **📚 Document Management**: Create and manage multiple code documents
- **⚡ Instant Sync**: Changes are synchronized instantly using CRDT (Conflict-free Replicated Data Types)
- **🎯 Modern UI**: Clean, intuitive interface built with Bootstrap 5
- **📋 Copy to Clipboard**: Easy code sharing functionality
- **🌐 Cross-platform**: Works on all modern browsers

## 🛠️ Tech Stack

### Backend
- **Spring Boot 3** - Application framework
- **Spring Security** - Authentication and authorization
- **Spring WebSocket** - Real-time communication
- **JPA/Hibernate** - Database ORM
- **PostgreSQL** - Production database
- **Maven** - Dependency management

### Frontend
- **Y.js** - CRDT for real-time collaboration
- **Monaco Editor** - Code editor (VS Code editor)
- **Bootstrap 5** - Responsive UI framework
- **Thymeleaf** - Server-side templating
- **WebSocket** - Real-time communication

### Infrastructure
- **Docker** - Containerization
- **Docker Compose** - Multi-container orchestration

## 🚀 Quick Start

### Prerequisites
- Java 17 or later
- Maven 3.6+
- Docker & Docker Compose (optional)

### Option 1: Run with Docker (Recommended)

1. **Clone the repository**
```bash
git clone https://github.com/yourusername/wsTextEditor.git
cd wsTextEditor
```

2. **Start with Docker Compose**
```bash
docker-compose up -d
```

3. **Access the application**
- Open your browser and navigate to: `http://localhost:8080`

### Option 2: Run Locally

1. **Clone the repository**
```bash
git clone https://github.com/yourusername/wsTextEditor.git
cd wsTextEditor
```

2. **Build the project**
```bash
./mvnw clean package
```

3. **Run the application**
```bash
./mvnw spring-boot:run
```

4. **Start Y.js WebSocket Server**
```bash
cd y-websocket
npm install
npm start
```

5. **Access the application**
- Open your browser and navigate to: `http://localhost:8080`

## 📖 How to Use

### 1. **Register/Login**
- Create a new account or log in with existing credentials
- Navigate to the dashboard after successful authentication

### 2. **Create a Code Document**
- Click "✨ Create New Code Share" on the dashboard
- You'll be redirected to the collaborative editor

### 3. **Start Collaborating**
- Share the document URL with team members
- See real-time changes and cursor positions
- Choose programming language from the dropdown
- Copy code using the "📋 Copy Code" button

### 4. **Real-time Features**
- **Live Editing**: Type and see changes instantly
- **Cursor Tracking**: View other users' cursor positions
- **Language Support**: Switch between different programming languages
- **Auto-save**: Changes are automatically saved

## 🏗️ Project Structure

```
wsTextEditor/
├── src/main/java/com/example/wsTextEditor/
│   ├── config/          # Configuration classes
│   ├── controller/      # REST and WebSocket controllers
│   ├── crdt/           # CRDT implementation
│   ├── model/          # JPA entities
│   ├── repository/     # Data repositories
│   └── WsTextEditorApplication.java
├── src/main/resources/
│   ├── static/css/     # CSS files
│   ├── templates/      # Thymeleaf templates
│   └── application.properties
├── y-websocket/        # Y.js WebSocket server
├── docker-compose.yml  # Docker configuration
├── Dockerfile         # Application container
└── pom.xml           # Maven dependencies
```

## ⚙️ Configuration

### Database Configuration
The application uses PostgreSQL as the database. Configuration is done via environment variables:

```properties
# PostgreSQL (via environment variables)
spring.datasource.url=${JDBC_DATABASE_URL:jdbc:postgresql://localhost:5432/postgres}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
```

**Environment Variables:**
- `SPRING_DATASOURCE_USERNAME` - Database username
- `SPRING_DATASOURCE_PASSWORD` - Database password  
- `SPRING_DATASOURCE_HOST` - Database host (default: localhost)
- `SPRING_DATASOURCE_PORT` - Database port (default: 5432)
- `SPRING_DATASOURCE_DB` - Database name (default: postgres)

### WebSocket Configuration
The Y.js WebSocket server runs on port `1234` by default. Update the URL in templates if needed.

## 🐳 Docker Configuration

The project includes Docker support with the following services:

- **Web Application**: Spring Boot app (Port 8080)
- **Y.js WebSocket**: Real-time sync server (Port 1234)
- **Database**: PostgreSQL (Port 5432)

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/amazing-feature`
3. Commit your changes: `git commit -m 'Add amazing feature'`
4. Push to the branch: `git push origin feature/amazing-feature`
5. Open a Pull Request

## 📝 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/` | Home page (redirects to login) |
| GET | `/login` | Login page |
| POST | `/login` | Process login |
| GET | `/register` | Registration page |
| POST | `/register` | Process registration |
| GET | `/dashboard` | User dashboard |
| POST | `/documents/create` | Create new document |
| GET | `/editor/{id}` | Collaborative editor |

## 🔧 Troubleshooting

### Common Issues

1. **WebSocket Connection Failed**
   - Ensure Y.js WebSocket server is running on port 1234
   - Check firewall settings

2. **Database Connection Error**
   - Verify PostgreSQL server is running
   - Check environment variables for database credentials
   - Ensure database exists and user has proper permissions

3. **Mobile Zoom Issues**
   - The app includes mobile optimizations to prevent unwanted zooming
   - Use latest browser versions for best experience

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- [Y.js](https://github.com/yjs/yjs) - Real-time collaboration framework
- [Monaco Editor](https://microsoft.github.io/monaco-editor/) - Code editor
- [Spring Boot](https://spring.io/projects/spring-boot) - Application framework
- [Bootstrap](https://getbootstrap.com/) - UI framework

## 📞 Support

If you have any questions or issues, please:
1. Check existing [Issues](https://github.com/yourusername/wsTextEditor/issues)
2. Create a new issue with detailed description
3. Contact the maintainers

---

⭐ **Star this repository if you find it helpful!** 