package com.trueffect.validation;

import com.trueffect.tools.ConstantData;
import com.trueffect.tools.RegularExpression;
import java.util.regex.Pattern;

/**
 *
 * @author santiago.mamani
 */
public class PersonValidationUtil {

    public static boolean isValidTypeIdentifier(String typeId) {
        boolean res = true;
        try {
            ConstantData.TypeIdentifier typeIdentifier = ConstantData.TypeIdentifier.valueOf(typeId.toUpperCase());
        } catch (Exception e) {
            res = false;
        }
        return res;
    }

    public static boolean isValidIdentifier(String identifier) {
        identifier = identifier.toUpperCase();
        return Pattern.matches(RegularExpression.CI, identifier)
                || Pattern.matches(RegularExpression.PASS, identifier)
                || Pattern.matches(RegularExpression.NIT, identifier);
    }

    public static boolean isValidName(String name) {
        if (name.contains(" ")) {
            return Pattern.matches(RegularExpression.NAME_TWO_PERSON, name);
        } else {
            return Pattern.matches(RegularExpression.NAME_ONE_PERSON, name);
        }
    }

    public static boolean isValidGenre(String genre) {
        ConstantData.GenrePerson genreEnum = null;
        genre = genre.toUpperCase();
        return genre.equals(genreEnum.M.name()) || genre.equals(genreEnum.F.name());
    }

    public static boolean isValidIdentifier(String typeIdentifier, String identifier) {
        typeIdentifier = typeIdentifier.toUpperCase();
        identifier = identifier.toUpperCase();
        boolean res = false;
        try {
            ConstantData.TypeIdentifier typeIden = ConstantData.TypeIdentifier.valueOf(typeIdentifier);
            switch (typeIden) {

                case CI:
                    if (Pattern.matches(RegularExpression.CI, identifier)) {
                        res = true;
                    }
                    break;
                case PASS:
                    if (Pattern.matches(RegularExpression.PASS, identifier)) {
                        res = true;
                    }
                    break;
                case NIT:
                    if (Pattern.matches(RegularExpression.NIT, identifier)) {
                        res = true;
                    }
                    break;
            }
        } catch (Exception e) {
        }
        return res;
    }
}
