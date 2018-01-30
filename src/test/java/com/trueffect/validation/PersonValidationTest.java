package com.trueffect.validation;

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
        Assert.assertEquals(PersonValidationUtil.isValidTypeIdentifier(typeIdentifier), expected);
    }

    @Test
    public void testTypeIdentifierNit() {
        boolean expected = true;
        String typeIdentifier = "NIT";
        Assert.assertEquals(PersonValidationUtil.isValidTypeIdentifier(typeIdentifier), expected);
    }

    @Test
    public void testTypeIdentifierCiLowerCase() {
        boolean expected = true;
        String typeIdentifier = "ci";
        Assert.assertEquals(PersonValidationUtil.isValidTypeIdentifier(typeIdentifier), expected);
    }

    @Test
    public void testTypeIdentifierPassLowerCase() {
        boolean expected = true;
        String typeIdentifier = "pass";
        Assert.assertEquals(PersonValidationUtil.isValidTypeIdentifier(typeIdentifier), expected);
    }

    @Test
    public void testTypeIdentifierNitLowerCase() {
        boolean expected = true;
        String typeIdentifier = "nit";
        Assert.assertEquals(PersonValidationUtil.isValidTypeIdentifier(typeIdentifier), expected);
    }

    @Test
    public void testTypeIdentifierError() {
        boolean expected = false;
        String typeIdentifier = "ni";
        Assert.assertEquals(PersonValidationUtil.isValidTypeIdentifier(typeIdentifier), expected);
    }

    /*
    *Identifier 
     */
    @Test
    public void testIdentyfierCI() {

        boolean expected = true;
        String typeIdentifier = "1234567";
        Assert.assertEquals(PersonValidationUtil.isValidIdentifier(typeIdentifier), expected);
    }

    @Test
    public void testIdentyfierPASSPORT() {

        boolean expected = true;
        String typeIdentifier = "ABC123456A";
        Assert.assertEquals(PersonValidationUtil.isValidIdentifier(typeIdentifier), expected);
    }

    @Test
    public void testIdentyfierNIT() {

        boolean expected = true;
        String typeIdentifier = "10223456";
        Assert.assertEquals(PersonValidationUtil.isValidIdentifier(typeIdentifier), expected);
    }

    @Test
    public void testIdentyfierInvalid() {

        boolean expected = true;
        String typeIdentifier = "10223456";
        Assert.assertEquals(PersonValidationUtil.isValidIdentifier(typeIdentifier), expected);
    }

    /*
    * Validation 
     */
    @Test
    public void testFirstName() {

        boolean expected = true;
        String firstName = "Santiago";
        Assert.assertEquals(PersonValidationUtil.isValidName(firstName), expected);
    }

    @Test
    public void testFirstNameTwoName() {

        boolean expected = true;
        String firstName = "santiago elias";
        Assert.assertEquals(PersonValidationUtil.isValidName(firstName), expected);
    }

    @Test
    public void testFirstNameSymbol() {

        boolean expected = false;
        String firstName = "Sant#iago$";
        Assert.assertEquals(PersonValidationUtil.isValidName(firstName), expected);
    }

    @Test
    public void testFirstNameUpperCase() {
        boolean expected = true;
        String lastName = "elIa's";
        Assert.assertEquals(PersonValidationUtil.isValidName(lastName), expected);
    }

    @Test
    public void testFirstNameLowerCase() {
        boolean expected = true;
        String lastName = "e'lias";
        Assert.assertEquals(PersonValidationUtil.isValidName(lastName), expected);
    }

    @Test
    public void testOneLastName() {
        boolean expected = true;
        String lastName = "Zeballo's";
        Assert.assertEquals(PersonValidationUtil.isValidName(lastName), expected);
    }

    @Test
    public void testLastNameTwoSimbolValid() {
        boolean expected = true;
        String lastName = "mamaN'i zeballo's";
        Assert.assertEquals(PersonValidationUtil.isValidName(lastName), expected);
    }

    @Test
    public void testLastNameSymbol() {
        boolean expected = false;
        String lastName = "Mamani# Zeballos";
        Assert.assertEquals(PersonValidationUtil.isValidName(lastName), expected);
    }

    @Test
    public void testLastNameOne() {
        boolean expected = true;
        String lastName = "mamani";
        Assert.assertEquals(PersonValidationUtil.isValidName(lastName), expected);
    }

    @Test
    public void testLastNameUpperCase() {
        boolean expected = true;
        String lastName = "zebAllos";
        Assert.assertEquals(PersonValidationUtil.isValidName(lastName), expected);
    }

    @Test
    public void testLastNameUpperCaseAndLowerCase() {
        boolean expected = true;
        String lastName = "zebAllos mamANi";
        Assert.assertEquals(PersonValidationUtil.isValidName(lastName), expected);
    }

    @Test
    public void testLastNameTwoLowerCase() {
        boolean expected = true;
        String lastName = "Mamani zeballos";
        Assert.assertEquals(PersonValidationUtil.isValidName(lastName), expected);
    }

    @Test
    public void testLastNameTwoSpace() {
        boolean expected = true;
        String lastName = "Mamani     Zeballos";
        Assert.assertEquals(PersonValidationUtil.isValidName(lastName), expected);
    }

    @Test
    public void testLastNameTwoVALID() {
        boolean expected = true;
        String lastName = "Mamani Nina";
        Assert.assertEquals(PersonValidationUtil.isValidName(lastName), expected);
    }
}
