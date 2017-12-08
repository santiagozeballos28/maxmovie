package com.trueffect.validation;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author santiago.mamani
 */
public class TestPersonTypeIdentifier {
      @Test
      public void testTypeIdentifierCi(){
       boolean expected = true;
       String typeIdentifier = "CI";
       Assert.assertEquals(PersonValidation.isValidTypeIdentifier(typeIdentifier),expected);     

      } 
      @Test
      public void testTypeIdentifierPass(){
       boolean expected = true;
       String typeIdentifier = "PASS";
       Assert.assertEquals(PersonValidation.isValidTypeIdentifier(typeIdentifier),expected);     

      } 
        @Test
      public void testTypeIdentifierNit(){
       boolean expected = true;
       String typeIdentifier = "NIT";
       Assert.assertEquals(PersonValidation.isValidTypeIdentifier(typeIdentifier),expected);     

      } 
           @Test
      public void testTypeIdentifierCiLowerCase(){
       boolean expected = false;
       String typeIdentifier = "ci";
       Assert.assertEquals(PersonValidation.isValidTypeIdentifier(typeIdentifier),expected);     

      } 
      @Test
      public void testTypeIdentifierPassLowerCase(){
       boolean expected = false;
       String typeIdentifier = "pass";
       Assert.assertEquals(PersonValidation.isValidTypeIdentifier(typeIdentifier),expected);     
      } 
      @Test
      public void testTypeIdentifierNitLowerCase(){
       boolean expected = false;
       String typeIdentifier = "nit";
       Assert.assertEquals(PersonValidation.isValidTypeIdentifier(typeIdentifier),expected);     
      } 
       @Test
      public void testTypeIdentifierError(){
       boolean expected = false;
       String typeIdentifier = "ni";
       Assert.assertEquals(PersonValidation.isValidTypeIdentifier(typeIdentifier),expected);     
      }
      @Test
      public void testTypeIdentifierEmpty(){
       boolean expected = false;
       String typeIdentifier = "";
       Assert.assertEquals(PersonValidation.isValidTypeIdentifier(typeIdentifier),expected);     
      } 
}
