# Student Course Application - API Documentation

## Base URL
```
http://localhost:8080
```

## Content Type
All requests and responses use `application/json` content type.

---
## Course API Endpoints

### 1. Create Course
**Endpoint:** `POST /api/courses/create`

**Description:** Creates a new course.

**Request Body:**
```json
{
  "title": "Java Programming",
  "credits": 3
}
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "title": "Java Programming",
  "credits": 3,
  "students": []
}
```

---

### 2. Get All Courses
**Endpoint:** `GET /api/courses/get`

**Description:** Retrieves all courses in the system.

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "title": "Java Programming",
    "credits": 3,
    "students": [
      {
        "id": 1,
        "name": "John Doe",
        "department": "Computer Science",
        "email": "john.doe@example.com"
      }
    ]
  },
  {
    "id": 2,
    "title": "Database Systems",
    "credits": 4,
    "students": []
  }
]
```

---

### 3. Get Course by ID
**Endpoint:** `GET /api/courses/{id}`

**Description:** Retrieves a specific course by its ID.

**Path Parameters:**
- `id` (Long) - Course ID

**Response:** `200 OK`
```json
{
  "id": 1,
  "title": "Java Programming",
  "credits": 3,
  "students": [
    {
      "id": 1,
      "name": "John Doe",
      "department": "Computer Science",
      "email": "john.doe@example.com"
    }
  ]
}
```

**Error Responses:**
- `404 Not Found` - Course not found

---

### 4. Update Course
**Endpoint:** `PUT /api/courses/{id}`

**Description:** Updates an existing course.

**Path Parameters:**
- `id` (Long) - Course ID

**Request Body:**
```json
{
  "title": "Advanced Java Programming",
  "credits": 4
}
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "title": "Advanced Java Programming",
  "credits": 4,
  "students": []
}
```

**Error Responses:**
- `404 Not Found` - Course not found

---

### 5. Get Course Enrollment Count
**Endpoint:** `GET /api/courses/{id}/enrollment-count`

**Description:** Returns the number of students enrolled in a course.

**Path Parameters:**
- `id` (Long) - Course ID

**Response:** `200 OK`
```json
5
```

**Error Responses:**
- `404 Not Found` - Course not found

---

### 6. Get Popular Courses
**Endpoint:** `GET /api/courses/popular`

**Description:** Returns courses ordered by enrollment count (most popular first).

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "title": "Java Programming",
    "credits": 3
  },
  {
    "id": 2,
    "title": "Database Systems",
    "credits": 4
  }
]
```

---

### 7. Get Course's Registered Students
**Endpoint:** `GET /api/courses/{id}/students`

**Description:** Retrieves all students registered for a specific course.

**Path Parameters:**
- `id` (Long) - Course ID

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "department": "Computer Science",
    "email": "john.doe@example.com",
    "courseIds": [1, 2]
  }
]
```

**Error Responses:**
- `404 Not Found` - Course not found

---

### 8. Delete Course
**Endpoint:** `DELETE /api/courses/{id}`

**Description:** Deletes a course by its ID.

**Path Parameters:**
- `id` (Long) - Course ID

**Response:** `200 OK`
```json
"Course deleted successfully"
```

**Error Responses:**
- `404 Not Found` - Course not found

---

## Error Response Format

All error responses follow this format:

```json
{
  "timestamp": "2024-01-15T10:30:00.000+00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Student not found",
  "path": "/api/students/999"
}
```

## Student API Endpoints

### 1. Create Student
**Endpoint:** `POST /api/students/create`

**Description:** Creates a new student and optionally enrolls them in courses.

**Request Body:**
```json
{
  "name": "John Doe",
  "department": "Computer Science",
  "email": "john.doe@example.com",
  "courseIds": [1, 2, 3]
}
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "name": "John Doe",
  "department": "Computer Science",
  "email": "john.doe@example.com",
  "courseIds": [1, 2, 3]
}
```

**Error Responses:**
- `404 Not Found` - If any course ID doesn't exist
- `400 Bad Request` - Invalid request data

---

### 2. Get Student by ID
**Endpoint:** `GET /api/students/{id}`

**Description:** Retrieves a specific student by their ID.

**Path Parameters:**
- `id` (Long) - Student ID

**Response:** `200 OK`
```json
{
  "id": 1,
  "name": "John Doe",
  "department": "Computer Science",
  "email": "john.doe@example.com",
  "courseIds": [1, 2, 3]
}
```

**Error Responses:**
- `404 Not Found` - Student not found

---

### 3. Get All Students
**Endpoint:** `GET /api/students/get`

**Description:** Retrieves all students in the system.

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "department": "Computer Science",
    "email": "john.doe@example.com",
    "courses": [
      {
        "id": 1,
        "title": "Java Programming",
        "credits": 3
      }
    ]
  },
  {
    "id": 2,
    "name": "Jane Smith",
    "department": "Mathematics",
    "email": "jane.smith@example.com",
    "courses": []
  }
]
```

