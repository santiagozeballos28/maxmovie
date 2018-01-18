package com.trueffect.validation;

import com.trueffect.tools.ConstantData;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author santiago.mamani
 */
public class MovieValidationTest {

    @Test
    public void testEmpty() {
        boolean expected = true;
        String name = null;
        Assert.assertEquals(MovieValidation.isEmpty(name), expected);
    }

    @Test
    public void testEmpty2() {
        boolean expected = false;
        String name = "";
        Assert.assertEquals(MovieValidation.isEmpty(name), expected);
    }

    @Test
    public void testEmpty3() {
        boolean expected = false;
        String name = "hola";
        Assert.assertEquals(MovieValidation.isEmpty(name), expected);
    }

    @Test
    public void testSize() {
        boolean expected = true;
        String name = "hola";
        Assert.assertEquals(MovieValidation.isValidSize(name, 4), expected);
    }

    @Test
    public void testSize2() {
        boolean expected = false;
        String name = "hola";
        Assert.assertEquals(MovieValidation.isValidSize(name, 3), expected);
    }

    @Test
    public void testGenre() {
        boolean expected = true;
        String name = "teRRor";
        Assert.assertEquals(MovieValidation.isValidGenre(name), expected);
    }

    @Test
    public void testGenre2() {
        boolean expected = false;
        String name = "teRR2or";
        Assert.assertEquals(MovieValidation.isValidGenre(name), expected);
    }

    @Test
    public void testGenre3() {
        boolean expected = false;
        String name = "teRR#or";
        Assert.assertEquals(MovieValidation.isValidGenre(name), expected);
    }

    @Test
    public void testNameMovie() {
        boolean expected = true;
        String name = "silicon VAlley";
        Assert.assertEquals(MovieValidation.isValidName(name), expected);
    }

    @Test
    public void testNameMovie2() {
        boolean expected = true;
        String name = "silicon VAlley 2";
        Assert.assertEquals(MovieValidation.isValidName(name), expected);
    }

    @Test
    public void testNameMovie3() {
        boolean expected = false;
        String name = "silicon V#All$ey ";
        Assert.assertEquals(MovieValidation.isValidName(name), expected);
    }

    @Test
    public void testTheNumberIsUpTo() {
        boolean expected = true;
        int number = 24;
        int start = ConstantData.MIN_OSCAR_NOMINATION;
        int limit = ConstantData.MAX_OSCAR_NOMINATION_MOVIE;
        Assert.assertEquals(MovieValidation.theNumberIsInRange(number, start, limit), expected);
    }

    @Test
    public void testTheNumberIsUpTo2() {
        boolean expected = true;
        int number = 100;
         int start = ConstantData.MIN_OSCAR_NOMINATION;
        int limit = ConstantData.MAX_OSCAR_NOMINATION_MOVIE;
        Assert.assertEquals(MovieValidation.theNumberIsInRange(number,start, limit), expected);
    }

    @Test
    public void testTheNumberIsUpTo3() {
        boolean expected = false;
        int number = 101;
        int start = ConstantData.MIN_OSCAR_NOMINATION;
        int limit = ConstantData.MAX_OSCAR_NOMINATION_MOVIE;
        Assert.assertEquals(MovieValidation.theNumberIsInRange(number, start,limit), expected);
    }

    @Test
    public void testIsUsAscii() {
        boolean expected = true;
        String name = " movie ";
        Assert.assertEquals(MovieValidation.isUsAscii(name), expected);
    }

    @Test
    public void testIsUsAscii3() {
        boolean expected = true;
        String name = " !ab-c~ ";
        Assert.assertEquals(MovieValidation.isUsAscii(name), expected);
    }

    @Test
    public void testIsUsAscii2() {
        boolean expected = false;
        String name = " Película ";
        Assert.assertEquals(MovieValidation.isUsAscii(name), expected);
    }

}
