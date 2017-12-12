package com.trueffect.validation;

import com.trueffect.model.Person;
import com.trueffect.sql.crud.PersonCrud;
import com.trueffect.tools.DataResourse;
import com.trueffect.tools.DataResourse.Genre;
import com.trueffect.tools.DataResourse.TypeIdentifier;
import com.trueffect.tools.RegularExpression;
import java.sql.Connection;
import java.util.regex.Pattern;

/*
 * @author santiago.mamani
 */
public class PersonValidation {

    public static boolean isValidTypeIdentifier(String typeId) {
        TypeIdentifier typeIdentifier = null;
        if (typeId.length() > 0) {
            return typeId.equals(typeIdentifier.CI.name())
                    || typeId.equals(typeIdentifier.PASS.name())
                    || typeId.equals(typeIdentifier.NIT.name());
        }
        return false;
    }

    public static boolean isValidIdentifier(String identifier) {
        if (identifier.length() > 0) {
            return Pattern.matches(RegularExpression.CI, identifier)
                    || Pattern.matches(RegularExpression.PASS, identifier)
                    || Pattern.matches(RegularExpression.NIT, identifier);
        }
        return false;
    }

    public static boolean isValidFirstName(String firstName, int size) {
        if (firstName.length() > 0) {
            return firstName.length() <= DataResourse.MAXIMUM_VALUES && Pattern.matches(RegularExpression.FIRST_NAME, firstName);
        }
        return false;
    }

    public static boolean isValidSize(String name, int size) {
        return name.length() >= 0 && name.length() <= DataResourse.MAXIMUM_VALUES;
    }

    public static boolean isValidLastName(String lastName, int size) {
        if (lastName.length() > 0) {
            if (lastName.contains(" ")) {
                return lastName.length() <= DataResourse.MAXIMUM_VALUES && Pattern.matches(RegularExpression.LAST_NAME_TWO, lastName);
            }
        }

        return lastName.length() <= DataResourse.MAXIMUM_VALUES && Pattern.matches(RegularExpression.LAST_NAME_ONE, lastName);
    }

    public static boolean isValidGenre(String genre) {
        Genre genreEnum = null;
        if (genre != null) {
            return genre.equals(genreEnum.M.name()) || genre.equals(genreEnum.F.name());
        }
        return false;
    }

    public static boolean isValidBirthday(String date) {
        if (date.length() > 0) {
            return Pattern.matches(RegularExpression.DATE, date);
        }
        return false;
    }

    public static boolean isValidIdentifier(String typeIdentifier, String identifier) throws Exception {
        return true;
    }

    public static boolean alreadyExists(String typeIdentifier, String identifier, Connection connection) throws Exception {
        Person person = (Person) PersonCrud.getPersonByTypeIdentifier(typeIdentifier, identifier, connection);

        if (person == null) {
            return false;
        }
        return true;
    }
}
