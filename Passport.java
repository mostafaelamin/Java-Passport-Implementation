package com.passport.system;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a passport with detailed personal information and travel stamps.
 * This class is immutable to ensure data integrity once created.
 */
public final class Passport {

    private final UUID passportId;
    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final LocalDate dateOfBirth;
    private final String nationality;
    private final LocalDate expirationDate;
    private final StringBuilder passportStamps;

    /**
     * Private constructor to be used by the Builder.
     */
    private Passport(Builder builder) {
        this.passportId = UUID.randomUUID();
        this.firstName = builder.firstName;
        this.middleName = builder.middleName;
        this.lastName = builder.lastName;
        this.dateOfBirth = builder.dateOfBirth;
        this.nationality = builder.nationality;
        // Expiration date is 10 years from the date of birth for simplicity
        this.expirationDate = builder.dateOfBirth.plusYears(10);
        this.passportStamps = new StringBuilder();
    }

    // --- Getters ---
    public UUID getPassportId() {
        return passportId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public String getPassportStamps() {
        return passportStamps.toString();
    }

    /**
     * Adds a travel stamp to the passport.
     * @param stamp The country or location to add as a stamp.
     */
    public void addStamp(String stamp) {
        if (stamp != null && !stamp.trim().isEmpty()) {
            if (this.passportStamps.length() > 0) {
                this.passportStamps.append(", ");
            }
            this.passportStamps.append(stamp.trim());
        }
    }

    /**
     * Checks if the passport is expired relative to the current date.
     * @return true if the passport is expired, false otherwise.
     */
    public boolean isExpired() {
        return LocalDate.now().isAfter(expirationDate);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String middle = (middleName == null || middleName.isEmpty()) ? "" : " " + middleName;
        return String.format(
            "Passport [ID=%s, Name=%s%s %s, DOB=%s, Nationality=%s, Expires=%s, Expired=%b]",
            passportId.toString().substring(0, 8),
            firstName,
            middle,
            lastName,
            dateOfBirth.format(formatter),
            nationality,
            expirationDate.format(formatter),
            isExpired()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passport passport = (Passport) o;
        return Objects.equals(firstName, passport.firstName) &&
               Objects.equals(middleName, passport.middleName) &&
               Objects.equals(lastName, passport.lastName) &&
               Objects.equals(dateOfBirth, passport.dateOfBirth) &&
               Objects.equals(nationality, passport.nationality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, middleName, lastName, dateOfBirth, nationality);
    }

    /**
     * Builder class for creating Passport instances.
     * Enforces required fields and provides a fluent interface.
     */
    public static class Builder {
        private String firstName;
        private String lastName;
        private LocalDate dateOfBirth;
        private String nationality;
        private String middleName = "";

        public Builder(String firstName, String lastName, LocalDate dateOfBirth, String nationality) {
            this.firstName = Objects.requireNonNull(firstName, "First name cannot be null.");
            this.lastName = Objects.requireNonNull(lastName, "Last name cannot be null.");
            this.dateOfBirth = Objects.requireNonNull(dateOfBirth, "Date of birth cannot be null.");
            this.nationality = Objects.requireNonNull(nationality, "Nationality cannot be null.");
        }

        public Builder middleName(String middleName) {
            this.middleName = middleName;
            return this;
        }

        public Passport build() throws InvalidPassportDataException {
            validate();
            return new Passport(this);
        }

        private void validate() throws InvalidPassportDataException {
            if (firstName.trim().isEmpty() || !firstName.matches("[a-zA-Z.-]+")) {
                throw new InvalidPassportDataException("Invalid first name format.");
            }
            if (lastName.trim().isEmpty() || !lastName.matches("[a-zA-Z.-]+")) {
                throw new InvalidPassportDataException("Invalid last name format.");
            }
            if (dateOfBirth.isAfter(LocalDate.now())) {
                throw new InvalidPassportDataException("Date of birth cannot be in the future.");
            }
             this.firstName = capitalize(firstName);
             this.lastName = capitalize(lastName);
             if(this.middleName != null && !this.middleName.isEmpty()){
                this.middleName = capitalize(this.middleName);
             }
        }

        private String capitalize(String name) {
            if (name == null || name.isEmpty()) {
                return name;
            }
            return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        }
    }
}

