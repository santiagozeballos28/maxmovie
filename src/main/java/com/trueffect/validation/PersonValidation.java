package com.trueffect.validation;

import com.trueffect.tools.ConstantData.Genre;
import com.trueffect.tools.ConstantData.TypeIdentifier;
import com.trueffect.tools.RegularExpression;
import java.util.Calendar;
import java.util.regex.Pattern;

/*
 * @author santiago.mamani
 */
public class PersonValidation {

    public static boolean isEmpty(String typeId) {
        return typeId == null;
    }

    public static boolean isValidTypeIdentifier(String typeId) {
        boolean res = true;
        try {
            TypeIdentifier typeIdentifier = TypeIdentifier.valueOf(typeId);
        } catch (Exception e) {
            res = false;
        }
        return res;
    }

    public static boolean isValidIdentifier(String identifier) {
        return Pattern.matches(RegularExpression.CI, identifier)
                || Pattern.matches(RegularExpression.PASS, identifier)
                || Pattern.matches(RegularExpression.NIT, identifier);

    }

    public static boolean isValidFirstName(String firstName) {
        return Pattern.matches(RegularExpression.FIRST_NAME, firstName);
    }

    public static boolean isValidSize(String name, int size) {
        return name.length() <= size;
    }

    public static boolean isValidLastName(String lastName) {
        if (lastName.contains(" ")) {
            return Pattern.matches(RegularExpression.LAST_NAME_TWO, lastName);
        } else {
            return Pattern.matches(RegularExpression.LAST_NAME_ONE, lastName);
        }
    }

    public static boolean isValidGenre(String genre) {
        Genre genreEnum = null;
        return genre.equals(genreEnum.M.name()) || genre.equals(genreEnum.F.name());
    }

    public static boolean isValidBirthday(String date) {
        return Pattern.matches(RegularExpression.DATE, date);
    }

    public static boolean isValidAge(String age, int ageMin) {
        boolean res = false;
        String yearUser = age.substring(6).trim();
        try {
            int yearNow = Calendar.getInstance().get(Calendar.YEAR);
            int yearOfBirth = (int) Integer.parseInt(yearUser);
            if ((yearNow - yearOfBirth) >= ageMin) {
                res = true;
            }
        } catch (Exception e) {
            res = false;
        }
        return res;
    }

    public static boolean isValidIdentifier(String typeIdentifier, String identifier) {
        boolean res = false;
        try {
            TypeIdentifier typeIden = TypeIdentifier.valueOf(typeIdentifier);
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
