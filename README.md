# Community Complaint Management System (CCMS)

A full-stack, enterprise-grade Community Complaint Management System designed to empower residents and simplify administrative operations. The system features secure user/admin dashboards, real-time WebSocket communication, automated notifications, event registration with mock payment handling, and an **AI-powered RAG (Retrieval-Augmented Generation) Chatbot** to answer community-related queries using local LLMs.

---

## 🚀 Features

### 👤 Resident (User) Portal
- **Dashboard**: A personalized hub showcasing active complaints, recent announcements, upcoming events, and notification alerts.
- **Complaint Management**: Register new complaints, edit active complaints, track resolution progress in real-time, or delete complaints.
- **AI Chatbot**: Chat with an intelligent assistant powered by **Spring AI & Ollama** using a localized knowledge base (`CCM.pdf`).
- **Event Registration**: View upcoming events, register, and complete mock fee payments.
- **Live Admin Chat**: Directly communicate with regional admins in real-time.
- **Notifications**: Receive instant alerts on complaint status updates or new admin broadcasts.

### 🛡️ Admin Portal
- **Dashboard**: Track system metrics, view complaints by status, and coordinate region-based duties.
- **Complaint Handling**: Update complaint statuses (Pending, In Progress, Resolved) with automatic notifications sent to residents.
- **Event Publishing**: Create and schedule community events with title, registration fee, dates, and flyer image uploads.
- **Announcements**: Broadcast community-wide announcements.
- **Real-Time Inbox**: Send and receive instant WebSocket messages with residents assigned to specific regions (e.g., Central, East, West).

---

## 🛠️ Tech Stack

### Backend
- **Framework**: Spring Boot 3.5.7 (Java 21)
- **Security**: Spring Security (Role-based access, CORS, and full CSRF protection with Cookie-based tokens)
- **Data Access**: Spring Data JPA & Hibernate
- **Database**: MySQL 8.x
- **AI/LLM**: Spring AI (Ollama integration, Spring AI Vector Store)
- **WebSockets**: Spring Messaging (STOMP over SockJS)
- **Mailing**: Spring Mail (SMTP integration)
- **Document Processing**: Apache PDFBox

### Frontend
- **Structure & Styling**: Semantic HTML5 & Responsive CSS3 (Glassmorphism design, clean UI)
- **Scripting**: Vanilla JavaScript (ES6+)
- **APIs & State**: Native Fetch API with custom CSRF Token Manager
- **WebSockets**: SockJS & STOMP client

---

## 📁 Directory Structure

```text
CommunityCompaintManagement/
├── backend/
│   ├── src/main/java/com/CommunityCompaintManagement/CommunityCompaintManagement/
│   │   ├── configuration/     # Security, WebSocket, and CORS Configurations
│   │   ├── controller/        # REST Controllers (User, Admin, Chat, RAG)
│   │   ├── model/             # JPA Entities and Data Objects
│   │   ├── repository/        # Spring Data Repositories
│   │   └── service/           # Core Business Logic & AI RAG Integration
│   └── src/main/resources/
│       ├── application.properties # Application Settings (DB, Mail, Ollama)
│       └── data/                  # AI Knowledge Base PDF Documents
├── frontend/
│   ├── login.html             # User login
│   ├── register.html          # User registration
│   ├── dashboard.html         # User main dashboard & AI Chat
│   ├── admin-login.html       # Admin login
│   ├── admin-dashboard.html   # Admin control panel
│   ├── payment.html           # Event registration payment gateway mockup
│   └── shared.js              # Shared CSRF and fetch API utilities
└── README.md
```

---

## ⚙️ Configuration & Setup

### Prerequisites
1. **Java Development Kit (JDK)**: Version 21 or higher.
2. **Maven**: 3.8+ for building the backend.
3. **MySQL Server**: Running on port `3306`.
4. **Ollama**: Running locally for AI RAG capabilities.

### 1. Database Setup
Create a MySQL database named `communitycomplaintmanager`:
```sql
CREATE DATABASE communitycomplaintmanager;
```

Update your database credentials in `backend/src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/communitycomplaintmanager?useSSL=false&serverTimezone=UTC
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### 2. Mail Configuration (Optional)
If you wish to use the email notification feature, configure your SMTP server details in `application.properties`:
```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=YOUR_EMAIL@gmail.com
spring.mail.password=YOUR_APP_PASSWORD
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

### 3. AI / LLM Setup (Ollama)
1. Download and install [Ollama](https://ollama.com/).
2. Pull the default Llama 3.2 model:
   ```bash
   ollama pull llama3.2:latest
   ```
3. Keep Ollama running in the background. The application uses this model for chatting and embedding generations.
4. Place your community guidelines document (PDF) under `backend/src/main/resources/data/CCM.pdf`. The backend will parse and embed this document automatically for RAG query retrieval.

---

## 🚀 Running the Application

### Start the Backend
Navigate to the `backend` directory and run:
```bash
mvn spring-boot:run
```
The backend server will start running on **`http://localhost:8080/CCM`**.

### Start the Frontend
Since the frontend consists of static HTML files, you can host them using any local server.
1. Recommended: Use the **Live Server** extension in Visual Studio Code.
2. Ensure it runs on **`http://127.0.0.1:5500`** or **`http://localhost:5500`** (pre-configured in the backend's CORS policy).
3. Open `login.html` or `admin-login.html` to begin.

---

## 🔌 API Endpoints Summary

### User Authentication
- `POST /CCM/registeruser` - Registers a new user.
- `POST /CCM/login` - Authenticates a resident.
- `GET /CCM/csrfToken` - Fetches a CSRF token (required for secure POST/PUT/DELETE requests).

### Complaint Management
- `POST /CCM/registercomplaint/{username}` - Files a new complaint.
- `GET /CCM/viewcomplaints/{username}` - Returns complaints filed by a user.
- `PUT /CCM/editcomplaint?complaintId={id}` - Modifies a complaint description.
- `DELETE /CCM/deletecomplaint/{id}` - Deletes a complaint.

### Admin Operations
- `POST /CCM/admin/login` - Authenticates an admin.
- `GET /CCM/admin/viewcomplaints` - Retrieves all complaints across the system.
- `PUT /CCM/admin/editcomplaintstatus?complaintId={id}` - Updates status (Pending/In Progress/Resolved).
- `POST /CCM/admin/addnotification` - Broadcasts a custom notification.
- `POST /CCM/admin/addevent` - Publishes a community event (supports title, cost, dates, and flyer image).

### AI & Chatbot (RAG)
- `POST /v2/rag/chat/{username}` - Submits a query to the AI assistant using local PDF context.

### WebSockets (Real-time Messaging)
- **STOMP Connection Endpoint**: `/ws`
- **User outbound destination**: `/app/sendusermessage`
- **Admin outbound destination**: `/app/sendadminmessage`
- **User inbound subscription**: `/user/queue/adminmessage`
- **Admin inbound subscription**: `/user/queue/usermessage`

---

## 🔒 Security & CORS
- **Cross-Origin Resource Sharing (CORS)** is explicitly configured on the backend to allow credentials and requests only from trusted frontend origins (`http://127.0.0.1:5500` and `http://localhost:5500`).
- **Cross-Site Request Forgery (CSRF)** protection is enabled using Spring Security's `CookieCsrfTokenRepository` with `HttpOnly` set to `false`. The frontend automatically handles retrieval of this token via `shared.js` to authorize state-changing HTTP requests.
