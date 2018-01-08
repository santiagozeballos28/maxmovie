package com.trueffect.model;

import com.trueffect.tools.ConstantData;
import com.trueffect.tools.ConstantData.Status;
import com.trueffect.util.ModelObject;

/**
 * @author santiago.mamani
 */
public class Phone extends ModelObject {

    private int idPerson;
    private int numberPhone;
    private String status;

    public Phone(int numberPhone) {
        this.numberPhone = numberPhone;
    }

    public Phone(int idPerson, int numberPhone, String status) {
        this.idPerson = idPerson;
        this.numberPhone = numberPhone;
        this.status = status.trim();
    }

    public int getIdPerson() {
        return idPerson;
    }

    public int getNumberPhone() {
        return numberPhone;
    }

    public String getStatus() {
        return status;
    }

    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

    public void setNumberPhone(int numberPhone) {
        this.numberPhone = numberPhone;
    }

    public void setStatus(String status) {
        this.status = status.trim();
    }

    public boolean isActive() {
        //Active
        return status.equals(Status.Active.name());
    }
}
