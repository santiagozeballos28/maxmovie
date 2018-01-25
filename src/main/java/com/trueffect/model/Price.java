package com.trueffect.model;

import com.trueffect.util.ModelObject;

/**
 *
 * @author santiago.mamani
 */
public class Price extends ModelObject {

    private String Id;
    private String name;
    private double price;

    public Price() {
    }

    public Price(String Id, String name, double price) {
        this.Id = Id;
        this.name = name.trim();
        this.price = price;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
