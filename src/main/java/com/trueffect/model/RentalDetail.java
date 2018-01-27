package com.trueffect.model;

/**
 *
 * @author santiago.mamani
 */
public class RentalDetail extends SaleDetail {

    private int returnAmount;
    private String returnDate;
    private double amountOficial;
    private long employeeReceive;
    private double penalty;
    private double priceOficial;
    private String note;

    public RentalDetail() {
    }

    public RentalDetail(long copyMovieId, int rentalAmount, double rentalPrice, String idPrice) {
        super(copyMovieId, rentalAmount, rentalPrice, idPrice);
    }

    public int getReturnAmount() {
        return returnAmount;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public double getAmountOficial() {
        return amountOficial;
    }

    public long getEmployeeReceive() {
        return employeeReceive;
    }

    public double getPenalty() {
        return penalty;
    }

    public double getPriceOficial() {
        return priceOficial;
    }

    public String getNote() {
        return note;
    }

    public void setReturnAmount(int returnAmount) {
        this.returnAmount = returnAmount;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate.trim();
    }

    public void setAmountOficial(double amountOficial) {
        this.amountOficial = amountOficial;
    }

    public void setEmployeeReceive(long employeeReceive) {
        this.employeeReceive = employeeReceive;
    }

    public void setPenalty(double penalty) {
        this.penalty = penalty;
    }

    public void setPriceOficial(double priceOficial) {
        this.priceOficial = priceOficial;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
