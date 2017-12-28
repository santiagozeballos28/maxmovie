package com.trueffect.logica;

import com.trueffect.messages.Message;
import com.trueffect.model.PersonDetail;
import com.trueffect.response.Either;
import com.trueffect.tools.ConstantData;
import com.trueffect.util.ModelObject;
import java.util.ArrayList;

/**
 *
 * @author santiago.mamani
 */
public class OperationModel {

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
                    person.setGenre(Message.M_DESCRIPTION);

                    break;
                case F:
                    person.setGenre(Message.F_DESCRIPTION);
                    break;
            }
        } catch (Exception e) {
        }
        return person;
    }
}
