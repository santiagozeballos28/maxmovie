package com.trueffect.model;

/**
 * @author santiago.mamani
 */
public class PersonDetail extends Person {

    protected String typeIdDescription;
    protected String nameRenterUser;
    protected String genreDescription;
    protected String dateCreate;
    protected String nameCreateUser;

    public PersonDetail() {
    }

    public PersonDetail(int id, String typeIdentifier, String typeIdDescription, String identifier, String nameRenterUser, String genre, String genreDescription, String birthday, String dateCreate, String nameCreateUser) {
        super(id, typeIdentifier, identifier, genre, birthday);
        this.typeIdDescription = typeIdDescription.trim();
        this.nameRenterUser = nameRenterUser.trim();
        this.genreDescription = genreDescription.trim();
        this.dateCreate = dateCreate.trim();
        this.nameCreateUser = nameCreateUser.trim();
    }

    public String getTypeIdDescription() {
        return typeIdDescription;
    }

    public String getNameRenterUser() {
        return nameRenterUser;
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

    public void setNameRenterUser(String nameRenterUser) {
        this.nameRenterUser = nameRenterUser.trim();
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
}
