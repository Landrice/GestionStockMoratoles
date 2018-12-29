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
public class CatagoryGetway {
    SQLSyntax sql = new SQLSyntax();
    bddConnection dbCon = new bddConnection();
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    
    BddPropreties dBProperties = new BddPropreties();
    String db = dBProperties.loadPropertiesFile();

    public void save(Catagory catagory) {
        con = dbCon.geConnection();
        catagory.brandName = sql.getIdNo(catagory.brandName, catagory.brandId, "marque", "NomMarque");
        catagory.brandId = sql.getIdNo(catagory.brandName, catagory.brandId, "marque", "NomMarque");
        catagory.supplyerId = sql.getIdNo(catagory.supplyerName, catagory.supplyerId, "fournisseur", "NomFournisseur");
        try {
            pst = con.prepareStatement("insert into "+db+".categorie values(?,?,?,?,?,?,?)");
            pst.setString(1, null);
            pst.setString(2, catagory.catagoryName);
            pst.setString(3, catagory.catagoryDescription);
            pst.setString(4, catagory.brandId);
            pst.setString(5, catagory.supplyerId);
            pst.setString(6, catagory.creatorId);
            pst.setString(7, LocalDate.now().toString());
            pst.executeUpdate();
            pst.close();
            con.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucess");
            alert.setHeaderText("Ajout avec succès");
            alert.setContentText("categorie" + "  '" + catagory.catagoryName + "' " + "ajouté avec suucès");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void view(Catagory catagory) {
        con = dbCon.geConnection();
        try {
            con = dbCon.geConnection();
            pst = con.prepareCall("select * from "+db+".categorie");
            rs = pst.executeQuery();
            while (rs.next()) {
                catagory.id = rs.getString(1);
                catagory.catagoryName = rs.getString(2);
                catagory.catagoryDescription = rs.getString(3);
                catagory.brandId = rs.getString(4);
                catagory.supplyerId = rs.getString(5);
                catagory.creatorId = rs.getString(6);
                catagory.date = rs.getString(7);
                catagory.brandName = sql.getName(catagory.brandId, catagory.brandName, "marque");
                catagory.supplyerName = sql.getName(catagory.supplyerId, catagory.supplyerName, "fournisseur");
                catagory.creatorName = sql.getName(catagory.creatorId, catagory.catagoryName, "utilisateur");
                catagory.catagoryDetails.addAll(new ListCatagory(catagory.id, catagory.catagoryName, catagory.catagoryDescription, catagory.brandName, catagory.supplyerName, catagory.creatorName, catagory.date));
            }
            pst.close();
            con.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Supplyer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void selectedView(Catagory catagory) {
        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("select * from "+db+".categorie where Id=?");
            pst.setString(1, catagory.id);
            rs = pst.executeQuery();
            while (rs.next()) {
                catagory.id = rs.getString(1);
                catagory.catagoryName = rs.getString(2);
                catagory.catagoryDescription = rs.getString(3);
                catagory.brandId = rs.getString(4);
                catagory.supplyerId = rs.getString(5);
                catagory.brandName = sql.getName(catagory.brandId, catagory.brandName, "marque");
                catagory.supplyerName = sql.getName(catagory.supplyerId, catagory.supplyerName, "fournisseur");
            }
            pst.close();
            con.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void brandView(Catagory catagory) {
        con = dbCon.geConnection();

        try {
            pst = con.prepareStatement("select * from "+db+".marque where FournisseurID=?");
            pst.setString(1, catagory.supplyerId);
            rs = pst.executeQuery();
            while (rs.next()) {
                catagory.brandName = rs.getString(2);
            }
            pst.close();
            con.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void searchView(Catagory catagory) {
        con = dbCon.geConnection();
        catagory.catagoryDetails.clear();

        try {
            pst = con.prepareStatement("select * from "+db+".categorie where NomCategory like ? ORDER BY NomCategory");

            pst.setString(1, "%" + catagory.catagoryName + "%");
            rs = pst.executeQuery();
            while (rs.next()) {
                catagory.id = rs.getString(1);
                catagory.catagoryName = rs.getString(2);
                catagory.catagoryDescription = rs.getString(3);
                catagory.brandId = rs.getString(4);
                catagory.supplyerId = rs.getString(5);
                catagory.creatorId = rs.getString(6);
                catagory.date = rs.getString(7);

                catagory.brandName = sql.getName(catagory.brandId, catagory.brandName, "marque");
                catagory.supplyerName = sql.getName(catagory.supplyerId, catagory.supplyerName, "fournisseur");
                catagory.creatorName = sql.getName(catagory.creatorId, catagory.catagoryName, "utilisateur");

                catagory.catagoryDetails.addAll(new ListCatagory(catagory.id, catagory.catagoryName, catagory.catagoryDescription, catagory.brandName, catagory.supplyerName, catagory.creatorName, catagory.date));
            }
            pst.close();
            con.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Catagory catagory) {
        con = dbCon.geConnection();
        catagory.brandId = sql.getIdNo(catagory.brandName, catagory.brandId, "marque", "NomMarque");
        catagory.supplyerId = sql.getIdNo(catagory.supplyerName, catagory.supplyerId, "fournisseur", "NomFournisseur");

        try {
            pst = con.prepareStatement("update "+db+".categorie set NomCategory=? , DescriptionCategory=?,MarqueId=?,FournisseurId=? where Id=?");
            pst.setString(1, catagory.catagoryName);
            pst.setString(2, catagory.catagoryDescription);
            pst.setString(3, catagory.brandId);
            pst.setString(4, catagory.supplyerId);
            pst.setString(5, catagory.id);
            pst.executeUpdate();
            pst.close();
            con.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucess");
            alert.setHeaderText("Modification avec succès");
            alert.setContentText("Categorie" + "  '" + catagory.catagoryName + "' " + "modifié avec succès");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();

        } catch (SQLException e) {
            System.out.println("Erreur sur "+e);
        }

    }

    public void delete(Catagory catagory) {
        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("delete from "+db+".categorie where Id=?");
            pst.setString(1, catagory.id);
            pst.executeUpdate();
            pst.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isNotUse(Catagory catagory) {
        con = dbCon.geConnection();
        boolean isNotUse = false;
        try {
            pst = con.prepareCall("select * from "+db+".produits where CategorieId=?");
            pst.setString(1, catagory.id);
            rs = pst.executeQuery();
            while (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("ERREUR: existant ");
                alert.setContentText("La categorie" + "  '" + rs.getString(2) + "' " + "existe déja");
                alert.initStyle(StageStyle.UNDECORATED);
                alert.showAndWait();
                return isNotUse;
            }
            pst.close();
            rs.close();
            con.close();
            isNotUse = true;
        } catch (SQLException ex) {
            Logger.getLogger(CatagoryGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isNotUse;
    }
}
