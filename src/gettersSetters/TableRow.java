/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gettersSetters;

import bdd.BddPropreties;
import bdd.bddConnection;
import java.net.URL;
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
public class TableRow extends javafx.scene.control.TableRow<ListProduct> {

    BddPropreties dBProperties = new BddPropreties();
    String db = dBProperties.loadPropertiesFile();
    bddConnection dbCon = new bddConnection();
    Connection con = dbCon.geConnection();
    PreparedStatement pst;
    ResultSet rs;

    public TableRow() {
        super();
        getStyleClass().add("download-table-row");
       // Chargement de la feuille de style.
         final URL cssURL = getClass().getResource("/css/MainStyle.css");
        getStylesheets().add(cssURL.toExternalForm());

    }
    
    @Override
    protected void updateItem(ListProduct item, boolean empty) {
        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates
        System.out.println("here");
if(!empty){
     super.setStyle("-fx-background-color:white;");
        try {

            pst = con.prepareStatement("SELECT * FROM " + db + ".alert, " + db + ".produits WHERE " + db + ".alert.IdProduit = " + db + ".produits.IdProduit");
            rs = pst.executeQuery();
            String idproduit=item.getId();
            int idproduitin=Integer.parseInt(idproduit);
           // String quantite=item.getQuantite();
           // int qtt=Integer.parseInt(quantite);
          
            while (rs.next()) {
                //System.out.println("non ");
                String idproduitBase=rs.getString(4);
                    int idproduitBasein=Integer.parseInt(idproduitBase);
                    if (idproduitin==idproduitBasein) {
                       if (Integer.parseInt(rs.getString(7)) < Integer.parseInt(rs.getString(3)) ) {
                    System.out.println("alert");
                    super.setStyle("-fx-control-inner-background: palegreen;");
                   // super.setStyle("-fx-background-color: red;");
                } else  {
                    System.out.println("non ");
           //super.setStyle("-fx-background-color:white;");
           super.setStyle(" -fx-control-inner-background: palevioletred;");

                } 
                    }
                    else{
                     super.setStyle("-fx-background-color:white;");
                    super.setStyle(" -fx-control-inner-background: gray;");
                    }
            }
        } catch (NumberFormatException | SQLException e) {
            Logger.getLogger(TableRow.class.getName()).log(Level.SEVERE, null, e);
        }
    
   //  super.setStyle("-fx-background-color:green;");
      
     
  
}
    
    
    
    }
    }
    
