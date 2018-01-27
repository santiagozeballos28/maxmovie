package com.trueffect.validation;

import com.trueffect.tools.ConstantData;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author santiago.mamani
 */
public class EmployeeValidationTest {

    //Test Address
    @Test
    public void testAddress() {
        boolean expected = true;
        String dateOfHire = "Av. Ayacucho";
        int maxLength = ConstantData.MAX_LENGTH_ADDRESS;
        Assert.assertEquals(ObjectValidationUtil.isValidSize(dateOfHire,maxLength), expected);
    }

    @Test
    public void testAddress2() {
        boolean expected = true;
        String dateOfHire = "#Av. Ayacucho";
        int maxLength = ConstantData.MAX_LENGTH_ADDRESS;
        Assert.assertEquals(ObjectValidationUtil.isValidSize(dateOfHire,maxLength), expected);
    }

    //
    @Test
    public void testJob() {
        boolean expected = true;
        String dateOfHire = "MGR";
        Assert.assertEquals(EmployeeValidationUtil.isValidJob(dateOfHire), expected);
    }

    @Test
    public void testJob2() {
        boolean expected = false;
        String dateOfHire = "Maager";
        Assert.assertEquals(EmployeeValidationUtil.isValidJob(dateOfHire), expected);
    }
    //test Phone

    @Test
    public void testPhone() {
        boolean expected = true;
        int phone = 77973186;
        Assert.assertEquals(EmployeeValidationUtil.isValidPhone(phone), expected);
    }

    @Test
    public void testPhone2() {
        boolean expected = false;
        int phone = 779786;
        Assert.assertEquals(EmployeeValidationUtil.isValidPhone(phone), expected);
    }
}
