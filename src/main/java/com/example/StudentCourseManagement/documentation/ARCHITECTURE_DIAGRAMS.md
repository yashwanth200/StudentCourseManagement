# Student Course Application - Architecture Diagrams

## 1. High-Level System Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                    Student Course Application                    │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐         │
│  │   Client    │    │   Client    │    │   Client    │         │
│  │ (Browser)   │    │ (Mobile)    │    │ (Postman)   │         │
│  └─────────────┘    └─────────────┘    └─────────────┘         │
│         │                   │                   │              │
│         └───────────────────┼───────────────────┘              │
│                             │                                  │
│                    ┌─────────────────┐                         │
│                    │   REST API      │                         │
│                    │ (Spring Boot)   │                         │
│                    │   Port: 8080    │                         │
│                    └─────────────────┘                         │
│                             │                                  │
│                    ┌─────────────────┐                         │
│                    │   H2 Database   │                         │
│                    │  (In-Memory)    │                         │
│                    └─────────────────┘                         │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

## 2. Layered Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                           │
├─────────────────────────────────────────────────────────────────┤
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐  │
│  │ StudentController│  │ CourseController│  │GlobalExceptionH.│  │
│  │                 │  │                 │  │                 │  │
│  │ @RestController │  │ @RestController │  │ @ControllerAdvice│ │
│  │ /api/students   │  │ /api/courses    │  │                 │  │
│  └─────────────────┘  └─────────────────┘  └─────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                    DATA TRANSFER LAYER                          │
├─────────────────────────────────────────────────────────────────┤
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐  │
│  │   StudentDTO    │  │   CourseDTO     │  │   MapperUtil    │  │
│  │                 │  │                 │  │                 │  │
│  │ - id            │  │ - id            │  │ + toStudentDTO()│  │
│  │ - name          │  │ - title         │  │ + toCourseDTO() │  │
│  │ - department    │  │ - credits       │  │                 │  │
│  │ - email         │  │                 │  │                 │  │
│  │ - courseIds     │  │                 │  │                 │  │
│  └─────────────────┘  └─────────────────┘  └─────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                    BUSINESS LOGIC LAYER                         │
├─────────────────────────────────────────────────────────────────┤
│  ┌─────────────────┐              ┌─────────────────┐           │
│  │ StudentService  │              │ CourseService   │           │
│  │                 │              │                 │           │
│  │ @Service        │              │ @Service        │           │
│  │                 │              │                 │           │
│  │ + createStudent()│              │ + create()      │           │
│  │ + getById()     │              │ + getAll()      │           │
│  │ + getAll()      │              │ + getById()     │           │
│  │ + searchByName()│              │ + deleteById()  │           │
│  │ + delete()      │              │                 │           │
│  └─────────────────┘              └─────────────────┘           │
└─────────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                    DATA ACCESS LAYER                            │
├─────────────────────────────────────────────────────────────────┤
│  ┌─────────────────┐              ┌─────────────────┐           │
│  │StudentRepository│              │CourseRepository │           │
│  │                 │              │                 │           │
│  │ extends         │              │ extends         │           │
│  │ JpaRepository   │              │ JpaRepository   │           │
│  │                 │              │                 │           │
│  │ + findByDept()  │              │ (CRUD methods)  │           │
│  │ + findByName    │              │                 │           │
│  │   Containing()  │              │                 │           │
│  └─────────────────┘              └─────────────────┘           │
└─────────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                    DOMAIN/ENTITY LAYER                          │
├─────────────────────────────────────────────────────────────────┤
│  ┌─────────────────┐              ┌─────────────────┐           │
│  │    Student      │◄────────────►│     Course      │           │
│  │                 │              │                 │           │
│  │ @Entity         │              │ @Entity         │           │
│  │                 │              │                 │           │
│  │ - id (PK)       │              │ - id (PK)       │           │
│  │ - name          │              │ - title         │           │
│  │ - department    │              │ - credits       │           │
│  │ - email         │              │                 │           │
│  │ - courses       │              │ - students      │           │
│  │   @ManyToMany   │              │   @ManyToMany   │           │
│  └─────────────────┘              └─────────────────┘           │
└─────────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                    DATABASE LAYER                               │
├─────────────────────────────────────────────────────────────────┤
│                    H2 In-Memory Database                        │
│                                                                 │
│  ┌─────────────┐  ┌─────────────────┐  ┌─────────────┐         │
│  │   student   │  │ student_course  │  │   course    │         │
│  │             │  │                 │  │             │         │
│  │ id (PK)     │  │ student_id (FK) │  │ id (PK)     │         │
│  │ name        │  │ course_id (FK)  │  │ title       │         │
│  │ department  │  │                 │  │ credits     │         │
│  │ email       │  │                 │  │             │         │
│  └─────────────┘  └─────────────────┘  └─────────────┘         │
└─────────────────────────────────────────────────────────────────┘
```

## 3. Entity Relationship Diagram (ERD)

```
┌─────────────────────────────────────────────────────────────────┐
│                    Entity Relationship Diagram                  │
└─────────────────────────────────────────────────────────────────┘

    ┌─────────────────┐                    ┌─────────────────┐
    │     Student     │                    │     Course      │
    ├─────────────────┤                    ├─────────────────┤
    │ id: Long (PK)   │                    │ id: Long (PK)   │
    │ name: String    │                    │ title: String   │
    │ department: Str │                    │ credits: Integer│
    │ email: String   │                    │                 │
    └─────────────────┘                    └─────────────────┘
            │                                      │
            │                                      │
            │              ┌─────────────────┐     │
            │              │ student_course  │     │
            │              ├─────────────────┤     │
            └──────────────┤student_id (FK)  │     │
                           │course_id (FK)   ├─────┘
                           └─────────────────┘

    Relationship: Many-to-Many
    - One Student can enroll in many Courses
    - One Course can have many Students
    - Join table: student_course
