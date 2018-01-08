package com.trueffect.tools;

/*
 * @author santiago.mamani
 */
public class ConstantData {

    //DATA MAXIMUN
    public static int MAX_LENGTH_NAME = 50;
    public static int MAX_LENGTH_IDENTIFIER = 10;
    public static int MAX_LENGTH_ADDRESS = 100;
    //DATA MINIMUM 
    public static int MIN_AGE_RENTER = 15;
    public static int MIN_AGE_EMPLOYEE = 18;
    public static int MIN_AMOUNT_PHONE = 2;
    // DATA FOR THE MESSAGES
    public static String TYPE_DATA = "{typeData}";
    public static String TYPE_DATA_TWO = "{typeData2}";
    public static String DATA = "{data}";
    public static String DATA_TWO = "{data2}";
    public static String VALID = "{valid}";
    public static String SIZE = "{size}";
    public static String OPERATION = "{operation}";
    public static String OBJECT = "{object}";
    // valid data
    public static String VALID_IDENTIFIER = "CI = 123455, PASS = ABD122334X , NIT=8679232456";
    public static String VALID_LASTNAME = "Letters = A-Za-z, apostrophes = '";
    public static String VALID_FIRSTNAME = "Letters = A-Za-z, apostrophes = '";
    public static String VALID_BIRTHDAY = "YYYY-MM-DD";
    public static String VALID_PHONE = "77973186, 71XXXXXX";
    // Data renter user
    public static String TYPE_IDENTIFIER = "Type identifier";
    public static String IDENTIFIER = "Identifier";
    public static String LAST_NAME = "Last name";
    public static String FIRST_NAME = "First name";
    public static String GENRE = "Genre";
    public static String BIRTHDAY = "Birthday";
    public static String NAME = "Name";
    // Data employee max movie
    public static String DATE_OF_HIRE = "Date of hire";
    public static String ADDRESS = "Address";
    public static String JOB = "Job";
    public static String PHONE = "Reference Phone";

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
        CI("Identity Card"), PASS("Passport"), NIT("Tax Identification Numbe");
        private String description;

        private TypeIdentifier(String typeId) {
            this.description = typeId;
        }

        public String getDescription() {
            return description;
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
        RennterUser, Employee, Movie
    }
}
