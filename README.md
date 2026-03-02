# 🤖 AI Chatbot Web Application

An intelligent AI-powered Chatbot Web Application built using **Java Spring Boot**, **MySQL**, and **Groq API (LLM Integration)**.  
The chatbot can understand user queries and generate AI-based responses in real-time.

This project demonstrates backend API integration, database management, and real-world AI implementation.

---

## 🚀 Features

- 💬 Real-time AI Chat Interface
- 🔗 Groq API Integration (LLM-based responses)
- 🗄️ Chat History Storage using MySQL
- 🔐 Secure REST API Architecture
- ⚡ Fast response handling
- 📱 Responsive UI
- 🧠 Context-aware conversation handling

---

## 🛠️ Tech Stack

### Backend
- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- REST APIs

### AI Integration
- Groq API (Free LLM access)

### Database
- MySQL

### Frontend
- HTML5
- CSS3
- JavaScript (Fetch API / AJAX)

### Tools
- Git & GitHub
- IntelliJ IDEA
- Postman
- Maven

---

## 🏗️ Architecture

User → Frontend → Spring Boot Controller → Service Layer → Groq API  
                                                     ↓  
                                               MySQL Database

The application follows **MVC Architecture** and clean layered structure.

---

## 📂 Project Structure

```
ai-chatbot/
│
├── src/main/java/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── model/
│   └── config/
│
├── src/main/resources/
│   ├── static/
│   ├── templates/
│   └── application.properties
│
└── pom.xml
```

---

## ⚙️ Setup & Installation

### 1️⃣ Clone Repository

```
git clone https://github.com/yourusername/ai-chatbot.git
```

### 2️⃣ Configure MySQL

Update `application.properties`:

```
spring.datasource.url=jdbc:mysql://localhost:3306/chatbot_db
spring.datasource.username=yourusername
spring.datasource.password=yourpassword
```

### 3️⃣ Add Groq API Key

```
groq.api.key=YOUR_API_KEY
```

### 4️⃣ Run Application

```
mvn spring-boot:run
```

Open:
```
http://localhost:8080
-----

---

## 🔥 Key Learning Outcomes

- Integrated external AI API in Spring Boot
- Built RESTful backend services
- Implemented database persistence
- Managed API keys securely
- Understood LLM request-response cycle
- Applied MVC architecture

---

## 🌟 Future Improvements

- JWT Authentication
- Multi-user login system
- Chat history dashboard
- Deploy on AWS / Render
- Streaming AI responses
- Docker containerization

---

## 📬 Contact

👤 Saidurgarao Chintalapudi  
📧 saidurgaraochintalapudi@gmail.com 
🔗 LinkedIn: your-linkedin-https://www.linkedin.com/in/saidurgarao-chintalapudi-354537365/

---

⭐ If you found this project interesting, please give it a star!
