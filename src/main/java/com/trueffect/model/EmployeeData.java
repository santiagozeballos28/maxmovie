package com.trueffect.model;

import com.trueffect.util.ModelObject;

/**
 *
 * @author santiago.mamani
 */
public class EmployeeData extends ModelObject {

    private long idEmployeeData;
    private long idJob;
    private String dateOfHire;
    private String address;
    private Boolean enableRent;
    public EmployeeData() {
    }

    public EmployeeData(long idEmployeeData, long jobId, String dateOfHire, String address,Boolean enableRent) {
        this.idEmployeeData = idEmployeeData;
        this.idJob = jobId;
        this.dateOfHire = dateOfHire;
        this.address = address;
        this.enableRent = enableRent;
    }
   
    public EmployeeData(long idEmployeeData, String dateOfHire, String address) {
        this.idEmployeeData = idEmployeeData;
        this.dateOfHire = dateOfHire.trim();
        this.address = address.trim();
    }

    public long getIdEmployeeData() {
        return idEmployeeData;
    }

    public long getIdJob() {
        return idJob;
    }

    public String getDateOfHire() {
        return dateOfHire;
    }

    public String getAddress() {
        return address;
    }

    public Boolean isEnableRent() {
        return enableRent;
    }

    public void setIdEmployeeData(long idEmployeeData) {
        this.idEmployeeData = idEmployeeData;
    }

    public void setIdJob(long idJob) {
        this.idJob = idJob;
    }

    public void setDateOfHire(String dateOfHire) {
        this.dateOfHire = dateOfHire.trim();
    }

    public void setAddress(String address) {
        this.address = address.trim();
    }

    public void setEnableRent(Boolean enableRent) {
        this.enableRent = enableRent;
    }
    
}