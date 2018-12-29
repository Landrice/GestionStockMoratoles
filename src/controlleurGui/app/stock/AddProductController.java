/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlleurGui.app.stock;

import bdd.BddPropreties;
import bdd.SQLSyntax;
import bdd.bddConnection;
import gettersSetters.AlertList;
import gettersSetters.CurrentProduct;
import gettersSetters.CurrentProductBLL;
import gettersSetters.CurrentProductGetway;
import gettersSetters.userNameMedia;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
public class AddProductController implements Initializable {

    @FXML
    public Label lblHeader;
    @FXML
    private RadioButton rbStatic;
    @FXML
    private RadioButton rbSeq;
    @FXML
    private TextField tfProductId;
    @FXML
    private TextField tfProductName;
    @FXML
    private TextField tfProductQuantity;
    @FXML
    private TextField tfProductPursesPrice;
    @FXML
    private TextArea taProductDescription;
    @FXML
    private DatePicker dpDate;
    @FXML
    private Button btnAddSupplier;
    @FXML
    private Button btnAddBrand;
    @FXML
    private Button btnAddCatagory;
    @FXML
    public Button btnSave;
    @FXML
    private TextField tfValue;
    @FXML
    private ComboBox<String> cbSupplyer;
    @FXML
    private ComboBox<String> cmbBrand;
    @FXML
    private ComboBox<String> cmbCatagory;
    @FXML
    public Button btnUpdate;
    @FXML
    private Button btnClose;

    private String usrId;
    private userNameMedia nameMedia;

    CurrentProduct currentProduct = new CurrentProduct();
    AlertList alert = new AlertList();
    CurrentProductBLL currentProductBLL = new CurrentProductBLL();
    CurrentProductGetway currentProductGetway = new CurrentProductGetway();
    SQLSyntax sql = new SQLSyntax();

    bddConnection dbCon = new bddConnection();
    Connection con = dbCon.geConnection();
    PreparedStatement pst;
    ResultSet rs;

    BddPropreties dBProperties = new BddPropreties();
    String db = dBProperties.loadPropertiesFile();

    public String id;
    private String supplyerName;
    private String supplyerId;
    private String brandName;
    private String brandId;
    private String catagoryName;
    private String catagoryId;
    private String unitId;
    private String rmaId;
    @FXML
    private TextField seuiStock;
    @FXML
    private ComboBox<String> categoriestatic;

    public userNameMedia getNameMedia() {
        return nameMedia;
    }

