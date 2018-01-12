package com.trueffect.sql.crud;

import com.trueffect.model.Job;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author santiago.mamani
 */
public class JobCrud {

    public static Either getJobOf(Connection connection, long idUser) {
        Either either = new Either();
        try {
            String sql
                    = "SELECT JOB.job_id, "
                    + "       job_name\n"
                    + "  FROM DATA_JOB,"
                    + "       JOB\n"
                    + " WHERE DATA_JOB.job_id= JOB.job_id "
                    + "   AND status = 'Active'"
                    + "   AND person_id = ?;";
            PreparedStatement st = connection.prepareStatement(sql);
            //st.setInt(1, idUser);
            st.setLong(1, idUser);
            ResultSet rs = st.executeQuery();
            Job job = new Job();
            if (rs.next()) {
                job = new Job(
                        rs.getInt("job_id"),
                        rs.getString("job_name"));
            }
            if (st != null) {
                st.close();
            }
            either.setCode(CodeStatus.OK);
            either.addModeloObjet(job);
        } catch (Exception exception) {
            either.setCode(CodeStatus.INTERNAL_SERVER_ERROR);
            either.addError(exception.getMessage());
        }
        return either;
    }

    public static Either getJobOfName(Connection connection, String name) {
        Either either = new Either();
        try {
            String sql
                    = "SELECT job_id, "
                    + "       job_name"
                    + "  FROM JOB"
                    + "  WHERE job_name=?";

            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            Job job = new Job();
            if (rs.next()) {
                job = new Job(
                        rs.getLong("job_id"),
                        rs.getString("job_name"));
            }
            if (st != null) {
                st.close();
            }
            either.setCode(CodeStatus.OK);
            either.addModeloObjet(job);
        } catch (Exception exception) {
            either.setCode(CodeStatus.INTERNAL_SERVER_ERROR);
            either.addError(exception.getMessage());
        }
        return either;
    }
}
