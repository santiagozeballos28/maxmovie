package com.trueffect.validation;


import com.trueffect.tools.ConstantData.GenrePerson;
import com.trueffect.tools.ConstantData.TypeIdentifier;
import com.trueffect.tools.RegularExpression;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/*
 * @author santiago.mamani
 */
public class PersonValidation {

    public static boolean isEmpty(String data) {
        return data == null;
    }

    public static boolean isValidTypeIdentifier(String typeId) {
        boolean res = true;
        try {
            TypeIdentifier typeIdentifier = TypeIdentifier.valueOf(typeId.toUpperCase());
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
        GenrePerson genreEnum = null;
        genre = genre.toUpperCase();
        return genre.equals(genreEnum.M.name()) || genre.equals(genreEnum.F.name());
    }

    public static boolean isValidBirthday(String dateBirthday) {
        if (!Pattern.matches(RegularExpression.DATE, dateBirthday)) {
            return false;
        }
        String[] dates = dateBirthday.split("-");
        int year = Integer.parseInt(dates[0]);
        int month = Integer.parseInt(dates[1]);
        int dayOfMonth = Integer.parseInt(dates[2]);

        if (year < 1900) {
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

    public static boolean isValidAge(String age, int ageMin) {
        boolean res = false;
        String yearUser = age.substring(0, 4).trim();
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date datePerson = dateFormat.parse(age);
            Date dateNow = Calendar.getInstance().getTime();
            long deference = dateNow.getTime() - datePerson.getTime();
            int deferenceDays = (int) (deference / (1000 * 60 * 60 * 24));
            int deferenceYear = deferenceDays / 365;
            if (deferenceYear >= ageMin) {
                res = true;
            }
        } catch (Exception e) {
            res = false;
        }
        return res;
    }

    public static boolean isValidIdentifier(String typeIdentifier, String identifier) {
        typeIdentifier = typeIdentifier.toUpperCase();
        identifier = identifier.toUpperCase();
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
