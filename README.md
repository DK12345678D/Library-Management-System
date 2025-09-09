# 📚 Library Management System

This project is a simple Library Management System built as a take-home assignment.  
It includes both **backend (API)** and **frontend (web app)** implementations.

---

## ✨ Features

### Backend
- ➕ Add new books (title, author, ISBN, availability status)  
- 📖 Fetch all available books  
- 📥 Borrow a book  
- 📤 Return a book  
- 🔒 JWT authentication (bonus)  
- 👤 User roles: **Admin** and **Member** (bonus)  

### Frontend
- 📚 View all available books  
- 🔎 Search books by title/author  
- 📥 Borrow / 📤 return books  
- 💻📱 Responsive design (mobile + desktop)  
- 🔑 Basic authentication (login/logout)  

 Link- https://github.com/DK12345678D/Library-Management-System-Frontend
---

## 🛠 Tech Stack
- **Backend:** Node.js + Express, PostgreSQL (or MySQL/MongoDB)  
- **Frontend:** React (with Vite or CRA)  
- **Auth:** JWT  
- **Docs:** Postman Collection / Swagger  

---

## 📂 Project Structure
```
library/
├── src
│ ├── main
│ │ ├── java
│ │ │ └── com
│ │ │ └── example
│ │ │ └── library
│ │ │ ├── LibraryApplication.java
│ │ │ │
│ │ │ ├── config
│ │ │ │ └── DataInitializer.java
│ │ │ │
│ │ │ ├── controller
│ │ │ │ ├── AuthController.java
│ │ │ │ └── BookController.java
│ │ │ │
│ │ │ ├── dto
│ │ │ │ ├── AuthRequest.java
│ │ │ │ ├── AuthResponse.java
│ │ │ │ └── BookDto.java
│ │ │ │
│ │ │ ├── model
│ │ │ │ ├── Book.java
│ │ │ │ ├── Role.java
│ │ │ │ └── User.java
│ │ │ │
│ │ │ ├── repository
│ │ │ │ ├── BookRepository.java
│ │ │ │ └── UserRepository.java
│ │ │ │
│ │ │ ├── repository
│ │ │ │ ├── BookService.java
│ │ │ │ └── UserService.java
├── pom.xml
├── README.md
```
---

## 🌐 Backend API Endpoints

### Auth
- `POST /api/auth/register` – Register user (default role: member)  
- `POST /api/auth/login` – Login and get JWT  

### Books
- `GET /api/books` – Fetch all books (`?available=true` filter supported)  
- `POST /api/books` – Add book (Admin only)  
- `POST /api/books/:id/borrow` – Borrow book (Member)  
- `POST /api/books/:id/return` – Return book  

### API docs
- http://localhost:8080/swagger-ui.html

```
---

## 🚀 Usage Flow
1. Register or login as Admin/Member  
2. Admin adds books  
3. Member can view, search, borrow, and return books  
4. UI adapts to role and book availability  

---

## 🎁 Bonus Features
- 🔒 **JWT-based authentication with role checks**  
- 📱 **Responsive design with search functionality**  
- 🎯 **State management (Context API / Redux optional)**  

---

## 📝 Notes
- Passwords are hashed with bcrypt  
- JWT stored client-side and validated on every request  
- Database queries are parameterized for security

 
