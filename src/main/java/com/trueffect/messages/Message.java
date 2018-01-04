package com.trueffect.messages;

/*
 * @author santiago.mamani
 */
public class Message {

    // Message of Empty
    public static String EMPTY_DATA = "The [{typeData}] is required";
    //Message dates incorrect
    public static String NOT_VALID_DATA = "The [{typeData}] [{data}] is not valid. The valid format is as follows: ({valid})";
    //Message same type identifier
    public static String NOT_SAME_TYPE = "The [{typeData1}] [{data1}] is not of the [{data2}] [{typeData2}]";
    //Message de restrictions    
    public static String SIZE_MAX = "The [{typeData}] [{data}] is very long, must be less than ({size} characters)";
    public static String REFERENCE_PHONE = "At least [{data} phone numbers] are required.";
    //Message Age
    public static String NOT_MEET_THE_AGE = "To be registered must be greater than ({data} years)";
    //Message duplicate
    public static String DUPLICATE = "The [{typeData}] [{data}] already exists";
    // Message of permission
    public static String NOT_HAVE_PERMISSION = "The user don't have permission to [{operation}] the [{typeData}]";
    // Message of results
    public static String NOT_FOUND = "The [{object}] requested record was not found";
    public static String NOT_FOUND_USER_MODIFY = "the user who wants to modify does not exist";
    public static String CONFLCT_ID = "The identifier not corresponde to value of renter user";
    // Data renter user
    public static String TYPE_IDENTIFIER = "Type identifier";
    public static String IDENTIFIER = "Identifier";
    public static String LAST_NAME = "Last name";
    public static String FIRST_NAME = "First name";
    public static String GENRE = "Genre";
    public static String BIRTHDAY = "Birthday";
    public static String IDENTIFIERS = "Identifiers";
    public static String NAMES = "Names";
    // Data employee max movie
    public static String DATE_OF_HIRE = "Date of hire";
    public static String ADDRESS = "Address";
    public static String JOB = "Job";
    public static String PHONE = "Reference Phone";
    //Data Valid person
    public static String VALID_TI = "CI, PASS , NIT";
    public static String VALID_I = "CI = 123455, PASS = ABD122334X , NIT=8679232456";
    public static String VALID_LN = "Letters = A-Za-z, apostrophes = '";
    public static String VALID_FN = "Letters = A-Za-z, apostrophes = '";
    public static String VALID_G = "M,F";
    public static String VALID_B = "YYYY-MM-DD";
    public static String VALID_STATUS = "Active, Inactive";
    //data Valid Employee
    public static String VALID_JOB = "Cashier, Custom care , Manager";
    public static String VALID_PHONE = "77973186, 71XXXXXX";
    //Description
    public static String CI_DESCRIPTION = "Identity Card";
    public static String PASS_DESCRIPTION = "Passport";
    public static String NIT_DESCRIPTION = "Tributary Identification Number";
    public static String M_DESCRIPTION = "Male";
    public static String F_DESCRIPTION = "Female";
}
