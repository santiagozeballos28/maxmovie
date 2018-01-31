package com.trueffect.resources;

import com.trueffect.logic.GenreMovieLogic;
import com.trueffect.logic.MovieLogic;
import com.trueffect.logic.MovieReportLogic;
import com.trueffect.model.GenreMovie;
import com.trueffect.model.Movie;
import com.trueffect.response.Either;
import com.trueffect.response.MapperResponse;
import com.trueffect.validation.MovieCreate;
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

/**
 *
 * @author santiago.mamani
 */
@Path("Movie")
public class MovieResourse {

    private MovieLogic movieLogic = new MovieLogic();
    private GenreMovieLogic genreMovieLogic = new GenreMovieLogic();
    private MovieReportLogic movieReportLogic = new MovieReportLogic();
    private MapperResponse mapper = new MapperResponse();
    @POST
    @Path("/genreMovie")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertGenreMovie(@QueryParam("idCreateUser") long idCreateUser, GenreMovie genreMovie) {
        Either either = genreMovieLogic.createGenreMovie(idCreateUser,genreMovie, new MovieCreate());
        Response response = mapper.toResponse(either);
        return response;
    }
    @PUT
    @Path("/genreMovie/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateGenreMovie( @PathParam("id") String idGenreMovie,@QueryParam("idModifyUser") long idModifyUser, GenreMovie genreMovie) {
        Either either = genreMovieLogic.updateGenreMovie(idGenreMovie,idModifyUser,genreMovie);
        Response response = mapper.toResponse(either);
        return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertMovie(@QueryParam("idCreateUser") long idCreateUser, Movie movie) {
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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response reportMovie(@QueryParam("idSearchUser") long idSearchUser) {
        Either either = movieReportLogic.getInformationOfMovies(idSearchUser);
        Response response = mapper.toResponse(either);
        return response;
    }
}
