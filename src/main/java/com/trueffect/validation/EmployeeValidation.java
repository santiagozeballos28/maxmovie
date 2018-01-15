package com.trueffect.validation;

import com.trueffect.tools.ConstantData;
import com.trueffect.tools.ConstantData.JobName;
import com.trueffect.tools.RegularExpression;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 *
 * @author santiago.mamani
 */
public class EmployeeValidation {

    public static boolean isValidAddress(String address) {
        return address.length() < ConstantData.MAX_LENGTH_ADDRESS;
    }

    public static boolean isValidJob(String job) {
        try {
            JobName jobEnum = JobName.valueOf(job);
            switch (jobEnum) {
                case ADMIN:
                    return false;
                default:
                    return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidPhone(int phone) {
        return Pattern.matches(RegularExpression.PHONE_CELL, phone + "")
                || Pattern.matches(RegularExpression.PHONE_FIXED, phone + "");
    }
}
