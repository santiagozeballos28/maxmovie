package com.trueffect.model;

import com.trueffect.util.ModelObject;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author santiago.mamani
 */
public class Job extends ModelObject {

    private int id;
    protected String nameJob;

    public Job() {
    }

    public Job(int id, String nameJob) {
        this.id = id;
        this.nameJob = nameJob;
    }

    public int getId() {
        return id;
    }

    public String getNameJob() {
        return nameJob;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNameJob(String nameJob) {
        this.nameJob = nameJob;
    }

    public int compareTo(Job o) {
        return nameJob.compareTo(o.nameJob);
    }

    @JsonIgnore
    public boolean isEmpty() {
        return id == 0 && nameJob == null;

    }
}
