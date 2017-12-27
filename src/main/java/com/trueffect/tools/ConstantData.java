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
        M, F
    }

    public enum TypeIdentifier {
        CI, PASS, NIT
    }

    public enum StatusPerson {
        Active, Inactive
    }

    public enum JobName {
        Cashier, CustomCare, Manager, Administrator
    }
}
