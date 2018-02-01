package com.trueffect.validation;

import com.trueffect.logic.DateOperation;
import com.trueffect.tools.ConstantData;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author santiago.mamani
 */
public class DateValidationTest {

    /*Test date
     */
    @Test
    public void testDateFormat() {

        boolean expected = true;
        String date = "2002-12-15";
        Assert.assertEquals(DateOperation.isValidDateFormat(date), expected);
    }

    @Test
    public void testDateFormat2() {

        boolean expected = false;
        String date = "-12-2014";
        Assert.assertEquals(DateOperation.isValidDateFormat(date), expected);
    }

    @Test
    public void testDateFormat3() {

        boolean expected = true;
        String date = "2014-15-02";
        Assert.assertEquals(DateOperation.isValidDateFormat(date), expected);
    }

    @Test
    public void testDateFormat4() {

        boolean expected = true;
        String date = "2015-12-30";
        Assert.assertEquals(DateOperation.isValidDateFormat(date), expected);
    }

    @Test
    public void testDateRangeValid() {

        boolean expected = true;
        String date = "2015-12-30";
        Assert.assertEquals(DateOperation.isValidDate(date), expected);
    }

    @Test
    public void testDateRangeValid2() {

        boolean expected = true;
        String date = "2018-01-12";
        Assert.assertEquals(DateOperation.isValidDate(date), expected);
    }

    //coment
    //@Test
    public void testDateRangeValid3() {

        boolean expected = false;
        String date = "2018-01-13";
        Assert.assertEquals(DateOperation.isValidDate(date), expected);
    }

    //@Test
    public void testDateDiferenceYear1() {

        int expected = -1;
        String date = "2018-01-13";
        Assert.assertEquals(DateOperation.diferenceYear(date), expected);
    }

    //coment
    //  @Test
    public void testDateDiferenceYear2() {

        int expected = 0;
        String date = "2017-01-13";
        Assert.assertEquals(DateOperation.diferenceYear(date), expected);
    }

    @Test
    public void testDateDiferenceYear3() {

        int expected = 1;
        String date = "2017-01-12";
        Assert.assertEquals(DateOperation.diferenceYear(date), expected);
    }

    @Test
    public void testDateDiferenceYear4() {

        int expected = 1;
        String date = "2017-01-11";
        Assert.assertEquals(DateOperation.diferenceYear(date), expected);
    }

    @Test
    public void testDateDiferenceYear5() {

        int expected = 2;
        String date = "2016-01-12";
        Assert.assertEquals(DateOperation.diferenceYear(date), expected);
    }

    @Test
    public void testDateDiferenceYear6() {

        int expected = 0;
        String date = "2017-07-02";
        Assert.assertEquals(DateOperation.diferenceYear(date), expected);
    }

    @Test
    public void testDateYearIsGreaterThan() {

        boolean expected = false;
        String birthday = "2005-12-12";
        int minimumAge = ConstantData.MIN_AGE_RENTER;
        Assert.assertEquals(DateOperation.yearIsGreaterThan(birthday, minimumAge), expected);
    }

    @Test
    public void testDateYearIsGreaterThan2() {

        boolean expected = true;
        String birthday = "2002-12-12";
        int minimumAge = ConstantData.MIN_AGE_RENTER;
        Assert.assertEquals(DateOperation.yearIsGreaterThan(birthday, minimumAge), expected);
    }

    @Test
    public void testDateComparation() {

        boolean expected = true;
        String dateFirst = "2002-12-11";
        String dateLast = "2002-12-12";
        Assert.assertEquals(DateOperation.isLess(dateFirst, dateLast), expected);
    }

    @Test
    public void testDateComparation2() {

        boolean expected = true;
        String dateFirst = "2002-12-12";
        String dateLast = "2002-12-12";
        Assert.assertEquals(DateOperation.isLess(dateFirst, dateLast), expected);
    }

    @Test
    public void testDateComparation3() {

        boolean expected = false;
        String dateFirst = "2002-12-13";
        String dateLast = "2002-12-12";
        Assert.assertEquals(DateOperation.isLess(dateFirst, dateLast), expected);
    }

    @Test
    public void testDateComparation4() {

        boolean expected = false;
        String dateFirst = "2003-12-11";
        String dateLast = "2002-12-12";
        Assert.assertEquals(DateOperation.isLess(dateFirst, dateLast), expected);
    }

    @Test
    public void testDateSameYearAndMonth() {

        boolean expected = true;
        String dateFirst = "2018-01-17 19:55:27.114";
        String dateLast = "2018-01-29";
        Assert.assertEquals(DateOperation.areSameMonthAndYear(dateFirst, dateLast), expected);
    }

    @Test
    public void testDateSameYearAndMonth2() {

        boolean expected = false;
        String dateFirst = "2018-02-17 19:55:27.114";
        String dateLast = "2018-01-29";
        Assert.assertEquals(DateOperation.areSameMonthAndYear(dateFirst, dateLast), expected);
    }

    @Test
    public void testDateSameYearAndMonth3() {

        boolean expected = true;
        String dateFirst = "2018-02-17";
        String dateLast = "2018-02-23";
        Assert.assertEquals(DateOperation.areSameMonthAndYear(dateFirst, dateLast), expected);
    }

    @Test
    public void testDateSameYearAndMonth4() {

        boolean expected = false;
        String dateFirst = "2017-02-17";
        String dateLast = "2018-02-23";
        Assert.assertEquals(DateOperation.areSameMonthAndYear(dateFirst, dateLast), expected);
    }

    //@Test
    public void testTimestampDiferenceMinutos() {

        int expected = 0;
        String dateFirst = "2018-02-17 19:55:27.114";
        String dateSecond = "2018-02-17 20:59:27.114";
        System.out.println("DIF1: " + DateOperation.diferenceMinutes(dateFirst, dateSecond));
        Assert.assertEquals(DateOperation.diferenceMinutes(dateFirst, dateSecond), expected);
    }

    //@Test
    public void testTimestampDiferenceMinutos2() {

        int expected = 1440;
        String dateFirst = "2018-02-17 19:55:27.114";
        String dateSecond = "2018-02-19 19:57:27.114";
        System.out.println("DIF2: " + DateOperation.diferenceMinutes(dateFirst, dateSecond));
        Assert.assertEquals(DateOperation.diferenceMinutes(dateFirst, dateSecond), expected);
    }

    @Test
    public void testTimestampDiferenceDay1() {

        int expected = 2;
        String dateFirst = "2018-02-17 19:55:27.114";
        String dateSecond = "2018-02-19 19:57:27.114";
        System.out.println("DIF2: " + DateOperation.diferenceDays(dateFirst, dateSecond));
        Assert.assertEquals(DateOperation.diferenceDays(dateFirst, dateSecond), expected);
    }

    // @Test
    public void testTimestampDiferenceDay2() {

        int expected = 2;
        String dateFirst = "2018-01-25 19:55:27.114";
        String dateSecond = DateOperation.getTimertampCurrent();
        System.err.println("DATE SECOND: " + dateSecond);
        System.out.println("DIF2: " + DateOperation.diferenceDays(dateFirst, dateSecond));
        Assert.assertEquals(DateOperation.diferenceDays(dateFirst, dateSecond), expected);
    }
}
