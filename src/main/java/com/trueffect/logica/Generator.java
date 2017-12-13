package com.trueffect.logica;

import com.trueffect.model.Person;

/**
 *
 * @author santiago.mamani
 */
public class Generator {

    public static String getStringSet(Person person) {
        String resSet = "";
        // variable to store the person attributes
        String varSet = person.getTypeIdentifier();
        if (varSet != null) {
            if (varSet.length() > 0) {
                resSet = "type_identifier= '" + varSet + "'";
            }
        }

        varSet = person.getIdentifier();
        if (varSet != null) {
            if (varSet.length() > 0) {
                resSet = resSet + "identifier= '" + varSet + "'";
            }
        }
        varSet = person.getLastName();
        if (varSet != null) {
            if (varSet.length() > 0) {
                resSet = resSet + "last_name= '" + varSet + "'";
            }
        }
        varSet = person.getFirstName();
        if (varSet != null) {
            if (varSet.length() > 0) {
                resSet = resSet + "first_name= '" + varSet + "'";
            }
        }
        varSet = person.getGenre();
        if (varSet != null) {
            if (varSet.length() > 0) {
                resSet = resSet + "genre= '" + varSet + "'";
            }
        }
        varSet = person.getBirthday();
        if (varSet != null) {
            if (varSet.length() > 0) {
                resSet = resSet + "birthday= '" + varSet + "'";
            }
        }

        return resSet;
    }
}
