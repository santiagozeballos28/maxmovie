package com.trueffect.resources;

import com.trueffect.logica.employee.EmployeeLogic;
import com.trueffect.logica.person.PersonLogic;
import com.trueffect.model.Employee;
import com.trueffect.model.Person;
import com.trueffect.response.Either;
import com.trueffect.response.MapperResponse;
import com.trueffect.tools.ConstantData;
import com.trueffect.validation.EmployeeCreate;
import com.trueffect.validation.PersonCreate;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author santiago.mamani
 */
@Path("employee")
public class EmployeeResourse {

    private EmployeeLogic employeeLogic = new EmployeeLogic();
    private MapperResponse mapper = new MapperResponse();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertEmployee(@QueryParam("idModifyUser") int idModifyUser, Employee employee) {
        Either either = employeeLogic.createEmployee(idModifyUser, employee, new EmployeeCreate(ConstantData.MINIMUM_AGE_EMPLOYEE));
        Response response = mapper.toResponse(either);
        return response;
    }
}
