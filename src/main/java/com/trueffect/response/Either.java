package com.trueffect.response;

import com.trueffect.util.ModelObject;
import java.util.ArrayList;

/**
 *
 * @author santiago.mamani
 */
public class Either extends Exception {

    private int code;
    private ArrayList<String> listError;
    private ArrayList<ModelObject> listObject;

    public Either() {
        code = 0;
        listError = new ArrayList<String>();
        listObject = new ArrayList<ModelObject>();
    }

    public Either(int code, ArrayList<String> listError) {
        this.code = code;
        this.listError = listError;
        listObject = new ArrayList<ModelObject>();
    }

    public Either(int code, ModelObject modelObject) {
        this.code = code;
        this.listError = new ArrayList<String>();
        listObject = new ArrayList<ModelObject>();
        listObject.add(modelObject);
    }

    public int getCode() {
        return code;
    }

    public ArrayList<String> getListError() {
        return listError;
    }

    public ArrayList<ModelObject> getListObject() {
        return listObject;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setListError(ArrayList<String> listError) {
        this.listError = listError;
    }

    public void setListObject(ArrayList<ModelObject> listObject) {
        this.listObject = listObject;
    }

    public void addError(String errorMessage) {
        listError.add(errorMessage);
    }

    public void addModeloObjet(ModelObject modelObject) {
        listObject.add(modelObject);
    }

    public boolean existError() {
        return !listError.isEmpty();
    }

    public boolean haveModelObject() {
        if (!listObject.isEmpty()) {
            return !listObject.get(0).isEmpty();
        }
        return false;
    }

    public ModelObject getFirstObject() {
        if (!listObject.isEmpty()) {
            return listObject.get(0);
        } else {
            return new ModelObject();
        }
    }
}
