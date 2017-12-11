
package com.trueffect.validation;
import com.trueffect.util.OperationUpdate;
import com.trueffect.response.ErrorResponse;
import com.trueffect.sql.crud.PersonCrud;
import com.trueffect.messages.Message;
import com.trueffect.model.Person;
import com.trueffect.util.DataCondition;
import com.trueffect.util.ModelObject;
import java.sql.Connection;
/**
 * @author santiago.mamani
 */
public class RenterUserUpdate implements DataCondition{
    private RenterUser renterUserComply;

    public void setRenterUserComply(RenterUser renterUserComply) {
        this.renterUserComply = renterUserComply;
    }
    
    public boolean complyCondition(int id , ModelObject resource,    Connection connection) throws Exception {
        boolean complyJob = false;
        Person renterUserNew =  (Person) resource;
        Person renterUserActual = PersonCrud.getRenterUserById(renterUserNew.getId());
        OperationUpdate operationUpdate = null; 
        String jobName = "Manager";
        if (jobName.equals("Administrator")){
               operationUpdate = new RenterUserUpdateByAdministrator();
               operationUpdate.complyData(renterUserActual,renterUserNew);  
             // return renterUserComply.complyCondition(id, resource);
           
        }else{
           if(jobName.equals("Manager")){
               operationUpdate = new RenterUserUpdateByManager();
               operationUpdate.complyData(renterUserActual,renterUserNew);   
             //  return renterUserComply.complyCondition(id, resource);
           
           }else{
             throw new ErrorResponse(460,Message.NOT_HAVE_PERMISSION_FOR_MODIFY);
           }
        }
        return false;
    }

    
}
