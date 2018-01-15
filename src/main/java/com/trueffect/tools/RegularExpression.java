package com.trueffect.tools;

/*
 * @author santiago.mamani
 */
public class RegularExpression {

    public static String DATE = "^\\d{4}-\\d{1,2}-\\d{1,2}$";
    public static String CI = "^[0-9]*$";
    public static String PASS = "^[A-Z]{3}[0-9]{6}[A-Z]?$";
    public static String NIT = "^[0-9]*$";
    public static String LAST_NAME_TWO = "^[A-Za-z]{1,}[A-Za-z']{1,}[A-Za-z]{1,}\\s{1,}[A-Za-z]{1,}[A-Za-z']{1,}[A-Za-z]{1,}$";
    public static String LAST_NAME_ONE = "^[A-Za-z]{1,}[A-Za-z']{1,}[A-Za-z]{1,}$";
    public static String FIRST_NAME = "^[A-Za-z]{1,}[A-Za-z']{1,}[A-Za-z]{1,}$";
    public static String PHONE_CELL = "^(6|7){1}\\d{7}$";
    public static String PHONE_FIXED = "^(2|3|4){1}\\d{6}$";
}
