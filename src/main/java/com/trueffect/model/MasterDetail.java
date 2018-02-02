package com.trueffect.model;

import com.trueffect.util.ModelObject;

/**
 *
 * @author santiago.mamani
 */
public class MasterDetail extends ModelObject {

    private long idMasterDetail;
    private int amountTotal;
    private double priceTotal;
    protected long idEmployeeData;
    protected long idRenterUser;

    public MasterDetail() {
    }

    public MasterDetail(long idMasterDetail, int amountTotal, double priceTotal, long idEmployeeData, long idRenterUser) {
        this.idMasterDetail = idMasterDetail;
        this.amountTotal = amountTotal;
        this.priceTotal = priceTotal;
        this.idEmployeeData = idEmployeeData;
        this.idRenterUser = idRenterUser;
    }

    public MasterDetail(int amountTotal, double priceTotal, long idEmployeeData, long idRenterUser) {
        this.amountTotal = amountTotal;
        this.priceTotal = priceTotal;
        this.idEmployeeData = idEmployeeData;
        this.idRenterUser = idRenterUser;
    }

    public MasterDetail(long idMasterDetail, int amountTotal, double priceTotal) {
        this.idMasterDetail = idMasterDetail;
        this.amountTotal = amountTotal;
        this.priceTotal = priceTotal;
    }

    public long getIdMasterDetail() {
        return idMasterDetail;
    }

    public int getAmountTotal() {
        return amountTotal;
    }

    public double getPriceTotal() {
        return priceTotal;
    }

    public long getIdEmployeeData() {
        return idEmployeeData;
    }

    public long getIdRenterUser() {
        return idRenterUser;
    }

    public void setIdMasterDetail(long idMasterDetail) {
        this.idMasterDetail = idMasterDetail;
    }

    public void setAmountTotal(int amountTotal) {
        this.amountTotal = amountTotal;
    }

    public void setPriceTotal(double priceTotal) {
        this.priceTotal = priceTotal;
    }

    public void setIdEmployeeData(long idEmployeeData) {
        this.idEmployeeData = idEmployeeData;
    }

    public void setIdRenterUser(long idRenterUser) {
        this.idRenterUser = idRenterUser;
    }
}
