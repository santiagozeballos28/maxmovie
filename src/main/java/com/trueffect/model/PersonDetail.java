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
    protected String genreDescription;
    protected String birthday;
    protected String dateCreate;
    protected String nameCreateUser;

    public PersonDetail() {
    }

    public PersonDetail(int id, String typeIdentifier, String typeIdDescription, String identifier, String nameRenterUser, String genre, String genreDescription, String birthday, String dateCreate, String nameCreateUser) {
        this.id = id;
        this.typeIdentifier = typeIdentifier.trim();
        this.typeIdDescription = typeIdDescription.trim();
        this.identifier = identifier.trim();
        this.nameRenterUser = nameRenterUser.trim();
        this.genre = genre.trim();
        this.genreDescription = genreDescription.trim();
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

    public String getGenreDescription() {
        return genreDescription;
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
        this.typeIdentifier = typeIdentifier.trim();
    }

    public void setTypeIdDescription(String typeIdDescription) {
        this.typeIdDescription = typeIdDescription.trim();
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier.trim();
    }

    public void setNameRenterUser(String nameRenterUser) {
        this.nameRenterUser = nameRenterUser.trim();
    }

    public void setGenre(String genre) {
        this.genre = genre.trim();
    }

    public void setGenreDescription(String genreDescription) {
        this.genreDescription = genreDescription.trim();
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday.trim();
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate.trim();
    }

    public void setNameCreateUser(String nameCreateUser) {
        this.nameCreateUser = nameCreateUser.trim();
    }

   
}
