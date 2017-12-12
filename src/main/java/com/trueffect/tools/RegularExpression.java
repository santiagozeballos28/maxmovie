
package com.trueffect.tools;
/*
 * @author santiago.mamani
 */
public class RegularExpression {
    public static String DATE = "^\\d{1,2}/\\d{1,2}/\\d{4}$";
    public static String CI="^[0-9]{6}$";
    public static String PASS="^[A-Z]{3}[0-9]{6}[A-Z]?$";
    public static String NIT="^[0-9]{6,}$";
    public static String LAST_NAME_TWO="^[A-Z]{1}[a-z]{1,}\\s{1}[A-Z]{1}[a-z]{1,}$";
    public static String LAST_NAME_ONE="^[A-Z]{1}[a-z]{2,}$";
    public static String FIRST_NAME="^[A-Z]{1}[a-z]{2,}$";
}
