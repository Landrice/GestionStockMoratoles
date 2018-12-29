/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlleurGui.app.stock;

import bdd.SQLSyntax;
import gettersSetters.History;
import gettersSetters.ListSupplyer;
import gettersSetters.Supplyer;
import gettersSetters.SupplyerBLL;
import gettersSetters.SupplyerGetway;
import gettersSetters.userNameMedia;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class ViewSupplierController implements Initializable {
    
    SQLSyntax sql = new SQLSyntax();
    Supplyer supplyer = new Supplyer();
    SupplyerGetway supplyerGetway = new SupplyerGetway();
    SupplyerBLL supplyerBLL = new SupplyerBLL();
    History history = new History();

    private String usrId;
    private String creatorName;
    private String creatorId;
    private String supplyerId;
    private String userName;

    private userNameMedia media;

    @FXML
    private AnchorPane acContent;
    @FXML
    private TextField tfSearch;
    @FXML
    private Button btnRefresh;
    @FXML
    private Button btnAdditems;
    @FXML
    private Button btnUpdate;
    @FXML
    private TableView<ListSupplyer> tblSupplyer;
    @FXML
    private MenuItem mbSearch;
    @FXML
    private TableColumn<Object, Object> clmSUpplyerId;
    @FXML
    private TableColumn<Object, Object> clmSupplyerName;
    @FXML
    private TableColumn<Object, Object> clmSupplyerPhoneNumber;
    @FXML
    private TableColumn<Object, Object> clmSupplyerAddress;
    @FXML
    private TableColumn<Object, Object> clmSupplyerJoining;
    @FXML
    private TableColumn<Object, Object> clmSupplyerDescription;
    
    private final static int dataSize = 10_023;
    private final static int rowsPerPage = 1000;
    
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
    public void tfSearchOnType(Event event) {
        supplyer.supplyerDetails.removeAll();
        supplyer.supplyerName = tfSearch.getText().trim();

        tblSupplyer.setItems(supplyer.supplyerDetails);
        clmSUpplyerId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        clmSupplyerName.setCellValueFactory(new PropertyValueFactory<>("NomFournisseur"));
        clmSupplyerPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("TelFournisseur"));
        clmSupplyerAddress.setCellValueFactory(new PropertyValueFactory<>("AdressFournisseur"));
        clmSupplyerDescription.setCellValueFactory(new PropertyValueFactory<>("DescripFournisseur"));
        clmSupplyerJoining.setCellValueFactory(new PropertyValueFactory<>("Date"));
        supplyerGetway.searchView(supplyer);
    }

    @FXML
    private void btnRefreshOnAction(ActionEvent event) {
                supplyer.supplyerDetails.clear();
        showDetails();
    }

    @FXML
    private void btnAdditemsOnAction(ActionEvent event) {
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
        tfSearchOnType(event);
    }

    @FXML
    private void btnUpdateOnAction(ActionEvent event) {
         if(tblSupplyer.getSelectionModel().getSelectedItem() != null){
            viewDetails();
        }else {
            System.out.println("EMPTY SELECTION");
        }
    }

    @FXML
    private void btnDeleteOnAction(ActionEvent event) {
        mbDeleteItem(event);
    }

    @FXML
    private void mbSearch(ActionEvent event) {
                tblSupplyer.getSelectionModel().clearSelection();
        tfSearch.requestFocus();
    }

    @FXML
    private void mbView(ActionEvent event) {
        btnUpdateOnAction(event);
    }

    @FXML
    private void mbViewHistory(ActionEvent event) {
    }

    @FXML
    private void mbAddNew(ActionEvent event) {
        btnAdditemsOnAction(event);
    }

    @FXML
    private void mbDeleteItem(ActionEvent event) {
        System.out.println("clicked to delete");
        acContent.setOpacity(0.5);
        ListSupplyer selectedSupplyer = tblSupplyer.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Comfirmation");
        alert.setHeaderText("Confirmation");
        alert.setContentText("Vous etês sur de supprimer?  \n Cliquer sur ok pour confirmer");
        alert.initStyle(StageStyle.UNDECORATED);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            supplyer.id = selectedSupplyer.getSupplyerId();
            System.out.println(supplyer.id+ "On hear");
            supplyerBLL.delete(supplyer);
            tblSupplyer.getItems().clear();
            showDetails();
        }else{
            
        }
    }

    @FXML
    private void mbEdit(ActionEvent event) {
                btnUpdateOnAction(event);
        tfSearchOnType(event);
    }

    @FXML
    private void tblSupplyerOnClick(MouseEvent event) {
         int click = event.getClickCount();
        if (click == 2) {
            viewDetails();
        }
    }

    @FXML
    private void tblSupplyerOnKeyPress(KeyEvent event) {
    }
    
     public void showDetails() {
        tblSupplyer.setItems(supplyer.supplyerDetails);
        clmSUpplyerId.setCellValueFactory(new PropertyValueFactory<>("supplyerId"));
        clmSupplyerName.setCellValueFactory(new PropertyValueFactory<>("supplyerName"));
        clmSupplyerPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("supplyerPhoneNumber"));
        clmSupplyerAddress.setCellValueFactory(new PropertyValueFactory<>("supplyerAddress"));
        clmSupplyerDescription.setCellValueFactory(new PropertyValueFactory<>("supplyerDescription"));
        clmSupplyerJoining.setCellValueFactory(new PropertyValueFactory<>("dataOfjoining"));
        supplyerGetway.view(supplyer);

    }
     
     private void viewDetails() {
        if(!tblSupplyer.getSelectionModel().isEmpty()){
            ListSupplyer selectedSupplyer = tblSupplyer.getSelectionModel().getSelectedItem();
            System.out.println("ID is");
            System.out.println(selectedSupplyer.getSupplyerId());
            String items = selectedSupplyer.getSupplyerId();
            if (!items.equals(null)) {
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
                    supplyerController.lblCaption.setText("Fournissseur détails");
                    supplyerController.btnUpdate.setVisible(true);
                    supplyerController.btnSave.setVisible(false);
                    supplyerController.supplyerId = selectedSupplyer.getSupplyerId();
                    supplyerController.showDetails();
                    Stage nStage = new Stage();
                    nStage.setScene(scene);
                    nStage.initModality(Modality.APPLICATION_MODAL);
                    nStage.initStyle(StageStyle.TRANSPARENT);
                    nStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else {
            System.out.println("empty Selection");
        }



    }

}