```

## 4. Component Interaction Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                    Component Interaction Flow                    │
└─────────────────────────────────────────────────────────────────┘

Client Request Flow:

1. HTTP Request
   │
   ▼
┌─────────────────┐
│   Controller    │ ──── Validates request
│                 │ ──── Maps to DTO
└─────────────────┘
   │
   ▼ Call service method
┌─────────────────┐
│    Service      │ ──── Business logic
│                 │ ──── Transaction management
└─────────────────┘
   │
   ▼ Repository call
┌─────────────────┐
│   Repository    │ ──── Data access
│                 │ ──── Query execution
└─────────────────┘
   │
   ▼ JPA/Hibernate
┌─────────────────┐
│   Database      │ ──── SQL execution
│   (H2)          │ ──── Data persistence
└─────────────────┘

Response Flow (reverse):
Database → Repository → Service → Controller → Client
```

## 5. API Request/Response Flow

```
┌─────────────────────────────────────────────────────────────────┐
│                    API Request/Response Flow                     │
└─────────────────────────────────────────────────────────────────┘

Example: Create Student with Courses

POST /api/students
{
  "name": "John Doe",
  "department": "Computer Science",
  "email": "john@example.com",
  "courseIds": [1, 2, 3]
}

Flow:
┌─────────────┐    ┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│   Client    │───▶│ Controller  │───▶│   Service   │───▶│ Repository  │
└─────────────┘    └─────────────┘    └─────────────┘    └─────────────┘
       ▲                  │                  │                  │
       │                  ▼                  ▼                  ▼
       │           1. Receive DTO     2. Validate data   3. Save entity
       │           2. Validate        3. Map DTO→Entity  4. Execute SQL
       │              request         4. Business logic  5. Return entity
       │                              5. Map Entity→DTO
       │                  │                  │                  │
       │                  ▼                  ▼                  ▼
┌─────────────┐    ┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│  Response   │◀───│   Return    │◀───│   Return    │◀───│   Return    │
│    DTO      │    │    DTO      │    │    DTO      │    │   Entity    │
└─────────────┘    └─────────────┘    └─────────────┘    └─────────────┘

Response:
{
  "id": 1,
  "name": "John Doe",
  "department": "Computer Science",
  "email": "john@example.com",
  "courseIds": [1, 2, 3]
}
```

