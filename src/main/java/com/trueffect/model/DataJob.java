package com.trueffect.model;

import com.trueffect.util.ModelObject;

/**
 *
 * @author santiago.mamani
 */
public class DataJob extends ModelObject {

    private long employeeId;
    private long jobId;
    private String dateOfHire;
    private String address;
    private String status;

    public DataJob() {
    }

    public DataJob(long employeeId, long jobId, String dateOfHire, String address) {
        this.employeeId = employeeId;
        this.jobId = jobId;
        this.dateOfHire = dateOfHire.trim();
        this.address = address.trim();
    }

    public DataJob(long employeeId, long jobId, String dateOfHire, String address, String status) {
        this.employeeId = employeeId;
        this.jobId = jobId;
        this.dateOfHire = dateOfHire.trim();
        this.address = address.trim();
        this.status = status.trim();
    }

    public DataJob(long employeeId, String dateOfHire, String address) {
        this.employeeId = employeeId;
        this.dateOfHire = dateOfHire.trim();
        this.address = address.trim();
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

    public String getStatus() {
        return status;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public void setDateOfHire(String dateOfHire) {
        this.dateOfHire = dateOfHire.trim();
    }

    public void setAddress(String address) {
        this.address = address.trim();
    }

    public void setStatus(String status) {
        this.status = status.trim();
    }
}
