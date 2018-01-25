package com.trueffect.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

/**
 *
 * @author santiago.mamani
 */
@JsonPropertyOrder({
    "copyMovieId",
    "nameMovie",
    "amountInitial",
    "amountCurrent"})
public class ReportCopyMovie extends CopyMovie {

    private String nameMovie;

    public ReportCopyMovie(long copyMovieId, String nameMovie, int amountInitial, int amountCurrent) {
        super(copyMovieId, amountInitial, amountCurrent);
        this.nameMovie = nameMovie;
    }

    public String getNameMovie() {
        return nameMovie;
    }

    public void setNameMovie(String nameMovie) {
        this.nameMovie = nameMovie;
    }

    @JsonIgnore
    public String getCreateDate() {
        return createDate;
    }

    @JsonIgnore
    public long getMovieId() {
        return movieId;
    }
}
