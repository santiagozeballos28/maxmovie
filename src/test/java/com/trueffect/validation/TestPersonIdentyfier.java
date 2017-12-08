
package com.trueffect.validation;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author santiago.mamani
 */
public class TestPersonIdentyfier {
     @Test
      public void testIdentyfierEmpty(){
                   
       boolean expected = false;
       String typeIdentifier = "";
       Assert.assertEquals(PersonValidation.isValidIdentifier(typeIdentifier),expected);     
        
      } 
    @Test
      public void testIdentyfierCI(){
                   
       boolean expected = true;
       String typeIdentifier = "1234567";
       Assert.assertEquals(PersonValidation.isValidIdentifier(typeIdentifier),expected);     
        
      } 
       @Test
      public void testIdentyfierPASSPORT(){
                   
       boolean expected = true;
       String typeIdentifier = "ABC123456A";
       Assert.assertEquals(PersonValidation.isValidIdentifier(typeIdentifier),expected);     
        
      } 
        @Test
      public void testIdentyfierNIT(){
                   
       boolean expected = true;
       String typeIdentifier = "10223456";
       Assert.assertEquals(PersonValidation.isValidIdentifier(typeIdentifier),expected);     
        
      } 
      @Test
      public void testIdentyfierInvalid(){
                   
       boolean expected = false;
       String typeIdentifier = "1022";
       Assert.assertEquals(PersonValidation.isValidIdentifier(typeIdentifier),expected);     
        
      } 
}
