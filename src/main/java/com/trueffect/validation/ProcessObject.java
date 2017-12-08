package com.trueffect.validation;

import com.trueffect.util.ModelObject;
import com.trueffect.response.ErrorResponse;
import com.trueffect.util.DataCondition;
/**
 * @author santiago.mamani
 */
public class ProcessObject {

    //me llega un renter User
    public static ModelObject processInsert(int id,ModelObject object, DataCondition conditiondata) throws Exception{
        ModelObject res_resourse=null;
        try {
            if (conditiondata.complyCondition(id ,object)) {
                res_resourse = object.insertResourse(id);
            }
        } catch (ErrorResponse ex) {
           throw ex;
        }
        return res_resourse;
    }
       public static ModelObject processUpdate(int idUserCreate,ModelObject object, DataCondition conditiondata) throws Exception{
        ModelObject res_resourse=null;
        try {
            if (conditiondata.complyCondition(idUserCreate, res_resourse)) {
                //falta por el id del actualizador
                res_resourse = object.updateResourse(idUserCreate);
            }
        } catch (ErrorResponse ex) {
           throw ex;
        }
        return res_resourse;
    }
}
