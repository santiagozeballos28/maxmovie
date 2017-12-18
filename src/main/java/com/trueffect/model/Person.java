package com.trueffect.model;

import com.trueffect.util.ModelObject;

/*
 * @author santiago.mamani
 */
public class Person extends ModelObject {

    private int id;
    protected String typeIdentifier;
    protected String identifier;
    protected String lastName;
    protected String firstName;
    protected String genre;
    protected String birthday;

    public Person() {
    }

    public Person(int id, String typeIdentifier, String identifier, String lastName, String firstName, String genre, String birthday) {
        this.id = id;
        this.typeIdentifier = typeIdentifier.trim();
        this.identifier = identifier.trim();
        this.lastName = lastName.trim();
        this.firstName = firstName.trim();
        this.genre = genre.trim();
        this.birthday = birthday.trim();
    }

    public int getId() {
        return id;
    }

    public String getTypeIdentifier() {
        return typeIdentifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getGenre() {
        return genre;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTypeIdentifier(String typeIdentifier) {
        this.typeIdentifier = typeIdentifier.trim();
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier.trim();
    }

    public void setLastName(String lastName) {
        this.lastName = lastName.trim();
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName.trim();
    }

    public void setGenre(String genre) {
        this.genre = genre.trim();
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday.trim();
    }

    public int compareTo(Person o) {
        return (typeIdentifier.compareTo(o.typeIdentifier) == 0
                && identifier.compareTo(o.identifier) == 0
                && lastName.compareTo(o.lastName) == 0
                && firstName.compareTo(o.firstName) == 0
                && genre.compareTo(o.genre) == 0)
                ? 0 : -1;
    }

    public boolean isEmpty() {
        return id == 0
                && typeIdentifier == null
                && lastName == null
                && firstName == null
                && genre == null
                && birthday == null;
    }

    public void formatOfTheName() {
        String[] lastNameAux = lastName.split(" ");
        String resLastName = "";
        //The first letter of the last name is divided to capitalize.
        for (String lastName : lastNameAux) {
            resLastName = resLastName + " "
                    + lastName.substring(0, 1).toUpperCase()
                    + lastName.substring(1, lastName.length()).toLowerCase();
        }
        lastName = resLastName.trim();
        //The first letter becomes to upper case
        firstName = firstName.substring(0, 1).toUpperCase()
                + firstName.substring(1, firstName.length()).toLowerCase();

    }
}
