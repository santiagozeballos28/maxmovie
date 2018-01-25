package com.trueffect.tools;

/*
 * @author santiago.mamani
 */
public class ConstantData {

    //DATA MAXIMUN
    public static int MAX_LENGTH_NAME = 50;
    public static int MAX_LENGTH_IDENTIFIER = 10;
    public static int MAX_LENGTH_ADDRESS = 100;
    public static int MAX_NAME_MOVIE = 256;
    public static int MAX_NAME_ACTOR_MOVIE = 256;
    public static int MAX_NAME_DIRECTOR_MOVIE = 256;
    public static int MAX_NAME_GENRE_MOVIE = 120;
    public static int MAX_YEAR_MOVIE = 9999;
    public static int MAX_OSCAR_NOMINATION_MOVIE = 100;
    //DATA MINIMUM 
    public static int MIN_AGE_RENTER = 15;
    public static int MIN_AGE_EMPLOYEE = 18;
    public static int MIN_AMOUNT_PHONE = 2;
    public static int MIN_YEAR = 1900;
    public static int MIN_OSCAR_NOMINATION = 0;
    // DATA FOR THE MESSAGES
    public static String TYPE_DATA = "{typeData}";
    public static String TYPE_DATA_TWO = "{typeData2}";
    public static String DATA = "{data}";
    public static String DATA_TWO = "{data2}";
    public static String VALID = "{valid}";
    public static String SIZE = "{size}";
    public static String OPERATION = "{operation}";
    public static String OBJECT = "{object}";
    public static String NAME_OBJECT = "{nameObject}";
    // valid data
    public static String VALID_LASTNAME = "Letters = A-Za-z, apostrophes = '";
    public static String VALID_FIRSTNAME = "Letters = A-Za-z, apostrophes = '";
    public static String VALID_PHONE = "7XXXXXXX(eight numbers), 6XXXXXXX(seven numbers), XXXXXXX(seven numbers)";
    // Data renter user
    public static String TYPE_IDENTIFIER = "type identifier";
    public static String IDENTIFIER = "identifier";
    public static String LAST_NAME = "last name";
    public static String FIRST_NAME = "lirst name";
    public static String GENRE = "genre";
    public static String BIRTHDAY = "birthday";
    public static String NAME = "name";
    // Data employee max movie
    public static String DATE_OF_HIRE = "date of hire";
    public static String ADDRESS = "address";
    public static String JOB = "job";
    public static String PHONE = "reference phone";
    //Data Generic
    public static String ID = "id";
    public static String STATUS = "status";
    public static String YEAR = "Year";
    public static String US_ASCII = "US-ASCII";
    // Valid data generic
    public static String VALID_DATE = "YYYY-MM-DD";
    //Data movie
    public static String NAME_MOVIE = "Name movie";
    public static String NAME_ACTOR_MOVIE = "Name actor";
    public static String NAME_GENRE_MOVIE = "Name genre";
    public static String NAME_DIRECTOR_MOVIE = "Name director";
    public static String OSCAR_NOMINATION_MOVIE = "Oscar nomination";
    //Valid data movie
    public static String VALID_NAME_MOVIE = "Letters = A-Za-z, Numbers = 0-9";
    public static String VALID_NAME_GENRE_MOVIE = "Letters = A-Za-z";
    public static String VALID_US_ASCII = "US-ASCII";
    public static String VALID_YEAR = "YYYY, Example: year = 1999";
    public static String VALID_OSCAR_NOMINARION = "0-100";
    // Date format to validate
    public static String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";
    public static String POSITIVE_NUMBERS = "positive numbers";
    //sale
    public static String AMOUNT_MOVIES = "amount the movies";
    public static String OPERATION_SALE = "type of sale";

    public enum ValidIdentifier {
        CI("all number"), PASS("3 letters 6 numbers [0-1] letter"), NIT("all number");
        private String validIdentifier;

        private ValidIdentifier(String validIdentifier) {
            this.validIdentifier = validIdentifier;
        }

        public String getValidIdentifier() {
            return validIdentifier;
        }
    }

    public enum GenrePerson {
        M("Male"), F("Female");
        private String nameGenre;

        private GenrePerson(String nameGenre) {
            this.nameGenre = nameGenre;
        }

        public String getNameGenre() {
            return nameGenre;
        }
    }

    public enum TypeIdentifier {
        CI("Identity Card"), PASS("Passport"), NIT("Tax Identification Number");
        private String descriptionIdentifier;

        private TypeIdentifier(String description) {
            this.descriptionIdentifier = description;
        }

        public String getDescriptionIdentifier() {
            return descriptionIdentifier;
        }
    }

    public enum Status {
        Active, Inactive
    }

    public enum JobName {
        CSHR("Cashier"), CC("Custom care"), MGR("Manager"), ADMIN("Administrator");
        private String descriptionJobName;

        private JobName(String descriptionJobName) {
            this.descriptionJobName = descriptionJobName;
        }

        public String getDescriptionJobName() {
            return descriptionJobName;
        }
    }

    public enum Crud {
        create, get, update, delete
    }

    public enum ObjectMovie {
        RennterUser("renter user"), Employee("employee"), Movie("movie"), Copy("copy"), Sale("sale"), MovieReport("movie report");
        private String description;

        private ObjectMovie(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum Ocupation {
        Rents, Works
    }

    public enum OperationSale {
        R("Rental"), B("Buy"), BP("Buy Premier");
        private String description;

        private OperationSale(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
