package com.trueffect.response;

import com.trueffect.util.ModelObject;
import javax.ws.rs.core.Response;

/*
 * @author santiago.mamani
 */
public class MapperResponse {

    public Response toResponse(int codeStatus, ModelObject modelObject) {
        return Response.status(new MyStatusType(codeStatus))
                .entity(modelObject)
                .build();
    }

    public Response toResponse(ErrorResponse ep) {
        return Response.status(new MyStatusType(ep.getCode()))
                .entity(ep.getMessage())
                .build();
    }

}
