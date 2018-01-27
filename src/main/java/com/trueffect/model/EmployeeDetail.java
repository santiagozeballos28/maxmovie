package com.trueffect.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

/**
 *
 * @author santiago.mamani
 */
@JsonPropertyOrder({
    "id",
    "typeIdentifier",
    "typeIdDescription",
    "identifier",
    "namePerson",
    "genre",
    "genreDescription",
    "birthday",
    "dateOfHire",
    "address",
    "job",
    "dateCreate",
    "nameCreateUser"})
public class EmployeeDetail extends PersonDetail {

    private String dateOfHire;
    private String address;
    private String job;

    public EmployeeDetail() {
    }

    public EmployeeDetail(
            long id,
            String typeIdentifier,
            String typeIdDescription,
            String identifier,
            String nameRenterUser,
            String genre,
            String genreDescription,
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
                genreDescription,
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

    @JsonIgnore
    public String getStatus() {
        return status;
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
