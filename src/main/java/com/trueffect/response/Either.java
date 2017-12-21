package com.trueffect.response;

import com.trueffect.util.ModelObject;
import java.util.ArrayList;

/**
 *
 * @author santiago.mamani
 */
public class Either extends Exception {

    private int code;
    private ModelObject modelObject;
    private ArrayList<String> listError;
    public Either(){
    code = 0;
    modelObject =  new ModelObject();
    listError= new ArrayList<String>();
    }

    public Either(int code, ModelObject modelObject) {
        this.code = code;
        this.modelObject = modelObject;
        listError = new ArrayList<String>();
        }

    public Either(int code, ArrayList<String> listError) {
        this.code = code;
        modelObject =  new ModelObject();
        this.listError = listError;
    
    }

    public int getCode() {
        return code;
    }

    public ModelObject getModelObject() {
        return modelObject;
    }

    public ArrayList<String> getListError() {
        return listError;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setModelObject(ModelObject modelObject) {
        this.modelObject = modelObject;
    }

    public void setListError(ArrayList<String> listError) {
        this.listError = listError;
    }
 
    public void addError(String errorMessage) {
        listError.add(errorMessage);
    }

    public boolean existError() {
        return !listError.isEmpty();
    }

    public boolean haveModelObject() {
        return !modelObject.isEmpty();
    }
}
