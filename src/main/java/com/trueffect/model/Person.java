
package com.trueffect.model;
import com.trueffect.sql.crud.PersonCrud;
import com.trueffect.util.ModelObject;
import java.sql.Connection;
/*
 * @author santiago.mamani
 */
public class Person extends ModelObject{
    private int id;
    public String typeIdentifier;
    public String identifier;
    public String lastName;
    public String firstName;
    public String genre;
    public String birthday;
    public Person() {
    }
    public Person(int id, String typeIdentifier, String identifier, String lastName, String firstName, String genre, String birthday) {
        this.id = id;
        this.typeIdentifier = typeIdentifier;
        this.identifier = identifier;
        this.lastName = lastName;
        this.firstName = firstName;
        this.genre = genre;
        this.birthday = birthday;
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
        this.typeIdentifier = typeIdentifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }


    
    @Override
     public ModelObject insertResourse(int idCreatorUser, Connection connection)throws Exception{
     return PersonCrud.insertrenterUser(connection,idCreatorUser, this);
     }
     @Override
    public ModelObject updateResourse(int idCreatorUser) {
      return PersonCrud.updateRenterUser(idCreatorUser,this);       
    }
    public int compareTo(Person o) {
     return (
             typeIdentifier.compareTo(o.typeIdentifier)==0&&
             identifier.compareTo(o.identifier)==0&&
             lastName.compareTo(o.lastName)==0&&
             firstName.compareTo(o.firstName)==0&&
             genre.compareTo(o.genre)==0)?0:-1;
      }
 
}
