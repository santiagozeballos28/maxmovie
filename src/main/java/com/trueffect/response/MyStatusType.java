package com.trueffect.response;

import javax.ws.rs.core.Response.StatusType;
import javax.ws.rs.core.Response.Status.Family;

/*
 * @author santiago.mamani
 */
public class MyStatusType implements StatusType {

    private final int statusCode;

    public MyStatusType(final int statusCode) {
        super();
        this.statusCode = statusCode;
    }

    @Override
    public Family getFamily() {
        return null;
    }

    @Override
    public String getReasonPhrase() {
        return null;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

}
