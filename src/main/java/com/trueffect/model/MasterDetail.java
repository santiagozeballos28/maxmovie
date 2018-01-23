package com.trueffect.model;

/**
 *
 * @author santiago.mamani
 */
public class MasterDetail {

    /*
     master_detail_id serial NOT NULL,
     amount_total integer,
     price_total double precision,
     create_user integer,
     create_date timestamp without time zone,
     modifier_date timestamp without time zone,
     modifier_user integer,
     renter_user integer,
     */
    private long id;
    private int amountTotal;
    private double priceTotal;
    private long idCreateUser;
    private long idRenterUser;

    public MasterDetail(int amountTotal, double priceTotal, long idCreateUser, long idRenterUser) {
        this.amountTotal = amountTotal;
        this.priceTotal = priceTotal;
        this.idCreateUser = idCreateUser;
        this.idRenterUser = idRenterUser;
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
