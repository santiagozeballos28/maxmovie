package com.trueffect.resources;

import com.trueffect.logic.PersonLogic;
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
    public Response insertRenterUser(@QueryParam("idModifyUser") long idModifyUser, Person renterUser) {
        Either either = personLogic.createPerson(idModifyUser, renterUser, new PersonCreate(ConstantData.MIN_AGE_RENTER));
        Response response = mapper.toResponse(either);
        return response;
    }

    @PUT
    @Path("/{id}/updateStatus")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStatus(@PathParam("id") long idUser, @QueryParam("idUserModify") long idUserModify, @QueryParam("status") String status) {
        Either either = personLogic.updateStatus(idUser, idUserModify, status);
        Response response = mapper.toResponse(either);
        return response;
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRenterUser(@PathParam("id") long idUser, @QueryParam("idModifyUser") long idModifyUser, Person renterUser) {
        Either eitherRenter = personLogic.update(renterUser, idUser, idModifyUser);
        Response response = mapper.toResponse(eitherRenter);
        return response;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRenterUser(
            @QueryParam("idUserSearch") long idUserSearch,
            @QueryParam("typeIdentifier") String typeIdentifier,
            @QueryParam("identifier") String identifier,
            @QueryParam("lastName") String lastName,
            @QueryParam("firstName") String firstName,
            @QueryParam("genre") String genre,
            @QueryParam("birthdayStart") String birthdayStart,
            @QueryParam("birthdayEnd") String birthdayEnd) {
        Either eitherRenter = personLogic.get(
                idUserSearch,
                typeIdentifier,
                identifier,
                lastName,
                firstName,
                genre,
                birthdayStart,
                birthdayEnd);
        Response response = mapper.toResponse(eitherRenter);
        return response;
    }
}
