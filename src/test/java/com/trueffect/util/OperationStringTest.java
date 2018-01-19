package com.trueffect.util;

import com.trueffect.messages.Message;
import com.trueffect.tools.ConstantData;
import com.trueffect.validation.EmployeeValidation;
import java.util.HashMap;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author santiago.mamani
 */
public class OperationStringTest {

    //@Test
    public void testTypeIdentifierPass() {
        String msgInput = Message.NOT_VALID_THE_VALID_DATA_ARE;
        String msgOutputExpected = "The Last name [Manti$ago] is not valid. The valid format is as follows: (Letters = A-Za-z, apostrophes = ')";
        HashMap<String, String> listData = new HashMap<String, String>();
        listData.put(ConstantData.TYPE_DATA, ConstantData.LAST_NAME);
        listData.put(ConstantData.DATA, "Manti$ago");
        listData.put(ConstantData.VALID, ConstantData.VALID_LASTNAME);
        String res = OperationString.generateMesage(msgInput, listData);
        Assert.assertTrue(res.equals(msgOutputExpected));
    }

    @Test
    public void testGenerateNameJobCustomCare1() {
        String input = "CSHR";
        boolean expected = true;

        Assert.assertEquals(EmployeeValidation.isValidJob(input), expected);
    }

    @Test
    public void testGenerateNameJobCustomCare2() {
        String input = "MGR";
        boolean expected = true;

        Assert.assertEquals(EmployeeValidation.isValidJob(input), expected);
    }

    @Test
    public void testGenerateNameJob() {
        String input = "CC";
        boolean expected = true;

        Assert.assertEquals(EmployeeValidation.isValidJob(input), expected);
    }

    @Test
    public void testGenerateNameJobAdministrador() {
        String input = "   CDCD  ";
        boolean expected = false;

        Assert.assertEquals(EmployeeValidation.isValidJob(input), expected);
    }
}
