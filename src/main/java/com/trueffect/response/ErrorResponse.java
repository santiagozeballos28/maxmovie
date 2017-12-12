package com.trueffect.response;

/*
 * @author santiago.mamani
 */
public class ErrorResponse extends Exception {

    private final int code;

    public ErrorResponse(int code, final String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
