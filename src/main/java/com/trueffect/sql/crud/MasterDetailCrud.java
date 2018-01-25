package com.trueffect.sql.crud;

import com.trueffect.model.Identifier;
import com.trueffect.model.MasterDetail;
import com.trueffect.model.MasterDetailSaile;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
                    + amountTotal + ","
                    + priceTotal + ","
                    + idCreateUser + ","
                    + "current_timestamp,"
                    + "null,"
                    + "null,"
                    + idRenterUser + ")"
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

    public Either getDetailSaleOf(Connection connection, long idMasterDetail) {

        try {
            String query
                    = "SELECT master_detail_id, "
                    + "       last_name || ' '|| first_name AS person_name, "
                    + "       amount_total, "
                    + "       price_total \n"
                    + "  FROM MASTER_DETAIL, PERSON \n"
                    + " WHERE master_detail_id= ? "
                    + "   AND MASTER_DETAIL.renter_user = PERSON.person_id;";

            PreparedStatement st = connection.prepareStatement(query);
            st.setLong(1, idMasterDetail);
            ResultSet rs = st.executeQuery();
            MasterDetailSaile masterDetailPerson = new MasterDetailSaile();
            if (rs.next()) {
                masterDetailPerson = new MasterDetailSaile(
                        rs.getLong("master_detail_id"),
                        rs.getString("person_name"),
                        rs.getInt("amount_total"),
                        rs.getDouble("price_total"));
            }
            if (st != null) {
                st.close();
            }
            return new Either(CodeStatus.OK, masterDetailPerson);
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }

}
