
package com.trueffect.resources;

import com.trueffect.tools.CodeStatus;
import com.trueffect.response.CorrectResponse;
import com.trueffect.response.ErrorResponse;
import com.trueffect.response.MapperResponse;
import com.trueffect.sql.crud.PersonCrud;
import com.trueffect.validation.RenterUser;
import com.trueffect.validation.ProcessObject;
import com.trueffect.validation.RenterUserUpdate;
import com.trueffect.model.Person;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/*
 * @author santiago.mamani
 */
@Path("renterUser")
public class RenterUserResourse  {
    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertRenterUser(@PathParam ("id")int id,Person renterUser) throws Exception{
        Response response = null;
        MapperResponse mapper = new MapperResponse();
        try {
            Person resRenterUser= (Person)ProcessObject.processInsert(id,renterUser, new RenterUser());
            CorrectResponse phraseCorrect = new CorrectResponse(CodeStatus.CREATED,"",resRenterUser);
            response = mapper.toResponse(phraseCorrect);
        } catch (ErrorResponse ex) {
            response = mapper.toResponse(ex);
        }
      return response;
    }
 
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRenterUser(@PathParam ("id")int id){
      return null;
    }
    @GET
    @Path("/getAllRenterUser") 
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getAllRentesUser() throws Exception{
      return PersonCrud.getAllRenterUser();
    }
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putRenterUser(Person renterUser, @PathParam ("id")int id) throws Exception{
            Response response = null;
        MapperResponse mapper = new MapperResponse();
        try {
            Person res_renter_user= (Person)ProcessObject.processUpdate(id,renterUser, new RenterUserUpdate());
            CorrectResponse phraseCorrect = new CorrectResponse(CodeStatus.CREATED,"",res_renter_user);
            response = mapper.toResponse(phraseCorrect);
        } catch (ErrorResponse ex) {
            response = mapper.toResponse(ex);
        }
      return response;
    }
}
