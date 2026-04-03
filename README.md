# 📏 Quantity Measurement App - Backend

This is the backend service for the Quantity Measurement App built using Spring Boot. It handles unit conversion logic, authentication, and user history.

---

## 🚀 Features

* 🔄 Unit conversion APIs
* 🔐 JWT Authentication
* 🔑 Google OAuth2 login
* 📊 User history tracking
* 🛡️ Secure REST APIs

---

## 🛠️ Tech Stack

* Spring Boot
* Spring Security (JWT + OAuth2)
* MySQL / H2 Database
* Maven

---

## 📦 Installation

```bash
git clone <your-backend-repo-url>
cd backend
```

---

## ▶️ Run the App

```bash
mvn spring-boot:run
```

Server will run on:

```
http://localhost:8080
```

---

## 🔗 API Endpoints

| Method | Endpoint     | Description        |
| ------ | ------------ | ------------------ |
| POST   | /auth/signup | Register user      |
| POST   | /auth/login  | Login user         |
| GET    | /convert     | Perform conversion |
| GET    | /history     | Get user history   |

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
```

---

## 📂 Project Structure

```
src/main/java/
│
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
* Protected APIs for user history

---

## 💡 Future Improvements

* Add caching
* Improve error handling
* Add unit tests
* Deploy on cloud (AWS / Render)

---

## 📄 License

MIT License
