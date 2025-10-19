package com.passport.system;

/**
 * Custom exception for handling invalid data when creating or updating a Passport.
 */
public class InvalidPassportDataException extends Exception {

    public InvalidPassportDataException(String message) {
        super(message);
    }

    public InvalidPassportDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
