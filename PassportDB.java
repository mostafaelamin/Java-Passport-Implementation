package com.passport.system;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * A simulated in-memory database for storing and retrieving Passport objects.
 * This class follows a Singleton pattern to ensure a single data source.
 */
public class PassportDB {

    private static final Logger LOGGER = Logger.getLogger(PassportDB.class.getName());
    private static PassportDB instance;
    private final Map<UUID, Passport> passportTable;

    private PassportDB() {
        passportTable = new HashMap<>();
    }

    /**
     * Gets the singleton instance of the database.
     * @return The single instance of PassportDB.
     */
    public static synchronized PassportDB getInstance() {
        if (instance == null) {
            instance = new PassportDB();
        }
        return instance;
    }

    /**
     * Saves a passport to the database.
     * @param passport The passport to save.
     */
    public void save(Passport passport) {
        passportTable.put(passport.getPassportId(), passport);
        LOGGER.info("Saved passport: " + passport.getPassportId());
    }

    /**
     * Finds a passport by its ID.
     * @param id The UUID of the passport.
     * @return An Optional containing the passport if found, otherwise empty.
     */
    public Optional<Passport> findById(UUID id) {
        return Optional.ofNullable(passportTable.get(id));
    }

    /**
     * Deletes a passport by its ID.
     * @param id The UUID of the passport to delete.
     * @return true if the passport was found and deleted, false otherwise.
     */
    public boolean deleteById(UUID id) {
        if (passportTable.remove(id) != null) {
            LOGGER.info("Deleted passport: " + id);
            return true;
        }
        LOGGER.warning("Attempted to delete non-existent passport: " + id);
        return false;
    }

    /**
     * Clears all records from the database.
     */
    public void clear() {
        passportTable.clear();
        LOGGER.info("Passport database cleared.");
    }

    /**
     * Gets the current number of passports in the database.
     * @return The count of passports.
     */
    public int count() {
        return passportTable.size();
    }
}
