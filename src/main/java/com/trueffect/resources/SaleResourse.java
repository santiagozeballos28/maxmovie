package com.trueffect.resources;

import com.trueffect.logic.SaleLogic;
import com.trueffect.model.Sale;
import com.trueffect.response.Either;
import com.trueffect.response.MapperResponse;
import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author santiago.mamani
 */
@Path("Sale")
public class SaleResourse {

    private SaleLogic movieLogic = new SaleLogic();
    private MapperResponse mapper = new MapperResponse();
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerSale(@QueryParam("idCreateUser") long idCreateUser, @QueryParam("idRenterUser") int idRenterUser, ArrayList<Sale> sales) {
        Either either = movieLogic.registerSale(idCreateUser, idRenterUser, sales);
        Response response = mapper.toResponse(either);
        return response;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerReturningMovie(@QueryParam("idEmployeeReceive") long idEmployeeReceive, @QueryParam("idRenterUser") int idRenterUser, ArrayList<Sale> sales) {
//        Either either = movieLogic.registerSale(idEmployeeReceive, idRenterUser, sales);
        Response response = mapper.toResponse(null);
        return response;
    }
}
