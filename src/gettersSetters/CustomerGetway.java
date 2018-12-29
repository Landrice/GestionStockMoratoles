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
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

/**
 *
 * @author Ralande
 */
public class CustomerGetway {
     SQLSyntax sql = new SQLSyntax();
    bddConnection dbCon = new bddConnection();
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    
    BddPropreties dBProperties = new BddPropreties();
    String db = dBProperties.loadPropertiesFile();

    public void save(Customer customer) {
        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("insert into "+db+".Acheteur values(?,?,?,?,?,?,?)");
            pst.setString(1, null);
            pst.setString(2, customer.customerName);
            pst.setString(3, customer.customerConNo);
            pst.setString(4, customer.customerAddress);
            pst.setString(5, customer.totalBuy);
            pst.setString(6, customer.userId);
            pst.setString(7, LocalDateTime.now().toString());
            pst.executeUpdate();
            pst.close();
            con.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucess");
            alert.setHeaderText("Sucess : Ajout avec succès");
            alert.setContentText("L'Acheteur" + "  '" + customer.customerName + "' " + "Ajouté avec succès");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void view(Customer customer) {
        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("select * from "+db+".Acheteur");
            rs = pst.executeQuery();
            while (rs.next()) {
                customer.id = rs.getString(1);
                customer.customerName = rs.getString(2);
                customer.customerConNo = rs.getString(3);
                customer.customerAddress = rs.getString(4);
                customer.totalBuy = rs.getString(5);
                customer.userId = rs.getString(6);
                customer.date = rs.getString(7);
                customer.userName = sql.getName(customer.userId, customer.userName, "Utilisateur");
                customer.customerList.addAll(new ListCustomer(customer.id, customer.customerName, customer.customerConNo, customer.customerAddress, customer.totalBuy, customer.userName, customer.date));
            }
            rs.close();
            pst.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void selectedView(Customer customer) {
        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("select * from "+db+".Acheteur where Id=?");
            pst.setString(1, customer.id);
            rs = pst.executeQuery();
            while (rs.next()) {
                customer.customerName = rs.getString(2);
                customer.customerConNo = rs.getString(3);
                customer.customerAddress = rs.getString(4);
                customer.totalBuy = rs.getString(5);
            }
            rs.close();
            pst.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchView(Customer customer) {
        con = dbCon.geConnection();
        customer.customerList.clear();
        try {
            pst = con.prepareStatement("select * from "+db+".Acheteur where NomAcheteur like ? or NumeroAcheteur like ?");
            pst.setString(1, "%" + customer.customerName + "%");
            pst.setString(2, "%" + customer.customerName + "%");
            rs = pst.executeQuery();
            while (rs.next()) {
                customer.id = rs.getString(1);
                customer.customerName = rs.getString(2);
                customer.customerConNo = rs.getString(3);
                customer.customerAddress = rs.getString(4);
                customer.totalBuy = rs.getString(5);
                customer.userId = rs.getString(6);
                customer.date = rs.getString(7);
                customer.userName = sql.getName(customer.userId, customer.userName, "Utilisateur");
                customer.customerList.addAll(new ListCustomer(customer.id, customer.customerName, customer.customerConNo, customer.customerAddress, customer.totalBuy, customer.userName, customer.date));
            }
            rs.close();
            pst.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(Customer customer) {
        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("UPDATE "+db+".Acheteur set NomAcheteur=?,NumeroAcheteur=?,AdresseAcheteur=? where Id=?");
            pst.setString(1, customer.customerName);
            pst.setString(2, customer.customerConNo);
            pst.setString(3, customer.customerAddress);
            pst.setString(4, customer.id);
            pst.executeUpdate();
            pst.close();
            con.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucess");
            alert.setHeaderText("Modification : Modification avec succès");
            alert.setContentText("L'Acheteur" + "  '" + customer.customerName + "' " + "Modifié avec succès");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Customer customer) {
        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("delete from "+db+".Acheteur where id=?");
            pst.setString(1, customer.id);
            pst.executeUpdate();
            pst.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isNotUsed(Customer customer) {
        con = dbCon.geConnection();
        boolean isNotUsed = false;
        try {
            pst = con.prepareStatement("select * from "+db+".Vente where AcheteurId=?");
            pst.setString(1, customer.id);
            rs = pst.executeQuery();
            while (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("ERREUR : Existe déja ");
                alert.setContentText("Le client Acheteur'" + rs.getString(2) + "' Existe");
                alert.initStyle(StageStyle.UNDECORATED);
                alert.showAndWait();
                return isNotUsed;
            }
            rs.close();
            pst.close();
            con.close();
            isNotUsed = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isNotUsed;
    }

}
