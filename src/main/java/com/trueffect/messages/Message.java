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
    public static String NOT_VALID_TYPE_IDENTIFIER = "The type of dentifier not is valid";
    public static String NOT_VALID_IDENTIFIER = "The dentifier not's  valid";
    public static String NOT_VALID_LAST_NAME = "The last name not's valid";
    public static String NOT_VALID_FIRST_NAME = "The first name not's valid";
    public static String NOT_VALID_GENRE = "The genre not's valid";
    public static String NOT_VALID_BIRTHDAY = "The date format not's valid";
    public static String NOT_SAME_TYPE = "The identifier is not the same type";
    //Message de restrictions    
    public static String SIZE_IDENTIFIER = "The identifier is very long, must be less than 10 characters";
    public static String SIZE_LAST_NAME = "The last name is very long, must be less than 50 characters";
    public static String SIZE_FIRST_NAME = "The first name is very long, must be less than 50 characters";
    public static String DUPLICATE_IDENTIFIER = "The identifier already exists";
    public static String NOT_MEET_THE_AGE = "To be registered must be greater than 15 years";
    public static String THE_NAMES_ALREADY_EXIST = "The names already exist";
    // Message of results
    public static String NOT_FOUND = "The requested resource does not exist";
    public static String NOT_RESOURCE = "There not's resource";
    // Message of permission
    public static String NOT_HAVE_PERMISSION_TYPE_IDENTIFIER = "You do not have permission to modify type identifier";
    public static String NOT_HAVE_PERMISSION_IDENTIFIER = "You do not have permission to modify identifier";
    public static String NOT_HAVE_PERMISSION_FOR_MODIFY = "You do not have permission for modify";

}
