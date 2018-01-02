package com.trueffect.resources;

import com.trueffect.logica.EmployeeLogic;
import com.trueffect.model.Employee;
import com.trueffect.response.Either;
import com.trueffect.response.MapperResponse;
import com.trueffect.tools.ConstantData;
import com.trueffect.validation.EmployeeCreate;
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
@Path("Employee")
public class EmployeeResourse {

    private EmployeeLogic employeeLogic = new EmployeeLogic();
    private MapperResponse mapper = new MapperResponse();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertEmployee(@QueryParam("idUserCreate") int idUserCreate, Employee employee) {
        Either eitherEmployee = employeeLogic.createEmployee(idUserCreate, employee, new EmployeeCreate(ConstantData.MINIMUM_AGE_EMPLOYEE));
        Response response = mapper.toResponse(eitherEmployee);
        return response;
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEmployee(@PathParam("id") int idUser, @QueryParam("idModifyUser") int idModifyUser, Employee employee) {
        Either eitherEmployee = employeeLogic.update(employee, idUser, idModifyUser);
        Response response = mapper.toResponse(eitherEmployee);
        return response;
    }

    @PUT
    @Path("/{id}/updateStatus")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStatus(@PathParam("id") int idEmployee, @QueryParam("idUserModify") int idUserModify, @QueryParam("status") String status) {
        Either either = employeeLogic.updateStatus(idEmployee, idUserModify, status);
        Response response = mapper.toResponse(either);
        return response;
    }

    @PUT
    @Path("/updateBonus")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBond( @QueryParam("idUserModify") int idUserModify) {
        Either either = employeeLogic.updateBond(idUserModify);
        Response response = mapper.toResponse(either);
         return response;
     }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployee(
            @QueryParam("idUserSearch") int idUserSearch,
            @QueryParam("typeIdentifier") String typeIdentifier,
            @QueryParam("identifier") String identifier,
            @QueryParam("lastName") String lastName,
            @QueryParam("firstName") String firstName,
            @QueryParam("genre") String genre,
            @QueryParam("dateOfHire") String dateOfHire,
            @QueryParam("job") String job) {
        Either eitherRenter = employeeLogic.get(
                idUserSearch,
                typeIdentifier,
                identifier,
                lastName,
                firstName,
                genre,
                dateOfHire,
                job);
        Response response = mapper.toResponse(eitherRenter);
        return response;
    }
}
