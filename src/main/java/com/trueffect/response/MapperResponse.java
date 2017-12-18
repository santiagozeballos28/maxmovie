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
        String [] list = getError(mgs);
        return Response.status(new MyStatusType(ep.getCode()))
                .entity(list)
                .build();
    }
    private String [] getError( String mgs){
    String [] list = mgs.split("\n");
    String [] resMsg = new String [list.length-1];
        for (int i = 1; i < list.length; i++) {
            resMsg[i-1]=list[i];
        }
    return resMsg;
    }
    //.entity("\""+ep.getMessage().toString()+"\"")
}
