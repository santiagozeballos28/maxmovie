package com.trueffect.resources;

import com.trueffect.logica.person.PersonLogic;
import com.trueffect.response.MapperResponse;
import com.trueffect.validation.RenterUserCreate;
import com.trueffect.model.Person;
import com.trueffect.response.Either;
import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.apache.commons.lang3.StringUtils;

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
    public Response updateRenterUser(@PathParam("id") int idUser, @QueryParam("idModifyUser") int idModifyUser, Person renterUser) {
        Either eitherRenter = personLogic.update(renterUser, idUser, idModifyUser);
        Response response = mapper.toResponse(eitherRenter);
        return response;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRenterUser(@Context UriInfo uriInfo) {
        MultivaluedMap<String, String> list = uriInfo.getQueryParameters();
        Either eitherRenter = personLogic.get(list);
        Response response = mapper.toResponse(eitherRenter);
        return response;
       
//       for (Map.Entry<String, List<String>> e : list.entrySet()) {
//        for (String v : e.getValue()) {
//            String key = e.getKey() + "";
//            String value =v+ "";
//            System.out.println("KEY NOW: " +key);
//            System.out.println("VALUE NOW: " +value);
//         }
//    }

    }
}
