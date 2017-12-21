package com.trueffect.validation;

import com.trueffect.tools.ConstantData;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author santiago.mamani
 */
public class PersonValidationTest {

    /*Validations type Identifier
     */
    @Test
    public void testTypeIdentifierPass() {
        boolean expected = true;
        String typeIdentifier = "PASS";
        Assert.assertEquals(PersonValidation.isValidTypeIdentifier(typeIdentifier), expected);
    }

    @Test
    public void testTypeIdentifierNit() {
        boolean expected = true;
        String typeIdentifier = "NIT";
        Assert.assertEquals(PersonValidation.isValidTypeIdentifier(typeIdentifier), expected);
    }

    @Test
    public void testTypeIdentifierCiLowerCase() {
        boolean expected = false;
        String typeIdentifier = "ci";
        Assert.assertEquals(PersonValidation.isValidTypeIdentifier(typeIdentifier), expected);
    }

    @Test
    public void testTypeIdentifierPassLowerCase() {
        boolean expected = false;
        String typeIdentifier = "pass";
        Assert.assertEquals(PersonValidation.isValidTypeIdentifier(typeIdentifier), expected);
    }

    @Test
    public void testTypeIdentifierNitLowerCase() {
        boolean expected = false;
        String typeIdentifier = "nit";
        Assert.assertEquals(PersonValidation.isValidTypeIdentifier(typeIdentifier), expected);
    }

    @Test
    public void testTypeIdentifierError() {
        boolean expected = false;
        String typeIdentifier = "ni";
        Assert.assertEquals(PersonValidation.isValidTypeIdentifier(typeIdentifier), expected);
    }

    @Test
    public void testTypeIdentifierEmpty() {
        boolean expected = false;
        String typeIdentifier = "";
        Assert.assertEquals(PersonValidation.isEmpty(typeIdentifier), expected);
    }

    /*
    *Identifier 
     */
    @Test
    public void testIdentyfierCI() {

        boolean expected = true;
        String typeIdentifier = "1234567";
        Assert.assertEquals(PersonValidation.isValidIdentifier(typeIdentifier), expected);
    }

    @Test
    public void testIdentyfierPASSPORT() {

        boolean expected = true;
        String typeIdentifier = "ABC123456A";
        Assert.assertEquals(PersonValidation.isValidIdentifier(typeIdentifier), expected);
    }

    @Test
    public void testIdentyfierNIT() {

        boolean expected = true;
        String typeIdentifier = "10223456";
        Assert.assertEquals(PersonValidation.isValidIdentifier(typeIdentifier), expected);
    }

    @Test
    public void testIdentyfierInvalid() {

        boolean expected = true;
        String typeIdentifier = "10223456";
        Assert.assertEquals(PersonValidation.isValidIdentifier(typeIdentifier), expected);
    }

    /*
    * Validation 
     */
    @Test
    public void testFirstName() {

        boolean expected = true;
        String firstName = "Santiago";
        Assert.assertEquals(PersonValidation.isValidFirstName(firstName), expected);
    }

    @Test
    public void testFirstNameTwoName() {

        boolean expected = false;
        String firstName = "Santiago Elias";
        Assert.assertEquals(PersonValidation.isValidFirstName(firstName), expected);
    }

    @Test
    public void testFirstNameSymbol() {

        boolean expected = false;
        String firstName = "Sant#iago$";
        Assert.assertEquals(PersonValidation.isValidFirstName(firstName), expected);
    }
   @Test
    public void testFirstNameUpperCase() {
        boolean expected = true;
        String lastName = "elIa's";
        Assert.assertEquals(PersonValidation.isValidLastName(lastName), expected);
    }
       @Test
    public void testFirstNameLowerCase() {
        boolean expected = true;
        String lastName = "e'lias";
        Assert.assertEquals(PersonValidation.isValidLastName(lastName), expected);
    }
    @Test
    public void testOneLastName() {
        boolean expected = true;
        String lastName = "Zeballo's";
        Assert.assertEquals(PersonValidation.isValidLastName(lastName), expected);
    }
     @Test
    public void testLastNameTwoSimbolValid() {
        boolean expected = true;
        String lastName = "mamaN'i zeballo's";
        Assert.assertEquals(PersonValidation.isValidLastName(lastName), expected);
    }
    @Test
    public void testLastNameSymbol() {
        boolean expected = false;
        String lastName = "Mamani# Zeballos";
        Assert.assertEquals(PersonValidation.isValidLastName(lastName), expected);
    }

    @Test
    public void testLastNameOne() {
        boolean expected = true;
        String lastName = "mamani";
        Assert.assertEquals(PersonValidation.isValidLastName(lastName), expected);
    }
        @Test
    public void testLastNameUpperCase() {
        boolean expected = true;
        String lastName = "zebAllos";
        Assert.assertEquals(PersonValidation.isValidLastName(lastName), expected);
    }
            @Test
    public void testLastNameUpperCaseAndLowerCase() {
        boolean expected = true;
        String lastName = "zebAllos mamANi";
        Assert.assertEquals(PersonValidation.isValidLastName(lastName), expected);
    }

    @Test
    public void testLastNameTwoLowerCase() {
        boolean expected = true;
        String lastName = "Mamani zeballos";
        Assert.assertEquals(PersonValidation.isValidLastName(lastName), expected);
    }

    @Test
    public void testLastNameTwoSpace() {
        boolean expected = false;
        String lastName = "Mamani  Zeballos";
        Assert.assertEquals(PersonValidation.isValidLastName(lastName), expected);
    }

    @Test
    public void testLastNameTwoVALID() {
        boolean expected = true;
        String lastName = "Mamani Nina";
        Assert.assertEquals(PersonValidation.isValidLastName(lastName), expected);
    }

    /*Test Briday
     */
    @Test
    public void testBirthDay() {

        boolean expected = true;
        String birthday = "2002-12-15";
        Assert.assertEquals(PersonValidation.isValidBirthday(birthday), expected);
    }

    @Test
    public void testBirthday2() {

        boolean expected = false;
        String birthday = "-12-2014";
        Assert.assertEquals(PersonValidation.isValidBirthday(birthday), expected);
    }
      @Test
    public void testBirthday3() {

        boolean expected = false;
        String birthday = "2014-15-02";
        Assert.assertEquals(PersonValidation.isValidBirthday(birthday), expected);
    }
    @Test
    public void testBirthday4() {

        boolean expected = true;
        String birthday = "2015-12-30";
        Assert.assertEquals(PersonValidation.isValidBirthday(birthday), expected);
    }
       @Test
    public void testAgeIvalid() {

        boolean expected = false;
        String birthday = "2005-12-12";
        int minimumAge =  ConstantData.MINIMUM_AGE;
        Assert.assertEquals(PersonValidation.isValidAge(birthday,minimumAge), expected);
    }
     @Test
    public void testAgeValid() {

        boolean expected = true;
        String birthday = "2002-12-12";
        int minimumAge =  ConstantData.MINIMUM_AGE;
        Assert.assertEquals(PersonValidation.isValidAge(birthday,minimumAge), expected);
    }
}
