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
        CI("Identity Card"), PASS("Passport"), NIT("Tributary Identification Number");
        private String description;

        private TypeIdentifier(String typeId) {
            this.description = typeId;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return CI + ", " + NIT + ", " + PASS;
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
