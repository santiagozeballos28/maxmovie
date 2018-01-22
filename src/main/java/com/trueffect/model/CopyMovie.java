package com.trueffect.model;

import com.trueffect.util.ModelObject;

/**
 *
 * @author santiago.mamani
 */
public class CopyMovie extends ModelObject{

    /*
      copy_movie_id serial NOT NULL,
  amount_initial integer,
  amount_current integer,
  create_user integer,
  create_date timestamp without time zone,
  modifier_user integer,
  modifier_date timestamp without time zone,
  movie_id integer,
     */
    private long copyMovieId;
    private int amountInitial;
    private int amountCurrent;
    private String createDate;
    private long movieId;
        
    public CopyMovie() {
    }

    public CopyMovie(long copyMovieId, int amountInitial, int amountCurrent, String createDate,long movieId) {
        this.copyMovieId = copyMovieId;
        this.amountInitial = amountInitial;
        this.amountCurrent = amountCurrent;
        this.createDate = createDate;
        this.movieId = movieId;
    }

    public CopyMovie(long movieId,int amountCurrent) {
        this.amountCurrent = amountCurrent;
        this.movieId = movieId;
    }

    public long getCopyMovieId() {
        return copyMovieId;
    }

    public int getAmountInitial() {
        return amountInitial;
    }

    public int getAmountCurrent() {
        return amountCurrent;
    }

    public String getCreateDate() {
        return createDate;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setCopyMovieId(long copyMovieId) {
        this.copyMovieId = copyMovieId;
    }

    public void setAmountInitial(int amountInitial) {
        this.amountInitial = amountInitial;
    }

    public void setAmountCurrent(int amountCurrent) {
        this.amountCurrent = amountCurrent;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }
}
