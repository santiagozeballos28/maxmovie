
package com.trueffect.model;

import com.trueffect.util.ModelObject;

/**
 *
 * @author santiago.mamani
 */
public class ReportMovie extends ModelObject{
    private long idMovie;
    private String nameMovie;
    private int quantityCurrent;
    private String statusMovie;

    public ReportMovie() {
    }

    public ReportMovie(long idMovie, String nameMovie, int quantityCurrent, String statusMovie) {
        this.idMovie = idMovie;
        this.nameMovie = nameMovie.trim();
        this.quantityCurrent = quantityCurrent;
        this.statusMovie = statusMovie.trim();
    }

    public ReportMovie(long idMovie, String nameMovie, String statusMovie) {
        this.idMovie = idMovie;
        this.nameMovie = nameMovie;
        this.statusMovie = statusMovie;
    }

    public ReportMovie(long idMovie, String nameMovie, int quantityCurrent) {
        this.idMovie = idMovie;
        this.nameMovie = nameMovie;
        this.quantityCurrent = quantityCurrent;
    }
  
    public long getIdMovie() {
        return idMovie;
    }

    public String getNameMovie() {
        return nameMovie;
    }

    public int getQuantityCurrent() {
        return quantityCurrent;
    }

    public String getStatusMovie() {
        return statusMovie;
    }

    public void setIdMovie(long idMovie) {
        this.idMovie = idMovie;
    }

    public void setNameMovie(String nameMovie) {
        this.nameMovie = nameMovie;
    }

    public void setQuantityCurrent(int quantityCurrent) {
        this.quantityCurrent = quantityCurrent;
    }

    public void setStatusMovie(String statusMovie) {
        this.statusMovie = statusMovie;
    }
}
