package com.trueffect.util;

import com.trueffect.model.Employee;
import com.trueffect.model.Person;
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

    public static void formatOfTheName(Person person) {
        if (StringUtils.isNotBlank(person.getLastName())) {
            String lastName = generateLastName(person.getLastName());
            person.setLastName(lastName);
        }
        if (StringUtils.isNotBlank(person.getFirstName())) {
            String firstName = generateFirstName(person.getFirstName());
            person.setFirstName(firstName);
        }
    }

    public static String generateLastName(String lastName) {
        String resLastName = "";
        String[] lastNameAux = lastName.toLowerCase().split(" ");
        for (String lastN : lastNameAux) {
            resLastName = resLastName + " " + StringUtils.capitalize(lastN);
        }
        resLastName = resLastName.trim();
        return resLastName;
    }

    public static String generateFirstName(String firstName) {
        String resFirstName = "";
        resFirstName = StringUtils.capitalize(firstName.toLowerCase());
        return resFirstName;
    }

    public static void formatOfNameJob(Employee employee) {
        if (StringUtils.isNotBlank(employee.getJob())) {
            String job = generateNameJob(employee.getJob());
            employee.setJob(job);
        }
    }

    public static String generateNameJob(String job) {
        String resJob = "";
        String[] jobSplit = job.toLowerCase().split(" ");
        for (String nameOfJob : jobSplit) {
            resJob = resJob + StringUtils.capitalize(nameOfJob);
        }
        resJob = resJob.trim();
        return resJob;
    }
}
