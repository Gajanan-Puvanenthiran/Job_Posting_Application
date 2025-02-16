# Job Posting Application

## Overview
This is a Spring Boot-based REST API for a job posting platform. It allows companies to create, retrieve, update, and delete job postings while ensuring data validation, exception handling, and logging for a smooth backend experience.

## Technologies Used
- **Spring Boot** (Spring Web, Spring Data JPA)
- **PostgreSQL** (Database)
- **SLF4J + Logback** (Logging)
- **Jackson** (JSON Processing)
- **Lombok** (For cleaner code, optional)

## Features
- CRUD operations for job postings
- Global exception handling using `@RestControllerAdvice`
- Request/response logging with SLF4J
- Auto-generated timestamps for job postings
- Structured API response format

## API Endpoints

| Method | Endpoint        | Description |
|--------|----------------|-------------|
| POST   | `/jobs`        | Add a new job posting |
| GET    | `/jobs`        | Retrieve all job postings |
| GET    | `/jobs/{id}`   | Get job details by ID |
| PUT    | `/jobs/{id}`   | Update a job posting |
| DELETE | `/jobs/{id}`   | Delete a job posting |

## Sample API Responses

### Success Response:
```json
{
  "status": "success",
  "message": "Job retrieved successfully",
  "data": {
    "id": 1,
    "title": "Software Engineer",
    "company": "Meta Inc.",
    "description": "We are looking for a skilled Java developer to join our team.",
    "location": "New York, USA",
    "salary": 75000.0,
    "createdAt": "2024-01-23T10:30:00Z"
  },
  "timestamp": "2024-01-23T12:00:00Z"
}
```

### Failure Response:
```json
{
  "status": "failed",
  "message": "Job with ID 99 not found",
  "data": null,
  "timestamp": "2024-01-23T12:00:00Z"
}
```

## Setup & Installation

### Prerequisites:
- Java 17+
- PostgreSQL installed and running
- Maven installed

### Steps to Run Locally:
1. Clone the repository:
   ```sh
   git clone https://github.com/Gajanan-Puvanenthiran/Job_Posting_Application.git
   cd Job_Posting_Application
   ```

2. Configure the database in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/jobdb
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```

3. Build and run the application:
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```

4. Access the API at `http://localhost:8080`

5. (Optional) Access the H2 Console (if enabled) at `http://localhost:8080/h2-console`

## Exception Handling
- **JobNotFoundException** → Returns a `404 Not Found` response
- **Validation Errors** → Returns a `400 Bad Request` response with field-specific error messages
- **Malformed JSON** → Returns a `400 Bad Request` response

## Logging
- All API requests and responses are logged using SLF4J with Logback
- Errors and exceptions are logged with stack traces for easier debugging

## Future Enhancements
- Add Swagger documentation
- Implement authentication & authorization (JWT-based)
- Improve pagination and filtering options

## Contributing
Feel free to submit issues and pull requests. Any contributions are welcome!

## License
MIT License

