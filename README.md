
# AUCA Web Technology Mid-Term Exam Project

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/) 
[![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white)](https://hibernate.org/)
[![Bootstrap](https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white)](https://getbootstrap.com/)
[![Apache Tomcat](https://img.shields.io/badge/Apache%20Tomcat-F8DC75?style=for-the-badge&logo=apache-tomcat&logoColor=black)](https://tomcat.apache.org/)
[![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)

This repository contains the **Mid-Term exam project** for Web Technology at **Adventist University of Central Africa (AUCA)**. The project was developed using **Java Servlets, JSP, HTML5, CSS, Bootstrap, JavaScript, and Hibernate**. It is designed to manage web-based applications and deployed using **Apache Tomcat**.

## Table of Contents
1. [Tech Stack](#tech-stack)
2. [Features](#features)
3. [Preparing the Environment](#preparing-the-environment)
4. [Getting Started](#getting-started)
5. [Project Structure](#project-structure)
6. [Screenshots](#screenshots)

---

## Tech Stack
The project was built using the following technologies:

- **Java Servlet**: Handles the backend logic and user requests.
- **JSP (JavaServer Pages)**: Used to dynamically generate HTML content.
- **Hibernate ORM**: Manages data persistence and database interactions.
- **MySQL**: Database used to store the application data.
- **HTML5, CSS3, and Bootstrap**: Frontend design and styling.
- **JavaScript**: Frontend interactivity and logic.
- **Apache Tomcat**: Used as the web server for deployment.

---

## Features
- User registration and login system.
- CRUD operations for managing student accounts.
- User roles for students and admins.
- Dynamic content rendering using JSP.
- Persistent storage of data using Hibernate.
- Interactive frontend with Bootstrap and JavaScript.

---

## Preparing the Environment

### Prerequisites
Ensure you have the following installed on your machine:
- **JDK 17 or above**: Required to run Java code.
- **Apache Tomcat 10.x**: Used as the web server.
- **MySQL Server**: For database setup.
- **Maven**: To manage dependencies and build the project.
- **IDE (e.g., Eclipse, IntelliJ IDEA)**: To write and manage the code.

### Setting Up
1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/project-repo.git
   ```
2. **Navigate to the project directory**:
   ```bash
   cd project-repo
   ```

3. **Set up MySQL database**:
   - Create a new database called `mondayweb` (or your preferred name).
   - Import the required tables and schema using the `schema.sql` file provided in the repository.

4. **Configure `src/main/resources/hibernate.cfg.xml`**:
   - Set your MySQL username and password in the configuration.

   ```xml
   <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/mondayweb</property>
   <property name="hibernate.connection.username">root</property>
   <property name="hibernate.connection.password">your_password</property>
   ```

5. **Build the project** using Maven:
   ```bash
   mvn clean install
   ```

6. **Deploy to Apache Tomcat**:
   - Copy the generated `.war` file to the Tomcat `webapps` directory.
   - Start the Tomcat server and access the application at `http://localhost:8080/project-name`.

---

## Getting Started

Once the environment is set up:

1. **Run the Application**:
   - Open a browser and go to `http://localhost:8080/project-name`.

2. **Accessing the Features**:
   - You will be presented with a login page where new users can register and log in.
   - Once logged in, you can access the dashboard, manage user accounts, and perform CRUD operations on the student database.

3. **Developing with Eclipse**:
   - Import the project as a Maven project into Eclipse.
   - Run the project on Tomcat using Eclipse's "Run on Server" option.

---

## Project Structure
The project follows a standard Maven structure:

```
project-name/
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── model/
│   │   │   └── controller/
│   │   └── webapp
│   │       └── WEB-INF/
│   │       └── views/
│   │       └── resources/
│   └── test
│
├── pom.xml
├── README.md
├── schema.sql
```

---

## Screenshots

### 1. Login Page
![Login Page](https://via.placeholder.com/800x400)

### 2. Student Dashboard
![Dashboard](https://via.placeholder.com/800x400)

### 3. Admin CRUD Operations
![CRUD Operations](https://via.placeholder.com/800x400)

---

## License

[![MIT License](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

This project is licensed under the **MIT License**. You are free to modify and distribute it as long as proper credit is given.
