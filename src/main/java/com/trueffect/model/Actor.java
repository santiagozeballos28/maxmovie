package com.trueffect.model;

import com.trueffect.util.ModelObject;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author santiago.mamani
 */
public class Actor extends ModelObject {

    private long id;
    private String name;
    private String status;

    public Actor() {
    }

    public Actor(long id, String name, String status) {
        this.id = id;
        this.name = name.trim();
        this.status = status.trim();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }
       @JsonIgnore
    public boolean isEmpty() {
        return   name == null;
    }
}
