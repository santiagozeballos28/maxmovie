package com.trueffect.model;

import java.util.ArrayList;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author santiago.mamani
 */
public class Employee extends Person {

    protected String dateOfHire;
    protected String address;
    protected String job;
    protected ArrayList<Long> phones;

    public Employee() {
        phones = new ArrayList<Long>();
    }

    public Employee(
            long id,
            String typeIdentifier,
            String identifier,
            String lastName,
            String firstName,
            String genre,
            String birthday,
            String dateOfHire,
            String address,
            String job,
            String status
    ) {
        super(id, typeIdentifier, identifier, lastName, firstName, genre, birthday, status);
        this.dateOfHire = dateOfHire.trim();
        this.address = address.trim();
        this.job = job.trim();
        this.phones = new ArrayList<Long>();
    }

    public Employee(
            int id,
            String typeIdentifier,
            String identifier,
            String lastName,
            String firstName,
            String genre,
            String birthday,
            String dateOfHire,
            String address,
            String job,
            ArrayList<Long> phones,
            String status
    ) {
        super(id, typeIdentifier, identifier, lastName, firstName, genre, birthday, status);
        this.dateOfHire = dateOfHire.trim();
        this.address = address.trim();
        this.job = job.trim();
        this.phones = phones;
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

    public ArrayList<Long> getPhones() {
        return phones;
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

    public void setPhones(ArrayList<Long> phones) {
        this.phones = phones;
    }

    @JsonIgnore
    public boolean haveDataJob() {
        return dateOfHire != null || address != null || job != null;
    }
}
