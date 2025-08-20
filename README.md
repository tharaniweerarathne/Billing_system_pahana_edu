<h1 align="center">
  <img src="https://img.icons8.com/fluency/96/000000/books.png" alt="Logo" width="60" height="60"/>Pahana Edu Billing System</h1>

<h3 align="center">💻 A Web-Based Billing & Customer Management System</h3>

<p align="center">
    <a href="https://www.oracle.com/java/" target="blank">
    <img src="https://img.shields.io/badge/Language-Java-red?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java"/>
  </a>

  <a href="https://www.apache.org/" target="blank">
    <img src="https://img.shields.io/badge/Server-Apache%20Tomcat%209-blue?style=for-the-badge&logo=apachetomcat&logoColor=white" alt="Tomcat"/>
  </a>
  <a href="https://www.jetbrains.com/idea/" target="blank">
    <img src="https://img.shields.io/badge/IDE-IntelliJ%20Ultimate-purple?style=for-the-badge&logo=intellijidea&logoColor=white" alt="IntelliJ"/>
  </a>
  <a href="https://www.mysql.com/" target="blank">
    <img src="https://img.shields.io/badge/Database-MySQL-orange?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL"/>
  </a>

</p>

---

### 🚀 Project Overview
*Pahana Edu* is a leading bookshop in Colombo City.  
This project introduces a *web-based system* to efficiently manage *customer accounts, items, and billing operations*, replacing the current manual process.  

The system ensures:  
✔ Faster billing operations  
✔ Accurate customer & item management  
✔ Secure user authentication  

---

## 🚀 Core Functionalities

| Module | Features | Access Level |
|--------|----------|-------------|
| **🔐 Authentication** | Secure login with session management, role validation | Admin & Staff|
| **👥 Staff Management** | Add, edit, view, search staff accounts | Admin Only |
| **👨‍💼 Customer Management** | Add, edit, view, search customer accounts | Admin & Staff |
| **📦 Item Management** | Add, edit, view, search inventory items | Admin Only |
| **🧾 Billing System** | Generate invoices, calculate totals, apply discounts | Admin & Staff |
| **❓ Help Section** | User guides and system documentation | All Users |

---


### Additional Features
- Role-based access control
- Data validation and error handling
 


---

### 🛠 Tech Stack
| Technology | Usage |
|------------|-------|
| *Java (JSP/Servlets)* | Backend Logic |
| *Tomcat 9* | Application Server |
| *MySQL (phpMyAdmin via WAMP)* | Database Management |
| *IntelliJ IDEA Ultimate* | Development Environment |
| *HTML, CSS, JSP* | User Interface |
| *GitHub* | Version Control |

---


## 📋 Prerequisites

Ensure the following are installed on your system:

- **Java Development Kit (JDK)** - Version 17 or higher
- **IntelliJ IDEA Ultimate** - Latest version recommended
- **WAMP Server** - Includes Apache, MySQL, and PHP
- **Apache Tomcat** - Version 9.x
- **Git** - Latest version
- **Web Browser** - Chrome, Firefox, or Edge (latest versions)


---

### ⚙ Setup & Installation

1. *Clone the Repository*
   ```bash
   git clone https://github.com/your-username/pahana-edu-billing-system.git

### ⚙ DB Setup
01. *Start WAMP Server*
<li>Launch WAMP Server</li>
<li>Ensure all services (Apache, MySQL) are running (green icon)</li>

02. *Create the Database*
<li>Open phpMyAdmin at: http://localhost/phpmyadmin</li>
<li>Launch WAMP Server</li>
<li>Run the following SQL commands:</li>

```sql
CREATE DATABASE pahanaedu_db;
USE pahanaedu_db;
```

03. *Create Tables*
   
```sql
-- Users Table
CREATE TABLE users (
    id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    username VARCHAR(50) UNIQUE,
    password VARCHAR(100),
    role ENUM('Admin', 'Staff') NOT NULL
);

-- Customers Table
CREATE TABLE customers (
    accountNo VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    address VARCHAR(255),
    telephone VARCHAR(15)
);

-- Items Table
CREATE TABLE items (
    itemId VARCHAR(20) PRIMARY KEY,
    itemName VARCHAR(100) NOT NULL,
    category VARCHAR(50),
    price INT NOT NULL,
    unit INT NOT NULL
);

-- Bills Table
CREATE TABLE bills (
    billId INT AUTO_INCREMENT PRIMARY KEY,
    customerId VARCHAR(20) NOT NULL,
    totalAmount DOUBLE NOT NULL,
    discount DOUBLE DEFAULT 0,
    finalAmount DOUBLE NOT NULL,
    billDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customerId) REFERENCES customers(accountNo)
);

-- Bill Items Table
CREATE TABLE bill_items (
    billItemId INT AUTO_INCREMENT PRIMARY KEY,
    billId INT NOT NULL,
    itemId VARCHAR(20) NOT NULL,
    unit INT NOT NULL,
    unitPrice DOUBLE NOT NULL,
    totalPrice DOUBLE NOT NULL,
    FOREIGN KEY (billId) REFERENCES bills(billId),
    FOREIGN KEY (itemId) REFERENCES items(itemId)
);

```

### 🔑 Default Credentials

The system comes with a preconfigured admin account.  
Make sure the `role` is set manually as `Admin` in the `users` table.

| ID       | Username | Password   | Role  |
|----------|----------|------------|-------|
| S001   | admin    | admin123   | Admin |

```sql
INSERT INTO users (id, name, email, username, password, role)
VALUES ('S001', 'Admin User', 'admin@example.com', 'admin', 'admin123', 'Admin');
```

---
### 🛠️ Troubleshooting

**Common Issues**

1. **Database Connection Failed**  
   - Make sure WAMP Server is running.  
   - Check database credentials in the project configuration.

2. **Port Already in Use**  
   - Change Tomcat server port in configuration.  
   - Identify and stop any programs currently using the same port.  

3. **Build Failures**  
   - Clean and rebuild the project in IntelliJ IDEA.  
   - Check that all Maven dependencies are installed correctly.
  
---
### 📦 Maven Dependencies

- `javax.servlet-api:4.0.1` → Servlet support (provided by Tomcat)  
- `mysql-connector-java:8.0.33` → MySQL JDBC driver  
- `junit:4.13.2` → For unit testing
   
