# Rental of Pets

## Overview
"Rental of Pets" is a web application designed to facilitate the rental of pets. It allows users to rent pets for a specified duration to see if they can live with animals. Users can view available pets, make reservations, and manage their bookings. The application is built using Java with Spring Boot on the backend and Thymeleaf for server-side rendering.

## Features
- User Authorization and Authentication
- Viewing Available Pets
- Making and Managing Bookings
- User Notifications
- User settings
- Admin Panel for Managing Pets, Bookings, Users
- Admin Server
- Google Map

## Technologies Used
- Java
- Spring
- Thymeleaf
- Hibernate
- PostgreSQL
- Lombok
- Maven
- QueryDSL
- Liquibase
- Swagger
- CSS3

## Getting Started

### Prerequisites
- JDK 17 or later
- Maven
- PostgreSQL

# Install dependencies
 ```

mvn clean install

 ```
### DataBase
![db-structure](https://github.com/yaroslaavl/Rental-of-Pets-Szczecin/assets/149341488/88be752c-4db4-410e-96d2-2c010f00cf97)

### Setup PostgreSQL
![image](https://github.com/yaroslaavl/Rental-of-Pets-Szczecin/assets/149341488/7ee16c15-4dd7-4429-b16f-647814da2cbb)


### Configuring Email Settings
To properly configure email settings in your application:

Host: Set host to the SMTP server hostname. Example: smtp.example.com.

Port: Specify the port number used by the SMTP server. Example: 465 for SSL/TLS.

Username: Enter the email address used for authentication on the SMTP server. Example: your-email@example.com.

Password: To generate a 16-digit key for email configuration, follow these steps:

Log in to your Email Service Provider:
Access the website of your email service provider (e.g., Gmail, Yahoo Mail) and log in to your account.

Navigate to API or App Settings:
Find the section related to API access or app settings. Look for options related to generating API keys or app-specific passwords.

Generate New API Key or App Password:
Depending on the provider, select the option to generate a new API key or app password. This key will be used to authenticate your application when sending emails.

Copy and Save the Key:
Once generated, copy the 16-digit key or password provided by the email service provider. Ensure to save it securely as it will not be shown again.

Ensure these settings are correctly configured in application.yml file to enable email functionality in application.

![image](https://github.com/yaroslaavl/Rental-of-Pets-Szczecin/assets/149341488/7777e2a4-0728-4300-8501-0a8f91830624)

### Configuring OAuth2 for Google Authentication
To configure OAuth2 for Google authentication:

Client ID: Set client-id to your Google OAuth2 client ID obtained from the Google Developer Console.

Client Secret: Use your client-secret provided by Google when registering your application.

Redirect URI: Specify the redirect-uri where Google should redirect users after authentication. Example: http://localhost:8080/login/oauth2/code/google.

Scope: Define scope to determine the level of access granted to your application. Common scopes include openid, email, and profile.

These settings are essential for enabling Google OAuth2 authentication in your application. Ensure they are correctly configured to facilitate seamless user authentication with Google services.

### Installation
1. Clone the repository:


git clone https://github.com/yaroslaavl/Rental-of-Pets-Szczecin.git

### Running Spring Boot Admin Server
  ```

mvn -pl adminServer spring-boot:run

 ```

### Running Spring Boot Server
 ```

mvn spring-boot:run

```

### Contact
Email - ylopatkin@gmail.com
