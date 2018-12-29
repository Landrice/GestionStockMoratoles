/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gettersSetters;

import bdd.BddPropreties;
import bdd.SQLSyntax;
import bdd.bddConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

/**
 *
 * @author Ralande
 */
public class CustomerBLL {
    SQLSyntax sql = new SQLSyntax();
    CustomerGetway customerGetway = new CustomerGetway();
    bddConnection dbCon = new bddConnection();
    Connection con = dbCon.geConnection();
    PreparedStatement pst;
    ResultSet rs;
    BddPropreties dBProperties = new BddPropreties();
    String db = dBProperties.loadPropertiesFile();

    public void save(Customer customer){
        if(isUniqName(customer)){
            customerGetway.save(customer);
        }
    }

    public void update(Customer customer){
        if(isUpdate(customer)){
            if(isSame(customer)){
                customerGetway.update(customer);
            }else if(isUniqName(customer)){
                customerGetway.update(customer);
            }
        }
    }


    public boolean isUniqName(Customer customer) {
        boolean inUniqName = false;
        try {
            pst = con.prepareStatement("select * from "+db+".Acheteur where NomAcheteur=? and NumeroAcheteur=?");
            pst.setString(1, customer.customerName);
            pst.setString(2, customer.customerConNo);
            rs = pst.executeQuery();
            while (rs.next()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Erreur");
                alert.setHeaderText("ERREUR : existant");
                alert.setContentText("L'acheteur existe déja");
                alert.initStyle(StageStyle.UNDECORATED);
                alert.showAndWait();
                

                return inUniqName;
            }
            inUniqName = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inUniqName;
    }

    public boolean isUpdate(Customer customer) {
        boolean isUpdate = false;
        try {
            pst = con.prepareStatement("select * from "+db+".Acheteur where Id=? and NomAcheteur=? and NumeroAcheteur=? and AdresseAcheteur=?");
            pst.setString(1,customer.id);
            pst.setString(2,customer.customerName);
            pst.setString(3,customer.customerConNo);
            pst.setString(4,customer.customerAddress);
            rs = pst.executeQuery();
            while (rs.next()){

                return isUpdate;
            }
            isUpdate = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUpdate;
    }

    private boolean isSame(Customer customer){
        boolean isSame = false;
        try {
            pst = con.prepareStatement("select * from "+db+".Acheteur where id=? and NomAcheteur=? and NumeroAcheteur=?");
            pst.setString(1,customer.id);
            pst.setString(2,customer.customerName);
            pst.setString(3,customer.customerConNo);
            rs = pst.executeQuery();
            while (rs.next()){

                return isSame=true;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSame;
    }
    
}
