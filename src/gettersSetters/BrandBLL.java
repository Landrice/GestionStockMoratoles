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
public class BrandBLL {
    SQLSyntax sql = new SQLSyntax();

    bddConnection dbCon = new bddConnection();
    Connection con = dbCon.geConnection();
    PreparedStatement pst;
    ResultSet rs;
    
    BddPropreties dBProperties = new BddPropreties();
    String db = dBProperties.loadPropertiesFile();

    BrandsGetway brandsGetway = new BrandsGetway();

    public void save(Brands brands) {
        if (isUniqName(brands)) {
            brandsGetway.save(brands);
        }
    }

    public void update(Brands brands) {
        if (isTrueUpdate(brands)) {
            brandsGetway.update(brands);
        } else if (isUniqName(brands)) {
            brandsGetway.update(brands);
        }

    }

    public void delete(Brands brands){
        if(brandsGetway.isNotUsed(brands)){
            brandsGetway.delete(brands);
        }else{
            //noting
        }
    }

    public boolean isTrueUpdate(Brands brands) {
        boolean isTreu = false;
        brands.supplyrId = sql.getIdNo(brands.supplyerName, brands.supplyrId, "fournisseur", "NomFournisseur");
        System.out.println("we are in update");

        try {
            pst = con.prepareStatement("SELECT * FROM "+db+".marque WHERE NomMarque =? AND FournisseurID =? AND Id =?");
            pst.setString(1, brands.brandName);
            pst.setString(2, brands.supplyrId);
            pst.setString(3, brands.id);
            rs = pst.executeQuery();
            while (rs.next()) {
                return isTreu;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isTreu;
    }


    public boolean isUniqName(Brands brands) {
        boolean uniqSupplyer = false;
        try {
            pst = con.prepareCall("select * from "+db+".marque where NomMarque=? and FournisseurID=?");
            brands.supplyrId = sql.getIdNo(brands.supplyerName, brands.supplyrId, "fournisseur", "NomFournisseur");
            pst.setString(1, brands.brandName);
            pst.setString(2, brands.supplyrId);
            rs = pst.executeQuery();
            while (rs.next()) {
                System.out.println("in not uniq");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Erreur");
                alert.setHeaderText("ERREUR: Déja utilisé");
                alert.setContentText("La marque" + "  '" + brands.brandName + "' " + "Existe déja");
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
