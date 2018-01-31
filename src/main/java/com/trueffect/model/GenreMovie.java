package com.trueffect.model;

import com.trueffect.util.ModelObject;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author santiago.mamani
 */
public class GenreMovie extends ModelObject {

    private String idGenre;
    private String nameGenre;

    public GenreMovie() {
    }

    public GenreMovie(String idGenre, String nameGenre) {
        this.idGenre = idGenre.trim();
        this.nameGenre = nameGenre.trim();
    }

    public String getIdGenre() {
        return idGenre;
    }

    public String getNameGenre() {
        return nameGenre;
    }

    public void setIdGenre(String idGenre) {
        this.idGenre = idGenre;
    }

    public void setNameGenre(String nameGenre) {
        this.nameGenre = nameGenre;
    }
       @JsonIgnore
    public boolean isEmpty() {
        return  idGenre == null && nameGenre == null;
    }
}
