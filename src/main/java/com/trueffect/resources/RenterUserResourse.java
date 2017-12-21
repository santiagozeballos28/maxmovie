package com.trueffect.resources;

import com.trueffect.logica.person.PersonLogic;
import com.trueffect.response.MapperResponse;
import com.trueffect.validation.RenterUserCreate;
import com.trueffect.model.Person;
import com.trueffect.response.Either;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertRenterUser(@QueryParam("idModifyUser") int idModifyUser, Person renterUser) {

        Either either = personLogic.createPerson(idModifyUser, renterUser, new RenterUserCreate());
        Response response = mapper.toResponse(either);
        return response;
    }

    @PUT
    @Path("/{id}/updateStatus")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("id") int idUser, @QueryParam("idModifyUser") int idModifyUser, @QueryParam("status") String status) {
        Either either = personLogic.deleteById(idUser, idModifyUser, status);
        Response response = mapper.toResponse(either);
        return response;
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRenterUser(@PathParam("id") int idUser, @QueryParam("idModifyUser") int idModifyUser, Person renterUser) throws Exception {
        Response response = null;
//        try {
//            Person resRenterUser = personLogic.update(renterUser, idUser, idModifyUser);
//            response = mapper.toResponse(CodeStatus.CREATED, resRenterUser);
//        } catch (ErrorResponse ex) {
//            response = mapper.toResponse(ex);
//        }
        return response;
    }
}
