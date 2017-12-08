
package com.trueffect.sql;

import com.trueffect.sql.crud.PersonCrud;
import com.trueffect.response.ErrorResponse;
import com.trueffect.model.Person;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author santiago.mamani
 */
public class TestPerson {
 @Test
    public void getPersonByIdentifier() throws Exception {
         String typeIdentifier = "CI";
         String identifier = "8765432";
         Person renterUser = new Person(14,"CI","8765432", "Tellerina", "Claudia", "M", "1900-11-12");
         String exMsg = "";
         Person resRenterUser = null;
        try {
             resRenterUser=(Person)PersonCrud.getPersonByTypeIdentifier(typeIdentifier, identifier);
        } catch (ErrorResponse ex) {
            exMsg = ex.getMessage();
        }
        Assert.assertEquals(renterUser.compareTo(renterUser),0);
    }   
    @Test
    public void getPersonByIdentifier2() throws Exception {
        String typeIdentifier = "CI";
        String identifier = "8854456";
        Person renterUser = new Person(15,"CI","8365323", "Perez", "Morris", "M", "1989-11-12");
        String exMsg = "";
        Person resRenterUser = null;
        try {
            resRenterUser=(Person)PersonCrud.getPersonByTypeIdentifier(typeIdentifier, identifier);
        } catch (ErrorResponse ex) {
            exMsg = ex.getMessage();
        }
        Assert.assertEquals(renterUser.compareTo(renterUser),0);
    }   
}
