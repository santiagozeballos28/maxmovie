
package com.trueffect.validation;

/**
 * This class will have any validation of the project in general
 * @author santiago.mamani
 */
public class ObjectValidationUtil {

    public static boolean isValidSize(String name, int size) {
        return name.length() <= size;
    }
}
