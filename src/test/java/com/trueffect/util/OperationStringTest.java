package com.trueffect.util;

import com.trueffect.messages.Message;
import java.util.HashMap;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author santiago.mamani
 */
public class OperationStringTest {

    @Test
    public void testTypeIdentifierPass() {
        String msgInput = Message.NOT_VALID_DATA;
        String msgOutputExpected = "The [Last name] [Manti$ago] is not valid. The valid format is as follows: (Letters = A-Za-z, apostrophes = ')";
        HashMap<String, String> listData = new HashMap<String, String>();
        listData.put("{typeData}", Message.LAST_NAME);
        listData.put("{data}", "Manti$ago");
        listData.put("{valid}", Message.VALID_LASTNAME);
        String res = OperationString.generateMesage(msgInput, listData);
        Assert.assertTrue(res.equals(msgOutputExpected));
    }

    @Test
    public void testGenerateNameJobCustomCare1() {
        String input = "Custom care";
        String output = "CustomCare";
        String resNameJob = OperationString.generateNameJob(input);
        Assert.assertEquals(resNameJob, output);
    }

    @Test
    public void testGenerateNameJobCustomCare2() {
        String input = "custom     care";
        String output = "CustomCare";
        String resNameJob = OperationString.generateNameJob(input);
        Assert.assertEquals(resNameJob, output);
    }

    @Test
    public void testGenerateNameJob() {
        String input = "   custom   care ";
        String output = "CustomCare";
        String resNameJob = OperationString.generateNameJob(input);
        Assert.assertEquals(resNameJob, output);
    }

    @Test
    public void testGenerateNameJobAdministrador() {
        String input = "   administrator ";
        String output = "Administrator";
        String resNameJob = OperationString.generateNameJob(input);
        Assert.assertEquals(resNameJob, output);
    }

    @Test
    public void testGenerateNameJobCashier() {
        String input = "   cashier ";
        String output = "Cashier";
        String resNameJob = OperationString.generateNameJob(input);
        Assert.assertEquals(resNameJob, output);
    }
}
