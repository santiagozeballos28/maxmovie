/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trueffect.sql.crud;

import com.trueffect.conection.db.DataBasePostgres;
import com.trueffect.model.Job;
import java.sql.Connection;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author santiago.mamani
 */
public class JobCrudTest {
    @Test
    public void testJogGetByIdPerson() throws Exception {
        Job job = new Job(1,"Administrator");
        String exMsg = "";
        Job jobRes = null;
        Connection connection = DataBasePostgres.getConection();
        try {
            jobRes =  JobCrud.getJobOf(connection, 1);
        } catch (Exception ex) {
            
        }
        Assert.assertEquals(jobRes.compareTo(job),0);
    }
}
