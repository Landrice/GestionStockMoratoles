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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

/**
 *
 * @author Admin
 */
public class CatagoryBLL {
    SQLSyntax sql = new SQLSyntax();
    CatagoryGetway catagoryGetway = new CatagoryGetway();
    bddConnection dbCon = new bddConnection();
    Connection con = dbCon.geConnection();
    PreparedStatement pst;
    ResultSet rs;
    
    BddPropreties dBProperties = new BddPropreties();
    String db = dBProperties.loadPropertiesFile();

    public void save(Catagory catagory){
        if (isUniqName(catagory)){
            catagoryGetway.save(catagory);
        }
    }

    public void update(Catagory catagory){
        if(checkUpdate(catagory)){
            catagoryGetway.update(catagory);
        }else if(isUniqName(catagory)){
            catagoryGetway.update(catagory);
        }
    }
    
    public void delete(Catagory catagory){
        if(catagoryGetway.isNotUse(catagory)){
            catagoryGetway.delete(catagory);
        }else{
            //nada, rien
        }
    }

    public boolean checkUpdate(Catagory catagory){
        boolean isTrueUpdate = false;
        catagory.brandId = sql.getIdNo(catagory.brandName, catagory.brandId, "marque", "NomMarque");
        catagory.supplyerId = sql.getIdNo(catagory.supplyerName, catagory.supplyerId, "fournisseur", "NomFournisseur");

        try {
            pst = con.prepareStatement("select * from "+db+".categorie where NomCategory=? and MarqueId=? and FournisseurId=? and Id=?");
            pst.setString(1, catagory.catagoryName);
            pst.setString(2, catagory.brandId);
            pst.setString(3, catagory.supplyerId);
            pst.setString(4, catagory.id);
            rs = pst.executeQuery();
            while (rs.next()) {
                return isTrueUpdate = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isTrueUpdate;
    }

    public boolean isUniqName(Catagory catagory) {

        boolean uniqSupplyer = false;
        catagory.brandId = sql.getIdNo(catagory.brandName, catagory.brandId, "marque", "NomMarque");
        catagory.supplyerId = sql.getIdNo(catagory.supplyerName, catagory.supplyerId, "fournisseur", "NomFournisseur");
        try {
            pst = con.prepareCall("select * from "+db+".categorie where NomCategory=? and MarqueId=? and FournisseurId=?");
            pst.setString(1, catagory.catagoryName);
            pst.setString(2, catagory.brandId);
            pst.setString(3, catagory.supplyerId);
            rs = pst.executeQuery();
            while (rs.next()) {
                System.out.println("in not uniq");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Erreur");
                alert.setHeaderText("ERREUR: utilisé");
                alert.setContentText("Le categorie" + "  '" + catagory.catagoryName + "' " + "existe déja");
                alert.initStyle(StageStyle.UNDECORATED);
                alert.showAndWait();
                
                return uniqSupplyer;
            }
            uniqSupplyer = true;
        } catch (SQLException ex) {
            Logger.getLogger(Supplyer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return uniqSupplyer;
    }
}
