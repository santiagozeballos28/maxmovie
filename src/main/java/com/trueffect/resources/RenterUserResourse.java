package com.trueffect.resources;

import com.trueffect.logica.PersonLogic;
import com.trueffect.response.MapperResponse;
import com.trueffect.validation.PersonCreate;
import com.trueffect.model.Person;
import com.trueffect.response.Either;
import com.trueffect.tools.ConstantData;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
@Path("RenterUser")
public class RenterUserResourse {

    private PersonLogic personLogic = new PersonLogic();
    private MapperResponse mapper = new MapperResponse();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertRenterUser(@QueryParam("idModifyUser") int idModifyUser, Person renterUser) {
        Either either = personLogic.createPerson(idModifyUser, renterUser, new PersonCreate(ConstantData.MINIMUM_AGE_RENTER));
        Response response = mapper.toResponse(either);
        return response;
    }

    @PUT
    @Path("/{id}/updateStatus")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStatus(@PathParam("id") int idUser, @QueryParam("idUserModify") int idUserModify, @QueryParam("status") String status) {
        Either either = personLogic.updateStatus(idUser, idUserModify, status);
        Response response = mapper.toResponse(either);
        return response;
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRenterUser(@PathParam("id") int idUser, @QueryParam("idModifyUser") int idModifyUser, Person renterUser) {
        Either eitherRenter = personLogic.update(renterUser, idUser, idModifyUser);
        Response response = mapper.toResponse(eitherRenter);
        return response;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRenterUser(
            @QueryParam("idUserSearch") int idUserSearch,
            @QueryParam("typeIdentifier") String typeIdentifier,
            @QueryParam("identifier") String identifier,
            @QueryParam("lastName") String lastName,
            @QueryParam("firstName") String firstName,
            @QueryParam("genre") String genre) {
        Either eitherRenter = personLogic.get(idUserSearch, typeIdentifier, identifier, lastName, firstName, genre);
        Response response = mapper.toResponse(eitherRenter);
        return response;
    }
}
