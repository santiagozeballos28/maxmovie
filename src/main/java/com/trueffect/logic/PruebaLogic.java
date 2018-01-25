package com.trueffect.logic;

import com.trueffect.conection.db.DataBasePostgres;
import com.trueffect.model.MasterDetailSaile;
import com.trueffect.response.Either;
import com.trueffect.sql.crud.MasterDetailCrud;
import com.trueffect.sql.crud.ReportSaleCrud;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.tools.ConstantData.ObjectMovie;
import com.trueffect.util.ModelObject;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author santiago.mamani
 */
public class PruebaLogic {

    private HashMap<String, String> listData;
    private Permission permission;
    private PersonLogic personLogic;
    private MasterDetailCrud masterDetailCrud;
    private ReportSaleCrud reportSaleCrud;

    public PruebaLogic() {
        String object = ObjectMovie.Employee.name();
        listData = new HashMap<String, String>();
        permission = new Permission();
        personLogic = new PersonLogic();
        permission.setNameObject(object);
        masterDetailCrud = new MasterDetailCrud();
        reportSaleCrud = new ReportSaleCrud();
    }

    public Either getReportMovie(long idUserSearch, long idMasterDetail) {
        Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();
            String get = ConstantData.Crud.get.name();
            String status = ConstantData.Status.Active.name();
            //checks if the employee exists
            eitherRes = permission.getPerson(connection, idUserSearch, status, get);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //Check if the user can modify
            eitherRes = permission.checkUserPermission(connection, idUserSearch, get);
            if (eitherRes.existError()) {
                throw eitherRes;
            }

            eitherRes = masterDetailCrud.getDetailSaleOf(connection, idMasterDetail);
            if (eitherRes.existError()) {
                throw eitherRes;
            }

            DataBasePostgres.connectionCommit(connection);

        } catch (Either e) {
            eitherRes = e;
        } finally {
            DataBasePostgres.connectionClose(connection, eitherRes);
        }
        return eitherRes;
    }

    public Either getReportSale(long idUserSearch, long idMasterDetail) {
        Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();
            String get = ConstantData.Crud.get.name();
            String status = ConstantData.Status.Active.name();
            System.out.println("ENTRO A REPORT");
            //checks if the employee exists
            eitherRes = permission.getPerson(connection, idUserSearch, status, get);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //Check if the user can modify
            eitherRes = permission.checkUserPermission(connection, idUserSearch, get);
            if (eitherRes.existError()) {
                throw eitherRes;
            }

            eitherRes = masterDetailCrud.getDetailSaleOf(connection, idMasterDetail);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            System.out.println("Obtuvo el master detail");
            MasterDetailSaile masterDetailPerson = (MasterDetailSaile) eitherRes.getFirstObject();
            //Get amount subtotal and price subtotal of retal
            eitherRes = reportSaleCrud.getReportRental(connection, idMasterDetail);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            System.out.println("Se obtuvo el detail de rental");
            ArrayList<ModelObject> reportRental = eitherRes.getListObject();

            eitherRes = new Either();
            eitherRes.addModeloObjet(masterDetailPerson);
            eitherRes.addListObject(reportRental);
            eitherRes.setCode(CodeStatus.OK);
            DataBasePostgres.connectionCommit(connection);

        } catch (Either e) {
            eitherRes = e;
        } finally {
            DataBasePostgres.connectionClose(connection, eitherRes);
        }
        return eitherRes;
    }
}
