package com.trueffect.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author santiago.mamani
 */
public class OperationString {

    public static String generateMesage(String message, HashMap<String, String> listData) {
        String res = message;
        Set set = listData.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry) iterator.next();
            res = StringUtils.replace(res, mentry.getKey() + "", mentry.getValue() + "");
         }
        return res;
    }

}
