package com.trueffect.model;

import com.trueffect.util.ModelObject;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author santiago.mamani
 */
public class BondAssigned extends ModelObject {

    private long idEmployeeData;
    private long idBond;
    private String dateStart;
    private String dateEnd;
    private String status;

    public BondAssigned() {
    }

    public BondAssigned(long idEmployeeData, long idBond) {
        this.idEmployeeData = idEmployeeData;
        this.idBond = idBond;
    }

    public BondAssigned(long idEmpoyeeData, long idBond, String dateStart, String dateEnd, String status) {
        this.idEmployeeData = idEmpoyeeData;
        this.idBond = idBond;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.status = status;
    }

    public long getIdEmployeeData() {
        return idEmployeeData;
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
        return idEmployeeData == 0 && idBond == 0;

    }
}
