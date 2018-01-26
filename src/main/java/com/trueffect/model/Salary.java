package com.trueffect.model;

import com.trueffect.util.ModelObject;

/**
 *
 * @author santiago.mamani
 */
public class Salary extends ModelObject {

    private int idEmployee;
    private double netSalary;
    private double bond;
    private double liquidSalary;
    private String endDate;
    private String status;

    public Salary() {
    }

    public Salary(int idEmployee, double netSalary, double bond, double liquidSalary, String endDate, String status) {
        this.idEmployee = idEmployee;
        this.netSalary = netSalary;
        this.bond = bond;
        this.liquidSalary = liquidSalary;
        this.endDate = endDate;
        this.status = status.trim();
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public double getNetSalary() {
        return netSalary;
    }

    public double getBond() {
        return bond;
    }

    public double getLiquidSalary() {
        return liquidSalary;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }

    public void setBond(double bond) {
        this.bond = bond;
    }

    public void setLiquidSalary(double liquidSalary) {
        this.liquidSalary = liquidSalary;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        String res
                = "IdEmployee: " + idEmployee + "\n"
                + "NetSalary: " + netSalary + "\n"
                + "Bond: " + bond + "\n"
                + "LiquidSalary: " + liquidSalary + "\n"
                + "EndDate: " + endDate + "\n"
                + "Status: " + status + "\n";
        return res;
    }
}
