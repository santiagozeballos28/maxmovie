package com.trueffect.sql.crud;

import com.trueffect.model.Identifier;
import com.trueffect.model.MasterDetail;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author santiago.mamani
 */
public class MasterDetailCrud {

    public Either insert(Connection connection, MasterDetail masterDetail) {

        try {
            Either eitherIdentifier = new Either();
            int amountTotal = masterDetail.getAmountTotal();
            double priceTotal = masterDetail.getPriceTotal();
            long idCreateUser = masterDetail.getIdCreateUser();
            long idRenterUser = masterDetail.getIdRenterUser();
            Statement query = null;
            query = (Statement) connection.createStatement();
            String sql
                    = "INSERT INTO MASTER_DETAIL("
                    + "amount_total, "
                    + "price_total, "
                    + "create_user, "
                    + "create_date,"
                    + "modifier_date, "
                    + "modifier_user, "
                    + "renter_user)\n"
                    + "VALUES ("
                    + amountTotal+","
                    + priceTotal+","
                    + idCreateUser+","
                    + "current_timestamp,"
                    + "null,"
                    + "null,"
                    + idRenterUser+")"
                    + "returning master_detail_id; ";
            ResultSet rs = query.executeQuery(sql);
            if (rs.next()) {
                eitherIdentifier.addModeloObjet(new Identifier(rs.getLong("master_detail_id")));
            }

            if (query != null) {
                query.close();
            }
            eitherIdentifier.setCode(CodeStatus.CREATED);
            return eitherIdentifier;
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }
}
