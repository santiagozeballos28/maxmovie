package com.trueffect.validation;
import com.trueffect.tools.DataResourse;
import org.junit.Assert;
import org.junit.Test;
/**
 * @author santiago.mamani
 */
public class TestPersonName {
   @Test
      public void testFirstName(){
                   
       boolean expected = true;
       String firstName = "Santiago";
       int sizeMax=DataResourse.MAXIMUM_VALUES;
        Assert.assertEquals(PersonValidation.isValidFirstName(firstName, sizeMax),expected);     
        
      } 
      @Test
      public void testFirstNameTwoName(){
                   
       boolean expected = false;
       String firstName = "Santiago Elias";
       int sizeMax=DataResourse.MAXIMUM_VALUES;
        Assert.assertEquals(PersonValidation.isValidFirstName(firstName, sizeMax),expected);     
        
      } 
      @Test
      public void testFirstNameSymbol(){
                   
       boolean expected = false;
       String firstName = "Sant#iago$";
       int sizeMax=DataResourse.MAXIMUM_VALUES;
        Assert.assertEquals(PersonValidation.isValidFirstName(firstName, sizeMax),expected);     
        
      } 
      @Test
      public void testFirstEmpty(){      
       boolean expected = false;
       String firstName = "";
       int sizeMax=DataResourse.MAXIMUM_VALUES;
        Assert.assertEquals(PersonValidation.isValidFirstName(firstName, sizeMax),expected);     
        
      } 
  
         @Test
      public void testLastNameEmpty(){       
       boolean expected = false;
       String lastName = "";
       int sizeMax=DataResourse.MAXIMUM_VALUES;
        Assert.assertEquals(PersonValidation.isValidLastName(lastName, sizeMax),expected);     
        
      } 
      /*
        @Test
      public void testLastNameTwo(){
       boolean expected = true;
       String last_name = "Mamani Zeballos";
       int size_max=DataResourse.MAX_CHAR_LNAME_RUSER;
        Assert.assertEquals(PersonValidation.isValidLastName(last_name, size_max),expected);     
        
      } 
*/
      @Test
      public void testOneLastName(){          
       boolean expected = true;
       String lastName = "Zeballos";
       int sizeMax=DataResourse.MAXIMUM_VALUES;
        Assert.assertEquals(PersonValidation.isValidLastName(lastName, sizeMax),expected);   
        
      } 
      
      @Test
      public void testLastNameSymbol(){   
         boolean expected = false;
       String lastName = "Mamani# Zeballos";
       int sizeMax=DataResourse.MAXIMUM_VALUES;
        Assert.assertEquals(PersonValidation.isValidLastName(lastName, sizeMax),expected);     
        
      } 
      
   
}
