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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ralande
 */
public class SellCartBLL {
    SellCartGerway sellCartGerway = new SellCartGerway();

    bddConnection dbCon = new bddConnection();
    Connection con = dbCon.geConnection();
    PreparedStatement pst;
    ResultSet rs;
    
    BddPropreties dBProperties = new BddPropreties();
    String db = dBProperties.loadPropertiesFile();

    public void sell(SellCart sellCart) {

        updateCurrentQuentity(sellCart);
        sellCartGerway.save(sellCart);

    }

    public void updateCurrentQuentity(SellCart sellCart) {
        System.out.println("In Update");
        int oQ = Integer.parseInt(sellCart.oldQuentity);
        int nQ = Integer.parseInt(sellCart.quantity);
        int uQ = (oQ - nQ);
        System.out.println("NOW QUENTITY IS: " + uQ);
        String updatedQuentity = String.valueOf(uQ);
        try {
            System.out.println("Mise à jour en cours");
            pst = con.prepareStatement("update "+db+".produits set Quantite=? where Id=?");
            pst.setString(1, updatedQuentity);
            pst.setString(2, sellCart.Id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SellCartBLL.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Update Complate");
    }

}
