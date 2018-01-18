package com.trueffect.sql.crud;

import com.trueffect.model.BondAssigned;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import com.trueffect.util.ModelObject;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author santiago.mamani
 */
public class BondAssignedCrud {

    public Either insert(Connection connection, ArrayList<ModelObject> insertAssignBonds) {
        Statement query = null;
        try {

            query = (Statement) connection.createStatement();
            String sql = "";
            for (int i = 0; i < insertAssignBonds.size(); i++) {
                BondAssigned bondAssigned = (BondAssigned) insertAssignBonds.get(i);
                long idPerson = bondAssigned.getIdPerson();
                long idBond = bondAssigned.getIdBond();
                sql = sql
                        + "INSERT INTO bond_assigned ("
                        + "id_person, "
                        + "id_bond, "
                        + "start_date, "
                        + "end_date, "
                        + "status) "
                        + "VALUES ("
                        + idPerson + ","
                        + idBond + ","
                        + "current_date,"
                        + "null,"
                        + "'Active'); ";

            }
            query.execute(sql);
            if (query != null) {
                query.close();
            }
            return new Either();
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }

    public Either updateStatus(Connection connection, ArrayList<ModelObject> updateAssignBonds, String status) {
        Statement query = null;
        try {
            query = (Statement) connection.createStatement();
            String sql = "";
            for (int i = 0; i < updateAssignBonds.size(); i++) {
                BondAssigned bondAssigned = (BondAssigned) updateAssignBonds.get(i);
                sql = sql
                        = "UPDATE BOND_ASSIGNED\n"
                        + "   SET end_date = current_date , "
                        + "       status = '" + status + "'"
                        + " WHERE id_person = " + bondAssigned.getIdPerson() + " "
                        + "   AND id_bond = " + bondAssigned.getIdBond() + ";";
            }
            query.execute(sql);
            if (query != null) {
                query.close();
            }
            return new Either();
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }
   
}
