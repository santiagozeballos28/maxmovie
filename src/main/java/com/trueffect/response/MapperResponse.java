package com.trueffect.response;

import com.trueffect.util.ModelObject;
import javax.ws.rs.core.Response;

/*
 * @author santiago.mamani
 */
public class MapperResponse {

    public Response toResponse(Either either) {
        if (either.existError()) {
            System.out.println("EXISTIO MAPHER: " );
            return toResponseError(either);
        } else {
            System.out.println("EXISTIO MAPHER SIN ERROR: ");
            return toResponseModelObejt(either);
        }
    }

    public Response toResponseModelObejt(Either either) {
        return Response.status(new MyStatusType(either.getCode()))
                .entity(either.getModelObject())
                .build();
    }

    public Response toResponseError(Either either) {
        
        //String mgs = "\""+either.getErrorMessage()+"\"";
        //String[] list = getError(mgs);
        //System.out.println("EXISTIO MAPHER TORESPONCE: " +either.getErrorMessage());
        //System.out.println("SIZE LIST: " +list.length);
        return Response.status(new MyStatusType(either.getCode()))
                .entity(either.getListError())
                .build();
    }

//    public Response toResponse(int codeStatus, ModelObject modelObject) {
//        return Response.status(new MyStatusType(codeStatus))
//                .entity(modelObject)
//                .build();
//    }
//
//    public Response toResponse(ErrorResponse ep) {
//        String mgs = ep.getMessage();
//        String [] list = getError(mgs);
//        return Response.status(new MyStatusType(ep.getCode()))
//                .entity(list)
//                .build();
//    }
    private String[] getError(String mgs) {
        String[] list = mgs.split("\n");
        String[] resMsg = new String[list.length - 1];
        for (int i = 1; i < list.length; i++) {
            resMsg[i - 1] = list[i];
        }
        return resMsg;
    }
    //.entity("\""+ep.getMessage().toString()+"\"")
}
