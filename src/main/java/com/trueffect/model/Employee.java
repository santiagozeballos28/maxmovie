package com.trueffect.model;

import java.util.ArrayList;

/**
 * @author santiago.mamani
 */
public class Employee extends Person {
//   -	Date of hire%
//-	Address (Max 100 chars)
//-	Job title (Max 10 chars, catalog: cashier, custom_care, manager,administrator)
//-	Reference Phone(number) at least two.

    protected String dateOfHire;
    protected String address;
    protected String job;
    protected ArrayList<Integer> phones;

    public Employee() {
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
        this.dateOfHire = dateOfHire;
        this.address = address;
        this.job = job;
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
        this.dateOfHire = dateOfHire;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setPhones(ArrayList<Integer> phones) {
        this.phones = phones;
    }

}
