/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlleurGui.app.stock;

import bdd.SQLSyntax;
import bdd.bddConnection;
import gettersSetters.Catagory;
import gettersSetters.CatagoryBLL;
import gettersSetters.CatagoryGetway;
import gettersSetters.CellFactories;
import gettersSetters.ListCatagory;
import gettersSetters.userNameMedia;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class ViewCatagoryController implements Initializable {
    
    private String usrId;
    private String catagoryId;
    private String brandId;
    private String brandName;
    private String creatorId;

    SQLSyntax sql = new SQLSyntax();
    Catagory catagory = new Catagory();
    CellFactories cellFactories = new CellFactories();
    CatagoryGetway catagoryGetway = new CatagoryGetway();
    CatagoryBLL catagoryBLL = new CatagoryBLL();

    private userNameMedia media;

    @FXML
    private TextField tfSearch;
    @FXML
    private Button btnRefresh;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private TableView<ListCatagory> tblCatagory;
    @FXML
    private MenuItem miSearch;
    @FXML
    private MenuItem miUpdate;
    @FXML
    private MenuItem miAddNew;
    @FXML
    private MenuItem miDelete;
    @FXML
    private MenuItem miView;
    @FXML
    private TableColumn<Object, Object> clmCatagoryId;
    @FXML
    public TableColumn tablClmBox;
    @FXML
    private TableColumn<Object, Object> clmCatagoryName;
    @FXML
    private TableColumn<Object, Object> clmCatagoryBrand;
    @FXML
    private TableColumn<Object, Object> clmSupplyer;
    @FXML
    private TableColumn<Object, Object> clmCatagoryCreator;
    @FXML
    private TableColumn<Object, Object> clmCatagoryDate;
    @FXML
    private TableColumn<Object, Object> clmCatagoryDescription;
    
    public userNameMedia getMedia() {
        return media;
    }

    public void setMedia(userNameMedia media) {
        usrId = media.getId();
        this.media = media;
    }

    bddConnection dbCon = new bddConnection();
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void tfSearchOnType(KeyEvent event) {
        catagory.catagoryDetails.clear();
        catagory.catagoryName = tfSearch.getText().trim();
        tblCatagory.setItems(catagory.catagoryDetails);

        clmCatagoryId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmCatagoryName.setCellValueFactory(new PropertyValueFactory<>("catagoryName"));
        clmCatagoryBrand.setCellValueFactory(new PropertyValueFactory<>("brandId"));
        clmSupplyer.setCellValueFactory(new PropertyValueFactory<>("supplyerId"));
        clmCatagoryDescription.setCellValueFactory(new PropertyValueFactory<>("catagoryDescription"));
        clmCatagoryCreator.setCellValueFactory(new PropertyValueFactory<>("creatorId"));
        clmCatagoryDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        catagoryGetway.searchView(catagory);

    }

    @FXML
    private void btnRefreshOnAction(ActionEvent event) {
        catagory.catagoryDetails.clear();
        showDetails();
    }

    @FXML
    private void btnAddOnAction(ActionEvent event) {
        AddCatagoryController addCatagoryController = new AddCatagoryController();
        userNameMedia media = new userNameMedia();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/gui/app/stock/AddCatagory.fxml"));
        try {
            fxmlLoader.load();
            Parent parent = fxmlLoader.getRoot();
            Scene scene = new Scene(parent);
            scene.setFill(new Color(0, 0, 0, 0));
            AddCatagoryController catagoryController = fxmlLoader.getController();
            media.setId(usrId);
            catagoryController.setMedia(media);
            catagoryController.lblHeaderContent.setText("Ajouter");
            catagoryController.btnUpdate.setVisible(false);
            Stage nStage = new Stage();
            nStage.setScene(scene);
            nStage.initModality(Modality.APPLICATION_MODAL);
            nStage.initStyle(StageStyle.TRANSPARENT);
            nStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnUpdateOnAction(ActionEvent event) {
        if (tblCatagory.getSelectionModel().getSelectedItem() != null) {
            viewDetails();
        } else {
            System.out.println("EMPTY SELECTION");
        }
    }

    @FXML
    private void btnDeleteOnAction(ActionEvent event) {
        ListCatagory selectedCatagory = tblCatagory.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Comfirmation");
        alert.setHeaderText("Confirmation");
        alert.setContentText("Voulez vous supprimer vraiment? \n cliquer sur ok pour confirmer");
        alert.initStyle(StageStyle.UNDECORATED);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            catagory.id = selectedCatagory.getId();
            System.out.println(catagory.id + "On hear");
            catagoryBLL.delete(catagory);
            tblCatagory.getItems().clear();
            showDetails();
        }
    }
    
     public void showDetails() {
        tblCatagory.setItems(catagory.catagoryDetails);
        tablClmBox.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmCatagoryId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmCatagoryName.setCellValueFactory(new PropertyValueFactory<>("catagoryName"));
        clmCatagoryBrand.setCellValueFactory(new PropertyValueFactory<>("brandId"));
        clmSupplyer.setCellValueFactory(new PropertyValueFactory<>("supplyerId"));
        clmCatagoryDescription.setCellValueFactory(new PropertyValueFactory<>("catagoryDescription"));
        clmCatagoryCreator.setCellValueFactory(new PropertyValueFactory<>("creatorId"));
        clmCatagoryDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        catagoryGetway.view(catagory);
        tablClmBox.setCellFactory(cellFactories.cellFactoryCheckBox);

    }

    @FXML
    private void miSearchOnAction(ActionEvent event) {
        tblCatagory.getSelectionModel().clearSelection();
        tfSearch.requestFocus();
    }

    @FXML
    private void miUpdateOnAction(ActionEvent event) {
         btnUpdateOnAction(event);
    }

    @FXML
    private void miAddNewOnAction(ActionEvent event) {
        btnAddOnAction(event);
    }

    @FXML
    private void miDeleteOnAction(ActionEvent event) {
        btnDeleteOnAction(event);
    }

    @FXML
    private void miViewOnAction(ActionEvent event) {
         btnUpdateOnAction(event);
    }

    @FXML
    private void tblCatagoryOnClick(MouseEvent event) {
         if (event.getClickCount() == 2) {
            viewDetails();
        } else {
            System.out.println("CLICKED");
        }
    }
    
        private void viewDetails() {
        if (!tblCatagory.getSelectionModel().isEmpty()) {
            ListCatagory selectedCatagory = tblCatagory.getSelectionModel().getSelectedItem();
            System.out.println("ID is");
            System.out.println(selectedCatagory.getCreatorId());
            String items = selectedCatagory.getId();
            if (!items.equals(null)) {
                AddCatagoryController addCatagoryController = new AddCatagoryController();
                userNameMedia media = new userNameMedia();
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/gui/app/stock/AddCatagory.fxml"));
                try {
                    fxmlLoader.load();
                    Parent parent = fxmlLoader.getRoot();
                    Scene scene = new Scene(parent);
                    scene.setFill(new Color(0, 0, 0, 0));
                    AddCatagoryController catagoryController = fxmlLoader.getController();
                    media.setId(usrId);
                    catagoryController.setMedia(media);
                    catagoryController.lblHeaderContent.setText("Détails categorie");
                    catagoryController.btnUpdate.setVisible(true);
                    catagoryController.btnSave.setVisible(false);
                    catagoryController.catagoryId = selectedCatagory.id;
                    catagoryController.showDetails();
                    Stage nStage = new Stage();
                    nStage.setScene(scene);
                    nStage.initModality(Modality.APPLICATION_MODAL);
                    nStage.initStyle(StageStyle.TRANSPARENT);
                    nStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("empty Selection");
        }

    }
}
