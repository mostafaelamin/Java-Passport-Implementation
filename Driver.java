package com.passport.system;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Driver class to demonstrate the functionality of the Passport Management System.
 */
public class Driver {

    private static final Logger LOGGER = Logger.getLogger("com.passport.system");

    public static void main(String[] args) {
        // Setup basic logging to the console
        setupLogger();

        LOGGER.info("--- Passport Management System Simulation ---");
        PassportOffice office = new PassportOffice();

        // --- Issue a valid passport ---
        System.out.println("\n1. Issuing a valid passport...");
        Optional<Passport> p1Optional = office.issuePassport(
                "John", "Doe", LocalDate.of(1990, Month.JANUARY, 15), "American"
        );
        UUID johnsId = null;
        if (p1Optional.isPresent()) {
            Passport johnsPassport = p1Optional.get();
            johnsId = johnsPassport.getPassportId();
            System.out.println("   Success: " + johnsPassport);
        } else {
            System.out.println("   Failure to issue passport for John Doe.");
        }

        // --- Attempt to issue a passport with invalid data ---
        System.out.println("\n2. Attempting to issue a passport with a future date of birth...");
        office.issuePassport("Jane", "Smith", LocalDate.now().plusDays(1), "Canadian");


        // --- Retrieve and update a passport ---
        if (johnsId != null) {
            System.out.println("\n3. Retrieving and adding stamps to John's passport...");
            office.addTravelStamp(johnsId, "Spain");
            office.addTravelStamp(johnsId, "France");
            office.addTravelStamp(johnsId, "Japan");

            Optional<Passport> retrievedPassport = office.retrievePassport(johnsId);
            retrievedPassport.ifPresent(p -> {
                System.out.println("   Retrieved: " + p);
                System.out.println("   Stamps: " + p.getPassportStamps());
                System.out.println("   Is Expired? " + p.isExpired());
            });
        }

        // --- Revoke a passport ---
        if (johnsId != null) {
            System.out.println("\n4. Revoking John's passport...");
            boolean revoked = office.revokePassport(johnsId);
            System.out.println("   Revocation status: " + (revoked ? "Successful" : "Failed"));
            System.out.println("   Attempting to retrieve again: " + office.retrievePassport(johnsId).isPresent());
        }

        LOGGER.info("\n--- Simulation Complete ---");
    }

    private static void setupLogger() {
        Logger rootLogger = Logger.getLogger("com.passport.system");
        rootLogger.setLevel(Level.ALL);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter());
        handler.setLevel(Level.INFO); // Set console to see INFO and above
        rootLogger.addHandler(handler);
        rootLogger.setUseParentHandlers(false);
    }
}
