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
public class CurrentProductBLL {
    
    bddConnection dbCon = new bddConnection();
    Connection con = dbCon.geConnection();
    PreparedStatement pst;
    ResultSet rs;
    BddPropreties dBProperties = new BddPropreties();
    String db = dBProperties.loadPropertiesFile();
    
    SQLSyntax sql = new SQLSyntax();
    CurrentProductGetway currentProductGetway = new CurrentProductGetway();

    public void save(CurrentProduct currentProduct) {
        if (isUniqName(currentProduct)) {
            currentProductGetway.save(currentProduct);
        }
        
    }

    public void update(CurrentProduct currentProduct) {
        if(isNotNull(currentProduct)){
        if (isUpdate(currentProduct)) {
            if (checkUpdateCondition(currentProduct)) {
                currentProductGetway.update(currentProduct);
            } else if (isUniqName(currentProduct)) {
                currentProductGetway.update(currentProduct);
            }
        }else System.out.println("Erreur: else ici");
        }else System.out.println("Erreur, else de ifNotNull");
    }

    public boolean isUniqName(CurrentProduct currentProduct) {
        System.out.println("WE ARE IS IS UNIT NAME");
        boolean isUniqname = false;
        try {
            pst = con.prepareStatement("select * from "+db+".produits where IdProduit=?");
            pst.setString(1, currentProduct.idProduit);
            rs = pst.executeQuery();
            while (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur: Non Unique");
                alert.setContentText("Produit id" + "  '" + currentProduct.idProduit + "' " + "n'est pas unique");
                alert.initStyle(StageStyle.UNDECORATED);
                alert.showAndWait();
                
                return isUniqname;
            }
            isUniqname = true;
        } catch (SQLException e) {
            System.out.println("Erreur SQL: "+e);
        }
        return isUniqname;
    }

    public boolean isUpdate(CurrentProduct currentProduct) {
        System.out.println("WE ARE IS IS UPDTE");
        boolean isUpdate = false;
        try {
            System.out.println("WE ARE IS IS UPDTE ---01");
            pst = con.prepareStatement("select * from "+db+".produits where Id=? and IdProduit=? and NomProduit=? and Quantite=? and Description=? and FounisseurId=? and MarqueId=? "
                    + "and CategorieId=? and Prix =? and UtilisateurId=? and Date=?");
            pst.setString(1, currentProduct.id);
            pst.setString(2, currentProduct.idProduit);
            pst.setString(3, currentProduct.nomProduit);
            pst.setString(4, currentProduct.quantite);
            pst.setString(5, currentProduct.description);
            pst.setString(6, currentProduct.fournisseurID);
            pst.setString(7, currentProduct.marqueID);
            pst.setString(8, currentProduct.categorieID);
            pst.setString(9, currentProduct.prix);
            pst.setString(10, currentProduct.utilisateurID);
            pst.setString(11, currentProduct.date);
            rs = pst.executeQuery();
            while (rs.next()) {
                return isUpdate;
            }
            isUpdate = true;
        } catch (SQLException ex) {
            Logger.getLogger(CurrentProduct.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
        return isUpdate;
        
    }

    public boolean checkUpdateCondition(CurrentProduct currentProduct) {
        boolean isTrueUpdate = false;
        if (isUpdate(currentProduct)) {
            try {
                pst = con.prepareStatement("select * from "+db+".produits where id=? and IdProduit=?");
                pst.setString(1, currentProduct.id);
                pst.setString(2, currentProduct.idProduit);
                rs = pst.executeQuery();
                while (rs.next()) {

                    return isTrueUpdate = true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(CurrentProduct.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
        System.out.println("Ato misy else ely");
        }
        return isTrueUpdate;
    }

    public boolean isNotNull(CurrentProduct currentProduct) {
        
        boolean isNotNull = false;
        if (currentProduct.idProduit.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Erreur");
                alert.setHeaderText("ERREUR : Null");
                alert.setContentText("Veuillez remplir les champs");
                alert.initStyle(StageStyle.UNDECORATED);
                alert.showAndWait();
            
            return isNotNull;
        }else{
        System.out.println("Ato izy");
        isNotNull=true;
        }
        return isNotNull;
    }

    public Object delete(CurrentProduct currentProduct){
        if(currentProductGetway.isNotSoled(currentProduct)){
            currentProductGetway.delete(currentProduct);
        }else{
            //noting
        }
        return currentProduct;
    }
    
}
