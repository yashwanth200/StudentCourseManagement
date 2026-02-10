# Student Course Application - Architecture Documentation

## Overview
The Student Course Application is a Spring Boot REST API that manages students and courses with a many-to-many relationship. It follows a layered architecture pattern with clear separation of concerns.

## Technology Stack
- **Framework**: Spring Boot 3.5.7
- **Java Version**: 17
- **Database**: H2 (In-memory)
- **ORM**: Spring Data JPA / Hibernate
- **Build Tool**: Maven
- **Additional Libraries**: Lombok, Jakarta Persistence API

## Architecture Pattern
The application follows a **Layered Architecture** pattern with the following layers:

### 1. Presentation Layer (Controllers)
- **StudentController**: Handles HTTP requests for student operations
- **CourseController**: Handles HTTP requests for course operations
- **GlobalExceptionHandler**: Centralized exception handling

### 2. Business Logic Layer (Services)
- **StudentService**: Contains business logic for student operations
- **CourseService**: Contains business logic for course operations

### 3. Data Access Layer (Repositories)
- **StudentRepository**: Data access interface for Student entity
- **CourseRepository**: Data access interface for Course entity

### 4. Data Transfer Layer (DTOs)
- **StudentDTO**: Data transfer object for Student
- **CourseDTO**: Data transfer object for Course
- **MapperUtil**: Utility class for entity-DTO mapping

### 5. Domain Layer (Models)
- **Student**: Entity representing a student
- **Course**: Entity representing a course

## Database Schema

### Tables
1. **student**
   - id (BIGINT, PRIMARY KEY, AUTO_INCREMENT)
   - name (VARCHAR)
   - department (VARCHAR)
   - email (VARCHAR)

2. **course**
   - id (BIGINT, PRIMARY KEY, AUTO_INCREMENT)
   - title (VARCHAR)
   - credits (INTEGER)

3. **student_course** (Join Table)
   - student_id (BIGINT, FOREIGN KEY → student.id)
   - course_id (BIGINT, FOREIGN KEY → course.id)

### Relationships
- **Student ↔ Course**: Many-to-Many relationship
  - A student can enroll in multiple courses
  - A course can have multiple students

## API Endpoints

### Student Endpoints
- `POST /api/students` - Create a new student
- `GET /api/students/{id}` - Get student by ID
- `GET /api/students` - Get all students
- `GET /api/students/search?name={name}&page={page}&size={size}` - Search students by name (paginated)
- `DELETE /api/students/{id}` - Delete student by ID

### Course Endpoints
- `POST /api/courses` - Create a new course
- `GET /api/courses` - Get all courses
- `GET /api/courses/{id}` - Get course by ID
- `PUT /api/courses/{id}` - Update course by ID
- `DELETE /api/courses/{id}` - Delete course by ID

## Key Features

### 1. Many-to-Many Relationship Management
- Students can be enrolled in multiple courses
- Courses can have multiple students
- Managed through join table `student_course`

### 2. Data Transfer Objects (DTOs)
- Separation between internal entities and external API contracts
- StudentDTO includes courseIds for relationship management
- Prevents over-exposure of internal data structures

### 3. Exception Handling
- Custom `ResourceNotFoundException` for not found scenarios
- Global exception handler for centralized error management
- Proper HTTP status codes (404 for not found, 400 for bad requests)

### 4. Search and Pagination
- Name-based search for students
- Pagination support for large datasets
- Uses Spring Data JPA's Pageable interface

### 5. Validation and Error Handling
- Repository-level existence checks
- Service-level business logic validation
- Controller-level HTTP response management

## Configuration

### Database Configuration (application.properties)
```properties
spring.application.name=StudentCourseApplication
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:test
spring.jpa.show.sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### Maven Dependencies
- spring-boot-starter-web (REST API)
- spring-boot-starter-data-jpa (Data persistence)
- h2 (In-memory database)
- lombok (Boilerplate code reduction)
- jakarta.persistence-api (JPA annotations)

## Design Patterns Used

### 1. Repository Pattern
- Abstracts data access logic
- Provides clean interface for data operations
- Extends JpaRepository for CRUD operations

### 2. Data Transfer Object (DTO) Pattern
- Separates internal data model from API contracts
- Reduces coupling between layers
- Enables data transformation and validation

### 3. Service Layer Pattern
- Encapsulates business logic
- Provides transaction boundaries
- Coordinates between controllers and repositories

### 4. Dependency Injection
- Uses Spring's @Autowired for dependency management
- Promotes loose coupling
- Enables easy testing and mocking

## Security Considerations
- Currently no authentication/authorization implemented
- H2 console enabled (should be disabled in production)
- No input validation annotations (should be added)
- No rate limiting or API security measures

## Performance Considerations
- Uses LAZY fetching for many-to-many relationships
- Pagination support for large datasets
- In-memory H2 database (fast but not persistent)
- SQL logging enabled for debugging

## Scalability Considerations
- Stateless REST API design
- Layered architecture supports horizontal scaling
- Database can be easily switched from H2 to production databases
- Service layer can be extracted to microservices if needed

## Testing Strategy
- Unit tests for service layer business logic
- Integration tests for repository layer
- Controller tests for API endpoints
- Test database isolation using H2

## Deployment Architecture
```
[Client] → [Load Balancer] → [Spring Boot App] → [Database]
                                    ↓
                              [H2 Console] (Dev only)
```

## Future Enhancements
1. Add authentication and authorization (Spring Security)
2. Implement input validation (Bean Validation)
3. Add caching layer (Redis/Hazelcast)
4. Implement audit logging
5. Add API documentation (Swagger/OpenAPI)
6. Implement proper database migration (Flyway/Liquibase)
7. Add monitoring and metrics (Actuator/Micrometer)
8. Implement CI/CD pipeline
9. Add containerization (Docker)
10. Implement proper logging framework (Logback/SLF4J)