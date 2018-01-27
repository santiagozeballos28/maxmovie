package com.trueffect.validation;

import com.trueffect.logic.DateOperation;
import com.trueffect.messages.Message;
import com.trueffect.model.GenreMovie;
import com.trueffect.tools.ConstantData;
import com.trueffect.util.ModelObject;
import com.trueffect.util.OperationString;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author santiago.mamani
 */
public class MovieValidation {

    HashMap<String, String> listData;

    public MovieValidation() {
        listData = new HashMap<String, String>();
    }
    public void verifyNamesOfActors(ArrayList<String> listActor, ArrayList<String> listError) {
        for (int i = 0; i < listActor.size(); i++) {
            String nameActor = listActor.get(i);
            if (!MovieValidationUtil.isUsAscii(nameActor)) {
                listData.clear();
                listData.put(ConstantData.TYPE_DATA, ConstantData.NAME_ACTOR_MOVIE);
                listData.put(ConstantData.DATA, nameActor);
                listData.put(ConstantData.VALID, ConstantData.VALID_US_ASCII);
                String errorMessages = OperationString.generateMesage(Message.NOT_VALID_THE_VALID_DATA_ARE, listData);
                listError.add(errorMessages);
            }
        }
    }

    public void verifySizeNameActors(ArrayList<String> listActor, ArrayList<String> listError) {
        for (int i = 0; i < listActor.size(); i++) {
            String nameActor = listActor.get(i);
            if (!ObjectValidationUtil.isValidSize(nameActor, ConstantData.MAX_NAME_ACTOR_MOVIE)) {
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
        String nameAux = OperationString.removeSpace(name);
        if (!ObjectValidationUtil.isValidSize(nameAux, sizeMax)) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, typeData);
            listData.put(ConstantData.DATA, name);
            listData.put(ConstantData.SIZE, sizeMax + "");
            String errorMessages = OperationString.generateMesage(Message.SIZE_MAX, listData);
            listError.add(errorMessages);
        }
    }

    public void verifyName(String name, ArrayList<String> listError) {
        if (!MovieValidationUtil.isValidName(name)) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.NAME_MOVIE);
            listData.put(ConstantData.DATA, name);
            listData.put(ConstantData.VALID, ConstantData.VALID_NAME_MOVIE);
            String errorMessages = OperationString.generateMesage(Message.NOT_VALID_THE_VALID_DATA_ARE, listData);
            listError.add(errorMessages);
        }
    }

    public void verifyGenre(String genre, ArrayList<String> listError) {
        if (!MovieValidationUtil.isValidGenre(genre)) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.NAME_GENRE_MOVIE);
            listData.put(ConstantData.DATA, genre);
            listData.put(ConstantData.VALID, ConstantData.VALID_NAME_GENRE_MOVIE);
            String errorMessages = OperationString.generateMesage(Message.NOT_VALID_THE_VALID_DATA_ARE, listData);
            listError.add(errorMessages);
        }
    }

    public void verifyDirector(String director, ArrayList<String> listError) {
        if (!MovieValidationUtil.isUsAscii(director)) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.NAME_DIRECTOR_MOVIE);
            listData.put(ConstantData.DATA, director);
            listData.put(ConstantData.VALID, ConstantData.VALID_US_ASCII);
            String errorMessages = OperationString.generateMesage(Message.NOT_VALID_THE_VALID_DATA_ARE, listData);
            listError.add(errorMessages);
        }
    }

    public void verifyYear(int year, ArrayList<String> listError) {
        if (!MovieValidationUtil.theNumberIsInRange(year, ConstantData.MIN_YEAR, DateOperation.getYearCurrent())) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.YEAR);
            listData.put(ConstantData.DATA, year + "");
            listData.put(ConstantData.VALID, ConstantData.MIN_YEAR + "-" + DateOperation.getYearCurrent());
            String errorMessages = OperationString.generateMesage(Message.NOT_VALID_DATA_NUMBERS_ALLOWED, listData);
            listError.add(errorMessages);
        }
    }

    public void verifyOscarNomination(int oscarNomination, ArrayList<String> listError) {
        int minOscarNomination = ConstantData.MIN_OSCAR_NOMINATION;
        int maxOscarNomination = ConstantData.MAX_OSCAR_NOMINATION_MOVIE;
        if (!MovieValidationUtil.theNumberIsInRange(oscarNomination, minOscarNomination, maxOscarNomination)) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.OSCAR_NOMINATION_MOVIE);
            listData.put(ConstantData.DATA, oscarNomination + "");
            listData.put(ConstantData.VALID, minOscarNomination + "-" + maxOscarNomination);
            String errorMessages = OperationString.generateMesage(Message.NOT_VALID_DATA_NUMBERS_ALLOWED, listData);
            listError.add(errorMessages);
        }
    }

    public void verifyGenre(String genreId, ArrayList<ModelObject> listGenre, ArrayList<String> listError) {
        boolean findId = false;
        int i = 0;
        while (i < listGenre.size() && !findId) {
            GenreMovie genreMovie = (GenreMovie) listGenre.get(i);
            String genreIdAux = genreId.toUpperCase();
            if (genreIdAux.equals(genreMovie.getIdGenre())) {
                findId = true;
            }
            i++;
        }
        if (!findId) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.NAME_GENRE_MOVIE);
            listData.put(ConstantData.DATA, genreId);
            listData.put(ConstantData.VALID, ConstantData.VALID_NAME_GENRE_MOVIE);
            String errorMessages = OperationString.generateMesage(Message.NOT_VALID_THE_VALID_DATA_ARE, listData);
            listError.add(errorMessages);
        }
    }
}
