package com.trueffect.resources;

import com.trueffect.logic.MovieLogic;
import com.trueffect.model.Movie;
import com.trueffect.model.Sale;
import com.trueffect.response.Either;
import com.trueffect.response.MapperResponse;
import com.trueffect.validation.MovieCreate;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author santiago.mamani
 */
@Path("Movie")
public class MovieResourse {

    private MovieLogic movieLogic = new MovieLogic();
    private MapperResponse mapper = new MapperResponse();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertRenterUser(@QueryParam("idCreateUser") int idCreateUser, Movie movie) {
        Either either = movieLogic.createMovie(idCreateUser, movie, new MovieCreate());
        Response response = mapper.toResponse(either);
        return response;
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateMovie(
            @PathParam("id") long idMovie,
            @QueryParam("idModifyUser") long idModifyUser,
            Movie movie) {
        Either eitherEmployee = movieLogic.update(movie, idMovie, idModifyUser);
        Response response = mapper.toResponse(eitherEmployee);
        return response;
    }

    @PUT
    @Path("/{id}/updateStatus")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStatus(@PathParam("id") long idMovie, @QueryParam("idModifyUser") long idModifyUser, @QueryParam("status") String status) {
        Either either = movieLogic.updateStatus(idMovie, idModifyUser, status);
        Response response = mapper.toResponse(either);
        return response;
    }

    @POST
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertCopy(@PathParam("id") long idMovie, @QueryParam("idCreateUser") long idCreateUser, @QueryParam("amountCopy") int amountCopy) {
        Either either = movieLogic.insertCopy(idMovie, idCreateUser, amountCopy);
        Response response = mapper.toResponse(either);
        return response;
    }
}
