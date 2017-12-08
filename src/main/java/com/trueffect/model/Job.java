package com.trueffect.model;
/*
 * @author santiago.mamani
 */
public class Job {
    private int id;
    private String nameJob;

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
}