    public void setNameMedia(userNameMedia nameMedia) {
        usrId = nameMedia.getId();
        this.nameMedia = nameMedia;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        categoriestatic.getItems().addAll("Tôles","Peinture","Autres");
        
        ToggleGroup firstRedioBtn = new ToggleGroup();
        rbSeq.setToggleGroup(firstRedioBtn);
        rbStatic.setSelected(true);
        rbStatic.setToggleGroup(firstRedioBtn);
        tfValue.setVisible(false);
        dpDate.setValue(LocalDate.now());
        tfProductQuantity.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    tfProductQuantity.setText(oldValue);
                }
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        });
        tfProductPursesPrice.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,99}([\\.]\\d{0,4})?")) {
                    tfProductPursesPrice.setText(oldValue);
                }
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        });
        seuiStock.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    seuiStock.setText(oldValue);
                }
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        });
        // TODO

    }

    @FXML
    private void rbStaticOnClicked(MouseEvent event) {
        if (rbStatic.isSelected()) {
            tfValue.setVisible(false);
            tfValue.clear();
        } else if (!rbStatic.isSelected()) {
            tfValue.setVisible(true);
        }
    }

    @FXML
    private void rbStaticOnAction(ActionEvent event) {
        // Tsisy raha
    }

    @FXML
    private void rbSeqOnClick(MouseEvent event) {
        if (rbSeq.isSelected()) {
            tfValue.setVisible(true);
        } else if (!rbSeq.isSelected()) {
            tfValue.setVisible(false);
        }
    }

    @FXML
    private void rbSeqOnAction(ActionEvent event) {
        // Tsisy raha
    }

    //@FXML
    @FXML
    public void btnAddSupplierOnAction(ActionEvent event) {
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
            supplyerController.lblCaption.setText("Ajouter Fournisseur");
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

    // @FXML
    @FXML
    public void btnAddBrandOnAction(ActionEvent event) {
        AddBrandController addSupplyerController = new AddBrandController();
        userNameMedia media = new userNameMedia();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/gui/app/stock/AddBrand.fxml"));
        try {
            fxmlLoader.load();
            Parent parent = fxmlLoader.getRoot();
            Scene scene = new Scene(parent);
            scene.setFill(new Color(0, 0, 0, 0));
            AddBrandController supplyerController = fxmlLoader.getController();
            media.setId(usrId);
            supplyerController.setMedia(media);
            supplyerController.lblHeader.setText("Ajouter Marque");
            supplyerController.btnUpdate.setVisible(false);
            Stage nStage = new Stage();
            nStage.setScene(scene);
            nStage.initModality(Modality.APPLICATION_MODAL);
            nStage.initStyle(StageStyle.TRANSPARENT);
            nStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // @FXML
    @FXML
    public void btnAddCatagoryOnAction(ActionEvent event) {
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
    private void btnSaveOnAction(ActionEvent event) {
        System.out.println("Presesd");
        if (isNotNull()) {
            if (!tfValue.getText().trim().isEmpty()) {
                String value = tfValue.getText();
                int foo = Integer.parseInt(value);
                for (int i = 1; i <= foo; ++i) {
                    currentProduct.idProduit = tfProductId.getText().trim() + i;
                    currentProduct.nomProduit = tfProductName.getText().trim();
                    currentProduct.quantite = tfProductQuantity.getText().trim();
                    currentProduct.description = taProductDescription.getText().trim();
                    currentProduct.prix = tfProductPursesPrice.getText().trim();
                    currentProduct.fournisseurID = supplyerId;
                    currentProduct.marqueID = brandId;
                    currentProduct.categorieID = catagoryId;
                    //currentProduct.prix = unitId;
                    currentProduct.utilisateurID = usrId;
                    currentProduct.date = dpDate.getValue().toString();
                    currentProductBLL.save(currentProduct);
                    try {
                        con=dbCon.geConnection();
                        pst = con.prepareStatement("insert into " + db + ".alert values(?,?,?)");
                        pst.setString(1, null);
                        pst.setString(2, currentProduct.idProduit);
                        pst.setString(3, seuiStock.getText().trim());
                        pst.executeUpdate();
                        pst.close();
                        con.close();
                        
                        try {
                        con=dbCon.geConnection();
                        pst = con.prepareStatement("insert into " + db + ".categorietpa values(?,?,?)");
                        pst.setString(1, null);
                        pst.setString(2, currentProduct.idProduit);
                        pst.setString(3, categoriestatic.getSelectionModel().getSelectedItem());
                        pst.executeUpdate();
                        pst.close();
                        con.close();
                    } catch (SQLException e) {
                        Logger.getLogger(CurrentProduct.class.getName()).log(Level.SEVERE, null, e);
                        System.out.println("Too Many Connection");
                    }
                    } catch (SQLException e) {
                        Logger.getLogger(CurrentProduct.class.getName()).log(Level.SEVERE, null, e);
                        System.out.println("Too Many Connection");
                    }
                    

                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("succès");
                alert.setHeaderText("succès: Sauvegarde reussi");
                alert.setContentText("Le produit a été ajouté avec succès");
                alert.initStyle(StageStyle.UNDECORATED);
                alert.showAndWait();

            } else {
                currentProduct.idProduit = tfProductId.getText().trim();
                currentProduct.nomProduit = tfProductName.getText().trim();
                currentProduct.quantite = tfProductQuantity.getText().trim();
                currentProduct.description = taProductDescription.getText().trim();
                currentProduct.prix = tfProductPursesPrice.getText().trim();
                currentProduct.fournisseurID = supplyerId;
                currentProduct.marqueID = brandId;
                currentProduct.categorieID = catagoryId;
                //currentProduct.prix = unitId;
                currentProduct.utilisateurID = usrId;
                currentProduct.date = dpDate.getValue().toString();
                currentProductBLL.save(currentProduct);

                try {
                    //dbCon=new bddConnection();
                    con=dbCon.geConnection();
                    pst = con.prepareStatement("insert into " + db + ".alert values(?,?,?)");
                    pst.setString(1, null);
                    pst.setString(2, currentProduct.idProduit);
                    pst.setString(3, seuiStock.getText().trim());
                    pst.executeUpdate();
                    pst.close();
                    con.close();
                    
                    try {
                        con=dbCon.geConnection();
                        pst = con.prepareStatement("insert into " + db + ".categorietpa values(?,?,?)");
                        pst.setString(1, null);
                        pst.setString(2, currentProduct.idProduit);
                        pst.setString(3, categoriestatic.getSelectionModel().getSelectedItem());
                        pst.executeUpdate();
                        pst.close();
                        con.close();
                    } catch (SQLException e) {
                        Logger.getLogger(CurrentProduct.class.getName()).log(Level.SEVERE, null, e);
                        System.out.println("Too Many Connection");
                    }
                    
                } catch (SQLException e) {
                    Logger.getLogger(CurrentProduct.class.getName()).log(Level.SEVERE, null, e);
                    System.out.println("Too Many Connection");
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("succès");
                alert.setHeaderText("succès: Sauvegarde reussi ");
                alert.setContentText("Le produit a été ajouté avec succès");
                alert.initStyle(StageStyle.UNDECORATED);
                alert.showAndWait();

            }
        }
    }

    @FXML
    private void cbSupplyerOnClicked(MouseEvent event) {
        cbSupplyer.getSelectionModel().clearSelection();
        cbSupplyer.getItems().clear();
        cmbBrand.getSelectionModel().clearSelection();
        cmbBrand.getItems().clear();
        cmbBrand.getItems().removeAll();
        try {
            pst = con.prepareStatement("select * from " + db + ".fournisseur");
            rs = pst.executeQuery();
            while (rs.next()) {
                cbSupplyer.getItems().addAll(rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void cbSupplyerOnAction(ActionEvent event) {
        cbSupplyer.getSelectionModel().getSelectedItem();

        try {
            pst = con.prepareStatement("select * from " + db + ".fournisseur where NomFournisseur=?");
            pst.setString(1, cbSupplyer.getSelectionModel().getSelectedItem());
            rs = pst.executeQuery();
            while (rs.next()) {
                supplyerId = rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void cmbBrandOnClick(MouseEvent event) {
        cmbBrand.getItems().clear();
        cmbCatagory.getItems().clear();
        cmbCatagory.getItems().removeAll();
        cmbCatagory.setPromptText(null);
        try {
            pst = con.prepareStatement("select * from " + db + ".marque where FournisseurID=?");
            pst.setString(1, supplyerId);
            rs = pst.executeQuery();
            while (rs.next()) {
                cmbBrand.getItems().addAll(rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void cmbBrandOnAction(ActionEvent event) {
        System.out.println("AAAZZZZZ0a");
        cmbBrand.getSelectionModel().getSelectedItem();
        System.out.println("AAAZZZZZ0b");
        try {
            pst = con.prepareStatement("select * from " + db + ".marque where NomMarque=? and FournisseurID=?");
            System.out.println("AAAZZZZZ0c");
            pst.setString(1, cmbBrand.getSelectionModel().getSelectedItem());
            System.out.println("AAAZZZZZ0d");
            pst.setString(2, supplyerId);
            rs = pst.executeQuery();
            while (rs.next()) {
                brandId = rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void cmbCatagoryOnClick(MouseEvent event) {
        cmbCatagory.getItems().clear();
        try {
            pst = con.prepareStatement("select * from " + db + ".categorie where FournisseurId=? and MarqueId=?");
            pst.setString(1, supplyerId);
            pst.setString(2, brandId);
            rs = pst.executeQuery();
            while (rs.next()) {
                cmbCatagory.getItems().addAll(rs.getString(2));
                catagoryId = rs.getString(1);
                System.out.println(catagoryId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cmbCatagoryOnAction(ActionEvent event) {
        cmbCatagory.getSelectionModel().getSelectedItem();
        try {
            pst = con.prepareStatement("select * from " + db + ".categorie where FournisseurId=? and MarqueId=?");
            pst.setString(1, supplyerId);
            pst.setString(2, brandId);
            rs = pst.executeQuery();
            while (rs.next()) {
                catagoryId = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnUpdateOnAction(ActionEvent event) {
        if (isNotNull()) {
            System.out.println(supplyerId + brandId + usrId);

            currentProduct.idProduit = tfProductId.getText();
            currentProduct.nomProduit = tfProductName.getText();
            currentProduct.quantite = tfProductQuantity.getText();
            currentProduct.prix = tfProductPursesPrice.getText();
            currentProduct.fournisseurID = supplyerId;
            currentProduct.marqueID = brandId;
            currentProduct.categorieID = catagoryId;
            currentProduct.description=taProductDescription.getText();
            //currentProduct.prix = unitId;
            currentProduct.id = id;
            currentProduct.date=dpDate.getValue().toString();
            currentProduct.utilisateurID="";
            System.out.println("Balise 01");
            currentProductBLL.update(currentProduct);
            
            try {
                System.out.println("Balise AA1");
                con=dbCon.geConnection();
                System.out.println("Balise AA2");
            pst = con.prepareStatement("update " + db + ".alert set seuilAlert=? where IdProduit=?");
            System.out.println("Balise AA3");
            pst.setString(1, seuiStock.getText().trim());
            System.out.println("Balise AA4");
            pst.setString(2, tfProductId.getText().trim());
            System.out.println("Balise AA5");
            pst.executeUpdate();
            System.out.println("Balise AA6");
            pst.close();
            System.out.println("Balise AA7");
           // con.close();
            //    rs.close();
              try {
                  System.out.println("Balise AB1");
            pst = con.prepareStatement("update " + db + ".categorietpa set categorieTPA=? where IdProduit=?");
            System.out.println("Balise AB2");
            pst.setString(1, categoriestatic.getSelectionModel().getSelectedItem());
            System.out.println("l categorie est : "+categoriestatic.getSelectionModel().getSelectedItem());
            System.out.println("Balise AB3");
            pst.setString(2, tfProductId.getText().trim());
            System.out.println("Balise AB4");
            pst.executeUpdate();
            System.out.println("Balise AB5");
            pst.close();
            System.out.println("Balise AB6");
            con.close();
            System.out.println("Balise AB7");
            //    rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(CurrentProduct.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Ato erreur sql 01: " + ex);
        }

        } catch (SQLException ex) {
            Logger.getLogger(CurrentProduct.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Ato erreur sql 02: " + ex);
        }     
            refreshProductList();
        }
                    Alert aler = new Alert(Alert.AlertType.INFORMATION);
            aler.setTitle("Sucess");
            aler.setHeaderText("Mise à Jour : Mise à jour réussi");
            aler.setContentText("Categorie" + "  '" + currentProduct.idProduit + "' " + "a été mise à jour");
            aler.initStyle(StageStyle.UNDECORATED);
            aler.showAndWait();
    }

    @FXML
    private void btnCloseOnAction(ActionEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    private boolean isNotNull() {
        boolean insNotNull = false;
        if (cbSupplyer.getSelectionModel().getSelectedItem() == null
                && cbSupplyer.getPromptText().isEmpty()
                || cmbBrand.getSelectionModel().getSelectedItem() == null
                && cmbBrand.getPromptText().isEmpty()
                || cmbCatagory.getSelectionModel().getSelectedItem() == null
                && cmbCatagory.getPromptText().isEmpty()
                || tfProductId.getText().isEmpty()
                || tfProductName.getText().isEmpty()
                || tfProductQuantity.getText().isEmpty()
                || categoriestatic.getSelectionModel().getSelectedItem() == null
                || seuiStock.getText().isEmpty() // || tfProductPursesPrice.getText().isEmpty()
                ) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("ERREUR: Le champ ne peut pas être vide");
            alert.setContentText("Veuillez remplir les champs necessaires");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();

            insNotNull = false;
        } else {
            insNotNull = true;
        }
        return insNotNull;
    }

    public void viewSelected() {
        currentProduct.id = id;
        currentProductGetway.selectedView(currentProduct);
        tfProductId.setText(currentProduct.idProduit);
        tfProductName.setText(currentProduct.nomProduit);
        tfProductQuantity.setText(currentProduct.quantite);
        tfProductPursesPrice.setText(currentProduct.prix);
        taProductDescription.setText(currentProduct.description);
        dpDate.setValue(LocalDate.parse(currentProduct.date));
        supplyerId = currentProduct.fournisseurID;
        brandId = currentProduct.marqueID;
        catagoryId = currentProduct.categorieID;
        cbSupplyer.setPromptText(currentProduct.fournisseurNom);
        cmbBrand.setPromptText(currentProduct.marqueNom);
        cmbCatagory.setPromptText(currentProduct.categorieNom);
        
        try {
            pst = con.prepareStatement("select * from " + db + ".alert where IdProduit=?");
            pst.setString(1, currentProduct.idProduit);
            rs = pst.executeQuery();
            while (rs.next()) {
                seuiStock.setText(rs.getString(3));
            }
            pst.close();
           // con.close();
            rs.close();
            
             try {
                 System.out.println("BLS01");
            pst = con.prepareStatement("select * from " + db + ".categorietpa where IdProduit=?");
            System.out.println("BLS02");
            pst.setString(1, currentProduct.idProduit);
            System.out.println(" id est --------- : "+currentProduct.idProduit);
            System.out.println("BLS03");
            rs = pst.executeQuery();
            System.out.println("BLS04");
            while (rs.next()) {
                System.out.println("BLS05");
                switch (rs.getString(3)) { 
                    case "Tôles":
                        categoriestatic.getSelectionModel().select(0);
                        System.out.println("Tôles");
                    break;
                    case "Peinture":
                        categoriestatic.getSelectionModel().select(1);
                        System.out.println("Tôles");
                    break;
                    case "Autres":
                        categoriestatic.getSelectionModel().select(2);
                        System.out.println("Tôles");
                    break;
                    default:
                     categoriestatic.getSelectionModel().select(-1);
                     System.out.println("Aucun");
                    break;   
                        
                }
            }
            pst.close();
            con.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
            Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, e);
        }
            
        } catch (SQLException e) {
            Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void refreshProductList() {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.load(getClass().getResource("/gui/app/stock/CurrentStore.fxml").openStream());
            CurrentStoreController currentStoreController = fXMLLoader.getController();
            currentStoreController.viewDetails();
        } catch (IOException ex) {
            Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
