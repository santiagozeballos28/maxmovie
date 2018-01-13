package com.trueffect.messages;

/*
 * @author santiago.mamani
 */
public class Message {
    // Message of Empty

    public static String MANDATORY_ID = "The {object} primary key is mandatory";
    // Message of Empty
    public static String EMPTY_DATA = "The {typeData} is required";
    //Message dates incorrect
    public static String NOT_VALID_DATA_THE_VALID_DATA_ARE = "The {typeData} ({data}) is not valid. The valid values are: [{valid}]";
    public static String NOT_VALID_DATA = "The {typeData} ({data}) is not valid";
    //Message same type identifier
    public static String NOT_SAME_TYPE = "The {typeData} ({data}) is not of the ({data2}) {typeData2}";
    //Message de restrictions    
    public static String SIZE_MAX = "The {typeData} ({data}) is very long, must be less than [{size} characters]";
    public static String REFERENCE_PHONE = "At least [{data} phone numbers] are required.";
    //Message Age
    public static String NOT_MEET_THE_AGE = "The person, must be  at least [{data} years] old";
    //Message duplicate
    public static String DUPLICATE = "Someone already has that {typeData} ({data})";
    // Message of permission
    public static String NOT_HAVE_PERMISSION = "The user don't have permission to {operation} the {typeData}";
    // Message of results
    public static String NOT_FOUND = "The {object} requested record was not found";
    public static String NOT_FOUND_USER_REQUEST = "The user requesting to {operation} does not exist";
    public static String CONFLCT_ID = "The identifier not corresponde to value of {object}";
    public static String DATE_FUTURE = "The date({data})is not valid, because it is a date greater than now ({data2})";
    public static String DATE_INCOHERENT = "The {typeData}({data}), is not consistent with the date {typeData2}({data2})";
    public static String MANDATORY_IDENTIFY = "It is mandatory to identify yourself to {operation}";
}
