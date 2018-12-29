/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlleurGui.home;

import bdd.BddPropreties;
import bdd.bddConnection;
import gettersSetters.CurrentProductGetway;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class HomeController implements Initializable {

    @FXML
    private Label lblOrgName;
    @FXML
    private Text txtOrgAddress;
    @FXML
    private Text txtorgPhoneNumber;
    @FXML
    private Label lblTotalSupplyer;
    @FXML
    private Label lblTotalEmployee;

    bddConnection dbCon = new bddConnection();
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    BddPropreties dBProperties = new BddPropreties();
    String db = dBProperties.loadPropertiesFile();
    @FXML
    private Label lblTotalItem;
    @FXML
    private Label lblTotalToles;
    @FXML
    private Label lblTotalPeintures;
    @FXML
    private Label lblTotalAutres;
    @FXML
    private ImageView imageToles;
    @FXML
    private ImageView imagepeinture;
    @FXML
    private ImageView imageautre;
    
    Image imgtoles = new Image("/image/toles.jpg");
    Image imgpeinture = new Image("/image/peintures.jpg");
    Image imgautre = new Image("/image/autres.jpg");
    Image imgSmall = new Image("/image/gstock.jpg");
    @FXML
    private ImageView smallImg;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        totalCount();
        totalCountCategorieProduits(lblTotalToles, "Tôles");
        totalCountCategorieProduits(lblTotalPeintures, "Peinture");
        totalCountCategorieProduits(lblTotalAutres, "Autres");
        smallImg.setImage(imgSmall);
        
        imageToles.setImage(imgtoles);
        imagepeinture.setImage(imgpeinture);
        imageautre.setImage(imgautre);
        
    }
    // select count(*) from produits, categorietpa where categorietpa.categorieTPA='Autres' and categorietpa.IdProduit=produits.IdProduit;

    public void totalCount() {
        con = dbCon.geConnection();
        try {
            /*pst = con.prepareStatement("select count(*) from "+db+".vente");
            rs = pst.executeQuery();
            while (rs.next()) {
                lblTotalSell.setText(rs.getString(1));
            }*/
            pst = con.prepareStatement("select count(*) from " + db + ".acheteur");
            rs = pst.executeQuery();
            while (rs.next()) {
                lblTotalSupplyer.setText(rs.getString(1));
            }
            pst = con.prepareStatement("select count(*) from " + db + ".produits");
            rs = pst.executeQuery();
            while (rs.next()) {
                lblTotalItem.setText(rs.getString(1));
            }
            /*pst = con.prepareStatement("select sum(TotalPrice) from "+db+".Sell");
            rs = pst.executeQuery();
            while(rs.next()){
                lblSellValue.setText(rs.getString(1));
            }*/
            pst = con.prepareStatement("select count(*) from " + db + ".utilisateur");
            rs = pst.executeQuery();
            while (rs.next()) {
                lblTotalEmployee.setText(rs.getString(1));
            }
            /* pst =con.prepareStatement("select * from "+db+".Organize where Id=1");
            rs = pst.executeQuery();
            while(rs.next()){
                lblOrgName.setText(rs.getString(2));
                txtOrgAddress.setText(rs.getString(5));
                txtorgPhoneNumber.setText(rs.getString(4));
            }*/
            con.close();
            pst.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CurrentProductGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void totalCountCategorieProduits(Label label, String s) {
        con = dbCon.geConnection();

        try {
            pst = con.prepareStatement("select count(*) from "+db+".produits, "+db+".categorietpa where categorietpa.categorieTPA='"+s+"' and categorietpa.IdProduit=produits.IdProduit");
            rs = pst.executeQuery();
            while (rs.next()) {
                label.setText(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
