package com.trueffect.validation;

import com.trueffect.logica.DateOperation;
import com.trueffect.tools.ConstantData;
import static com.trueffect.logica.DateOperation.getDataCurrent;
import java.util.Date;
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
        Assert.assertEquals(DateOperation.dateIsInRangeValid(date), expected);
    }

    @Test
    public void testDateRangeValid2() {

        boolean expected = true;
        String date = "2018-01-12";
        Assert.assertEquals(DateOperation.dateIsInRangeValid(date), expected);
    }

    //coment
    //@Test
    public void testDateRangeValid3() {

        boolean expected = false;
        String date = "2018-01-13";
        Assert.assertEquals(DateOperation.dateIsInRangeValid(date), expected);
    }

    @Test
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
}
