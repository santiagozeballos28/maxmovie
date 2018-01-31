package com.trueffect.validation;

import com.trueffect.tools.ConstantData;
import com.trueffect.tools.RegularExpression;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.regex.Pattern;

/**
 *
 * @author santiago.mamani
 */
public class MovieValidationUtil {

    public static boolean isValidName(String name) {
        return Pattern.matches(RegularExpression.NAME_MOVIE, name);
    }

    public static boolean isValidIdGenreMovie(String idGenreMovie) {
        return Pattern.matches(RegularExpression.ID_GENRE_MOVIE, idGenreMovie.toUpperCase());
    }

    public static boolean isValidGenre(String name) {
        return Pattern.matches(RegularExpression.GENRE, name);
    }

    public static boolean isUsAscii(String name) {
        CharsetEncoder asciiEncoder = Charset.forName(ConstantData.US_ASCII).newEncoder();
        return asciiEncoder.canEncode(name);
    }

    public static boolean theNumberIsInRange(long number, long start, long limit) {
        return number >= start && number <= limit;
    }
}
