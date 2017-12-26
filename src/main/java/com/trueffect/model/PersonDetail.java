package com.trueffect.model;

import com.trueffect.util.ModelObject;

/**
 * @author santiago.mamani
 */
public class PersonDetail extends ModelObject {

    protected int id;
    protected String typeIdentifier;
    protected String typeIdDescription;
    protected String identifier;
    protected String nameRenterUser;
    protected String genre;
    protected String birthday;
    protected String dateCreate;
    protected String nameCreateUser;

    public PersonDetail() {
    }

    public PersonDetail(int id, String typeIdentifier, String typeIdDescription, String identifier, String nameRenterUser, String genre, String birthday, String dateCreate, String nameCreateUser) {
        this.id = id;
        this.typeIdentifier = typeIdentifier.trim();
        this.typeIdDescription = typeIdDescription.trim();
        this.identifier = identifier.trim();
        this.nameRenterUser = nameRenterUser.trim();
        this.genre = genre.trim();
        this.birthday = birthday.trim();
        this.dateCreate = dateCreate.trim();
        this.nameCreateUser = nameCreateUser.trim();
    }

    public int getId() {
        return id;
    }

    public String getTypeIdentifier() {
        return typeIdentifier;
    }

    public String getTypeIdDescription() {
        return typeIdDescription;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getNameRenterUser() {
        return nameRenterUser;
    }

    public String getGenre() {
        return genre;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public String getNameCreateUser() {
        return nameCreateUser;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTypeIdentifier(String typeIdentifier) {
        this.typeIdentifier = typeIdentifier;
    }

    public void setTypeIdDescription(String typeIdDescription) {
        this.typeIdDescription = typeIdDescription;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setNameRenterUser(String nameRenterUser) {
        this.nameRenterUser = nameRenterUser;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public void setNameCreateUser(String nameCreateUser) {
        this.nameCreateUser = nameCreateUser;
    }
}
