package com.trueffect.validation;

import com.trueffect.tools.DataResourse;
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
    public void testTypeIdentifierCi() {
        boolean expected = true;
        String typeIdentifier = "CI";
        Assert.assertEquals(PersonValidation.isValidTypeIdentifier(typeIdentifier), expected);

    }

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
        Assert.assertEquals(PersonValidation.isValidTypeIdentifier(typeIdentifier), expected);
    }

    /*
    *Identifier 
     */
    @Test
    public void testIdentyfierEmpty() {

        boolean expected = false;
        String typeIdentifier = "";
        Assert.assertEquals(PersonValidation.isValidIdentifier(typeIdentifier), expected);

    }

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

        boolean expected = false;
        String typeIdentifier = "1022";
        Assert.assertEquals(PersonValidation.isValidIdentifier(typeIdentifier), expected);

    }

    /*
    * Validation 
     */
    @Test
    public void testFirstName() {

        boolean expected = true;
        String firstName = "Santiago";
        int sizeMax = DataResourse.MAXIMUM_VALUES;
        Assert.assertEquals(PersonValidation.isValidFirstName(firstName, sizeMax), expected);

    }

    @Test
    public void testFirstNameTwoName() {

        boolean expected = false;
        String firstName = "Santiago Elias";
        int sizeMax = DataResourse.MAXIMUM_VALUES;
        Assert.assertEquals(PersonValidation.isValidFirstName(firstName, sizeMax), expected);

    }

    @Test
    public void testFirstNameSymbol() {

        boolean expected = false;
        String firstName = "Sant#iago$";
        int sizeMax = DataResourse.MAXIMUM_VALUES;
        Assert.assertEquals(PersonValidation.isValidFirstName(firstName, sizeMax), expected);

    }

    @Test
    public void testFirstEmpty() {
        boolean expected = false;
        String firstName = "";
        int sizeMax = DataResourse.MAXIMUM_VALUES;
        Assert.assertEquals(PersonValidation.isValidFirstName(firstName, sizeMax), expected);

    }

    @Test
    public void testLastNameEmpty() {
        boolean expected = false;
        String lastName = "";
        int sizeMax = DataResourse.MAXIMUM_VALUES;
        Assert.assertEquals(PersonValidation.isValidLastName(lastName, sizeMax), expected);

    }

    @Test
    public void testOneLastName() {
        boolean expected = true;
        String lastName = "Zeballos";
        int sizeMax = DataResourse.MAXIMUM_VALUES;
        Assert.assertEquals(PersonValidation.isValidLastName(lastName, sizeMax), expected);

    }

    @Test
    public void testLastNameSymbol() {
        boolean expected = false;
        String lastName = "Mamani# Zeballos";
        int sizeMax = DataResourse.MAXIMUM_VALUES;
        Assert.assertEquals(PersonValidation.isValidLastName(lastName, sizeMax), expected);

    }

    @Test
    public void testLastNameOne() {
        boolean expected = false;
        String lastName = "MamaniZeballos";
        int sizeMax = DataResourse.MAXIMUM_VALUES;
        Assert.assertEquals(PersonValidation.isValidLastName(lastName, sizeMax), expected);

    }

    @Test
    public void testLastNameTwoLowerCase() {
        boolean expected = false;
        String lastName = "Mamani zeballos";
        int sizeMax = DataResourse.MAXIMUM_VALUES;
        Assert.assertEquals(PersonValidation.isValidLastName(lastName, sizeMax), expected);

    }

    @Test
    public void testLastNameTwoSpace() {
        boolean expected = false;
        String lastName = "Mamani  Zeballos";
        int sizeMax = DataResourse.MAXIMUM_VALUES;
        Assert.assertEquals(PersonValidation.isValidLastName(lastName, sizeMax), expected);

    }

    @Test
    public void testLastNameTwoVALID() {
        boolean expected = true;
        String lastName = "Mamani Nina";
        int sizeMax = DataResourse.MAXIMUM_VALUES;
        Assert.assertEquals(PersonValidation.isValidLastName(lastName, sizeMax), expected);

    }

    /*Test Briday
     */

    @Test
    public void testBirthDay() {

        boolean expected = true;
        String birthday = "11/12/2014";
        Assert.assertEquals(PersonValidation.isValidBirthday(birthday), expected);

    }

    @Test
    public void testBirthday2() {

        boolean expected = false;
        String birthday = "/12/2014";
        Assert.assertEquals(PersonValidation.isValidBirthday(birthday), expected);

    }
}
