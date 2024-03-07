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

Explain how to set up the project locally.

### Prerequisites

List any prerequisites that users need to have installed on their systems before they can use your project.

- Java SDK
- Spring Boot
- ...

### Installation

Provide step-by-step instructions on how to install and configure the project.

```bash
# Clone the repository
git clone https://github.com/your-username/your-project.git

# Navigate to the project directory
cd your-project

# Run the application
./mvnw spring-boot:run
```
