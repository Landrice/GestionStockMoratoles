/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gettersSetters;

import bdd.BddPropreties;
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
public class SupplyerGetway {
    bddConnection dbCon = new bddConnection();
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    
    BddPropreties dBProperties = new BddPropreties();
    String db = dBProperties.loadPropertiesFile();

    public void save(Supplyer supplyer) {
        con = dbCon.geConnection();
        if (isUniqSupplyerName(supplyer)) {
            try {
                con = dbCon.geConnection();
                pst = con.prepareCall("insert into "+db+".fournisseur values(?,?,?,?,?,?,?)");
                pst.setString(1, null);
                pst.setString(2, supplyer.supplyerName);
                pst.setString(3, supplyer.supplyerContactNumber);
                pst.setString(4, supplyer.supplyerAddress);
                pst.setString(5, supplyer.supplyerDescription);
                pst.setString(6, supplyer.creatorId);
                pst.setString(7, LocalDate.now().toString());
                pst.executeUpdate();
                con.close();
                pst.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succèss");
                alert.setHeaderText("Ajout avec succes");
                alert.setContentText("Fournisseur" + "  '" + supplyer.supplyerName + "' " + "Ajouté avec succès");
                alert.initStyle(StageStyle.UNDECORATED);
                alert.showAndWait();

            } catch (SQLException ex) {
                Logger.getLogger(Supplyer.class.getName()).log(Level.SEVERE, null, ex);
                
               System.out.println("Asa eto edy erreur"+ supplyer.supplyerName);
            }
        }

    }

    public void view(Supplyer supplyer) {
        con = dbCon.geConnection();
        try {
            pst = con.prepareCall("select * from "+db+".fournisseur");
            rs = pst.executeQuery();
            while (rs.next()) {
                supplyer.id = rs.getString(1);
                supplyer.supplyerName = rs.getString(2);
                supplyer.supplyerContactNumber = rs.getString(3);
                supplyer.supplyerAddress = rs.getString(4);
                supplyer.supplyerDescription = rs.getString(5);
                supplyer.creatorId = rs.getString(6);
                supplyer.date = rs.getString(7);

                supplyer.supplyerDetails.addAll(new ListSupplyer(supplyer.id, supplyer.supplyerName, supplyer.supplyerContactNumber, supplyer.supplyerAddress, supplyer.supplyerDescription, supplyer.date));
            }
            con.close();
            pst.close();
            rs.close();
        } catch (SQLException ex) {
//            Logger.getLogger(Supplyer.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception: "+ex);
        }
    }

