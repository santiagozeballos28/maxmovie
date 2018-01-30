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
    protected long idDataJob;
    protected long idRenterUser;

    public MasterDetail() {
    }

    public MasterDetail(long id, int amountTotal, double priceTotal, long idDataJob, long idRenterUser) {
        this.idMasterDetail = id;
        this.amountTotal = amountTotal;
        this.priceTotal = priceTotal;
        this.idDataJob = idDataJob;
        this.idRenterUser = idRenterUser;
    }

    public MasterDetail(int amountTotal, double priceTotal, long idDataJob, long idRenterUser) {
        this.amountTotal = amountTotal;
        this.priceTotal = priceTotal;
        this.idDataJob = idDataJob;
        this.idRenterUser = idRenterUser;
    }

    public MasterDetail(long id, int amountTotal, double priceTotal) {
        this.idMasterDetail = id;
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

    public long getIdDataJob() {
        return idDataJob;
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

    public void setIdDataJob(long idDataJob) {
        this.idDataJob = idDataJob;
    }

    public void setIdRenterUser(long idRenterUser) {
        this.idRenterUser = idRenterUser;
    }
}
