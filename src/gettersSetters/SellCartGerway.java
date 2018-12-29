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

/**
 *
 * @author Ralande
 */
public class SellCartGerway {
    bddConnection dbCon = new bddConnection();
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    
    BddPropreties dBProperties = new BddPropreties();
    String db = dBProperties.loadPropertiesFile();


    public void save(SellCart sellCart){
        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("insert into "+db+".vente values(?,?,?,?,?,?,?,?)");
            pst.setString(1, null);
            pst.setString(2, sellCart.sellID);
            pst.setString(3, sellCart.customerID);
            pst.setString(4, sellCart.productID);
            pst.setString(5, sellCart.quantity);
            pst.setString(6, sellCart.totalPrice);
            pst.setString(7, sellCart.warrentyVoidDate);
   //         pst.setString(10, sellCart.sellerID);
            pst.setString(8, LocalDateTime.now().toString());
            pst.executeUpdate();
            pst.close();
            con.close();
            
//            Dialogs.create().title(null).masthead("Soled").message("Soled Sucessfuly").styleClass(Dialog.STYLE_CLASS_UNDECORATED).showInformation();
        } catch (SQLException ex) {
            Logger.getLogger(SellCartGerway.class.getName()).log(Level.SEVERE, null, ex);
            Alert a=new Alert(Alert.AlertType.ERROR);
            a.setTitle("Erreur");
            a.setHeaderText("Erreur");
            a.setContentText(ex.getMessage());
            a.showAndWait();
        }
    }

    public void view(SellCart sellCart){
        con = dbCon.geConnection();
        SQLSyntax sql = new SQLSyntax();
        try {
            pst = con.prepareStatement("select * from "+db+".Vente");
            rs = pst.executeQuery();
            while (rs.next()){
                sellCart.Id = rs.getString(1);
                sellCart.sellID = rs.getString(2);
                sellCart.customerID = rs.getString(3);
                sellCart.productID = rs.getString(4);
                sellCart.quantity = rs.getString(5);
                sellCart.totalPrice = rs.getString(6);
                sellCart.warrentyVoidDate = rs.getString(7);
                sellCart.sellDate = rs.getString(8);
                sellCart.givenProductID = sql.getName(sellCart.productID, sellCart.givenProductID, "produits");
                sellCart.productname = sql.getNameR(sellCart.productID, sellCart.givenProductID, "produits");
                sellCart.sellerName = sql.getName(sellCart.sellerID, sellCart.sellerName, "vente");
                sellCart.customerName = sql.getName(sellCart.customerID, sellCart.customerName, "acheteur");
                
                        
                        System.out.println("Numero 01 "+sellCart.givenProductID);
                System.out.println("Numero 02 "+sellCart.sellerName);
                System.out.println("Numero 03 "+sellCart.customerName);
                
                sellCart.soldList.addAll(new ListSold(sellCart.Id,sellCart.sellID ,sellCart.productID, sellCart.givenProductID, sellCart.customerID, sellCart.customerName, sellCart.pursesPrice, sellCart.sellPrice, null, sellCart.quantity, sellCart.totalPrice, sellCart.pursrsDate, sellCart.warrentyVoidDate, sellCart.sellerID, sellCart.sellerName, sellCart.sellDate, sellCart.productname));
            }pst.close();
            con.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SellCartGerway.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void firstTenView(SellCart sellCart){
        con = dbCon.geConnection();
        SQLSyntax sql = new SQLSyntax();
        try {
            pst = con.prepareStatement("select * from "+db+".Vente limit 0,15");
            rs = pst.executeQuery();
            while (rs.next()){
                sellCart.Id = rs.getString(1);
                sellCart.sellID = rs.getString(2);
                sellCart.customerID = rs.getString(3);
                sellCart.productID = rs.getString(4);
                sellCart.quantity = rs.getString(5);
                sellCart.totalPrice = rs.getString(6);
                sellCart.warrentyVoidDate = rs.getString(7);
                sellCart.sellDate = rs.getString(8);
                sellCart.givenProductID = sql.getName(sellCart.productID, sellCart.givenProductID, "Produits");
                sellCart.productname = sql.getNameR(sellCart.productID, sellCart.givenProductID, "produits");
                sellCart.sellerName = sql.getName(sellCart.sellerID, sellCart.sellerName, "Utilisateur");
                sellCart.customerName = sql.getName(sellCart.customerID, sellCart.customerName, "Acheteur");
                
                sellCart.soldList.addAll(new ListSold(sellCart.Id,sellCart.sellID ,sellCart.productID, sellCart.givenProductID, sellCart.customerID, sellCart.customerName, sellCart.pursesPrice, sellCart.sellPrice, null, sellCart.quantity, sellCart.totalPrice, sellCart.pursrsDate, sellCart.warrentyVoidDate, sellCart.sellerID, sellCart.sellerName, sellCart.sellDate, sellCart.productname));
            }pst.close();
            con.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SellCartGerway.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void searchView(SellCart sellCart){
        con = dbCon.geConnection();
        sellCart.carts.clear();
        SQLSyntax sql = new SQLSyntax();
        try {
            pst = con.prepareStatement("select * from "+db+".Vente where VenteId like ?");
            pst.setString(1, "%" + sellCart.sellID + "%");
            rs = pst.executeQuery();
            while (rs.next()){
                sellCart.Id = rs.getString(1);
                sellCart.sellID = rs.getString(2);
                sellCart.customerID = rs.getString(3);
                sellCart.productID = rs.getString(4);
                sellCart.quantity = rs.getString(5);
                sellCart.totalPrice = rs.getString(6);
                sellCart.warrentyVoidDate = rs.getString(7);
                sellCart.sellDate = rs.getString(8);
                sellCart.givenProductID = sql.getName(sellCart.productID, sellCart.givenProductID, "Produits");
                sellCart.productname = sql.getNameR(sellCart.productID, sellCart.givenProductID, "produits");
                sellCart.sellerName = sql.getName(sellCart.sellerID, sellCart.sellerName, "Utilisateur");
                sellCart.customerName = sql.getName(sellCart.customerID, sellCart.customerName, "Acheteur");
                
                sellCart.soldList.addAll(new ListSold(sellCart.Id,sellCart.sellID ,sellCart.productID, sellCart.givenProductID, sellCart.customerID, sellCart.customerName, sellCart.pursesPrice, sellCart.sellPrice, null, sellCart.quantity, sellCart.totalPrice, sellCart.pursrsDate, sellCart.warrentyVoidDate, sellCart.sellerID, sellCart.sellerName, sellCart.sellDate, sellCart.productname));
            }pst.close();
            con.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SellCartGerway.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