    public void searchView(Supplyer supplyer) {
        supplyer.supplyerDetails.clear();
        con = dbCon.geConnection();
        try {
            con = dbCon.geConnection();
            pst = con.prepareCall("select * from "+db+".fournisseur where NomFournisseur like ? or TelFournisseur like ? ORDER BY NomFournisseur");
            pst.setString(1, "%" + supplyer.supplyerName + "%");
            pst.setString(2, "%" + supplyer.supplyerName + "%");
            rs = pst.executeQuery();
            while (rs.next()) {
                supplyer.id = rs.getString(1);
                supplyer.supplyerName = rs.getString(2);
                supplyer.supplyerContactNumber = rs.getString(3);
                supplyer.supplyerAddress = rs.getString(4);
                supplyer.supplyerDescription = rs.getString(5);
                supplyer.creatorId = rs.getString(6);
                supplyer.date = rs.getString(7);
                supplyer.supplyerDetails.addAll(new ListSupplyer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(7)));
            }
            rs.close();
            con.close();
            pst.close();

        } catch (SQLException ex) {
            Logger.getLogger(Supplyer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void selectedView(Supplyer supplyer) {
        System.out.println("nom :" + supplyer.supplyerName);
        con = dbCon.geConnection();
        try {
            con = dbCon.geConnection();
            pst = con.prepareCall("select * from "+db+".fournisseur where id=?");
            pst.setString(1, supplyer.id);
            rs = pst.executeQuery();
            while (rs.next()) {
                supplyer.id = rs.getString(1);
                supplyer.supplyerName = rs.getString(2);
                supplyer.supplyerContactNumber = rs.getString(3);
                supplyer.supplyerAddress = rs.getString(4);
                supplyer.supplyerDescription = rs.getString(5);
                supplyer.creatorId = rs.getString(6);
                supplyer.date = rs.getString(7);
            }
            rs.close();
            con.close();
            pst.close();

        } catch (SQLException ex) {
            Logger.getLogger(Supplyer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(Supplyer supplyer) {
        System.out.println("we are in update");
        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("select * from "+db+".fournisseur where Id=? and NomFournisseur=?");
            pst.setString(1, supplyer.id);
            pst.setString(2, supplyer.supplyerName);
            rs = pst.executeQuery();
            while (rs.next()) {
                System.out.println("Dans le Boucle");
                updateNow(supplyer);
                rs.close();
                pst.close();
                con.close();
                return;
            }
            rs.close();
            con.close();
            pst.close();
            if (isUniqSupplyerName(supplyer)) {
                System.out.println("Dehors du boucle");
                updateNow(supplyer);
                rs.close();
                con.close();
                pst.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(Supplyer supplyer) {
        con = dbCon.geConnection();
        try {

            con = dbCon.geConnection();
            pst = con.prepareCall("SELECT * FROM "+db+".marque WHERE FournisseurID=?");
            pst.setString(1, supplyer.id);
            rs = pst.executeQuery();
            while (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Accès ou operation refusé");
                alert.setContentText("Le fournisseur vient de la table different, Veuillez d'abord effacer cette marque! Est-il important d'effacer le fournisseut ? \nSi c'est non, vous pouvez mettre à jour le fournisseur");
                alert.initStyle(StageStyle.UNDECORATED);
                alert.showAndWait();

                return;
            }
            rs.close();
            con.close();
            pst.close();
            deleteParmanently(supplyer);
        } catch (SQLException ex) {
            Logger.getLogger(Supplyer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean isUniqSupplyerName(Supplyer supplyer) {
        con = dbCon.geConnection();
        boolean uniqSupplyer = false;
        con = dbCon.geConnection();
        try {
            pst = con.prepareCall("select NomFournisseur from "+db+".fournisseur where NomFournisseur=?");
            pst.setString(1, supplyer.supplyerName);
            rs = pst.executeQuery();
            while (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Action impossible");
                alert.setContentText("Le fournisseur" + "  '" + supplyer.supplyerName + "' " + "existe déja");
                alert.initStyle(StageStyle.UNDECORATED);
                alert.showAndWait();

                return uniqSupplyer;
            }
            rs.close();
            con.close();
            pst.close();
            uniqSupplyer = true;
        } catch (SQLException ex) {
            Logger.getLogger(Supplyer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return uniqSupplyer;
    }

    public void updateNow(Supplyer supplyer) {
        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("update "+db+".fournisseur set NomFournisseur=? , TelFournisseur=?,AdressFournisseur=? ,DescripFournisseur=? where Id=?");
            pst.setString(1, supplyer.supplyerName);
            pst.setString(2, supplyer.supplyerContactNumber);
            pst.setString(3, supplyer.supplyerAddress);
            pst.setString(4, supplyer.supplyerDescription);
            pst.setString(5, supplyer.id);
            pst.executeUpdate();
            con.close();
            pst.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText("Modification avec succès");
            alert.setContentText("Le fournisseur" + "  '" + supplyer.supplyerName + "' " + "a été modifié");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteParmanently(Supplyer supplyer) {
        con = dbCon.geConnection();
        try {
            System.out.println("and i am hear");
            con = dbCon.geConnection();
            pst = con.prepareCall("delete from "+db+".fournisseur where Id=?");
            pst.setString(1, supplyer.id);
            pst.executeUpdate();
            con.close();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(Supplyer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private boolean isUpdate(Supplyer supplyer) {
        con = dbCon.geConnection();
        boolean isUpdate = false;
        try {
            pst = con.prepareStatement("select * from "+db+".fournisseur where Id=?");
            pst.setString(1, supplyer.id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUpdate;
    }

    public boolean isNotUse(Supplyer supplyer) {
        con = dbCon.geConnection();
        boolean isNotUse = false;
        try {
            pst = con.prepareStatement("select * from "+db+".marque where FournisseurID=?");
            pst.setString(1, supplyer.id);
            rs = pst.executeQuery();
            while (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setHeaderText("AVERTISSEMENT : ");
            alert.setContentText("Cette founisseur  '" + rs.getString(2) + "' est lié avec ma marque \n Supprimer d'abord la marque");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
                return isNotUse;
            }
            rs.close();
            pst.close();
            con.close();
            isNotUse = true;
        } catch (SQLException ex) {
            Logger.getLogger(SupplyerGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isNotUse;
    }
}
