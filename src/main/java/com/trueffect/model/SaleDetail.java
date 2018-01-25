package com.trueffect.model;

import com.trueffect.util.ModelObject;

/**
 *
 * @author santiago.mamani
 */
public class SaleDetail extends ModelObject {

    private long copyMovieId;
    private int amount;
    private double price;
    private long masterDetailId;
    private String idPrice;

    public SaleDetail() {
    }

    public SaleDetail(long copyMovieId, int amount, double price, String idPrice) {
        this.copyMovieId = copyMovieId;
        this.amount = amount;
        this.price = price;
        this.idPrice = idPrice;
    }

    public SaleDetail(long copyMovieId, int amount, double price, long masterDetailId) {
        this.copyMovieId = copyMovieId;
        this.amount = amount;
        this.price = price;
        this.masterDetailId = masterDetailId;
    }

    public long getCopyMovieId() {
        return copyMovieId;
    }

    public int getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }

    public long getMasterDetailId() {
        return masterDetailId;
    }

    public String getIdPrice() {
        return idPrice;
    }

    public void setCopyMovieId(long copyMovieId) {
        this.copyMovieId = copyMovieId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setMasterDetailId(long masterDetailId) {
        this.masterDetailId = masterDetailId;
    }

    public void setIdPrice(String idPrice) {
        this.idPrice = idPrice;
    }
}
