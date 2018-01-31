package com.trueffect.model;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author santiago.mamani
 */
public class MasterDetailSaile extends MasterDetail {

    private String nameRenterUser;

    public MasterDetailSaile() {
    }

    public MasterDetailSaile(long id, String nameRenterUser, int amountTotal, double priceTotal) {
        super(id, amountTotal, priceTotal);
        this.nameRenterUser = nameRenterUser.trim();
    }

    public String getNameRenterUser() {
        return nameRenterUser;
    }

    public void setNameRenterUser(String nameRenterUser) {
        this.nameRenterUser = nameRenterUser;
    }

    @JsonIgnore
    @Override
    public long getIdEmployeeData() {
        return idEmployeeData;
    }

    @JsonIgnore
    @Override
    public long getIdRenterUser() {
        return idRenterUser;
    }
}
