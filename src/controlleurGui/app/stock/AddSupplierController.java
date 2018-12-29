/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlleurGui.app.stock;

import gettersSetters.EffectUtility;
import gettersSetters.Supplyer;
import gettersSetters.SupplyerGetway;
import gettersSetters.userNameMedia;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class AddSupplierController implements Initializable {
    
        private String usrId;

    public String supplyerId;
    private Stage primaryStage;

    @FXML
    private AnchorPane apContent;
    @FXML
    private TextField tfSupplyerName;
    @FXML
    private TextArea taSupplyerAddress;
    @FXML
    private TextArea taSupplyerDescription;
    @FXML
     Button btnSave;
    @FXML
    private TextArea taContactNumbers;
    @FXML
     Button btnUpdate;
    @FXML
     Label lblCaption;
    @FXML
    private Button btnClose;
    private userNameMedia media;
    
    public userNameMedia getMedia() {
        return media;
    }

    public void setMedia(userNameMedia media) {
        usrId = media.getId();
        this.media = media;
    }

    Supplyer oSupplier = new Supplyer();
    SupplyerGetway supplyerGetway = new SupplyerGetway();

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnSaveOnAction(ActionEvent event) {
        if (isNotNull()) {
            oSupplier.supplyerName = tfSupplyerName.getText();
            oSupplier.supplyerContactNumber = taContactNumbers.getText();
            oSupplier.supplyerAddress = taSupplyerAddress.getText();
            oSupplier.supplyerDescription = taSupplyerDescription.getText();
            oSupplier.creatorId = usrId;
            supplyerGetway.save(oSupplier);

            clrAll();
        }
    }
    
    public boolean isNotNull() {
        boolean isNotNull;
        if (tfSupplyerName.getText().trim().isEmpty()
                || tfSupplyerName.getText().trim().isEmpty()
                || taSupplyerAddress.getText().trim().isEmpty()
                || taSupplyerAddress.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setHeaderText("ERREUR: Null");
            alert.setContentText("Veuillez remplir les champs necessaires");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
            
            isNotNull = false;

        } else {
            isNotNull = true;
        }
        return isNotNull;
    }

    private void clrAll() {
        tfSupplyerName.clear();
        taContactNumbers.clear();
        taSupplyerAddress.clear();
        taSupplyerDescription.clear();
    }

    @FXML
    private void btnUpdateOnAction(ActionEvent event) {
        if (isNotNull()) {
            oSupplier.id = supplyerId;
            oSupplier.supplyerName = tfSupplyerName.getText().trim();
            oSupplier.supplyerContactNumber = taContactNumbers.getText().trim();
            oSupplier.supplyerAddress = taSupplyerAddress.getText().trim();
            oSupplier.supplyerDescription = taSupplyerDescription.getText().trim();
            supplyerGetway.update(oSupplier);
//            takeHistoy();
//            tfSearchOnType(event);
        }
    }

    @FXML
    private void apOnMouseDragged(MouseEvent event) {
    }

    @FXML
    private void apOnMousePressed(MouseEvent event) {
    }

    @FXML
    private void btnCloseOnAction(ActionEvent event) {
        Stage stage = (Stage) btnUpdate.getScene().getWindow();
        stage.close();
    }
    
    public void showDetails() {
        oSupplier.id = supplyerId;
        supplyerGetway.selectedView(oSupplier);
        tfSupplyerName.setText(oSupplier.supplyerName);
        taContactNumbers.setText(oSupplier.supplyerContactNumber);
        taSupplyerAddress.setText(oSupplier.supplyerAddress);
        taSupplyerDescription.setText(oSupplier.supplyerDescription);
    }

    public void addSupplyerStage(Stage stage) {
        EffectUtility.makeDraggable(stage, apContent);
    }
}
