package com.passport.system;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service layer that handles the business logic for passport management.
 * It interacts with the PassportDB to perform CRUD operations.
 */
public class PassportOffice {

    private static final Logger LOGGER = Logger.getLogger(PassportOffice.class.getName());
    private final PassportDB database;

    public PassportOffice() {
        this.database = PassportDB.getInstance();
    }

    /**
     * Issues a new passport and saves it to the database.
     * @param firstName The first name of the holder.
     * @param lastName The last name of the holder.
     * @param dob The date of birth of the holder.
     * @param nationality The nationality of the holder.
     * @return An Optional containing the new Passport if successful, otherwise empty.
     */
    public Optional<Passport> issuePassport(String firstName, String lastName, LocalDate dob, String nationality) {
        try {
            Passport passport = new Passport.Builder(firstName, lastName, dob, nationality).build();
            database.save(passport);
            LOGGER.log(Level.INFO, "Successfully issued passport for: {0} {1}", new Object[]{firstName, lastName});
            return Optional.of(passport);
        } catch (InvalidPassportDataException e) {
            LOGGER.log(Level.SEVERE, "Failed to issue passport due to invalid data.", e);
            return Optional.empty();
        }
    }

    /**
     * Retrieves a passport by its ID.
     * @param id The UUID of the passport.
     * @return An Optional containing the passport if found.
     */
    public Optional<Passport> retrievePassport(UUID id) {
        return database.findById(id);
    }

    /**
     * Revokes (deletes) a passport from the system.
     * @param id The UUID of the passport to revoke.
     * @return true if revocation was successful, false otherwise.
     */
    public boolean revokePassport(UUID id) {
        return database.deleteById(id);
    }

    /**
     * Adds a travel stamp to an existing passport.
     * @param passportId The ID of the passport to update.
     * @param country The country stamp to add.
     * @return true if the stamp was added successfully, false otherwise.
     */
    public boolean addTravelStamp(UUID passportId, String country) {
        Optional<Passport> passportOptional = database.findById(passportId);
        if (passportOptional.isPresent()) {
            Passport passport = passportOptional.get();
            passport.addStamp(country);
            // In a real system, you'd save the updated state.
            // Here we just modify the in-memory object.
            LOGGER.info("Added stamp for " + country + " to passport " + passportId);
            return true;
        }
        LOGGER.warning("Could not add stamp. Passport not found: " + passportId);
        return false;
    }
}
