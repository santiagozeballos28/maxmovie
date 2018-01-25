package com.trueffect.model;

/**
 *
 * @author santiago.mamani
 */
public class ReportMovieDetail extends ReportMovie {

    private int quantityRental;
    private int quantityBuy;

    public ReportMovieDetail() {
    }

    public ReportMovieDetail(long idMovie, String nameMovie, String statusMovie) {
        super(idMovie, nameMovie, statusMovie);
    }

    public ReportMovieDetail(long idMovie, String nameMovie, int quantityCurrent, int quantityRental, int quantityBuy) {
        super(idMovie, nameMovie, quantityCurrent);
        this.quantityRental = quantityRental;
        this.quantityBuy = quantityBuy;
    }

    public int getQuantityRental() {
        return quantityRental;
    }

    public int getQuantityBuy() {
        return quantityBuy;
    }

    public void setQuantityRental(int quantityRental) {
        this.quantityRental = quantityRental;
    }

    public void setQuantityBuy(int quantityBuy) {
        this.quantityBuy = quantityBuy;
    }
}