## 6. Exception Handling Flow

```
┌─────────────────────────────────────────────────────────────────┐
│                    Exception Handling Flow                       │
└─────────────────────────────────────────────────────────────────┘

Exception Propagation:

┌─────────────┐    ┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│ Repository  │───▶│   Service   │───▶│ Controller  │───▶│   Client    │
└─────────────┘    └─────────────┘    └─────────────┘    └─────────────┘
       │                  │                  │                  │
       ▼                  ▼                  ▼                  ▼
1. Data not found   2. Catch exception  3. Exception     4. HTTP 404
2. Throw            3. Re-throw or         propagates        Response
   RuntimeException    handle             to global
                                         handler
                                             │
                                             ▼
                                   ┌─────────────────┐
                                   │ GlobalException │
                                   │    Handler      │
                                   │                 │
                                   │ @ControllerAdvice│
                                   └─────────────────┘
                                             │
                                             ▼
                                   Maps exception to
                                   appropriate HTTP
                                   status and message
```

## 7. Data Flow Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                        Data Flow Diagram                        │
└─────────────────────────────────────────────────────────────────┘

Student-Course Enrollment Process:

┌─────────────┐
│   Client    │
│  Request    │
└─────────────┘
       │
       ▼ StudentDTO with courseIds
┌─────────────┐
│ Student     │
│ Controller  │
└─────────────┘
       │
       ▼ Pass DTO to service
┌─────────────┐
│ Student     │ ──────────┐
│ Service     │           │
└─────────────┘           │
       │                  │
       ▼ Create Student    ▼ Fetch Courses by IDs
┌─────────────┐    ┌─────────────┐
│ Student     │    │ Course      │
│ Repository  │    │ Repository  │
└─────────────┘    └─────────────┘
       │                  │
       ▼ Save Student     ▼ Return Courses
┌─────────────┐    ┌─────────────┐
│ Student     │    │ Course      │
│ Entity      │◄──►│ Entities    │
└─────────────┘    └─────────────┘
       │                  │
       ▼ Many-to-Many     ▼
┌─────────────────────────────────┐
│      student_course             │
│      (Join Table)               │
└─────────────────────────────────┘
```

## 8. Deployment Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                    Deployment Architecture                       │
└─────────────────────────────────────────────────────────────────┘

Development Environment:
┌─────────────────────────────────────────────────────────────────┐
│                    Local Development                             │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌─────────────────┐    ┌─────────────────┐                    │
│  │      IDE        │    │   H2 Console    │                    │
│  │   (IntelliJ/    │    │  localhost:8080 │                    │
│  │    VS Code)     │    │  /h2-console    │                    │
│  └─────────────────┘    └─────────────────┘                    │
│           │                       │                            │
│           ▼                       ▼                            │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │           Spring Boot Application                       │   │
│  │              localhost:8080                             │   │
│  │                                                         │   │
│  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐     │   │
│  │  │ Controllers │  │  Services   │  │Repositories │     │   │
│  │  └─────────────┘  └─────────────┘  └─────────────┘     │   │
│  └─────────────────────────────────────────────────────────┘   │
│                              │                                 │
│                              ▼                                 │
│                    ┌─────────────────┐                         │
│                    │  H2 Database    │                         │
│                    │  (In-Memory)    │                         │
│                    └─────────────────┘                         │
└─────────────────────────────────────────────────────────────────┘

Production Environment (Recommended):
┌─────────────────────────────────────────────────────────────────┐
│                    Production Deployment                         │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌─────────────┐    ┌─────────────────┐    ┌─────────────┐     │
│  │Load Balancer│───▶│  Spring Boot    │───▶│  Database   │     │
│  │  (Nginx)    │    │  Application    │    │ (PostgreSQL/│     │
│  └─────────────┘    │   (Docker)      │    │   MySQL)    │     │
│                     └─────────────────┘    └─────────────┘     │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

This comprehensive architecture documentation provides a complete view of your Student Course Application's structure, components, and interactions. The diagrams illustrate the layered architecture, data relationships, and system flows that make up your application.