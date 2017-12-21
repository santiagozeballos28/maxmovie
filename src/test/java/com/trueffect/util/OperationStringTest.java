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
        //The {data} is not valid. The valid format is as follows: {valid}
        String msgInput = Message.NOT_VALID_DATA;
        String msgOutputExpected = "The [Last name] [Manti$ago] is not valid. The valid format is as follows: (Letters = A-Za-z, apostrophes = ')";
        HashMap<String, String> listData = new HashMap<String, String>();
        listData.put("{typeData}", Message.LAST_NAME);
        listData.put("{data}", "Manti$ago");
        listData.put("{valid}", Message.VALID_LN);
        String res = OperationString.generateMesage(msgInput, listData);
        Assert.assertTrue(res.equals(msgOutputExpected));
    }
}
