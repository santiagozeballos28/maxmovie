package com.trueffect.tools;

/*
 * @author santiago.mamani
 */
public class ConstantData {

    public static int MAXIMUM_NAMES = 50;
    public static int MAXIMUM_IDENTIFIER = 10;
    public static int MINIMUM_AGE = 15;

    public enum Genre {
        M, F
    }

    public enum TypeIdentifier {
        CI, PASS, NIT
    }

    public enum EmployeeWithPermissionModify {
        Administrator, Manager
    }
    public enum StatusPerson {
        Active,Inactive
    }
}
