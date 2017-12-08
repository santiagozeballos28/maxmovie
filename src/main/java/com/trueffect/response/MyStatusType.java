package com.trueffect.response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;
import javax.ws.rs.core.Response.Status.Family;
/*
 * @author santiago.mamani
 */
public class MyStatusType implements StatusType {

    private final Family family;
    private final int statusCode;
    private final String reasonPhrase;

    public MyStatusType(final Family family, final int statusCode,
            final String reasonPhrase) {
        super();

        this.family = family;
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
    }

    protected MyStatusType(final Status status,
            final String reasonPhrase) {
        this(status.getFamily(), status.getStatusCode(), reasonPhrase);
    }

    @Override
    public Family getFamily() {
        return family;
    }

    @Override
    public String getReasonPhrase() {
        return reasonPhrase;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

}
