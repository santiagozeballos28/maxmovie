package com.trueffect.tools;

/**
 * @author Santiago
 */
public class CodeStatus {

    //Code status Succesfull
    public static final int OK = 200;
    public static final int CREATED = 201;
    //code status Client error
    public static final int BAD_REQUEST = 400;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int CONFLICT = 409;
    //code status Server error
    public static final int INTERNAL_SERVER_ERROR = 500;
}