---

### 4. Update Student
**Endpoint:** `PUT /api/students/{id}`

**Description:** Updates an existing student and their course enrollments.

**Path Parameters:**
- `id` (Long) - Student ID

**Request Body:**
```json
{
  "name": "John Smith",
  "department": "Computer Science",
  "email": "john.smith@example.com",
  "courseIds": [2, 3]
}
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "name": "John Smith",
  "department": "Computer Science",
  "email": "john.smith@example.com",
  "courseIds": [2, 3]
}
```

**Note:** This completely replaces the student's course enrollments. Student will be unenrolled from previous courses and enrolled in new ones.

**Error Responses:**
- `404 Not Found` - Student or course not found
- `400 Bad Request` - Invalid request data

---

### 5. Search Students by Name (Paginated)
**Endpoint:** `GET /api/students/search`

**Description:** Searches for students by name with pagination support.

**Query Parameters:**
- `name` (String, required) - Name to search for (partial match)
- `page` (Integer, optional, default: 0) - Page number
- `size` (Integer, optional, default: 10) - Page size

**Example:** `GET /api/students/search?name=John&page=0&size=5`

**Response:** `200 OK`
```json
{
  "content": [
    {
      "id": 1,
      "name": "John Doe",
      "department": "Computer Science",
      "email": "john.doe@example.com",
      "courses": []
    }
  ],
  "pageable": {
    "sort": {
      "sorted": false,
      "unsorted": true,
      "empty": true
    },
    "pageNumber": 0,
    "pageSize": 5,
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalElements": 1,
  "totalPages": 1,
  "last": true,
  "first": true,
  "numberOfElements": 1,
  "size": 5,
  "number": 0,
  "sort": {
    "sorted": false,
    "unsorted": true,
    "empty": true
  },
  "empty": false
}
```

---

### 6. Search Students by Name (Simple)
**Endpoint:** `GET /api/students/search-name`

**Description:** Searches for students by name without pagination.

**Query Parameters:**
- `name` (String, required) - Name to search for (partial match)

**Example:** `GET /api/students/search-name?name=John`

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "department": "Computer Science",
    "email": "john.doe@example.com",
    "courseIds": [1, 2]
  }
]
```

---

### 7. Get Students by Course
**Endpoint:** `GET /api/students/course/{courseId}`

**Description:** Retrieves all students enrolled in a specific course.

**Path Parameters:**
- `courseId` (Long) - Course ID

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "department": "Computer Science",
    "email": "john.doe@example.com",
    "courseIds": [1, 2, 3]
  }
]
```

**Error Responses:**
- `404 Not Found` - Course not found

---

### 8. Get Student's Enrolled Courses
**Endpoint:** `GET /api/students/{id}/courses`

**Description:** Retrieves all courses a student is enrolled in.

