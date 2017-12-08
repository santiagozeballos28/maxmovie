package com.trueffect.validation;
import org.junit.Assert;
import org.junit.Test;
/**
 * @author santiago.mamani
 */
public class TestBirthday {
     @Test
      public void testBirthDay(){
                   
       boolean expected = true;
       String birthday = "11/12/2014";
       Assert.assertEquals(PersonValidation.isValidBirthday(birthday),expected);     
       
      } 
       @Test
      public void testBirthday2(){
                   
        boolean expected = false;
        String birthday = "/12/2014";
        Assert.assertEquals(PersonValidation.isValidBirthday(birthday),expected);     
        
      } 
}
