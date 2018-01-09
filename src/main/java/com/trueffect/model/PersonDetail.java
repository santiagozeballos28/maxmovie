package com.trueffect.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

/**
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
    "dateCreate",
    "nameCreateUser"})
public class PersonDetail extends Person {

    protected String typeIdDescription;
    protected String namePerson;
    protected String genreDescription;
    protected String dateCreate;
    protected String nameCreateUser;

    public PersonDetail() {
    }

    public PersonDetail(int id, String typeIdentifier, String typeIdDescription, String identifier, String namePerson, String genre, String genreDescription, String birthday, String dateCreate, String nameCreateUser) {
        super(id, typeIdentifier, identifier, genre, birthday);
        this.typeIdDescription = typeIdDescription.trim();
        this.namePerson = namePerson.trim();
        this.genreDescription = genreDescription.trim();
        this.dateCreate = dateCreate.trim();
        this.nameCreateUser = nameCreateUser.trim();
    }

    public String getTypeIdDescription() {
        return typeIdDescription;
    }

    public String getNamePerson() {
        return namePerson;
    }

    public String getGenreDescription() {
        return genreDescription;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public String getNameCreateUser() {
        return nameCreateUser;
    }

    public void setTypeIdDescription(String typeIdDescription) {
        this.typeIdDescription = typeIdDescription.trim();
    }

    public void setNamePerson(String namePerson) {
        this.namePerson = namePerson.trim();
    }

    public void setGenreDescription(String genreDescription) {
        this.genreDescription = genreDescription.trim();
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate.trim();
    }

    public void setNameCreateUser(String nameCreateUser) {
        this.nameCreateUser = nameCreateUser.trim();
    }

    @JsonIgnore
    public String getLastName() {
        return lastName;
    }

    @JsonIgnore
    public String getFirstName() {
        return firstName;
    }
}
