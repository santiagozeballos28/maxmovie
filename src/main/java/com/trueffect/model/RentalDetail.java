package com.trueffect.model;

import com.trueffect.util.ModelObject;

/**
 *
 * @author santiago.mamani
 */
public class RentalDetail extends ModelObject {

    private long copyMovieId;//
    private int rentalAmount;//
    private int returnAmount;
    private String returnDate;
    private double rentalPrice;//
    private double rentalAmountOficial;
    private long employeeReceive;
    private long masterDetailId;//
    private String note;

    public RentalDetail() {
    }

    public RentalDetail(long copyMovieId, int rentalAmount) {
        this.copyMovieId = copyMovieId;
        this.rentalAmount = rentalAmount;
        
    }


    public long getCopyMovieId() {
        return copyMovieId;
    }

    public int getRentalAmount() {
        return rentalAmount;
    }

    public int getReturnAmount() {
        return returnAmount;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public double getRentalPrice() {
        return rentalPrice;
    }

    public double getRentalAmountOficial() {
        return rentalAmountOficial;
    }

    public long getEmployeeReceive() {
        return employeeReceive;
    }

    public long getMasterDetailId() {
        return masterDetailId;
    }

    public String getNote() {
        return note;
    }

    public void setCopyMovieId(long copyMovieId) {
        this.copyMovieId = copyMovieId;
    }

    public void setRentalAmount(int rentalAmount) {
        this.rentalAmount = rentalAmount;
    }

    public void setReturnAmount(int returnAmount) {
        this.returnAmount = returnAmount;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public void setRentalPrice(double rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public void setRentalAmountOficial(double rentalAmountOficial) {
        this.rentalAmountOficial = rentalAmountOficial;
    }

    public void setEmployeeReceive(long employeeReceive) {
        this.employeeReceive = employeeReceive;
    }

    public void setMasterDetailId(long masterDetailId) {
        this.masterDetailId = masterDetailId;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
