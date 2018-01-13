package com.trueffect.logica;

import com.trueffect.tools.RegularExpression;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;
import org.joda.time.LocalDate;
import org.joda.time.Period;

/**
 *
 * @author santiago.mamani
 */
public class DateOperation {

    public static boolean isValidDateFormat(String date) {
        return Pattern.matches(RegularExpression.DATE, date);
    }

    public static boolean dateIsInRangeValid(String date) {

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dateOtherFormat = dateFormat.parse(date);
            LocalDate dateOther = LocalDate.fromDateFields(dateOtherFormat);
            LocalDate dateNow = LocalDate.now();
            Period diff = Period.fieldDifference(dateOther, dateNow);
            if (diff.getYears() > 0) {
                return true;
            } else if (diff.getYears() == 0) {
                if (diff.getMonths() >= 0 && diff.getDays() >= 0) {
                    return true;
                }
            }

        } catch (ParseException ex) {
        }
        return false;
    }

    public static int diferenceYear(String date) {
        int year = -1;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dateO = dateFormat.parse(date);
            LocalDate pdate = LocalDate.fromDateFields(dateO);
            LocalDate now = LocalDate.now();
            Period diff = Period.fieldDifference(pdate, now);
            if (diff.getMonths() >= 0 & diff.getDays() >= 0) {
                year = diff.getYears();
            } else {
                year = diff.getYears() - 1;
            }
        } catch (ParseException ex) {
        }
        return year;
    }

    public static boolean yearIsGreaterThan(String date, int year) {
        if (diferenceYear(date) >= year) {
            return true;
        }
        return false;
    }

    public static String getDataCurrent() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);

    }

    public static boolean isLess(String dateFirst, String dateSecond) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dateFirstFormat = dateFormat.parse(dateFirst);
            Date dateSecondFormat = dateFormat.parse(dateSecond);
            LocalDate localDateFirst = LocalDate.fromDateFields(dateFirstFormat);
            LocalDate localDateSecond = LocalDate.fromDateFields(dateSecondFormat);
            Period diff = Period.fieldDifference(localDateFirst, localDateSecond);
            if (diff.getYears() > 0) {
                return true;
            } else if (diff.getYears() == 0) {
                if (diff.getMonths() >= 0 && diff.getDays() >= 0) {
                    return true;
                }
            }
        } catch (ParseException ex) {
        }
        return false;
    }

}