**Path Parameters:**
- `id` (Long) - Student ID

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "title": "Java Programming",
    "credits": 3
  },
  {
    "id": 2,
    "title": "Database Systems",
    "credits": 4
  }
]
```

**Error Responses:**
- `404 Not Found` - Student not found

---

### 9. Enroll Student in Course
**Endpoint:** `POST /api/students/{studentId}/enroll/{courseId}`

**Description:** Enrolls a student in a specific course.

**Path Parameters:**
- `studentId` (Long) - Student ID
- `courseId` (Long) - Course ID

**Response:** `200 OK`
```json
{
  "id": 1,
  "name": "John Doe",
  "department": "Computer Science",
  "email": "john.doe@example.com",
  "courseIds": [1, 2, 3]
}
```

**Error Responses:**
- `404 Not Found` - Student or course not found

---

### 10. Delete Student
**Endpoint:** `DELETE /api/students/{id}`

**Description:** Deletes a student by their ID.

**Path Parameters:**
- `id` (Long) - Student ID

**Response:** `200 OK` (No content)

**Error Responses:**
- `404 Not Found` - Student not found

---


### Common HTTP Status Codes
- `200 OK` - Successful operation
- `400 Bad Request` - Invalid request data
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server error

---

## Data Models

### StudentDTO
```json
{
  "id": "Long (auto-generated)",
  "name": "String (required)",
  "department": "String (required)",
  "email": "String (required)",
  "courseIds": "Set<Long> (optional)"
}
```

### CourseDTO
```json
{
  "id": "Long (auto-generated)",
  "title": "String (required)",
  "credits": "Integer (required)"
}
```

### Student Entity (Full Response)
```json
{
  "id": "Long",
  "name": "String",
  "department": "String",
  "email": "String",
  "courses": "Set<Course>"
}
```

### Course Entity (Full Response)
```json
{
  "id": "Long",
  "title": "String",
  "credits": "Integer",
  "students": "Set<Student>"
}
```

---

## Usage Examples

### Example 1: Create a Student and Enroll in Courses

1. First, create some courses:
```bash
curl -X POST http://localhost:8080/api/courses \
  -H "Content-Type: application/json" \
  -d '{"title": "Java Programming", "credits": 3}'

curl -X POST http://localhost:8080/api/courses \
  -H "Content-Type: application/json" \
  -d '{"title": "Database Systems", "credits": 4}'
```

2. Create a student and enroll in courses:
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "department": "Computer Science",
    "email": "john.doe@example.com",
    "courseIds": [1, 2]
  }'
```

### Example 2: Search for Students (Paginated)
```bash
curl "http://localhost:8080/api/students/search?name=John&page=0&size=10"
```

### Example 2b: Search for Students (Simple)
```bash
curl "http://localhost:8080/api/students/search-name?name=John"
```

### Example 3: Update a Course
```bash
curl -X PUT http://localhost:8080/api/courses/1 \
  -H "Content-Type: application/json" \
  -d '{"title": "Advanced Java Programming", "credits": 4}'
```

### Example 4: Update Student Enrollment
```bash
curl -X PUT http://localhost:8080/api/students/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "department": "Computer Science",
    "email": "john.doe@example.com",
    "courseIds": [2, 3]
  }'
```

### Example 5: Get Student's Enrolled Courses
```bash
curl http://localhost:8080/api/students/1/courses
```

### Example 6: Get Course Enrollment Count
```bash
curl http://localhost:8080/api/courses/1/enrollment-count
```

### Example 7: Get Popular Courses
```bash
curl http://localhost:8080/api/courses/popular
```

### Example 8: Enroll Student in Course
```bash
curl -X POST http://localhost:8080/api/students/1/enroll/3
```

---

## H2 Database Console

For development and testing, you can access the H2 database console:

**URL:** `http://localhost:8080/h2-console`

**Connection Settings:**
- JDBC URL: `jdbc:h2:mem:test`
- User Name: `sa`
- Password: (leave empty)

**Available Tables:**
- `STUDENT`
- `COURSE`
- `STUDENT_COURSE` (join table)

---

## Testing with Postman

Import the following collection to test all endpoints:

### Postman Collection Structure
```
Student Course API
├── Students
│   ├── Create Student
│   ├── Get All Students
│   ├── Get Student by ID
│   ├── Update Student
│   ├── Search Students (Paginated)
│   ├── Search Students (Simple)
│   ├── Get Students by Course
│   ├── Get Student's Courses
│   ├── Enroll Student in Course
│   └── Delete Student
└── Courses
    ├── Create Course
    ├── Get All Courses
    ├── Get Course by ID
    ├── Update Course
    ├── Get Enrollment Count
    ├── Get Popular Courses
    ├── Get Course Students
    └── Delete Course
└── Analytics
    ├── Popular Courses
    ├── Enrollment Statistics
    └── Course Analytics
```

### Environment Variables
- `baseUrl`: `http://localhost:8080`
- `studentId`: `1` (for testing)
- `courseId`: `1` (for testing)