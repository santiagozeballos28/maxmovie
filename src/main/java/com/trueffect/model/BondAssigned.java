package com.trueffect.model;

import com.trueffect.util.ModelObject;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author santiago.mamani
 */
public class BondAssigned extends ModelObject{
    private long idPerson;
    private long idBond;
    private String dateStart;
    private String dateEnd;
    private String status;
    public BondAssigned() {
    }

    public BondAssigned(long idPerson, long idBond) {
        this.idPerson = idPerson;
        this.idBond = idBond;
    }
    
    public BondAssigned(long idPerson, long idBond, String dateStart, String dateEnd, String status) {
        this.idPerson = idPerson;
        this.idBond = idBond;
        this.dateStart = dateStart.trim();
        this.dateEnd = dateEnd.trim();
        this.status = status.trim();
    }

    public long getIdPerson() {
        return idPerson;
    }

    public long getIdBond() {
        return idBond;
    }

    public String getDateStart() {
        return dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public String getStatus() {
        return status;
    }

       @JsonIgnore
    public boolean isEmpty() {
        return idPerson == 0&& idBond == 0;
               
    }


    
}
