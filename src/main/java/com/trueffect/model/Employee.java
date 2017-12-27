package com.trueffect.model;

import java.util.ArrayList;

/**
 * @author santiago.mamani
 */
public class Employee extends Person {

    protected String dateOfHire;
    protected String address;
    protected String job;
    protected ArrayList<Integer> phones;

    public Employee() {
        phones = new ArrayList<Integer>();
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
            String job
    ) {
        super(id, typeIdentifier, identifier, lastName, firstName, genre, birthday);
        this.dateOfHire = dateOfHire.trim();
        this.address = address.trim();
        this.job = job.trim();
        this.phones = new ArrayList<Integer>();
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
            ArrayList<Integer> phones
    ) {
        super(id, typeIdentifier, identifier, lastName, firstName, genre, birthday);
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

    public ArrayList<Integer> getPhones() {
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

    public void setPhones(ArrayList<Integer> phones) {
        this.phones = phones;
    }
}
