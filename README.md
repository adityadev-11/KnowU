# KnowU – Student Management System

A full-stack student management system with a modern dark-themed UI and a Java Spring Boot backend.

---

## 📁 Project Structure

```
KnowU/
├── frontend/
│   ├── index.html       ← Main UI (single-page app)
│   ├── styles.css       ← All styles (deep navy/indigo theme)
│   └── app.js           ← Navigation, data rendering, interactions
│
└── backend/
    ├── pom.xml          ← Maven dependencies (Spring Boot 3.2, Java 17)
    └── src/main/
        ├── java/com/knowu/
        │   ├── KnowUApplication.java
        │   ├── config/
        │   │   ├── SecurityConfig.java        ← CORS + Spring Security
        │   │   ├── DataSeeder.java            ← Seeds sample data on startup
        │   │   └── GlobalExceptionHandler.java
        │   ├── controller/
        │   │   ├── StudentController.java     ← /api/v1/students
        │   │   ├── AttendanceController.java  ← /api/v1/attendance
        │   │   ├── ExamController.java        ← /api/v1/exams
        │   │   ├── ClassScheduleController.java ← /api/v1/schedule
        │   │   └── DashboardController.java   ← /api/v1/dashboard/stats
        │   ├── service/
        │   │   ├── StudentService.java
        │   │   ├── AttendanceService.java
        │   │   └── ExamService.java
        │   ├── repository/
        │   │   ├── StudentRepository.java
        │   │   ├── AttendanceRepository.java
        │   │   ├── ExamRepository.java
        │   │   ├── SchoolClassRepository.java
        │   │   └── ClassScheduleRepository.java
        │   ├── model/
        │   │   ├── Student.java
        │   │   ├── Attendance.java
        │   │   ├── Exam.java
        │   │   ├── Fee.java
        │   │   ├── ClassSchedule.java
        │   │   ├── SchoolClass.java
        │   │   └── Result.java
        │   └── dto/
        │       ├── StudentDTO.java
        │       ├── AttendanceDTO.java
        │       └── ExamDTO.java
        └── resources/
            └── application.properties
```

---

## 🚀 Running the Frontend

1. Open `frontend/index.html` directly in any browser (no build step needed).
2. Or use VS Code Live Server for hot reload.

---

## ⚙️ Running the Backend

**Prerequisites:** Java 17+, Maven 3.8+

```bash
cd backend
mvn spring-boot:run
```

- API base URL: `http://localhost:8080`
- H2 Console (dev DB): `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:knowudb`
  - Username: `sa` / Password: *(empty)*

---

## 🌐 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/dashboard/stats` | Dashboard metrics |
| GET/POST | `/api/v1/students` | List / Create students |
| GET/PUT/DELETE | `/api/v1/students/{id}` | Get / Update / Delete student |
| GET | `/api/v1/students/stats` | Student count stats |
| POST | `/api/v1/attendance/bulk` | Mark attendance in bulk |
| GET | `/api/v1/attendance` | Get attendance by class+date |
| GET | `/api/v1/attendance/weekly-overview` | Weekly attendance % |
| GET/POST | `/api/v1/exams` | List / Create exams |
| GET | `/api/v1/exams/upcoming` | Upcoming exams |
| GET/POST | `/api/v1/schedule` | Class timetable slots |

---

## 🛠️ Tech Stack

**Frontend:** HTML5, CSS3 (CSS Variables), Vanilla JS, Font Awesome, Google Fonts (Syne + DM Sans)

**Backend:** Java 17, Spring Boot 3.2, Spring Data JPA, Spring Security, H2 (dev) / PostgreSQL (prod), Lombok, Maven

---

## 🔄 Switch to PostgreSQL (Production)

In `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/knowudb
spring.datasource.username=your_user
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
```

And in `pom.xml`, uncomment the PostgreSQL dependency and remove H2.
