# Passport Implementation System

A robust, modern **Java application** for managing the lifecycle of digital passports — built with a focus on **data integrity**, **clean architecture**, and **comprehensive testing**.

---

## The Inspiration: A Traveler’s Nightmare

I have had a trip ruined by digital passport issues. Travel should be about exploring, not anxiety and verification issues. 

This project was inspired by that frustration. It answers a simple question:

> *What if we could build a digital system so reliable and intelligent that passport issues are caught before they ever cause real-world problems?*

The **Passport Management System** is designed to ensure that every passport record is valid, secure, and correctly managed.

---

## Core Features

This project goes beyond simple data storage. It models a professional-grade backend system that demonstrates **modern software engineering principles**.

- **Immutable Data Models**  
  Once created, Passport objects cannot be modified, ensuring absolute data consistency and preventing corruption.

- **Builder Design Pattern**  
  Passports are instantiated via a `Builder` pattern, validating all inputs (e.g., no future birthdates) before creation.

- **Service-Oriented Architecture**  
  The `PassportOffice` class serves as the business logic layer, handling operations like issuing or revoking passports.

- **Singleton Database**  
  The `PassportDB` class simulates a shared in-memory database, guaranteeing a single, consistent data source across the application.

- **Custom Exception Handling**  
  The `InvalidPassportDataException` class provides clear, precise error messages for invalid data.

- **Comprehensive Unit Testing**  
  Backed by **JUnit 5**, the system includes tests that verify integrity, exception handling, and business logic.

- **UUID-Based ID Generation**  
  Every passport is assigned a unique UUID, mirroring modern secure identification practices.
