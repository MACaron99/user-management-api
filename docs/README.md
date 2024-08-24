# User Management API

This repository contains an example implementation of a RESTful API using Spring Boot. The **User Management API** project is designed to manage user information with key functionalities including creation, updating, deletion, and search, all while adhering to RESTful principles.

### Features
- Includes a controller implementation for the "Users" resource, in accordance with RESTful API requirements.
- All API operations, including user creation, updating, deletion, and searching, are covered by tests using the Spring Framework.
- Error handling for the RESTful API is implemented to provide clear and informative responses.
- The API returns data in JSON format, adhering to RESTful API standards.

### Requirements
- Any version of Spring Boot, at your discretion.
- Java version of your choice.
- Database usage is not required, as the data persistence layer is not implemented.
- The project is created using Spring Initializr.

### Endpoints
- **Create User**: `POST /api/users`
  - This endpoint allows you to register users who are more than 18 years old. The age restriction is defined in the properties file.
- **Update User**: `PATCH /api/users/{id}`
  - Update one or more fields of a user identified by their ID.
- **Update All User Fields**: `PUT /api/users/{id}`
  - Update all fields of a user identified by their ID.
- **Delete User**: `DELETE /api/users/{id}`
  - Delete a user by their ID.
- **Search Users by Birth Date Range**: `GET /api/users?from={from_date}&to={to_date}`
  - Retrieve a list of users within the specified birth date range. The "from" date should be earlier than the "to" date.

### Testing

The project includes unit tests for each service method using JUnit. To run these tests, navigate to the `src/test/java` directory and execute the test classes to verify the functionality of individual components under various conditions.

### Error Handling

The API handles various errors with specific responses:

- **EmailRequiredException**: HTTP 400 for missing email during user creation.
- **BirthDateRequiredException**: HTTP 400 for missing birth date.
- **FirstNameRequiredException**: HTTP 400 for missing first name.
- **LastNameRequiredException**: HTTP 400 for missing last name.
- **InvalidBirthDateException**: HTTP 400 for future birth dates.
- **UserAgeInsufficientException**: HTTP 400 if user is under 18.
- **InvalidEmailException**: HTTP 400 for invalid email format.
- **InvalidDateRangeException**: HTTP 400 for invalid date range.
- **UserNotFoundException**: HTTP 404 if user ID not found.
