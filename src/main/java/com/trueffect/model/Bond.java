package com.trueffect.model;

import com.trueffect.util.ModelObject;

/**
 *
 * @author santiago.mamani
 */
public class Bond extends ModelObject {

    private int id;
    private double quantity;
    private int seniority;
    private String startDate;
    private String endDate;

    public Bond() {
    }

    public Bond(int id, double quantity, int seniority, String startDate, String endDate) {
        this.id = id;
        this.quantity = quantity;
        this.seniority = seniority;
        this.startDate = startDate.trim();
        this.endDate = endDate.trim();
    }

    public int getId() {
        return id;
    }

    public double getQuantity() {
        return quantity;
    }

    public int getSeniority() {
        return seniority;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setSeniority(int seniority) {
        this.seniority = seniority;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    
}
