# 📏 Quantity Measurement App - Backend

This is the backend service for the Quantity Measurement App built using Spring Boot. It handles unit operations, authentication (JWT + Google OAuth2), and user-specific history.

---

## 🚀 Features

* 🔄 Quantity operations (Add, Subtract, Multiply, Divide, Compare, Convert)
* 🔐 JWT Authentication
* 🔑 Google OAuth2 Login
* 👤 User Registration (Signup)
* 📊 User-specific history tracking
* 🛡️ Secure REST APIs

---

## 🛠️ Tech Stack

* Spring Boot
* Spring Security (JWT + OAuth2)
* Maven
* MySQL / H2 Database

---

## 📦 Installation

```bash
git clone <your-backend-repo-url>
cd backend
```

---

## ▶️ Run the Application

```bash
mvn spring-boot:run
```

Server runs on:

```
http://localhost:8080
```

---

## 🔗 API Endpoints

### 🔐 Authentication APIs

| Method | Endpoint    | Description             |
| ------ | ----------- | ----------------------- |
| POST   | /auth/login | Login and get JWT token |

---

### 👤 User APIs

| Method | Endpoint                       | Description       |
| ------ | ------------------------------ | ----------------- |
| POST   | /api/v1/quantities/auth/signup | Register new user |

---

### 🔄 Quantity Operations

| Method | Endpoint                   | Description                    |
| ------ | -------------------------- | ------------------------------ |
| POST   | /api/v1/quantities/perform | Perform any quantity operation |

📌 Supported Operations:

* ADD
* SUBTRACT
* MULTIPLY
* DIVIDE
* COMPARE
* CONVERT

---

### 📊 History APIs (Authenticated)

| Method | Endpoint                                  | Description           |
| ------ | ----------------------------------------- | --------------------- |
| GET    | /api/v1/quantities/history                | Get full user history |
| GET    | /api/v1/quantities/history/latest?limit=5 | Get latest N records  |

---

### ✅ Health Check

| Method | Endpoint           | Description                |
| ------ | ------------------ | -------------------------- |
| GET    | /api/v1/quantities | Check if server is running |

---

## 🔐 Authentication Flow

* User logs in via `/auth/login`
* Receives JWT token
* Token must be sent in headers:

```
Authorization: Bearer <token>
```

---

## 🔧 Configuration

Update `application.properties`:

```properties
spring.datasource.url=YOUR_DB_URL
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD

jwt.secret=YOUR_SECRET_KEY

spring.security.oauth2.client.registration.google.client-id=YOUR_GOOGLE_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_GOOGLE_CLIENT_SECRET

# Frontend redirect (OAuth)
app.oauth2.frontend-redirect=http://localhost:5173/auth/callback
app.oauth2.frontend-login-url=http://localhost:5173/login
```

---

## 📂 Project Structure

```
src/main/java/com/app/quantitymeasurement/
|
├── auth/
├── controller/
├── dto/
├── entity/
├── model/
├── repository/
├── security/
          └── config/
          ├── jwt/
          ├── oauth/
          ├── service/
├── service/
          └── impl/
├── unit/

```

---

## 🔐 Security

* JWT-based authentication
* OAuth2 login via Google
* Password encryption using PasswordEncoder
* Protected endpoints for history

---

## 💡 Notes

* `/auth/callback` is handled to redirect to frontend (React app)
* Google OAuth redirect URI must be:

```
http://localhost:8080/login/oauth2/code/google
```

---

## 💡 Future Improvements

* Add pagination for history
* Improve exception handling
* Add unit & integration tests
* Deploy to cloud (AWS / Render)

---

## 📄 License

MIT License
