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
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

/**
 *
 * @author Admin
 */
public class BrandsGetway {
     SQLSyntax sql = new SQLSyntax();

    bddConnection dbCon = new bddConnection();
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    
    BddPropreties dBProperties = new BddPropreties();
    String db = dBProperties.loadPropertiesFile();

    public void save(Brands brands) {
        con = dbCon.geConnection();
        brands.supplyrId = sql.getIdNo(brands.supplyerName, brands.supplyrId, "fournisseur", "NomFournisseur");

        try {
            pst = con.prepareStatement("insert into "+db+".marque values(?,?,?,?,?,?)");
            pst.setString(1, null);
            pst.setString(2, brands.brandName);
            pst.setString(3, brands.brandComment);
            pst.setString(4, brands.supplyrId);
            pst.setString(5, brands.creatorId);
            pst.setString(6, LocalDate.now().toString());
            pst.executeUpdate();
            con.close();
            pst.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succèss");
            alert.setHeaderText("Ajout avec succès");
            alert.setContentText("la marque" + "  '" + brands.brandName + "' " + "a été ajouté avec succès");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void view(Brands brands) {
        con = dbCon.geConnection();

        try {
            pst = con.prepareCall("select * from "+db+".marque");
            rs = pst.executeQuery();
            while (rs.next()) {
                brands.id = rs.getString(1);
                brands.brandName = rs.getString(2);
                brands.brandComment = rs.getString(3);
                brands.supplyrId = rs.getString(4);
                brands.creatorId = rs.getString(5);
                brands.date = rs.getString(6);
                brands.supplyerName = sql.getName(brands.supplyrId, brands.supplyerName, "fournisseur");
                brands.creatorName = sql.getName(brands.creatorId, brands.creatorName, "utilisateur");
                brands.brandDitails.addAll(new ListBrands(brands.id, brands.brandName, brands.brandComment, brands.supplyerName, brands.creatorName, brands.date));
            }
            con.close();
            pst.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Supplyer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void selectedView(Brands brands) {
        con = dbCon.geConnection();

        try {
            con = dbCon.geConnection();
            pst = con.prepareCall("select * from "+db+".marque where id=?");
            pst.setString(1, brands.id);
            rs = pst.executeQuery();
            while (rs.next()) {
                brands.id = rs.getString(1);
                brands.brandName = rs.getString(2);
                brands.brandComment = rs.getString(3);
                brands.supplyrId = rs.getString(4);
                brands.supplyerName = sql.getName(brands.supplyrId, brands.supplyerName, "fournisseur");
            }
            con.close();
            pst.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(Supplyer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void searchView(Brands brands) {
        con = dbCon.geConnection();

        brands.brandDitails.clear();
        System.out.println("name :" + brands.brandName);

        try {
            con = dbCon.geConnection();
            pst = con.prepareCall("select * from "+db+".marque where NomMarque like ? ORDER BY NomMarque");
            System.out.println("Brand name in Brand Object");
            pst.setString(1, "%" + brands.brandName + "%");

            rs = pst.executeQuery();
            while (rs.next()) {
                brands.id = rs.getString(1);
                brands.brandName = rs.getString(2);
                brands.brandComment = rs.getString(3);
                brands.supplyrId = rs.getString(4);
                brands.creatorId = rs.getString(5);
                brands.date = rs.getString(6);
                brands.supplyerName = sql.getName(brands.supplyrId, brands.supplyerName, "fournisseur");
                brands.creatorName = sql.getName(brands.creatorId, brands.creatorName, "utilisateur");
                brands.brandDitails.addAll(new ListBrands(brands.id, brands.brandName, brands.brandComment, brands.supplyerName, brands.creatorName, brands.date));
            }
            con.close();
            pst.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(Supplyer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(Brands brands) {
        con = dbCon.geConnection();

        try {
            pst = con.prepareStatement("delete from "+db+".marque where Id=?");
            pst.setString(1, brands.id);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Brands brands) {
        con = dbCon.geConnection();

        try {
            pst = con.prepareStatement("update "+db+".marque set NomMarque=? , Description=?,FournisseurID=? where Id=?");
            pst.setString(1, brands.brandName);
            pst.setString(2, brands.brandComment);
            pst.setString(3, brands.supplyrId);
            pst.setString(4, brands.id);
            pst.executeUpdate();
            con.close();
            pst.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucèss");
            alert.setHeaderText("Modification avec succès ");
            alert.setContentText("Modification" + "  '" + brands.brandName + "' " + "Ajouté avec succès");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean isNotUsed(Brands brands){
        con = dbCon.geConnection();
        boolean inNotUse = false;
        try {
            pst = con.prepareStatement("select * from "+db+".categorie where MarqueId=?");
            pst.setString(1, brands.id);
            rs = pst.executeQuery();
            while(rs.next()){
               Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("ERREUR: valeur existante ");
            alert.setContentText("La marque" + "  '" + brands.brandName + "' " + "existe déja");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
                return inNotUse;
            }rs.close();
            pst.close();
            con.close();
            inNotUse = true;
        } catch (SQLException ex) {
            Logger.getLogger(BrandsGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return inNotUse;
    }

}
