package com.trueffect.sql.crud;

import com.trueffect.model.Identifier;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import com.trueffect.util.ModelObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author santiago.mamani
 */
public class ParticipateCrud {

    public Either insertActorsAndMovie(
            Connection connection,
            long idCreateUser,
            long idMovie,
            ArrayList<ModelObject> identifiersActors) {
        Statement query = null;
        try {
            String sql = "";
            query = (Statement) connection.createStatement();
            for (ModelObject identifierActor : identifiersActors) {
                long idActor = ((Identifier) identifierActor).getId();
                sql = sql
                        + "INSERT INTO PARTICIPATE(\n"
                        + "movie_id, "
                        + "actor_id, "
                        + "create_user, "
                        + "create_date, "
                        + "modifier_user, "
                        + "modifier_date,"
                        + "status)\n"
                        + "VALUES ("
                        + idMovie + ","
                        + idActor + " ,"
                        + idCreateUser + ","
                        + "current_timestamp,"
                        + "null,"
                        + "null, "
                        + "'Active');";
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

    public Either getIdsActorOf(Connection connection, long idMovie, String status) {
        try {
            Either eitherIdentifier = new Either();
            String sql = "";
            sql = sql
                    + "SELECT actor_id\n"
                    + "  FROM PARTICIPATE\n"
                    + "  WHERE movie_id = ? ";
            if (StringUtils.isNotBlank(status)) {
                sql = sql + "AND status='" + status + "';";
            }
            PreparedStatement st = connection.prepareStatement(sql);
            st.setLong(1, idMovie);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                eitherIdentifier.addModeloObjet(new Identifier(rs.getLong("actor_id")));
            }
            if (st != null) {
                st.close();
            }
            eitherIdentifier.setCode(CodeStatus.CREATED);
            return eitherIdentifier;
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }

    public Either updateStatus(Connection connection, long idModifierUser, long idMovie, ArrayList<ModelObject> idsToUpdateStatus, String status) {
        try {
            String sql = "";
            for (ModelObject idUpdate : idsToUpdateStatus) {
                Identifier identifier = (Identifier) idUpdate;
                sql = sql
                        + "UPDATE PARTICIPATE\n"
                        + "   SET modifier_user = " + idModifierUser + ","
                        + "       modifier_date = current_timestamp" + ","
                        + "       status = '" + status + "'"
                        + " WHERE movie_id = " + idMovie
                        + "   AND actor_id = " + identifier.getId() + ";";
            }
            PreparedStatement st = connection.prepareStatement(sql);
            st.execute();
            if (st != null) {
                st.close();
            }
            return new Either();
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }
}
