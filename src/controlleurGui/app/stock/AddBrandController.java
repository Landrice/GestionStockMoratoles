/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlleurGui.app.stock;

import bdd.BddPropreties;
import bdd.bddConnection;
import gettersSetters.BrandBLL;
import gettersSetters.Brands;
import gettersSetters.BrandsGetway;
import gettersSetters.userNameMedia;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class AddBrandController implements Initializable {
    
    public Button btnAddSupplyer;
    private userNameMedia media;

    Brands brands = new Brands();
    BrandsGetway brandsGetway = new BrandsGetway();
    BrandBLL brandBLL = new BrandBLL();

    public String brandId;
    private String usrId;
    private String supplyerName;
    private String supplyerId;

    bddConnection dbCon = new bddConnection();
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    
    BddPropreties dBProperties = new BddPropreties();
    String db = dBProperties.loadPropertiesFile();

    @FXML
    private ComboBox<String> cbSupplyer;
    @FXML
    private TextField tfBrandName;
    @FXML
    private TextArea taDiscription;
    @FXML
    public Button btnAddBrand;
    @FXML
     Button btnUpdate;
   // @FXML
   // private Button btnAddSupplyer;
    @FXML
     Label lblHeader;
    @FXML
    private Button btnClose;
    
    public userNameMedia getMedia() {
        return media;
    }

    public void setMedia(userNameMedia media) {
        usrId = media.getId();
        this.media = media;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void cbSupplyerOnClick(MouseEvent event) {
         cbSupplyer.getItems().clear();
        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("select * from "+db+".fournisseur order by NomFournisseur");
            rs = pst.executeQuery();
            while (rs.next()) {
                supplyerName = rs.getString(2);

                cbSupplyer.getItems().add(supplyerName);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddBrandController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void cbSupplyerOnAction(ActionEvent event) {
    }

    @FXML
    private void btnAddBrandOnAction(ActionEvent event) {
        System.out.println(usrId);
        if (isNotNull()) {
            brands.creatorId = usrId;
            brands.brandName = tfBrandName.getText();
            brands.brandComment = taDiscription.getText();
            brands.supplyerName = cbSupplyer.getSelectionModel().getSelectedItem();
            brandBLL.save(brands);
        }

    }

    @FXML
    private void btnUpdateOnAction(ActionEvent event) {
        System.out.println();
        if (isNotNull()) {
            brands.id = brandId;
            if (!cbSupplyer.getSelectionModel().isEmpty()) {
                brands.supplyerName = cbSupplyer.getSelectionModel().getSelectedItem();
            } else if (!cbSupplyer.getPromptText().isEmpty()) {
                brands.supplyerName = cbSupplyer.getPromptText();
            }

            brands.brandName = tfBrandName.getText().trim();
            brands.brandComment = taDiscription.getText();
            brandBLL.update(brands);
        }
    }

    public void btnAddSupplyerOnAction(ActionEvent event) {
                AddSupplierController addSupplyerController = new AddSupplierController();
        userNameMedia media = new userNameMedia();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/gui/app/stock/AddSupplier.fxml"));
        try {
            fxmlLoader.load();
            Parent parent = fxmlLoader.getRoot();
            Scene scene = new Scene(parent);
            scene.setFill(new Color(0, 0, 0, 0));
            AddSupplierController supplyerController = fxmlLoader.getController();
            media.setId(usrId);
            supplyerController.setMedia(media);
            supplyerController.lblCaption.setText("Ajouter");
            supplyerController.btnUpdate.setVisible(false);
            Stage nStage = new Stage();
            supplyerController.addSupplyerStage(nStage);
            nStage.setScene(scene);
            nStage.initModality(Modality.APPLICATION_MODAL);
            nStage.initStyle(StageStyle.TRANSPARENT);
            nStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnCloseOnAction(ActionEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
    
    public boolean isNotNull() {
        System.out.println(cbSupplyer.getPromptText());
//        System.out.println(cbSupplyer.getSelectionModel().getSelectedItem().isEmpty());
        System.out.println(tfBrandName.getText());
        boolean isNotNull;
        if (tfBrandName.getText().trim().isEmpty()
                || cbSupplyer.getSelectionModel().isEmpty()
                && cbSupplyer.getPromptText().isEmpty()) {
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("erreur");
            alert.setHeaderText("Champ vide");
            alert.setContentText("Veuillez remplir les champs");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
            
            isNotNull = false;

        } else {
            isNotNull = true;
        }
        return isNotNull;
    }

    public void showDetails() {
        brands.id = brandId;
        brandsGetway.selectedView(brands);
        tfBrandName.setText(brands.brandName);
        taDiscription.setText(brands.brandComment);
        cbSupplyer.setPromptText(brands.supplyerName);
    }
}
