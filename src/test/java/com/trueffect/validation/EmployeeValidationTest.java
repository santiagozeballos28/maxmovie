package com.trueffect.validation;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author santiago.mamani
 */
public class EmployeeValidationTest {

    @Test
    public void testDateOfHire() {
        boolean expected = true;
        String dateOfHire = "2017-12-04";
        Assert.assertEquals(EmployeeValidation.isValidDateOfHire(dateOfHire), expected);
    }

    @Test
    public void testDateOfHire2() {
        boolean expected = false;
        String dateOfHire = "201712-04";
        Assert.assertEquals(EmployeeValidation.isValidDateOfHire(dateOfHire), expected);
    }

    //Test Address
    @Test
    public void testAddress() {
        boolean expected = true;
        String dateOfHire = "Av. Ayacucho";
        Assert.assertEquals(EmployeeValidation.isValidAddress(dateOfHire), expected);
    }

    @Test
    public void testAddress2() {
        boolean expected = false;
        String dateOfHire = "#Av. Ayacucho";
        Assert.assertEquals(EmployeeValidation.isValidAddress(dateOfHire), expected);
    }

    //
    @Test
    public void testJob() {
        boolean expected = true;
        String dateOfHire = "Manager";
        Assert.assertEquals(EmployeeValidation.isValidJob(dateOfHire), expected);
    }

    @Test
    public void testJob2() {
        boolean expected = false;
        String dateOfHire = "Maager";
        Assert.assertEquals(EmployeeValidation.isValidJob(dateOfHire), expected);
    }
    //test Phone

    @Test
    public void testPhone() {
        boolean expected = true;
        int phone = 77973186;
        Assert.assertEquals(EmployeeValidation.isValidPhone(phone), expected);
    }

    @Test
    public void testPhone2() {
        boolean expected = false;
        int phone = 779786;
        Assert.assertEquals(EmployeeValidation.isValidPhone(phone), expected);
    }
}
