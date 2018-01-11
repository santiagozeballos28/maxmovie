
package com.trueffect.model;

import com.trueffect.util.ModelObject;

/**
 *
 * @author santiago.mamani
 */
public class DataJob extends ModelObject{

    private long employeeId;
    private long jobId;
    private String dateOfHire;
    private String address;
    

    public DataJob() {
    }

    public DataJob(long employeeId, long jobId, String dateOfHire, String address) {
        this.employeeId = employeeId;
        this.jobId = jobId;
        this.dateOfHire = dateOfHire;
        this.address = address;
    }



    public long getEmployeeId() {
        return employeeId;
    }

    public long getJobId() {
        return jobId;
    }

    public String getDateOfHire() {
        return dateOfHire;
    }

    public String getAddress() {
        return address;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }
    

    public void setDateOfHire(String dateOfHire) {
        this.dateOfHire = dateOfHire;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
