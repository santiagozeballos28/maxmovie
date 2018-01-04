package com.trueffect.logica;

import com.trueffect.messages.Message;
import com.trueffect.model.PersonDetail;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.util.ModelObject;
import com.trueffect.util.OperationString;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author santiago.mamani
 */
public class OperationModel {
    private HashMap<String, String> listData;

    public OperationModel() {
        listData = new HashMap<String, String>();
    }
    

    public Either addDescription(Either either) {
        Either eitherRes = new Either();
        eitherRes.setCode(either.getCode());
        ArrayList<ModelObject> listObject = either.getListObject();
        for (int i = 0; i < listObject.size(); i++) {

            PersonDetail person = addDescriptionTypeIdentifier((PersonDetail) listObject.get(i));
            person = addDescriptionGenre(person);
            eitherRes.addModeloObjet(person);
        }
        return eitherRes;
    }

    private PersonDetail addDescriptionTypeIdentifier(PersonDetail person) {
        String typeIdPerson = person.getTypeIdentifier();
        try {
            ConstantData.TypeIdentifier typeIden = ConstantData.TypeIdentifier.valueOf(typeIdPerson);
            switch (typeIden) {
                case CI:
                    person.setTypeIdDescription(Message.CI_DESCRIPTION);

                    break;
                case PASS:
                    person.setTypeIdDescription(Message.PASS_DESCRIPTION);
                    break;
                case NIT:
                    person.setTypeIdDescription(Message.NIT_DESCRIPTION);
                    break;
            }
        } catch (Exception e) {
        }
        return person;
    }

    private PersonDetail addDescriptionGenre(PersonDetail person) {
        String genrePerson = person.getGenre();
        try {
            ConstantData.Genre typeIden = ConstantData.Genre.valueOf(genrePerson);
            switch (typeIden) {
                case M:
                    person.setGenreDescription(Message.M_DESCRIPTION);

                    break;
                case F:
                    person.setGenreDescription(Message.F_DESCRIPTION);
                    break;
            }
        } catch (Exception e) {
        }
        return person;
    }
        public Either verifyStatus(Connection connection, String status) {
        ArrayList<String> listError = new ArrayList<String>();
        try {
            ConstantData.StatusPerson statusPerson = ConstantData.StatusPerson.valueOf(status);
            return new Either();
        } catch (Exception e) {
            listData.clear();
            listData.put("{typeData}", "Status");
            listData.put("{data}", status);
            listData.put("{valid}", Message.VALID_STATUS);
            String errorMgs = OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
            listError.add(errorMgs);
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
    }
}
