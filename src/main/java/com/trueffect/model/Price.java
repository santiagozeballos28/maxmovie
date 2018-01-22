
package com.trueffect.model;

import com.trueffect.util.ModelObject;

/**
 *
 * @author santiago.mamani
 */
public class Price extends ModelObject{
    private String Id;
    private String name;
    private double priceNormal;
    private double pricePremier;

    public Price() {
    }

    public Price(String Id, String name, double priceNormal, double pricePremier) {
        this.Id = Id;
        this.name = name.trim();
        this.priceNormal = priceNormal;
        this.pricePremier = pricePremier;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public double getPriceNormal() {
        return priceNormal;
    }

    public double getPricePremier() {
        return pricePremier;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPriceNormal(double priceNormal) {
        this.priceNormal = priceNormal;
    }

    public void setPricePremier(double pricePremier) {
        this.pricePremier = pricePremier;
    }
    
}
