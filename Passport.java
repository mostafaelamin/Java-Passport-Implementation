package programs;

public class Passport {
    // Instance variables for names, separator, and passport stamps
    private String firstname;
    private String middlename;
    private String lastname;
    private char separator;
    private StringBuffer passportStamps;
    // Static variable to keep track of the number of Passport objects created
    private static int numOfPassportObj = 0;

    // Default constructor initializing with sample names
    public Passport() {
        this("Samplefirstname", "Samplemiddlename", "Samplelastname");
    }

    // Constructor with first and last name, initializes middle name as empty
    public Passport(String firstname, String lastname) {
        this.firstname = validateAndFormat(firstname);
        this.middlename = "";
        this.lastname = validateAndFormat(lastname);
        this.separator = ','; 
        this.passportStamps = new StringBuffer(); 
        numOfPassportObj++; 
    }

    // Constructor with first, middle, and last names
    public Passport(String firstname, String middlename, String lastname) {
        this.firstname = validateAndFormat(firstname);
        this.middlename = validateAndFormat(middlename);
        this.lastname = validateAndFormat(lastname);
        this.separator = ','; 
        this.passportStamps = new StringBuffer(); 
        numOfPassportObj++; 
    }

    // Copy constructor for creating a new Passport from an existing one
    public Passport(Passport passport) {
        this.firstname = passport.firstname;
        this.middlename = passport.middlename;
        this.lastname = passport.lastname;
        this.separator = passport.separator;
        this.passportStamps = new StringBuffer(passport.passportStamps.toString()); 
        numOfPassportObj++; 
    }

    // Returns a string representation of the passport
    public String toString() {
        if (middlename.isBlank()) {
            return lastname + separator + firstname; 
        } else {
            return lastname + separator + firstname + separator + middlename; 
        }
    }

    // Adds a stamp to the passport if it's valid
    public Passport addStamp(String stamp) {
        if (stamp != null && !stamp.isBlank()) {
            passportStamps.append(stamp); 
        }
        return this; 
    }

    // Returns a copy of the passport stamps
    public StringBuffer getStamps() {
        return new StringBuffer(passportStamps.toString());
    }

    // Returns the current separator character
    public char getSeparator() {
        return separator;
    }

    // Sets a new separator if it's valid
    public boolean setSeparator(char separator) {
        if (separator == '@' || Character.isSpaceChar(separator) || 
                Character.isLetter(separator)) {
            return false; 
        }
        if (this.separator == separator) {
            return true; 
        }
        this.separator = separator; 
        return true;
    }

    // Checks equality between two Passport objects
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; 
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false; 
        }
        Passport other = (Passport) obj;
        return firstname.equals(other.firstname) && 
               middlename.equals(other.middlename) &&
               lastname.equals(other.lastname);
    }

    // Compares this passport with another for ordering
    public int compareTo(Passport passport) {
        return this.toString().compareTo(passport.toString());
    }

    // Returns the total number of Passport objects created
    public static int getNumberOfPassportObjects() {
        return numOfPassportObj;
    }

    // Resets the count of Passport objects
    public static void resetNumberOfPassportObjects() {
        numOfPassportObj = 0;
    }

    // Normalizes name case based on the upper case parameter
    public static Passport normalize(Passport passport, boolean uppercase) {
        if (passport == null) {
            return null; 
        }

        if (uppercase) {
            passport.firstname = passport.firstname.toUpperCase();
            passport.lastname = passport.lastname.toUpperCase();
            passport.middlename = passport.middlename.toUpperCase();
        } else {
            passport.firstname = passport.firstname.toLowerCase();
            passport.lastname = passport.lastname.toLowerCase();
            passport.middlename = passport.middlename.toLowerCase();
        }

        return passport; 
    }

    // Changes the last name if the new name is valid
    public boolean changeLastname(String lastname) {
        if (lastname == null || lastname.isBlank()) {
            return false; 
        } else {
            this.lastname = validateAndFormat(lastname); 
            return true;
        }
    }

    // Validates and formats a given name
    private static String validateAndFormat(String name) {
        if (name == null || name.isBlank()) {
            return " "; 
        } else {
            String trimmedName = name.trim(); 
            String firstLetter = trimmedName.substring(0, 1).toUpperCase(); 
            String remainingLetters = trimmedName.substring(1).toLowerCase(); 
            return firstLetter + remainingLetters; 
        }
    }
}