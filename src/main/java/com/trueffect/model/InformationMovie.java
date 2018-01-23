
package com.trueffect.model;

/**
 *
 * @author santiago.mamani
 */
public class InformationMovie {
    private String nameMovie;
    private int amountCurrent;
    private int amountRental;
    private int amountBuy;

    public InformationMovie(String nameMovie, int amountCurrent, int amountRental, int amountBuy) {
        this.nameMovie = nameMovie.trim();
        this.amountCurrent = amountCurrent;
        this.amountRental = amountRental;
        this.amountBuy = amountBuy;
    }

    public String getNameMovie() {
        return nameMovie;
    }

    public int getAmountCurrent() {
        return amountCurrent;
    }

    public int getAmountRental() {
        return amountRental;
    }

    public int getAmountBuy() {
        return amountBuy;
    }

    public void setNameMovie(String nameMovie) {
        this.nameMovie = nameMovie.trim();
    }

    public void setAmountCurrent(int amountCurrent) {
        this.amountCurrent = amountCurrent;
    }

    public void setAmountRental(int amountRental) {
        this.amountRental = amountRental;
    }

    public void setAmountBuy(int amountBuy) {
        this.amountBuy = amountBuy;
    }
    
}
