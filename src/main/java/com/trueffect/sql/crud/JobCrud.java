package com.trueffect.sql.crud;

import com.trueffect.model.Job;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author santiago.mamani
 */
public class JobCrud {

    public static Job getJobOf(Connection connection, int idUser) throws Exception {
        Job job = null;
        try {
            String sql = "SELECT id, "
                       + "       name_job\n"
                       + "  FROM data_job,"
                       + "       job\n"
                       + " WHERE data_job.id_job= job.id "
                       + "   AND id_person=?;";

            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, idUser);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                job = new Job(
                        rs.getInt("id"),
                        rs.getString("name_job"));
            }
        } catch (Exception exception) {
            throw exception;
        }
        return job;
    }
}
