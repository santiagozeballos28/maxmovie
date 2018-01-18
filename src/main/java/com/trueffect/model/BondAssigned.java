package com.trueffect.model;

import com.trueffect.util.ModelObject;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author santiago.mamani
 */
public class BondAssigned extends ModelObject{
    private int idPerson;
    private int idBond;
    private String dateStart;
    private String dateEnd;
    private String status;
    public BondAssigned() {
    }

    public BondAssigned(int idPerson, int idBond) {
        this.idPerson = idPerson;
        this.idBond = idBond;
    }
    
    public BondAssigned(int idPerson, int idBond, String dateStart, String dateEnd, String status) {
        this.idPerson = idPerson;
        this.idBond = idBond;
        this.dateStart = dateStart.trim();
        this.dateEnd = dateEnd.trim();
        this.status = status.trim();
    }

    public int getIdPerson() {
        return idPerson;
    }

    public int getIdBond() {
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
