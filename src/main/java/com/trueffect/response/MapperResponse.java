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
        String mgs = ep.getMessage();
        String [] list = mgs.split("\n");
        return Response.status(new MyStatusType(ep.getCode()))
                .entity(list)
                .build();
    }
    //.entity("\""+ep.getMessage().toString()+"\"")
}
