package com.passport.system;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test class for the Passport Office and related classes.
 */
class PassportTest {

    private PassportOffice office;
    private PassportDB db;

    @BeforeEach
    void setUp() {
        office = new PassportOffice();
        db = PassportDB.getInstance();
        db.clear(); // Ensure a clean state for each test
    }

    @Test
    void testSuccessfulPassportIssuance() {
        Optional<Passport> passportOpt = office.issuePassport("Jane", "Doe", LocalDate.of(1995, 5, 23), "British");
        assertTrue(passportOpt.isPresent(), "Passport should be issued successfully.");
        assertEquals(1, db.count(), "Database should contain one passport.");

        Passport p = passportOpt.get();
        assertEquals("Jane", p.getFirstName());
        assertEquals("Doe", p.getLastName());
        assertFalse(p.isExpired(), "A new passport should not be expired.");
    }

    @Test
    void testPassportIssuanceFailureInvalidDob() {
        Optional<Passport> passportOpt = office.issuePassport("John", "Smith", LocalDate.now().plusYears(1), "German");
        assertTrue(passportOpt.isEmpty(), "Passport should not be issued for a future date of birth.");
        assertEquals(0, db.count(), "Database should be empty after a failed issuance.");
    }

    @Test
    void testPassportIssuanceFailureInvalidName() {
        Optional<Passport> passportOpt = office.issuePassport("  ", "ValidLast", LocalDate.of(2000, 1, 1), "French");
        assertTrue(passportOpt.isEmpty(), "Passport should not be issued for an invalid first name.");
        assertEquals(0, db.count(), "Database should remain empty.");
    }

    @Test
    void testRetrieveAndRevokePassport() {
        Passport p = office.issuePassport("Test", "User", LocalDate.of(1980, 1, 1), "Testican").orElseThrow();
        
        Optional<Passport> retrieved = office.retrievePassport(p.getPassportId());
        assertTrue(retrieved.isPresent(), "Should be able to retrieve an existing passport.");
        assertEquals(p, retrieved.get(), "Retrieved passport should be equal to the original.");

        boolean revoked = office.revokePassport(p.getPassportId());
        assertTrue(revoked, "Revocation of an existing passport should be successful.");
        
        Optional<Passport> shouldBeEmpty = office.retrievePassport(p.getPassportId());
        assertTrue(shouldBeEmpty.isEmpty(), "Passport should not be retrievable after revocation.");
        assertEquals(0, db.count(), "Database should be empty after revocation.");
    }

    @Test
    void testAddStamps() {
        Passport p = office.issuePassport("Stamp", "Collector", LocalDate.of(1992, 11, 30), "Wanderer").orElseThrow();
        office.addTravelStamp(p.getPassportId(), "Brazil");
        office.addTravelStamp(p.getPassportId(), "  Argentina  "); // Test trimming
        
        Passport retrieved = office.retrievePassport(p.getPassportId()).get();
        assertEquals("Brazil, Argentina", retrieved.getPassportStamps());
    }

    @Test
    void testExpiredPassport() {
        // A passport issued to someone born 15 years ago would have expired 5 years ago (10-year validity)
        LocalDate dob = LocalDate.now().minusYears(15);
        Passport p = office.issuePassport("Old", "Timer", dob, "Historic").orElseThrow();
        assertTrue(p.isExpired(), "Passport should be marked as expired.");
    }
}
