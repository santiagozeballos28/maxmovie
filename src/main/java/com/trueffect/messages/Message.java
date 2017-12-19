package com.trueffect.messages;

/*
 * @author santiago.mamani
 */
public class Message {

    // Message of Empty
    public static String EMPTY_TYPE_IDENTIFIER = "The type of identifier is required";
    public static String EMPTY_IDENTIFIER = "The identifier is required";
    public static String EMPTY_LAST_NAME = "The last name is required";
    public static String EMPTY_FIRST_NAME = "The firt name is required";
    public static String EMPTY_GENRE = "The genre is required";
    public static String EMPTY_BITHDAY = "The birthday is required";
    //Message dates incorrect
    public static String NOT_VALID_TYPE_IDENTIFIER = "The type of identifier is not valid";
    public static String NOT_VALID_IDENTIFIER = "The identifier is not valid";
    public static String NOT_VALID_LAST_NAME = "Can not use symbols except ('). Exmaple of the last names valids: Zeballos, O'relly";
    public static String NOT_VALID_FIRST_NAME = "Can not use symbols except ('). Exmaple of the First names valids: Santiago, Eve'lin";
    public static String NOT_VALID_GENRE = "The genre is not valid";
    public static String NOT_VALID_BIRTHDAY = "The date format is not valid. The valid format is as follows: yyy-MM-dd";
    public static String NOT_SAME_TYPE = "The identifier is not the same type. It must be an identity card, passport or tax identification numbe";
    //Message de restrictions    
    public static String SIZE_IDENTIFIER = "The identifier is very long, must be less than 10 characters";
    public static String SIZE_LAST_NAME = "The last name is very long, must be less than 50 characters";
    public static String SIZE_FIRST_NAME = "The first name is very long, must be less than 50 characters";
    public static String DUPLICATE_IDENTIFIER = "The identifier already exists";
    public static String NOT_MEET_THE_AGE = "To be registered must be greater than 15 years";
    public static String THE_NAMES_ALREADY_EXIST = "The names already exist";
    
    // Message of results
    public static String NOT_FOUND = "The requested resource does not exist";
    public static String NOT_FOUND_USER_MODIFY = "the user who wants to modify does not exist";
    public static String NOT_RESOURCE = "There is not resource";//
    public static String CONFLCT_ID = "The identifier not corresponde to value of renter user";
    // Message of permission
    public static String NOT_HAVE_PERMISSION_TYPE_IDENTIFIER = "The user don't have permission to modify type identifier";
    public static String NOT_HAVE_PERMISSION_IDENTIFIER = "The user don't have permission to modify identifier";
    public static String NOT_HAVE_PERMISSION_FOR_MODIFY = "The user don't have permission to modify";

}
