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

    public static Either getJobOf(Connection connection, int idUser) {
        Either either = new Either();
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
            Job job = new Job();
            if (rs.next()) {
                job = new Job(
                        rs.getInt("id"),
                        rs.getString("name_job"));

                System.out.println("ES un trabajador");
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
