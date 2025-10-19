Java Passport Implementation System

A robust, modern Java application for managing the lifecycle of digital passports, built with a focus on data integrity, architectural design patterns, and comprehensive testing.

The Inspiration: A Traveler's Nightmare

We've all been there—or at least, we've all heard the horror stories. A friend excitedly plans a trip for months, only to be stopped at the check-in counter because of a simple typo in their passport details. Or worse, a family realizes a child's passport has expired just days before a long-awaited vacation. Travel should be about adventure and creating memories, not wrestling with bureaucracy and data errors.

This project was born from that frustration. It's an answer to the question: "What if we could build a digital system so reliable and intelligent that these common passport problems could be prevented before they even start?" The goal was to engineer a backend system that treats passport data with the seriousness it deserves, ensuring every record is valid, secure, and correctly managed from issuance to expiration.

Core Features

This isn't just a simple data storage program. It's a simulation of a professional-grade, service-oriented system that demonstrates modern software engineering principles.

Immutable Data Models: The Passport objects are immutable. Once created, their core details cannot be changed, preventing accidental data corruption and ensuring record integrity.

Builder Design Pattern: Passports are created using a robust Builder pattern, which validates all data before an object is instantiated. This makes it impossible to create a Passport with invalid information, like a date of birth in the future.

Service-Oriented Architecture: The PassportOffice class acts as a central service layer, separating the business logic (how to issue or revoke a passport) from the underlying data storage.

Singleton Database: A singleton PassportDB class simulates an in-memory database, guaranteeing that the entire application uses a single, consistent data source.

Custom Exception Handling: The system uses a custom InvalidPassportDataException to provide clear, specific feedback when data validation fails.

Comprehensive Unit Testing: The entire system is backed by a suite of JUnit 5 tests (PassportTest), ensuring that every component works as expected and is resilient to errors.

Unique ID Generation: Each passport is assigned a UUID, the standard for generating unique identifiers in modern applications.

Project Structure

The system is organized into distinct layers, each with a clear responsibility:

Passport.java: The core, immutable data model representing a passport.

PassportOffice.java: The service layer that contains all business logic (issuing, revoking, updating).

PassportDB.java: The singleton data layer that simulates the database.

InvalidPassportDataException.java: A custom exception for handling data validation errors.

Driver.java: A simple application to simulate the functionality of the PassportOffice.

PassportTest.java: A comprehensive JUnit 5 test suite to ensure the system is working correctly.
