package com.trueffect.tools;
/**
 * @author Santiago
 */
public class CodeStatus {
    /*
    * Code status Succesfull
    */
    public static int OK=200;
    public static int CREATED=201;
    /*
    * code status Client error
    */
    public static int BAD_REQUEST=400;
    public static int NOT_FOUND=404;
    public static int UN_PROCESSABLE_ENTITY=422;
    /*
    * code status Server error
    */
    public static int INTERNAL_SERVER_ERROR=500;
}
