# Project Name: BharatPosts

Imagine a vibrant Spring Boot Blog application! Users can register, create posts, and explore content by category. Admins handle user and post management with added features like category control and comment moderation. Security is tight with role-based access, and users can even search for posts by keywords. It's a user-friendly, secure space for sharing and discovering engaging content.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Overview

Our project is a Spring Boot-based Blog application designed for seamless content creation, sharing, and discovery. 
The main purpose is to provide users with a platform to register, create, and manage their posts across various categories. 
Admins have additional control over user and post management, ensuring a secure and well-moderated environment. 
Key features include user authentication, profile management, category exploration, and advanced post search functionality. 
The project is structured for simplicity, security, and an engaging user experience.

## Features

### Users Features:

- Users can register by providing necessary details.
- Users can create, update, and delete their profiles.
- Users can create, update, and delete their posts.
- Users can view  all available post Categories
- Users can view posts, search for a post by its ID, and search for posts containing a specific keyword, and view paginated posts with sorting options
- Users can create and delete Comments on posts.

### Admin Features:
- Admins can perform CRUD operations on users and posts.
- Admins can manage post categories by creating, updating and deleting categories.
- Admins can view and delete comments on posts.
- Admins can implement security measures like role-based access control.

### Common Features:
- Both users and admins can obtain a new authentication token by providing the expired token

## Getting Started

### Prerequisites

1. **Java SDK:** The project is built using Java, and having the appropriate Java Development Kit (JDK) is essential.
2. **Spring Boot:** Since the project is a Spring Boot application, you need to have Spring Boot installed. You can typically include the Spring Boot dependencies in your project, or users can install it globally on their systems.
3. **Database:** Depending on your project's configuration, You can go with MySQL, PostgreSQL, or H2.
4. **Integrated Development Environment (IDE):** It's not mandatory, but using an IDE like Eclipse, IntelliJ IDEA, or VS Code can enhance the development experience.
5. **Build Tool (Optional):** A build tool like Maven or Gradle, You may need to have it installed to manage dependencies  and build the project.

### Installation

Setting up the project locally involves a series of steps to ensure that al dependencies are installed, and the application can be run successfully.
Here's a general guide:

**Step 1: Clone the Repository**
```bash
git clone https://github.com/Fiza32/BharatPosts.git
```
**Step 2: Navigate to the Project Directory**
```bash
cd your_Project
```

**Step 3: Build the Project (If using Maven or Gradle)**
- For Maven
```bash
./mvnw clean install
```
- For Gradle
```bash
./gradlew clean build
```
This step downloads dependencies and builds the project. Use the appropriate command based on your build tool.

**Step 4: Configure the Database (if applicable)**
- This project uses a database, and configures the database connection in the application properties or YAML file. 
- Update the application.properties or application.yml file with the necessary database connection details.

**Step 5: Run the Application**
- For Maven
```bash
./mvnw spring-boot:run
```

- For Gradle
```bash
./gradlew bootRun
```

This command starts the Spring Boot application. Once the application is running, you should see log messages indicating that the server is up and running.

**Step 6: Access the Application**
- Open a web browser or Postman and navigate to **`http://localhost:8080`** or the specified port if you've configured a different one.

**Step 7: Test the Application**
- Test Various features of your application to ensure that it's working as expected.
