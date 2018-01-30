package com.trueffect.model;

import com.trueffect.util.ModelObject;

/**
 *
 * @author santiago.mamani
 */
public class ReportSale extends ModelObject {

    private long idMovie;
    private String nameMovie;
    private double price;
    private int quantitySubTotal;
    private double priceSubTotal;
    private String nameSale;

    public ReportSale() {
    }

    public ReportSale(long idMovie, int amount, double priceSubTotal) {
        this.idMovie = idMovie;
        this.quantitySubTotal = amount;
        this.priceSubTotal = priceSubTotal;
    }

    public ReportSale(long idMovie, String nameMovie, double price, String nameSale) {
        this.idMovie = idMovie;
        this.nameMovie = nameMovie;
        this.price = price;
        this.nameSale = nameSale;
    }

    public ReportSale(long idMovie, String nameMovie, double price, int quantitySubTotal, double priceSubTotal, String nameSale) {
        this.idMovie = idMovie;
        this.nameMovie = nameMovie;
        this.price = price;
        this.quantitySubTotal = quantitySubTotal;
        this.priceSubTotal = priceSubTotal;
        this.nameSale = nameSale;
    }

    public long getIdMovie() {
        return idMovie;
    }

    public String getNameMovie() {
        return nameMovie;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantitySubTotal() {
        return quantitySubTotal;
    }

    public double getPriceSubTotal() {
        return priceSubTotal;
    }

    public String getNameSale() {
        return nameSale;
    }

    public void setIdMovie(long idMovie) {
        this.idMovie = idMovie;
    }

    public void setNameMovie(String nameMovie) {
        this.nameMovie = nameMovie;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantitySubTotal(int quantitySubTotal) {
        this.quantitySubTotal = quantitySubTotal;
    }

    public void setPriceSubTotal(double priceSubTotal) {
        this.priceSubTotal = priceSubTotal;
    }

    public void setNameSale(String nameSale) {
        this.nameSale = nameSale;
    }
}
