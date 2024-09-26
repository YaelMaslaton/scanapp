# Scan App - MEND.IO

## Overview
Scan App is a Java-based application designed to manage and track scans submitted by users. It captures all scans associated with different organizations, repositories, and branches. This app provides a user-friendly interface to initiate and monitor scans of various types, while also handling failures and logging key events.

Key features include:
- **Scalability**: The architecture is designed to handle an increasing number of users and scan requests efficiently.
- **Reliability**: The application ensures consistent performance and fault tolerance, providing users with a dependable scanning experience.
- **Performance**: Optimized for speed and efficiency, the app processes scans with minimal latency, ensuring quick response times.

This app allows users to initiate various types of scans, track their status, and retrieve insights on issues detected during scans.

## Technologies Used
- **Java**: The programming language used for development.
- **postgreSQL**: A powerful, open-source relational database system for data storage.
- **Docker**: Containerization platform for packaging the application and its dependencies.
- **Gradle**: Build automation tool for managing dependencies and builds.
- **Spring Boot**: Framework for building production-ready applications.
- **JDK 17**: The Java Development Kit used for compiling and running the application.

## API Endpoints

### 1. Initiate a Scan
- **POST** `/scans/initiate`
- **Description**: Starts a new scan with the provided details.
- **Request Body**: `ScanDTO` (must include `type`, `commitId`, `issues`, `valid`, and `userId`)

### 2. Count Pending Scans
- **GET** `/scans/pending/count`
- **Description**: Returns the total number of pending scans for all users.
- **Response**: Total count of pending scans (long).

### 3. Get Total Issues Found
- **GET** `/scans/issues/total`
- **Description**: Returns the total number of issues found across all scans.
- **Response**: Total number of issues (int).

### 4. Get Total Issues by User
- **GET** `/scans/users/{userId}/issues/total`
- **Description**: Returns the total number of issues found for a specific user.
- **Path Variable**: `userId` (ID of the user).
- **Response**: Total number of issues (int).

### 5. Get Most Active Regular Users
- **GET** `/scans/active-regular-users`
- **Description**: Returns the top 10 most active regular users.
- **Response**: List of active users (`UserActivityDTO`).

### 6. Get Scans by Commit
- **GET** `/scans/commit/{commitId}`
- **Description**: Return User scan by specific commit ID.
- **Path Variable**: `commitId` (ID of the commit).
- **Response**: User scan for the commit (`UserScanInfoResponseDTO`).

## Getting Started

### Prerequisites
Make sure you have the following installed:
- JDK 17 or later
- Docker
- Docker Compose
- Gradle 

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/YaelMaslaton/scan-app.git
   cd scan-app

2. **Build the application Using Gradle:**
   ```bash
   ./gradlew build
3. **Set up Docker Ensure Docker is running, then start the application using Docker Compose:**
   ```bash
    docker-compose up
4. **Access Swagger UI After starting the application, you can view the API endpoints using Swagger UI at:**
   http://localhost:8080/swagger-ui.html

5. **Populate Test Data After the application is running, use the following command to populate test data:**
    curl -X POST http://localhost:8080/api/populate

### Contact
For any questions or support, feel free to reach out:

- **Email**: yaelsulemani@gmail.com
- **GitHub**: https://github.com/YaelMaslaton
