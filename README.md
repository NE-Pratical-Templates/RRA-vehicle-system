

# 🚗 Rwanda Vehicle Tracking Management System

## 📌 Project Overview

This is a backend application developed using **Spring Boot 3**, **Spring Security 6**, and **PostgreSQL** to manage vehicle tracking and ownership records for the **Rwanda Revenue Authority (RRA)**. The system allows for vehicle registration, owner management, ownership transfers, and tracking the history of ownership changes.

> ⏱️ This project was built to meet the requirements of the Rwanda Revenue Authority's vehicle management system.

---

## 🚀 Features

- **User Management**:
    - User registration with roles (**ROLE_ADMIN**, **ROLE_STANDARD**).
    - User login using **JWT** authentication.

- **Vehicle Owner Management**:
    - Register vehicle owners with personal details.
    - Link multiple plate numbers to a single owner.
    - Search vehicle owners by **National ID**, **email**, or **phone number**.
    - Register plate numbers for owners and display associated plate numbers.

- **Vehicle Registration**:
    - Register vehicles with details such as **chassis number**, **manufacturer**, **price**, **model**, and **year**.
    - Link vehicles to specific owners and plate numbers.

- **Vehicle Transfer**:
    - Transfer vehicle ownership between registered users.
    - Capture the transfer price and assign a new plate number.
    - Mark old plate numbers as **available** once the transfer is complete.

- **Ownership History**:
    - Display the history of ownership changes for a vehicle.
    - Track the transfer date, previous owner, and sale price.

---

## 🛠️ Tech Stack

| Technology       | Version     |
|------------------|-------------|
| Spring Boot      | 3.x         |
| Spring Security  | 6.x         |
| PostgreSQL       | Latest      |
| Swagger UI       | Enabled     |

---

## 🔐 API Documentation

> Accessible via Swagger UI:

[http://localhost:9091/swagger-ui/index.html](http://localhost:9091/swagger-ui/index.html)

---

## 🧾 Database Configuration

In `application.properties` or `application.yml`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/vehicle_tracking_db
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## 💻 Running the Project

1. Clone the repository
2. Set up PostgreSQL database and create a schema for the application.
3. Configure `application.properties` for database connection and email settings.
4. Ensure the directories for file uploads exist and are writable.
5. Run the Spring Boot application.

---

## 📬 Email Notifications

Ensure that your email service is configured to send notifications on vehicle transfer actions. This can be set in `application.properties`:

```properties
spring.mail.host=smtp.yourprovider.com
spring.mail.port=587
spring.mail.username=your_email
spring.mail.password=your_email_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

---

## 🧪 Manual Testing

- **Postman** or **Swagger**: Test API endpoints.
- **Database**: Check data directly in PostgreSQL using tools like pgAdmin or DBeaver.

---

## 📝 Tables Overview

- **User**: `id`, `name`, `email`, `phone`, `national_id`, `role`, `password`
- **Owner**: `id`, `name`, `national_id`, `email`, `phone`, `address`
- **PlateNumber**: `id`, `owner_id`, `plate_number`, `issued_date`
- **Vehicle**: `id`, `chassis_number`, `manufacturer`, `model`, `price`, `year`, `owner_id`, `plate_number_id`
- **TransferHistory**: `id`, `vehicle_id`, `old_owner_id`, `new_owner_id`, `sale_price`, `transfer_date`

---

## ⚠️ Notes

- **User Authentication**: Admin users can perform all operations while standard users have limited access.
- **File Uploads**: Ensure the necessary directories exist for storing vehicle-related documents and profile images.

---

## 🧑‍💻 Author

Developed by **Jean de Dieu**

---

## 📄 License

This project is for educational and evaluation purposes only.

---

This structure should provide a clear overview of the project while maintaining a professional format for the repository. Let me know if you need further adjustments or additions!