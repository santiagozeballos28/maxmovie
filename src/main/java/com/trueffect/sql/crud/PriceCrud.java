package com.trueffect.sql.crud;

import com.trueffect.model.Price;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author santiago.mamani
 */
public class PriceCrud {

    public PriceCrud() {
    }

    public Either getPrice(Connection connection) {
        try {
            String query
                    = "SELECT price_id, "
                    + "       price_name, "
                    + "       price \n"
                    + "  FROM price;";
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            Either eitherRes = new Either();
            Price price = new Price();
            while (rs.next()) {
                price = new Price(
                        rs.getString("price_id"),
                        rs.getString("price_name"),
                        rs.getDouble("price"));
                eitherRes.addModeloObjet(price);
            }
            if (st != null) {
                st.close();
            }
            eitherRes.setCode(CodeStatus.OK);
            return eitherRes;
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }

    public Either getPriceOf(Connection connection, String idPricePenalty) {
        try {
            String query
                    = "SELECT price_id, "
                    + "       price_name, "
                    + "       price \n"
                    + "  FROM price"
                    + " WHERE price_id = '" + idPricePenalty + "'";
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            Either eitherRes = new Either();
            Price price = new Price();
            if (rs.next()) {
                price = new Price(
                        rs.getString("price_id"),
                        rs.getString("price_name"),
                        rs.getDouble("price"));
                eitherRes.addModeloObjet(price);
            }
            if (st != null) {
                st.close();
            }
            eitherRes.setCode(CodeStatus.OK);
            return eitherRes;
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }
}
