
package com.trueffect.validation;

import com.trueffect.validation.RenterUser;
import com.trueffect.validation.ProcessObject;
import com.trueffect.response.ErrorResponse;
import com.trueffect.sql.crud.PersonCrud;
import com.trueffect.model.Person;

import com.trueffect.tools.CodeStatus;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
//import org.powermock.modules.junit4.PowerMockRunner;
 //import static org.powermock.api.mockito.PowerMockito.mockStatic;
/**
 *
 * @author santiago.mamani
 */
//@RunWith(PowerMockRunner.class)
//@PrepareForTest(PersonCrud.class)
//@RunWith(PowerMockRunner.class)
public class TestInsert {
 /*
    @Test
    public void testValidationInsertConMock() throws Exception {

        Person renterUser = new Person(0,"NIT","6634564", "Martinez", "Jhosmar", "M", "5/25/92");
           RenterUser complyRenterUser = mock(RenterUser.class);
        when(complyRenterUser.complyCondition(2,renterUser)).thenReturn(true);
        String ex_msg = "";
        Person resRenterUser = null;
        try {
            resRenterUser = (Person) ProcessObject.processInsert(2,renterUser, complyRenterUser);
        } catch (ErrorResponse ex) {
            ex_msg = ex.getMessage();
        }
        Assert.assertEquals(renterUser.compareTo(resRenterUser),0);
    }
    */
    /*
    @Test
    public void testValidationInsertErrorSinMock() throws Exception {

        Person renterUser = new Person(0,"123456", "Bustamante", "Edwin4", "M", "5/25/92");
        RenterUser complyRenterUser = new RenterUser();
        String ex_msg = "";
        String res_msg = "ERROR: Ya existen los nombres. No se puede tener nombres duplicados";
        Person res_renter_user = null;
        try {
            res_renter_user = (Person) ProcessObject.processInsert(renterUser, complyRenterUser);
        } catch (ErrorResponse ex) {
            ex_msg = ex.getMessage();
        }
        Assert.assertEquals(ex_msg.equals(res_msg), true);

    }
  */  
}
