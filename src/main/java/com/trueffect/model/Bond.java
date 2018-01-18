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

    public Bond() {
    }

    public Bond(int id, double quantity, int seniority) {
        this.id = id;
        this.quantity = quantity;
        this.seniority = seniority;
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
}
