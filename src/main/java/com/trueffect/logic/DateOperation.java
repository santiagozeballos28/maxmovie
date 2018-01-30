package com.trueffect.logic;

import com.trueffect.tools.ConstantData;
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

    /*
    * This method must necessarily receive a valid formatted date.
    * Otherwise the "catch" must be implemented.
     */
    public static boolean isValidDate(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(ConstantData.SIMPLE_DATE_FORMAT);
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
            //Implement if the input date parameter is an invalid format
        }
        return false;
    }

    /*
    * This method must necessarily receive a valid formatted date.
    * Otherwise the "catch" must be implemented.
     */
    public static int diferenceYear(String date) {
        int year = -1;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(ConstantData.SIMPLE_DATE_FORMAT);
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
            //Implement if the input date parameter is an invalid format
        }
        return year;
    }

    public static boolean yearIsGreaterThan(String date, int year) {
        if (diferenceYear(date) >= year) {
            return true;
        }
        return false;
    }

    public static String getDateCurrent() {
        DateFormat dateFormat = new SimpleDateFormat(ConstantData.SIMPLE_DATE_FORMAT);
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getTimertampCurrent() {
        DateFormat dateFormat = new SimpleDateFormat(ConstantData.TIMESTAMP_DATE_FORMAT);
        Date date = new Date();
        return dateFormat.format(date);
    }

    /*
    * This method must necessarily receive a valid formatted date.
    * Otherwise the "catch" must be implemented.
     */
    public static boolean isLess(String dateFirst, String dateSecond) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(ConstantData.SIMPLE_DATE_FORMAT);
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
            //Implement if the input date parameter is an invalid format
        }
        return false;
    }

    public static int getYearCurrent() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    /*
    * This method must necessarily receive a valid formatted date.
    * Otherwise the "catch" must be implemented.
     */
    public static boolean areSameMonthAndYear(String dateFirst, String dateSecond) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(ConstantData.SIMPLE_DATE_FORMAT);
            Date dateFirstFormat = dateFormat.parse(dateFirst);
            Date dateSecondFormat = dateFormat.parse(dateSecond);
            LocalDate localDateFirst = LocalDate.fromDateFields(dateFirstFormat);
            LocalDate localDateSecond = LocalDate.fromDateFields(dateSecondFormat);
            Period diff = Period.fieldDifference(localDateFirst, localDateSecond);
            if (diff.getYears() == 0 && diff.getMonths() == 0) {
                return true;
            }
        } catch (ParseException ex) {
            //Implement if the input date parameter is an invalid format
        }
        return false;
    }

    /*
    *This method must necessarily receive a valid formatted date.
    *Otherwise the "catch" must be implemented.
    *It is mandatory that dateSecond be equal to or greater than date dateFirst.
     */
    public static int diferenceMinutes(String dateFirst, String dateSecond) {
        int diferenceMin = -1;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(ConstantData.TIMESTAMP_DATE_FORMAT);
            Date dateFirstFormat = dateFormat.parse(dateFirst);
            Date dateSecondFormat = dateFormat.parse(dateSecond);
            LocalDate localDateFirst = LocalDate.fromDateFields(dateFirstFormat);
            LocalDate localDateSecond = LocalDate.fromDateFields(dateSecondFormat);
            Period diff = Period.fieldDifference(localDateFirst, localDateSecond);
            diferenceMin = diff.getMinutes();
        } catch (ParseException ex) {
            //Implement if the input date parameter is an invalid format
        }
        return diferenceMin;
    }

    /*
    *This method must necessarily receive a valid formatted date.
    *Otherwise the "catch" must be implemented.
    *It is mandatory that dateSecond be equal to or greater than date dateFirst.
     */
    public static int diferenceDays(String dateFirst, String dateSecond) {
        int diferenceDays = -1;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(ConstantData.TIMESTAMP_DATE_FORMAT);
            Date dateFirstFormat = dateFormat.parse(dateFirst);
            Date dateSecondFormat = dateFormat.parse(dateSecond);
            LocalDate localDateFirst = LocalDate.fromDateFields(dateFirstFormat);
            LocalDate localDateSecond = LocalDate.fromDateFields(dateSecondFormat);
            Period diff = Period.fieldDifference(localDateFirst, localDateSecond);
            diferenceDays = diff.getDays();
        } catch (ParseException ex) {
            //Implement if the input date parameter is an invalid format
        }
        return diferenceDays;
    }

    /*
    *This method must necessarily receive a valid formatted date.
    *Otherwise the "catch" must be implemented.
    *It is mandatory that dateSecond be equal to or greater than date dateFirst.
     */
    public static int diferenceYear(String dateFirst, String dateSecond) {
        int year = -1;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(ConstantData.SIMPLE_DATE_FORMAT);
            Date dateFormatFirst = dateFormat.parse(dateFirst);
            Date dateFormatSecond = dateFormat.parse(dateSecond);
            LocalDate localDateFirst = LocalDate.fromDateFields(dateFormatFirst);
            LocalDate localDateSecond = LocalDate.fromDateFields(dateFormatSecond);
            Period diff = Period.fieldDifference(localDateFirst, localDateSecond);
            if (diff.getMonths() >= 0 & diff.getDays() >= 0) {
                year = diff.getYears();
            } else {
                year = diff.getYears() - 1;
            }
        } catch (ParseException ex) {
            //Implement if the input date parameter is an invalid format.
        }
        return year;
    }
}
