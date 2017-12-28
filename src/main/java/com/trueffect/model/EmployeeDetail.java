package com.trueffect.model;

/**
 *
 * @author santiago.mamani
 */
public class EmployeeDetail extends PersonDetail {

    private String dateOfHire;
    private String address;
    private String job;

    public EmployeeDetail() {
    }

    public EmployeeDetail(
            int id,
            String typeIdentifier,
            String typeIdDescription,
            String identifier,
            String nameRenterUser,
            String genre,
            String birthday,
            String dateCreate,
            String dateOfHire,
            String address,
            String job,
            String nameCreateUser) {
        super(
                id,
                typeIdentifier,
                typeIdDescription,
                identifier,
                nameRenterUser,
                genre,
                birthday,
                dateCreate,
                nameCreateUser);
        this.dateOfHire = dateOfHire.trim();
        this.address = address.trim();
        this.job = job.trim();
    }

    public String getDateOfHire() {
        return dateOfHire;
    }

    public String getAddress() {
        return address;
    }

    public String getJob() {
        return job;
    }

    public void setDateOfHire(String dateOfHire) {
        this.dateOfHire = dateOfHire.trim();
    }

    public void setAddress(String address) {
        this.address = address.trim();
    }

    public void setJob(String job) {
        this.job = job.trim();
    }
}
