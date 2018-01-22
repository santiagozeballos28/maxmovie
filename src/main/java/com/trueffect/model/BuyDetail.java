package com.trueffect.model;

import com.trueffect.util.ModelObject;

/**
 *
 * @author santiago.mamani
 */
public class BuyDetail extends ModelObject{

    private long copyMovieId;
    private int buyAmount;
    private double buyPrice;
    private long masterDetailId;

    public BuyDetail(long copyMovieId, int buyAmount) {
        this.copyMovieId = copyMovieId;
        this.buyAmount = buyAmount;
       
    }


    public int getBuyAmount() {
        return buyAmount;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public long getMasterDetailId() {
        return masterDetailId;
    }

    public long getCopyMovieId() {
        return copyMovieId;
    }

    public void setBuyAmount(int buyAmount) {
        this.buyAmount = buyAmount;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public void setMasterDetailId(long masterDetailId) {
        this.masterDetailId = masterDetailId;
    }

    public void setCopyMovieId(long copyMovieId) {
        this.copyMovieId = copyMovieId;
    }

}
