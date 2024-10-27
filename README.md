

# AUCA Library Management System

[![Java](https://img.shields.io/badge/Java-17%2B-blue)](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
[![Apache Tomcat](https://img.shields.io/badge/Tomcat-10.x-yellow)](https://tomcat.apache.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.x-orange)](https://dev.mysql.com/downloads/mysql/)
[![Hibernate](https://img.shields.io/badge/Hibernate-ORM-green)](https://hibernate.org/)
[![Maven](https://img.shields.io/badge/Maven-3.x-red)](https://maven.apache.org/)

This repository contains a **Library Management System** developed as part of the **Mid-Term exam project** for Web Technology at **Adventist University of Central Africa (AUCA)**. The project was built using **Java Servlets, JSP, HTML5, CSS, JavaScript, Hibernate ORM**, and deployed on **Apache Tomcat**. The system manages book borrowing activities and memberships for the institution's library.

---

## Table of Contents
1. [Tech Stack](#tech-stack)
2. [Features](#features)
3. [Preparing the Environment](#preparing-the-environment)
4. [Getting Started](#getting-started)
5. [Project Structure](#project-structure)
6. [Screenshots](#screenshots)

---

## Tech Stack

- **Java Servlets**: Backend logic to handle user requests.
- **JSP (JavaServer Pages)**: Dynamically generated HTML pages.
- **Hibernate ORM**: For data persistence and database interactions.
- **MySQL**: Database used to store information.
- **HTML5, CSS3, and JavaScript**: Frontend design and functionality.
- **Bootstrap**: For responsive UI design.
- **Apache Tomcat**: Web server used for deployment.
- **Maven**: Project management and build tool.

---

## Features

1. **User Account Management**:
   - Create accounts for different user roles such as Librarian, Students, Teachers, and Administrators.
   - Password hashing for secure authentication.

2. **Book Borrowing and Membership**:
   - Three membership types: Gold, Silver, Striver, with different daily fees and borrowing limits.
   - Validation to ensure users cannot borrow more books than their membership allows.

3. **Location Management**:
   - Add and manage user locations from Province to Village.

4. **Late Fees Calculation**:
   - Automatically apply charges for late book returns based on membership.

5. **Librarian Functions**:
   - Manage books, assign books to shelves, assign shelves to rooms, and track the number of books in each room.

6. **Administrator Access**:
   - Admins like HOD, Dean, and Registrar can view book details but cannot borrow.

---

## Preparing the Environment

### Prerequisites
Ensure you have the following installed:
- **JDK 17 or above**
- **Apache Tomcat 10.x**
- **MySQL Server**
- **Maven**
- **IDE (e.g., Eclipse, IntelliJ IDEA)**

### Setting Up

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/auca-library-system.git
   ```

2. **Navigate to the project directory**:
   ```bash
   cd auca-library-system
   ```

3. **Set up MySQL database**:
   - Create a database `auca_library_db`.
   - Import the SQL schema provided in the repository.

4. **Configure the Hibernate settings**:
   Modify the MySQL credentials in `hibernate.cfg.xml`:
   ```xml
   <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/auca_library_db</property>
   <property name="hibernate.connection.username">root</property>
   <property name="hibernate.connection.password">your_password</property>
   ```

5. **Build the project using Maven**:
   ```bash
   mvn clean install
   ```

6. **Deploy to Apache Tomcat**:
   - Copy the `.war` file to the `webapps` directory in Tomcat.
   - Start Tomcat and access the app at `http://localhost:8080/auca-library-system`.

---

## Getting Started

1. **Run the application**:
   - Access the application at `http://localhost:8080/auca-library-system`.

2. **User Roles**:
   - Students and teachers can borrow books.
   - Administrators can view details but are restricted from borrowing.
   - Librarians manage book borrowing, shelving, and late fees.

---

## Project Structure

```
Mid_WebTech_25337/
│
├── pom.xml
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │               ├── model/
│   │   │               │   ├── Book.java
│   │   │               │   ├── Borrower.java
│   │   │               │   ├── Location.java
│   │   │               │   ├── Membership.java
│   │   │               │   ├── MembershipType.java
│   │   │               │   ├── Room.java
│   │   │               │   ├── Shelf.java
│   │   │               │   └── User.java
│   │   │               │
│   │   │               ├── dao/
│   │   │               │   ├── BookDAO.java
│   │   │               │   ├── BorrowerDAO.java
│   │   │               │   ├── LocationDAO.java
│   │   │               │   ├── MembershipDAO.java
│   │   │               │   ├── MembershipTypeDAO.java
│   │   │               │   ├── RoomDAO.java
│   │   │               │   ├── ShelfDAO.java
│   │   │               │   └── UserDAO.java
│   │   │               │
│   │   │               ├── service/
│   │   │               │   ├── BookService.java
│   │   │               │   ├── BorrowerService.java
│   │   │               │   ├── LocationService.java
│   │   │               │   ├── MembershipService.java
│   │   │               │   ├── MembershipTypeService.java
│   │   │               │   ├── RoomService.java
│   │   │               │   ├── ShelfService.java
│   │   │               │   └── UserService.java
│   │   │               │
│   │   │               └── controller/
│   │   │                   ├── BookController.java
│   │   │                   ├── BorrowerController.java
│   │   │                   ├── LocationController.java
│   │   │                   ├── MembershipController.java
│   │   │                   ├── MembershipTypeController.java
│   │   │                   ├── RoomController.java
│   │   │                   ├── ShelfController.java
│   │   │                   └── UserController.java
│   │   │
│   │   └── resources/
│   │       ├── hibernate.cfg.xml
│   │       └── log4j.properties
│   │
│   └── webapp/
│       ├── WEB-INF/
│       │   ├── web.xml
│       │   └── views/
│       │       ├── book.jsp
│       │       ├── borrower.jsp
│       │       ├── location.jsp
│       │       ├── membership.jsp
│       │       ├── membershipType.jsp
│       │       ├── room.jsp
│       │       ├── shelf.jsp
│       │       └── user.jsp
│       │
│       ├── css/
│       │   └── styles.css
│       │
│       ├── js/
│       │   └── scripts.js
│       │
│       └── index.jsp
│
└── README.md

```

---

## Screenshots

### 1. Login Page
<img width="673" alt="image" src="https://github.com/user-attachments/assets/e6c3a8b5-f63d-4e8b-9778-6d60df895368">


### 2. User Dashboard
![Dashboard](https://via.placeholder.com/800x400)

### 3. Librarian Dashboard
![Librarian](https://via.placeholder.com/800x400)

---

## License

This project is licensed under the **MIT License**.
