package com.trueffect.model;

import com.trueffect.util.ModelObject;

/**
 *
 * @author santiago.mamani
 */
public class DataJob extends ModelObject{

    private int idPerson;
    private String dateOfHire;
    private String address;

    public DataJob() {
    }

    public DataJob(int idPerson, String dateOfHire, String address) {
        this.idPerson = idPerson;
        this.dateOfHire = dateOfHire.trim();
        this.address = address.trim();
    }

    public int getIdPerson() {
        return idPerson;
    }

    public String getDateOfHire() {
        return dateOfHire;
    }

    public String getAddress() {
        return address;
    }

    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

    public void setDateOfHire(String dateOfHire) {
        this.dateOfHire = dateOfHire;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
