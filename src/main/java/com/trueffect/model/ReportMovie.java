package com.trueffect.model;

import com.trueffect.util.ModelObject;

/**
 *
 * @author santiago.mamani
 */
public class ReportMovie extends ModelObject {

    private long idMovie;
    private String nameMovie;
    private int quantityCurrent;
    private int quantityRental;
    private int quantityBuy;
    private String statusMovie;

    public ReportMovie() {
    }

    public ReportMovie(long idMovie, String nameMovie, String statusMovie) {
        this.idMovie = idMovie;
        this.nameMovie = nameMovie;
        this.statusMovie = statusMovie;
    }

    public ReportMovie(long idMovie, String nameMovie, int quantityCurrent, int quantityRental, int quantityBuy) {
        this.idMovie = idMovie;
        this.nameMovie = nameMovie;
        this.quantityCurrent = quantityCurrent;
        this.quantityRental = quantityRental;
        this.quantityBuy = quantityBuy;
    }

    public long getIdMovie() {
        return idMovie;
    }

    public String getNameMovie() {
        return nameMovie;
    }

    public int getQuantityCurrent() {
        return quantityCurrent;
    }

    public int getQuantityRental() {
        return quantityRental;
    }

    public int getQuantityBuy() {
        return quantityBuy;
    }

    public String getStatusMovie() {
        return statusMovie;
    }

    public void setIdMovie(long idMovie) {
        this.idMovie = idMovie;
    }

    public void setNameMovie(String nameMovie) {
        this.nameMovie = nameMovie;
    }

    public void setQuantityCurrent(int quantityCurrent) {
        this.quantityCurrent = quantityCurrent;
    }

    public void setQuantityRental(int quantityRental) {
        this.quantityRental = quantityRental;
    }

    public void setQuantityBuy(int quantityBuy) {
        this.quantityBuy = quantityBuy;
    }

    public void setStatusMovie(String statusMovie) {
        this.statusMovie = statusMovie;
    }
}
