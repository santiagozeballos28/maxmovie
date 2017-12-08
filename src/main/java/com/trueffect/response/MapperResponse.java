package com.trueffect.response;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
/*
 * @author santiago.mamani
 */
public class MapperResponse  {
    public Response toResponse(ErrorResponse pe) {
        return Response.status(new MyStatusType(FamilyType.getFamily(pe.getCode()),pe.getCode(),pe.getMessage()))
                .entity("Error: " + pe.getMessage())
                .build();
    }
 public Response toResponse(CorrectResponse pc) {
        return Response.status(new MyStatusType(FamilyType.getFamily(pc.getCode()),pc.getCode(),pc.getMessage()))
                .entity(pc.getResourse())
                .build();
    }
}
