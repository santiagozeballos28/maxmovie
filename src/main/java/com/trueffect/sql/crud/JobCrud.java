package com.trueffect.sql.crud;
import com.trueffect.response.ErrorResponse;
import com.trueffect.conection.db.DatabasePostgres;
import com.trueffect.model.Job;
import com.trueffect.tools.CodeStatus;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 * @author santiago.mamani
 */
public class JobCrud {
    public static Job getJobOf(int id)throws Exception {
            Job job=null;
            DatabasePostgres.getConection();
            try {
                          String sql = "SELECT id, name_job\n" +
                             "  FROM data_job, job\n" +
                             "  WHERE data_job.id_job= job.id AND id_person=?;" ;

                PreparedStatement st = DatabasePostgres.connection.prepareStatement(sql);
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
            if (rs.next()) {
               job = new Job(rs.getInt("id"), rs.getString("name_job"));
            } else {
               throw new ErrorResponse(CodeStatus.NOT_FOUND, "No existe el recurso not job");
            }
            } catch (Exception e) {
                throw new ErrorResponse(CodeStatus.NOT_FOUND, e.getMessage());
            }
            DatabasePostgres.close();
            return job;
    }    
}
