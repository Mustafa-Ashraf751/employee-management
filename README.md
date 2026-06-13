# Employee Management System

A full-stack Employee Management System built using Spring Boot and Angular.

## Features

* Department Management (CRUD)
* Employee Management (CRUD)
* Project Management (CRUD)
* Employee-Project Assignment
* Department Assignment
* Input Validation
* Loading Indicators
* Responsive UI
* Swagger API Documentation

---

## Tech Stack

### Backend

* Java 21
* Spring Boot
* Spring Data JPA
* PostgreSQL
* MapStruct
* Lombok
* Swagger / OpenAPI

### Frontend

* Angular
* TypeScript
* Reactive Forms
* Angular Router

---

## Getting Started

You can choose to run this project either via **Docker (Recommended)** or **Manually**.

### Option 1: Running with Docker (Recommended)

This method spins up the frontend, backend, and a PostgreSQL database pre-configured with dummy data—all with a single command.

#### Prerequisites for Docker
* Docker
* Docker Compose

#### Steps
1. Make sure Docker is running on your machine.
2. In the root directory (where `docker-compose.yml` is located), run:

```bash
docker compose up --build
```

3. Access the application:
   * **Frontend:** `http://localhost:4200`
   * **Backend API / Swagger UI:** `http://localhost:8080/swagger-ui/index.html`

*Note: The database volume is persisted. To start completely fresh with the dummy data, you can run `docker compose down -v` before starting it up again.*

---

### Option 2: Running Manually

If you prefer to run the application without Docker, follow the steps below.

#### Prerequisites for Manual Setup

Before running the application, make sure the following are installed:

* Java 21
* Maven
* Node.js
* Angular CLI
* PostgreSQL

#### Database Configuration

Create a PostgreSQL database and update the following properties in:

```properties
src/main/resources/application.properties
```

```properties
spring.datasource.url=YOUR_DATABASE_URL
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
server.port=8080
spring.jpa.hibernate.ddl-auto=update
```

#### Running the Backend

Navigate to the backend directory:

```bash
cd backend
```

Run the application:

```bash
mvn spring-boot:run
```

Backend will start on `http://localhost:8080`.

#### Running the Frontend

Navigate to the frontend directory:

```bash
cd frontend
```

Install dependencies:

```bash
npm install
```

Run Angular:

```bash
npm start
```

Frontend will start on:

```text
http://localhost:4200
```

---

## API Documentation

Swagger UI is available at:

```text
http://localhost:8080/swagger-ui/index.html
```

---

## Database Documentation

The project includes:

* ERD Diagram
* DATABASE_SCHEMA.md

for database design documentation.

---

## Default Ports

| Application | Port |
| ----------- | ---- |
| Backend     | 8080 |
| Frontend    | 4200 |

---

## Project Structure

```text
backend/
frontend/
docs/
 ├── ERD.png
 └── DATABASE_SCHEMA.md
database/
 ├── init.sql
 └── Dummy Data.sql
```
