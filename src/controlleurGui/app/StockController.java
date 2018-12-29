/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlleurGui.app;

import bdd.BddPropreties;
import bdd.bddConnection;
import com.jfoenix.controls.JFXToggleButton;
import controlleurGui.app.stock.CurrentStoreController;
import controlleurGui.app.stock.ViewBrandController;
import controlleurGui.app.stock.ViewCatagoryController;
import controlleurGui.app.stock.ViewSupplierController;
import gettersSetters.userNameMedia;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class StockController implements Initializable {

    @FXML
    public BorderPane bpStore;
    @FXML
    private AnchorPane acHeadStore;
    @FXML
    private JFXToggleButton btnStock;
    @FXML
    private JFXToggleButton btnSupplyer;
    @FXML
    private JFXToggleButton btnBrands;
    @FXML
    private JFXToggleButton btnCatagory;
    @FXML
    private Label lblHeader;
    @FXML
    private StackPane spMainContent;
    @FXML
    private ToggleGroup btnGup;
    
    private String usrId;

    private userNameMedia userId;
    
    BddPropreties dBProperties = new BddPropreties();
    String db = dBProperties.loadPropertiesFile();

    public userNameMedia getUserId() {
        return userId;
    }

    public void setUserId(userNameMedia userId) {
        usrId = userId.getId();
        this.userId = userId;
    }
    
    bddConnection dbCon = new bddConnection();
    Connection con = dbCon.geConnection();
    PreparedStatement pst;
    ResultSet rs;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnStock.setSelected(true);
        
    }    

    @FXML
    public void btnStockOnAction(ActionEvent event) throws IOException {
        lblHeader.setText("Produits");
        userNameMedia media = new userNameMedia();
        FXMLLoader fXMLLoader = new FXMLLoader();
        fXMLLoader.load(getClass().getResource("/gui/app/stock/CurrentStore.fxml").openStream());
        media.setId(usrId);
        CurrentStoreController currentStoreController = fXMLLoader.getController();
        currentStoreController.setMedia(userId);
        currentStoreController.viewDetails();
        currentStoreController.apCombobox.getStylesheets().add("/css/StoreCombobox.css");
        currentStoreController.settingPermission();
        StackPane acPane = fXMLLoader.getRoot();
        spMainContent.getChildren().clear();
        spMainContent.getChildren().add(acPane);
    }

    @FXML
    private void btnSupplyerOnAction(ActionEvent event) throws IOException {
         lblHeader.setText("Fournisseur");
        ViewSupplierController vsc = new ViewSupplierController();
        userNameMedia media = new userNameMedia();
        FXMLLoader fXMLLoader = new FXMLLoader();
        fXMLLoader.load(getClass().getResource("/gui/app/stock/ViewSupplier.fxml").openStream());
        media.setId(usrId);
        ViewSupplierController viewSupplyerController = fXMLLoader.getController();
        viewSupplyerController.setMedia(userId);
        viewSupplyerController.showDetails();
        AnchorPane acPane = fXMLLoader.getRoot();

        spMainContent.getChildren().clear();
        spMainContent.getChildren().add(acPane);
    }

    @FXML
    private void btnBrandsOnAction(ActionEvent event) throws IOException {
                lblHeader.setText("Marque");
        ViewBrandController vbc = new ViewBrandController();
        userNameMedia media = new userNameMedia();
        FXMLLoader fXMLLoader = new FXMLLoader();
        fXMLLoader.load(getClass().getResource("/gui/app/stock/ViewBrand.fxml").openStream());
        media.setId(usrId);
        ViewBrandController viewBrandController = fXMLLoader.getController();
        viewBrandController.setMedia(userId);
        viewBrandController.showDetails();
        AnchorPane acPane = fXMLLoader.getRoot();

        spMainContent.getChildren().clear();
        spMainContent.getChildren().add(acPane);
    }

    @FXML
    private void btnCatagoryOnAction(ActionEvent event) throws IOException {
                lblHeader.setText("Categories");
        ViewCatagoryController vcc = new ViewCatagoryController();
        userNameMedia media = new userNameMedia();
        FXMLLoader fXMLLoader = new FXMLLoader();
        fXMLLoader.load(getClass().getResource("/gui/app/stock/ViewCatagory.fxml").openStream());
        media.setId(usrId);
        ViewCatagoryController viewCatagoryController = fXMLLoader.getController();
        viewCatagoryController.setMedia(userId);
        viewCatagoryController.showDetails();
        AnchorPane acPane = fXMLLoader.getRoot();

        spMainContent.getChildren().clear();
        spMainContent.getChildren().add(acPane);
    }
    
        public void settingPermission(){
        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("select * from "+db+".droitutilisateur where id=?");
            pst.setString(1, usrId);
            rs = pst.executeQuery();
            while(rs.next()){
                if(rs.getInt(2)==0 && rs.getInt(9) == 0){
                    btnSupplyer.setDisable(true);
                }if(rs.getInt(4) == 0 && rs.getInt(10) == 0){
                    btnBrands.setDisable(true);
                }if(rs.getInt(5) == 0 && rs.getInt(11) == 0){
                    btnCatagory.setDisable(true);
                }/*if(rs.getInt(6) == 0 && rs.getInt(12) == 0){
                    btnUnit.setDisable(true);
                }if(rs.getInt(14) == 0){
                    btnRma.setDisable(true);
                }*/
                
                else{
                    
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}
