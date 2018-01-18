package com.trueffect.validation;

import com.trueffect.logic.DateOperation;
import com.trueffect.messages.Message;
import com.trueffect.tools.ConstantData;
import com.trueffect.tools.RegularExpression;
import com.trueffect.util.OperationString;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 *
 * @author santiago.mamani
 */
public class MovieValidation {

    HashMap<String, String> listData;

    public MovieValidation() {
        listData = new HashMap<String, String>();
    }

    public static boolean isEmpty(String data) {
        return data == null;
    }

    public static boolean isValidSize(String name, int size) {
        return name.length() <= size;
    }

    public static boolean isValidName(String name) {
        return Pattern.matches(RegularExpression.NAME_MOVIE, name);
    }

    public static boolean isValidGenre(String name) {
        return Pattern.matches(RegularExpression.GENRE, name);
    }

    public static boolean isUsAscii(String name) {
        CharsetEncoder asciiEncoder = Charset.forName(ConstantData.US_ASCII).newEncoder();
        return asciiEncoder.canEncode(name);
    }

    public static boolean theNumberIsInRange(int number, int start, int limit) {
        return number >= start && number <= limit;
    }

    public void verifyNamesOfActors(ArrayList<String> listActor, ArrayList<String> listError) {
        for (int i = 0; i < listActor.size(); i++) {
            String nameActor = listActor.get(i);
            if (!isUsAscii(nameActor)) {
                listData.clear();
                listData.put(ConstantData.TYPE_DATA, ConstantData.NAME_ACTOR_MOVIE);
                listData.put(ConstantData.DATA, nameActor);
                listData.put(ConstantData.VALID, ConstantData.VALID_US_ASCII);
                String errorMessages = OperationString.generateMesage(Message.NOT_VALID_DATA_THE_VALID_DATA_ARE, listData);
                listError.add(errorMessages);
            }
        }

    }

    public void verifySizeNameActors(ArrayList<String> listActor, ArrayList<String> listError) {
        for (int i = 0; i < listActor.size(); i++) {
            String nameActor = listActor.get(i);
            if (!isValidSize(nameActor, ConstantData.MAX_NAME_ACTOR_MOVIE)) {
                listData.clear();
                listData.put(ConstantData.TYPE_DATA, ConstantData.NAME_ACTOR_MOVIE);
                listData.put(ConstantData.DATA, nameActor);
                listData.put(ConstantData.VALID, ConstantData.MAX_NAME_ACTOR_MOVIE + "");
                String errorMessages = OperationString.generateMesage(Message.SIZE_MAX, listData);
                listError.add(errorMessages);
            }
        }
    }

    public void verifySize(String name, String typeData, int sizeMax, ArrayList<String> listError) {
        //Validation of name movie size
        if (!MovieValidation.isValidSize(name, sizeMax)) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, typeData);
            listData.put(ConstantData.DATA, name);
            listData.put(ConstantData.SIZE, sizeMax + "");
            String errorMessages = OperationString.generateMesage(Message.SIZE_MAX, listData);
            listError.add(errorMessages);
        }
    }

    public void verifyName(String name, ArrayList<String> listError) {
        //Validation of name movie
        if (!MovieValidation.isValidName(name)) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.NAME_MOVIE);
            listData.put(ConstantData.DATA, name);
            listData.put(ConstantData.VALID, ConstantData.VALID_NAME_MOVIE);
            String errorMessages = OperationString.generateMesage(Message.NOT_VALID_DATA_THE_VALID_DATA_ARE, listData);
            listError.add(errorMessages);
        }
    }

    public void verifyGenre(String genre, ArrayList<String> listError) {
        //Validation of name movie
        if (!MovieValidation.isValidGenre(genre)) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.NAME_GENRE_MOVIE);
            listData.put(ConstantData.DATA, genre);
            listData.put(ConstantData.VALID, ConstantData.VALID_NAME_GENRE_MOVIE);
            String errorMessages = OperationString.generateMesage(Message.NOT_VALID_DATA_THE_VALID_DATA_ARE, listData);
            listError.add(errorMessages);
        }
    }

    public void verifyDirector(String director, ArrayList<String> listError) {
        //Validation of director movie
        if (!MovieValidation.isUsAscii(director)) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.NAME_DIRECTOR_MOVIE);
            listData.put(ConstantData.DATA, director);
            listData.put(ConstantData.VALID, ConstantData.VALID_US_ASCII);
            String errorMessages = OperationString.generateMesage(Message.NOT_VALID_DATA_THE_VALID_DATA_ARE, listData);
            listError.add(errorMessages);
        }
    }

    public void verifyYear(int year, ArrayList<String> listError) {
        //Validation of year movie
        if (!MovieValidation.theNumberIsInRange(year, ConstantData.MIN_YEAR, DateOperation.getYearCurrent())) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.YEAR);
            listData.put(ConstantData.DATA, year + "");
            listData.put(ConstantData.VALID, ConstantData.MIN_YEAR + "-" + DateOperation.getYearCurrent());
            String errorMessages = OperationString.generateMesage(Message.NOT_VALID_DATA_NUMBERS_ALLOWED, listData);
            listError.add(errorMessages);
        }
    }

    public void verifyOscarNomination(int oscarNomination, ArrayList<String> listError) {
        //Validation of oscar nomination
        int minOscarNomination = ConstantData.MIN_OSCAR_NOMINATION;
        int maxOscarNomination = ConstantData.MAX_OSCAR_NOMINATION_MOVIE;
        if (!MovieValidation.theNumberIsInRange(oscarNomination, minOscarNomination, maxOscarNomination)) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.OSCAR_NOMINATION_MOVIE);
            listData.put(ConstantData.DATA, oscarNomination + "");
            listData.put(ConstantData.VALID, minOscarNomination + "-" + maxOscarNomination);
            String errorMessages = OperationString.generateMesage(Message.NOT_VALID_DATA_NUMBERS_ALLOWED, listData);
            listError.add(errorMessages);
        }
    }

}
