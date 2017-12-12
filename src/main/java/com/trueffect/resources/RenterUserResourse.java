package com.trueffect.resources;

import com.trueffect.logica.PersonLogic;
import com.trueffect.tools.CodeStatus;
import com.trueffect.response.ErrorResponse;
import com.trueffect.response.MapperResponse;
import com.trueffect.validation.RenterUser;
import com.trueffect.model.Person;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/*
 * @author santiago.mamani
 */
@Path("renterUser")
public class RenterUserResourse {

    private PersonLogic personLogic = new PersonLogic();
    private MapperResponse mapper = new MapperResponse();

    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertRenterUser(@PathParam("id") int id, Person renterUser) throws Exception {
        Response response = null;
        try {
            Person resRenterUser = personLogic.createPerson(id, renterUser, new RenterUser());
            response = mapper.toResponse(CodeStatus.CREATED, resRenterUser);
        } catch (ErrorResponse ex) {
            response = mapper.toResponse(ex);
        }
        return response;
    }

    @DELETE
    @Path("/{id}/{idModifyUser}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("id") int idUser, @PathParam("idModifyUser") int idModifyUser) throws Exception {
        Response response = null;
        try {
            Person resRenterUser = personLogic.deleteById(idUser, idModifyUser);
            response = mapper.toResponse(CodeStatus.OK,resRenterUser);
        } catch (ErrorResponse ex) {
            response = mapper.toResponse(ex);
        }
        return response;
    }
}
