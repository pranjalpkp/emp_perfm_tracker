# Employee Performance Tracker API

A Spring Boot REST API to manage employees, submit performance reviews, and generate review-cycle summaries.

## Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA (Hibernate)
- PostgreSQL
- Maven (wrapper included)
- Lombok

## Prerequisites

- JDK 17
- PostgreSQL running locally

## Configuration

Create a PostgreSQL database:

```sql
CREATE DATABASE employee_db;
```

Set environment variables before running the app:

```bash
# Linux/macOS
export DB_NAME=employee_db
export DB_USERNAME=postgres
export DB_PASSWORD=postgres
```

```powershell
# Windows PowerShell
$env:DB_NAME = "employee_db"
$env:DB_USERNAME = "postgres"
$env:DB_PASSWORD = "postgres"
```

Default server port is `8080`.

## Run and Test

From the project root:

```bash
# Linux/macOS
./mvnw clean package
./mvnw test
./mvnw spring-boot:run
```

```powershell
# Windows
.\mvnw.cmd clean package
.\mvnw.cmd test
.\mvnw.cmd spring-boot:run
```

Application base URL:

```text
http://localhost:8080
```

## API Endpoints

### 1) Create Employee

- Method: `POST`
- Path: `/employees`
- Success: `201 Created`

Request body:

```json
{
  "empName": "Sangharsh Raj",
  "deptId": 2,
  "roleIds": [1],
  "joiningDate": "2024-08-03"
}
```

### 2) Submit Performance Review

- Method: `POST`
- Path: `/reviews`
- Success: `201 Created`

Request body:

```json
{
  "empId": 6,
  "reviewCycleId": 1,
  "rating": 4.4,
  "reviewerNotes": "Great work, well done! Keep up the good work!"
}
```

### 3) Get Reviews for an Employee

- Method: `GET`
- Path: `/employees/{id}/reviews`
- Success: `200 OK`
- Empty result: `204 No Content`

Example:

```http
GET /employees/2/reviews HTTP/1.1
Host: localhost:8080
```

### 4) Get Review Cycle Summary

- Method: `GET`
- Path: `/cycles/{id}/summary`
- Success: `200 OK`

Example response:

```json
{
  "cycleId": 1,
  "cycleName": "Q1 2025",
  "averageRating": 3.98,
  "topPerformer": "Kumar",
  "completedGoals": 0,
  "missedGoals": 0
}
```

### 5) Filter Employees by Department and Minimum Rating

- Method: `GET`
- Path: `/employees`
- Required query params:
  - `department` (string)
  - `minRating` (number)
- Success: `200 OK`

Example:

```http
GET /employees?department=Engineering&minRating=1 HTTP/1.1
Host: localhost:8080
```

## Notes

- This project reads database values from environment variables (`DB_NAME`, `DB_USERNAME`, `DB_PASSWORD`).
- If startup fails, verify PostgreSQL is running, the database exists, and environment variables are set in the same shell where you run Maven.