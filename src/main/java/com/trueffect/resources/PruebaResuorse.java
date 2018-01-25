/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trueffect.resources;

import com.trueffect.logic.PruebaLogic;
import com.trueffect.response.Either;
import com.trueffect.response.MapperResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author santiago.mamani
 */
@Path("/Test")
public class PruebaResuorse {
 private MapperResponse mapper = new MapperResponse();
 private PruebaLogic pruebaLogic = new PruebaLogic();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMasterDetail(@QueryParam("idSearchUser") long idSearchUser, @QueryParam("idMasterDetail") long idMasterDetail) {
        Either either = pruebaLogic.getReportMovie(idMasterDetail, idMasterDetail);
        Response response = mapper.toResponse(either);
        return response;
    }
    @GET
    @Path("/Sale")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSale(@QueryParam("idSearchUser") long idSearchUser, @QueryParam("idMasterDetail") long idMasterDetail) {
        Either either = pruebaLogic.getReportSale(idMasterDetail, idMasterDetail);
        Response response = mapper.toResponse(either);
        return response;
    }
}
