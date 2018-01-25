package com.trueffect.model;

import com.trueffect.util.ModelObject;

/**
 *
 * @author santiago.mamani
 */
public class CopyMovie extends ModelObject {

    private long copyMovieId;
    private int amountInitial;
    private int amountCurrent;
    protected String createDate;
    protected long movieId;

    public CopyMovie() {
    }

    public CopyMovie(long copyMovieId, int amountInitial, int amountCurrent, String createDate, long movieId) {
        this.copyMovieId = copyMovieId;
        this.amountInitial = amountInitial;
        this.amountCurrent = amountCurrent;
        this.createDate = createDate;
        this.movieId = movieId;
    }

    public CopyMovie(long movieId, int amountCurrent) {
        this.amountCurrent = amountCurrent;
        this.movieId = movieId;
    }

    public CopyMovie(long copyMovieId, int amountInitial, int amountCurrent) {
        this.copyMovieId = copyMovieId;
        this.amountInitial = amountInitial;
        this.amountCurrent = amountCurrent;
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
