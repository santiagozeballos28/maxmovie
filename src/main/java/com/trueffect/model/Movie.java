package com.trueffect.model;

import com.trueffect.util.ModelObject;
import java.util.ArrayList;
import java.util.logging.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author santiago.mamani
 */
public class Movie extends ModelObject {

    protected long id;
    protected String name;
    protected String genreId;
    protected String director;
    protected int year;
    protected int oscarNomination;
    protected ArrayList<String> actor;
    protected String status;

    public Movie() {
    }

    public Movie(long id, String name, String genreId, String director, int year, int oscarNomination, ArrayList<String> actor) {
        this.id = id;
        this.name = name.trim();
        this.genreId = genreId.trim();
        this.director = director.trim();
        this.year = year;
        this.oscarNomination = oscarNomination;
        this.actor = actor;
    }

    public Movie(long id, String name, String genreId, String director, int year, int oscarNomination, String status) {
        this.id = id;
        this.name = name.trim();
        this.genreId = genreId.trim();
        this.director = director.trim();
        this.year = year;
        this.oscarNomination = oscarNomination;
        this.status = status.trim();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGenreId() {
        return genreId;
    }

    public String getDirector() {
        return director;
    }

    public int getYear() {
        return year;
    }

    public int getOscarNomination() {
        return oscarNomination;
    }

    public ArrayList<String> getActor() {
        return actor;
    }

    public String getStatus() {
        return status;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId.trim();
    }

    public void setDirector(String director) {
        this.director = director.trim();
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setOscarNomination(int oscarNomination) {
        this.oscarNomination = oscarNomination;
    }

    public void setActor(ArrayList<String> actor) {
        this.actor = actor;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonIgnore
    public boolean isEmpty() {
        return id == 0
                && name == null
                && genreId == null
                && director == null;
    }
}
