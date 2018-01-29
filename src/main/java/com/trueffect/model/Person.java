package com.trueffect.model;

import com.trueffect.util.ModelObject;
import org.codehaus.jackson.annotate.JsonIgnore;

/*
 * @author santiago.mamani
 */
public class Person extends ModelObject {

    protected long id;
    protected String typeIdentifier;
    protected String identifier;
    protected String lastName;
    protected String firstName;
    protected String genre;
    protected String birthday;
    protected String status;

    public Person() {
    }

    public Person(long id, String typeIdentifier, String identifier, String lastName, String firstName, String genre, String birthday, String status) {
        this.id = id;
        this.typeIdentifier = typeIdentifier.trim();
        this.identifier = identifier.trim();
        this.lastName = lastName.trim();
        this.firstName = firstName.trim();
        this.genre = genre.trim();
        this.birthday = birthday.trim();
        this.status = status;
    }

    public Person(long id, String typeIdentifier, String identifier, String genre, String birthday) {
        this.id = id;
        this.typeIdentifier = typeIdentifier.trim();
        this.identifier = identifier.trim();
        this.genre = genre.trim();
        this.birthday = birthday.trim();
    }

    public long getId() {
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

    public String getStatus() {
        return status;
    }

    public void setId(long id) {
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

    public void setStatus(String status) {
        this.status = status;
    }

    public int compareTo(Person o) {
        return (typeIdentifier.compareTo(o.typeIdentifier) == 0
                && identifier.compareTo(o.identifier) == 0
                && lastName.compareTo(o.lastName) == 0
                && firstName.compareTo(o.firstName) == 0
                && genre.compareTo(o.genre) == 0)
                ? 0 : -1;
    }

    @JsonIgnore
    public boolean isEmpty() {
        return id == 0
                && typeIdentifier == null
                && lastName == null
                && firstName == null
                && genre == null
                && birthday == null;
    }
}
