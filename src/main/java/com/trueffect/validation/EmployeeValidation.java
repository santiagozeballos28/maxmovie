package com.trueffect.validation;

import com.trueffect.tools.ConstantData.Job;
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

    public static boolean isValidDateOfHire(String dateOfHire) {
        if (!Pattern.matches(RegularExpression.DATE, dateOfHire)) {
            return false;
        }
        String[] dates = dateOfHire.split("-");
        int year = Integer.parseInt(dates[0]);
        int month = Integer.parseInt(dates[1]);
        int dayOfMonth = Integer.parseInt(dates[2]);
        int yearNow = Calendar.getInstance().get(Calendar.YEAR);
        if (year < 1900 || year > yearNow) {
            return false;
        }

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setLenient(false);
            calendar.set(Calendar.YEAR, year);

            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            calendar.set(Calendar.MONTH, month - 1); // [0,...,11]
            Date date = calendar.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidAddress(String address) {
        return Pattern.matches(RegularExpression.ADDRESS, address);
    }

    public static boolean isValidJob(String job) {
        try {
            Job jobEnum = Job.valueOf(job);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public static boolean isValidPhone(int phone) {
        return Pattern.matches(RegularExpression.PHONE, phone + "");
    }
    
}
