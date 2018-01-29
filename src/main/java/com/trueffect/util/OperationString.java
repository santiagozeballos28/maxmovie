package com.trueffect.util;

import com.trueffect.model.Employee;
import com.trueffect.model.Movie;
import com.trueffect.model.Person;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author santiago.mamani
 */
public class OperationString {

    public static String generateMesage(String message, HashMap<String, String> listData) {
        String res = message;
        Set set = listData.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry) iterator.next();
            res = StringUtils.replace(res, mentry.getKey() + "", mentry.getValue() + "");
        }
        return res;
    }

    public static void addApostrophe(Person person) {
        if (StringUtils.isNotBlank(person.getLastName())) {
            String lastName = addApostrophe(person.getLastName());
            person.setLastName(lastName);
        }
        if (StringUtils.isNotBlank(person.getFirstName())) {
            String firstName = addApostrophe(person.getFirstName());
            person.setFirstName(firstName);
        }
    }

    public static String addApostrophe(String name) {
        return StringUtils.replace(name, "'", "''");
    }

    public static String lessAnApostrophe(String name) {
        return StringUtils.replace(name, "''", "'");
    }

    public static void formatOfTheName(Person person) {
        if (StringUtils.isNotBlank(person.getLastName())) {
            String lastName = generateName(person.getLastName());
            person.setLastName(lastName);
        }
        if (StringUtils.isNotBlank(person.getFirstName())) {
            String firstName = generateName(person.getFirstName());
            person.setFirstName(firstName);
        }
    }

    public static String generateName(String name) {
        String resName = "";
        String[] nameArrayAux = name.toLowerCase().split(" ");
        ArrayList<String> names = removeEmpty(nameArrayAux);
        for (String nameString : names) {
            resName = resName + " " + StringUtils.capitalize(nameString);
        }
        resName = resName.trim();
        return resName;
    }

    public static void formatOfNameJob(Employee employee) {
        if (StringUtils.isNotBlank(employee.getJob())) {
            String job = employee.getJob().toUpperCase();
            employee.setJob(job);
        }
    }

    public static String toUpperCase(String name) {
        if (StringUtils.isNotBlank(name)) {
            return name.toUpperCase();
        }
        return name;
    }

    public static ArrayList<String> removeEmpty(String[] array) {
        ArrayList<String> res = new ArrayList<String>();
        for (String string : array) {
            if (!string.isEmpty()) {
                res.add(string);
            }
        }
        return res;
    }

    public static String removeSpace(String string) {
        String resString = "";
        String[] stringArrayAux = string.split(" ");
        ArrayList<String> listString = removeEmpty(stringArrayAux);
        for (String strg : listString) {
            resString = resString + " " + strg;
        }
        resString = resString.trim();
        return resString;
    }
}
