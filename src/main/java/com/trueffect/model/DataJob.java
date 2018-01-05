
package com.trueffect.model;

import com.trueffect.util.ModelObject;

/**
 *
 * @author santiago.mamani
 */
public class DataJob extends ModelObject{

    private int idEmployee;
    private int idJob;
    private String dateOfHire;
    private String address;
    

    public DataJob() {
    }

    public DataJob(int idEmployee, int idJob, String dateOfHire, String address) {
        this.idEmployee = idEmployee;
        this.idJob = idJob;
        this.dateOfHire = dateOfHire;
        this.address = address;
    }



    public int getIdEmployee() {
        return idEmployee;
    }

    public int getIdJob() {
        return idJob;
    }

    public String getDateOfHire() {
        return dateOfHire;
    }

    public String getAddress() {
        return address;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public void setIdJob(int idJob) {
        this.idJob = idJob;
    }
    

    public void setDateOfHire(String dateOfHire) {
        this.dateOfHire = dateOfHire;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
