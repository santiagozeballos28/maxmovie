package com.trueffect.model;

import com.trueffect.util.ModelObject;

/**
 *
 * @author santiago.mamani
 */
public class MasterDetail extends ModelObject{

    private long id;
    private int amountTotal;
    private double priceTotal;
    protected long idCreateUser;
    protected long idRenterUser;

    public MasterDetail() {
    }
    

    public MasterDetail(long id, int amountTotal, double priceTotal, long idCreateUser, long idRenterUser) {
        this.id = id;
        this.amountTotal = amountTotal;
        this.priceTotal = priceTotal;
        this.idCreateUser = idCreateUser;
        this.idRenterUser = idRenterUser;
    }

    public MasterDetail(int amountTotal, double priceTotal, long idCreateUser, long idRenterUser) {
        this.amountTotal = amountTotal;
        this.priceTotal = priceTotal;
        this.idCreateUser = idCreateUser;
        this.idRenterUser = idRenterUser;
    }

    public MasterDetail(long id, int amountTotal, double priceTotal) {
        this.id = id;
        this.amountTotal = amountTotal;
        this.priceTotal = priceTotal;
    }

    public long getId() {
        return id;
    }

    public int getAmountTotal() {
        return amountTotal;
    }

    public double getPriceTotal() {
        return priceTotal;
    }

    public long getIdCreateUser() {
        return idCreateUser;
    }

    public long getIdRenterUser() {
        return idRenterUser;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAmountTotal(int amountTotal) {
        this.amountTotal = amountTotal;
    }

    public void setPriceTotal(double priceTotal) {
        this.priceTotal = priceTotal;
    }

    public void setIdCreateUser(long idCreateUser) {
        this.idCreateUser = idCreateUser;
    }

    public void setIdRenterUser(long idRenterUser) {
        this.idRenterUser = idRenterUser;
    }

}
