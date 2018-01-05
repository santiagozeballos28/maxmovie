package com.trueffect.tools;

/*
 * @author santiago.mamani
 */
public class ConstantData {

    //DATA MAXIMUN
    public static int MAXIMUM_NAMES = 50;
    public static int MAXIMUM_IDENTIFIER = 10;
    public static int MAXIMUM_ADDRESS = 100;
    //DATA MINIMUM 
    public static int MINIMUM_AGE_RENTER = 15;
    public static int MINIMUM_AGE_EMPLOYEE = 18;
    public static int MINIMUM_AMOUNT_PHONE = 2;

    public enum Genre {
        M("Male"), F("Female");
        private String nameGenre;

        private Genre(String nameGenre) {
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
    }

    public enum Status {
        Active, Inactive
    }

    public enum JobName {
        Cashier, CustomCare, Manager, Administrator
    }

    public enum Crud {
        create, get, update, delete
    }

    public enum ObjectMovie {
        RennterUser, Employee, Movie
    }
}
