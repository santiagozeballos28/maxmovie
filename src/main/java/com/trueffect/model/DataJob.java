
package com.trueffect.model;

import com.trueffect.util.ModelObject;

/**
 *
 * @author santiago.mamani
 */
public class DataJob extends ModelObject{

    private int employeeId;
    private int jobId;
    private String dateOfHire;
    private String address;
    

    public DataJob() {
    }

    public DataJob(int employeeId, int jobId, String dateOfHire, String address) {
        this.employeeId = employeeId;
        this.jobId = jobId;
        this.dateOfHire = dateOfHire;
        this.address = address;
    }



    public int getEmployeeId() {
        return employeeId;
    }

    public int getJobId() {
        return jobId;
    }

    public String getDateOfHire() {
        return dateOfHire;
    }

    public String getAddress() {
        return address;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }
    

    public void setDateOfHire(String dateOfHire) {
        this.dateOfHire = dateOfHire;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
