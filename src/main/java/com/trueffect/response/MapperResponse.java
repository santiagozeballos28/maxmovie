package com.trueffect.response;

import javax.ws.rs.core.Response;

/*
 * @author santiago.mamani
 */
public class MapperResponse {

    public Response toResponse(Either either) {
        if (either.existError()) {
            return toResponseError(either);
        } else {
            return toResponseModelObejt(either);
        }
    }

    public Response toResponseModelObejt(Either either) {
        return Response.status(new MyStatusType(either.getCode()))
                .entity(either.getListObject())
                .build();
    }

    public Response toResponseError(Either either) {
        return Response.status(new MyStatusType(either.getCode()))
                .entity(either.getListError())
                .build();
    }
}
