package com.trueffect.sql.crud;

import com.trueffect.model.Actor;
import com.trueffect.model.Identifier;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import com.trueffect.util.ModelObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author santiago.mamani
 */
public class ActorCrud {

    public Either insertActors(Connection connection, long idCreateUser, ArrayList<String> actors) {
        try {
            Either eitherIdentifier = new Either();
            String sql = "";
            Statement query = null;
            for (String actor : actors) {
                query = (Statement) connection.createStatement();
                sql
                        = "INSERT INTO ACTOR(\n"
                        + "name_actor, "
                        + "create_user, "
                        + "create_date, "
                        + "modifier_user,"
                        + "modifier_date, "
                        + "status)\n"
                        + "VALUES ('"
                        + actor + "',"
                        + idCreateUser + ","
                        + "current_timestamp,"
                        + "null,"
                        + "null,"
                        + "'Active') returning actor_id; ";
                ResultSet rs = query.executeQuery(sql);
                if (rs.next()) {
                    eitherIdentifier.addModeloObjet(new Identifier(rs.getLong("actor_id")));
                }
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

    public Either getAllActors(Connection connection, String status) {
        Statement query = null;
        try {

            String sql = "";
            query = (Statement) connection.createStatement();
            sql = sql
                    + "SELECT actor_id, "
                    + "       name_actor,"
                    + "       status\n"
                    + "  FROM ACTOR\n";
            if (StringUtils.isNotBlank(status)) {
                sql = sql + " WHERE status= '" + status + "';";
            }
            ResultSet rs = query.executeQuery(sql);
            Either eitherActors = new Either();
            while (rs.next()) {
                Actor actor = new Actor(
                        rs.getLong("actor_id"),
                        rs.getString("name_actor"),
                        rs.getString("status"));
                eitherActors.addModeloObjet(actor);
            }
            if (query != null) {
                query.close();
            }
            eitherActors.setCode(CodeStatus.OK);
            return eitherActors;
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }

    public Either getActorByIds(Connection connection, ArrayList<ModelObject> identifiersActors, String status) {
        Either eitherActor = new Either();
        try {

            Statement query = (Statement) connection.createStatement();
            for (ModelObject identifierActor : identifiersActors) {
                long idActor = ((Identifier) identifierActor).getId();
                String sql
                        = "SELECT actor_id, "
                        + "       name_actor,"
                        + "       status\n"
                        + "  FROM ACTOR\n"
                        + " WHERE actor_id=" + idActor;
                if (StringUtils.isNotBlank(status)) {
                    sql = sql + " AND status= '" + status + "';";
                }
                ResultSet rs = query.executeQuery(sql);
                if (rs.next()) {
                    Actor actor = new Actor(
                            rs.getLong("actor_id"),
                            rs.getString("name_actor"),
                            rs.getString("status"));
                    eitherActor.addModeloObjet(actor);
                }
            }
            if (query != null) {
                query.close();
            }
            eitherActor.setCode(CodeStatus.OK);
            return eitherActor;
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }
}
