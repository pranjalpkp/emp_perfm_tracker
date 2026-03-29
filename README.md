# Employee Performance Tracker API

A Spring Boot REST API to manage employees, submit performance reviews, and generate review-cycle summaries.

## Prerequisites

- JDK 17
- PostgreSQL running locally

## Configuration

Create a PostgreSQL database:

```sql
CREATE DATABASE employee_db;
```

Set environment variables before running the app:


Default server port is `8080`.

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
GET /employees/2/reviews 
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
GET /employees?department=Engineering&minRating=1
```

## Notes

- This project reads database values from environment variables (`DB_NAME`, `DB_USERNAME`, `DB_PASSWORD`).
- If startup fails, verify PostgreSQL is running, the database exists, and environment variables are set in the same shell where you run Maven.