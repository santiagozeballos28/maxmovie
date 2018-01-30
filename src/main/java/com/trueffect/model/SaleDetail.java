package com.trueffect.model;

import com.trueffect.util.ModelObject;

/**
 *
 * @author santiago.mamani
 */
public class SaleDetail extends ModelObject {

    private long idCopyMovie;
    private int amount;
    private double price;
    private long idMasterDetail;
    private String idPrice;

    public SaleDetail() {
    }

    public SaleDetail(long idCopyMovie, int amount, double price, String idPrice) {
        this.idCopyMovie = idCopyMovie;
        this.amount = amount;
        this.price = price;
        this.idPrice = idPrice;
    }

    public SaleDetail(long idCopyMovie, int amount, double price, long idMasterDetail) {
        this.idCopyMovie = idCopyMovie;
        this.amount = amount;
        this.price = price;
        this.idMasterDetail = idMasterDetail;
    }

    public long getIdCopyMovie() {
        return idCopyMovie;
    }

    public int getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }

    public long getIdMasterDetail() {
        return idMasterDetail;
    }

    public String getIdPrice() {
        return idPrice;
    }

    public void setIdCopyMovie(long idCopyMovie) {
        this.idCopyMovie = idCopyMovie;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setIdMasterDetail(long idMasterDetail) {
        this.idMasterDetail = idMasterDetail;
    }

    public void setIdPrice(String idPrice) {
        this.idPrice = idPrice;
    }
}
