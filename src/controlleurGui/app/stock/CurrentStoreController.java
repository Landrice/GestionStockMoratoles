/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlleurGui.app.stock;

import bdd.BddPropreties;
import bdd.SQLSyntax;
import bdd.bddConnection;
import com.jfoenix.controls.JFXRadioButton;
import gettersSetters.ConvertierMontantEnLettre;
import gettersSetters.CurrentProduct;
import gettersSetters.CurrentProductBLL;
import gettersSetters.CurrentProductGetway;
import gettersSetters.ListProduct;
import gettersSetters.TableRow;
import gettersSetters.userNameMedia;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
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
public class CurrentStoreController implements Initializable {

    CurrentProduct productCurrent = new CurrentProduct();
    CurrentProductGetway currentProductGetway = new CurrentProductGetway();
    CurrentProductBLL currentProductBLL = new CurrentProductBLL();

    BddPropreties dBProperties = new BddPropreties();
    String db = dBProperties.loadPropertiesFile();

    private String usrId;

    private userNameMedia media;

    @FXML
    private TextField tfSearch;
    @FXML
    private Button btnRefresh;
    @FXML
    public AnchorPane apCombobox;
    @FXML
    private ComboBox<String> cbSoteViewSupplyer;
    @FXML
    private ComboBox<String> cbSoteViewBrands;
    @FXML
    private ComboBox<String> cbSoteViewCatagory;
    @FXML
    private Button btnAddNew;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private TableView<ListProduct> tblViewCurrentStore;
    @FXML
    private MenuItem miSellSelected;
    @FXML
    private TableColumn<Object, Object> tblClmProductId;
    @FXML
    private TableColumn<Object, Object> tblClmProductName;
    @FXML
    private TableColumn<Object, Object> tblClmProductquantity;
    @FXML
    private TableColumn<Object, Object> tblClmProductSupplyer;
    @FXML
    private TableColumn<Object, Object> tblClmProductBrand;
    @FXML
    private TableColumn<Object, Object> tblClmProductCatagory;
    @FXML
    private TableColumn<Object, Object> tblClmProductPursesPrice;
    @FXML
    private TableColumn<Object, Object> tblClmProductdate;
    @FXML
    private TableColumn<Object, Object> tblClmProductAddBy;
    @FXML
    private TableColumn<Object, Object> tblClmProductdescription;

    String suplyerId;
    String suplyerName;
    String brandId;
    String brandName;
    String catagoryId;
    String catagoryName;
    String rmaID;
    String rmaName;
    SQLSyntax sql = new SQLSyntax();
    @FXML
    private ToggleGroup btncategorieGroup;
    @FXML
    private JFXRadioButton produitsTous;
    @FXML
    private JFXRadioButton produitsToles;
    @FXML
    private JFXRadioButton produitsPeintures;
    @FXML
    private JFXRadioButton ProduitsAutres;
    @FXML
    private JFXRadioButton stockMin;

    public userNameMedia getMedia() {
        return media;
    }

    public void setMedia(userNameMedia media) {
        usrId = media.getId();
        this.media = media;
    }

    bddConnection dbCon = new bddConnection();
    Connection con = dbCon.geConnection();
    PreparedStatement pst;
    ResultSet rs;
    ConvertierMontantEnLettre test;
    String alertseuil;
    String idproduitAlert;
    ListProduct lstpr;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        produitsTous.setSelected(true);
    }

    @FXML
    private void tfSearchOnKeyRelese(KeyEvent event) {
        productCurrent.idProduit = tfSearch.getText();
        productCurrent.nomProduit = tfSearch.getText();
        currentProductGetway.searchView(productCurrent);
    }

    
    public void searchAll(){
        productCurrent.currentProductList.clear();
        tfSearch.clear();
        cbSoteViewSupplyer.getItems().clear();
        cbSoteViewBrands.getItems().clear();
        cbSoteViewCatagory.getItems().clear();
        cbSoteViewSupplyer.setPromptText("Fournisseur");
        cbSoteViewBrands.setPromptText("Marque");
        cbSoteViewCatagory.setPromptText("Categorie");

        tblViewCurrentStore.setItems(productCurrent.currentProductList);
        tblClmProductId.setCellValueFactory(new PropertyValueFactory<>("IdProduit"));
        tblClmProductName.setCellValueFactory(new PropertyValueFactory<>("NomProduit"));
        tblClmProductquantity.setCellValueFactory(new PropertyValueFactory<>("Quantite"));
        tblClmProductdescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        tblClmProductSupplyer.setCellValueFactory(new PropertyValueFactory<>("fournisseurID"));
        tblClmProductBrand.setCellValueFactory(new PropertyValueFactory<>("marqueID"));
        tblClmProductCatagory.setCellValueFactory(new PropertyValueFactory<>("categorieID"));
        tblClmProductPursesPrice.setCellValueFactory(new PropertyValueFactory<>("Prix"));
        tblClmProductAddBy.setCellValueFactory(new PropertyValueFactory<>("utilisateur"));
        tblClmProductdate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        currentProductGetway.view(productCurrent);
        
        produitsTous.setSelected(true);
        tblViewCurrentStore.getStylesheets().clear();
        tblViewCurrentStore.setRowFactory((final TableView<ListProduct> p)->new TableRow());

    /*    for (int i = 0; i < tblViewCurrentStore.getItems().size(); i++) {
            ListProduct lst = tblViewCurrentStore.getItems().get(i);
            System.out.println("bbbbbbb : " + lst.getId());
            String idproduit=lst.getId();
            int idproduitin=Integer.parseInt(idproduit);
            try {
                pst = con.prepareStatement("SELECT * FROM " + db + ".alert, " + db + ".produits WHERE " + db + ".alert.IdProduit = " + db + ".produits.IdProduit");
                rs = pst.executeQuery();
                while (rs.next()) {
                    String idproduitBase=rs.getString(4);
                    int idproduitBasein=Integer.parseInt(idproduitBase);
                    if (Integer.parseInt(rs.getString(7)) < Integer.parseInt(rs.getString(3))) {
                        if(idproduitin==idproduitBasein){
                           // tblClmProductId.setStyle("-fx-background-color:red;");
                            //((ListProduct)getChildren.get(i)).setTextFill(Color.RED);
                           String innn=(String) tblClmProductId.getCellData(i);
                           System.out.println("Les valeurs de i sont: "+innn);
                           //tblClmProductdate.setStyle("-fx-background-color:red;");
                           //String ss=tblViewCurrentStore.getItems().get(i).nomProduit;
                           //tblViewCurrentStore.setRowFactory(n);
                           // tblViewCurrentStore.getStyleClass().add("-fx-background-color:red;")
                           tblViewCurrentStore.getStylesheets().add(GestionStock.class.getResource("/css/cssline.css").toExternalForm());
                           // ((Labeled)getChildren().get(i)).setStyle("-fx-background-color: yellow");
                           
                        }
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(CurrentStoreController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
    }
    
    
    @FXML
    private void btnRefreshOnACtion(ActionEvent event) {
        searchAll();
    }
    @FXML
    private void cbSoteViewSupplyerOnClick(MouseEvent event) {
        con = dbCon.geConnection();
        cbSoteViewSupplyer.getItems().clear();
        cbSoteViewBrands.setPromptText("Selectionner la marque");
        cbSoteViewCatagory.setPromptText("Selectionner le categorie");
        try {
            pst = con.prepareStatement("select * from " + db + ".fournisseur");
            rs = pst.executeQuery();
            while (rs.next()) {
                cbSoteViewSupplyer.getItems().remove(rs.getString(2));
                cbSoteViewSupplyer.getItems().add(rs.getString(2));
            }
            rs.close();
            con.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cbSoteViewSupplyerOnAction(ActionEvent event) {
        if (cbSoteViewSupplyer.getSelectionModel().getSelectedItem() == null) {

        } else {
            suplyerName = cbSoteViewSupplyer.getSelectionModel().getSelectedItem();
            productCurrent.fournisseurNom = suplyerName;
            currentProductGetway.searchBySupplyer(productCurrent);
        }
    }

    @FXML
    private void cbSoteViewBrandOnClick(MouseEvent event) {
        con = dbCon.geConnection();
        cbSoteViewBrands.getItems().clear();
        suplyerName = cbSoteViewSupplyer.getSelectionModel().getSelectedItem();
        suplyerId = sql.getIdNo(suplyerName, suplyerId, "fournisseur", "NomFournisseur");

        try {
            pst = con.prepareStatement("select * from " + db + ".marque where FournisseurID=?");
            pst.setString(1, suplyerId);
            rs = pst.executeQuery();
            while (rs.next()) {
                cbSoteViewBrands.getItems().add(rs.getString(2));
            }
            rs.close();
            con.close();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(CurrentStoreController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void cbSoteViewBrandOnAction(ActionEvent event) {
        if (cbSoteViewBrands.getSelectionModel().getSelectedItem() == null) {

        } else {
            brandName = cbSoteViewBrands.getSelectionModel().getSelectedItem();
            suplyerName = cbSoteViewSupplyer.getPromptText();
            productCurrent.fournisseurNom = suplyerName;
            productCurrent.marqueNom = brandName;
            currentProductGetway.searchByBrand(productCurrent);
        }
    }

    @FXML
    private void cbSoteViewCatagoryOnClick(MouseEvent event) {
        con = dbCon.geConnection();
        cbSoteViewCatagory.getItems().clear();
        suplyerName = cbSoteViewSupplyer.getSelectionModel().getSelectedItem();
        suplyerId = sql.getIdNo(suplyerName, suplyerId, "fournisseur", "NomFournisseur");
        brandId = sql.getBrandID(suplyerId, brandId, brandName);
        try {
            pst = con.prepareStatement("select * from " + db + ".categorie where FournisseurId=? and MarqueId=?");
            pst.setString(1, suplyerId);
            pst.setString(2, brandId);
            rs = pst.executeQuery();
            while (rs.next()) {
                cbSoteViewCatagory.getItems().add(rs.getString(2));
            }
            rs.close();
            con.close();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(CurrentStoreController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void cbSoteViewCatagoryOnAction(ActionEvent event) {
        if (cbSoteViewCatagory.getSelectionModel().getSelectedItem() == null) {

        } else {
            brandName = cbSoteViewBrands.getSelectionModel().getSelectedItem();
            suplyerName = cbSoteViewSupplyer.getPromptText();
            catagoryName = cbSoteViewCatagory.getSelectionModel().getSelectedItem();
            productCurrent.fournisseurNom = suplyerName;
            productCurrent.marqueNom = brandName;
            productCurrent.categorieNom = catagoryName;
            currentProductGetway.searchByCatagory(productCurrent);
        }
    }

    @FXML
    private void btnAddNewOnAction(ActionEvent event) {
        AddProductController apc = new AddProductController();
        userNameMedia media = new userNameMedia();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/gui/app/stock/AddProduct.fxml"));
        try {
            fxmlLoader.load();
            Parent parent = fxmlLoader.getRoot();
            Scene scene = new Scene(parent);
            scene.setFill(new Color(0, 0, 0, 0));
            AddProductController addProductController = fxmlLoader.getController();
            media.setId(usrId);
            addProductController.setNameMedia(media);
            addProductController.lblHeader.setText("Ajouter Produit");
            addProductController.btnUpdate.setVisible(false);
            Stage nStage = new Stage();
//            addProductController.addSupplyerStage(nStage);
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
        if (!tblViewCurrentStore.getSelectionModel().isEmpty()) {
            viewSelected();
        } else {
            System.out.println("EMPTY SELECTION");
            System.out.println("EMPTY SELECTION 01 :" + alertseuil);
            System.out.println("EMPTY SELECTION 02 :" + idproduitAlert);

        }
    }

    @FXML
    private void btnDeleteOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirmation");
        alert.setContentText("Voulez vous supprimer l'élement selectionné? \n cliquer sur OK pour confirmer");
        alert.initStyle(StageStyle.UNDECORATED);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String item = tblViewCurrentStore.getSelectionModel().getSelectedItem().getId();
            System.out.println("Product id" + item);
            productCurrent.id = item;
            currentProductBLL.delete(productCurrent);
            btnRefreshOnACtion(event);
        }
    }

    @FXML
    private void miSellSelectedOnAction(ActionEvent event) {
        /*  if (tblViewCurrentStore.getSelectionModel().getSelectedItem() != null) {
            String item = tblViewCurrentStore.getSelectionModel().getSelectedItem().getIdProduit();
            NewSellController acc = new NewSellController();
            userNameMedia media = new userNameMedia();
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("/view/application/sell/NewSell.fxml"));
            try {
                fXMLLoader.load();
                Parent parent = fXMLLoader.getRoot();
                Scene scene = new Scene(parent);
                scene.setFill(new Color(0, 0, 0, 0));
                NewSellController newSellController = fXMLLoader.getController();
                newSellController.tfProductId.setText(item);
                newSellController.tfProductIdOnAction(event);
                media.setId(usrId);
                newSellController.setNameMedia(media);
                newSellController.genarateSellID();
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.show();
            } catch (IOException ex) {
               // Logger.getLogger(ViewCustomerController.class.getName()).log(Level.SEVERE, null, ex);
               System.out.println(ex);
            }
        } else {

        }*/
    }

    @FXML
    private void tblViewCurrentStoreOnClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            if (!tblViewCurrentStore.getSelectionModel().isEmpty()) {
                viewSelected();
            } else {
                System.out.println("EMPTY SELECTION");
            }
        } else {
            tblViewCurrentStore.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    tblViewCurrentStore.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                }
            });

        }
    }

    @FXML
    private void tblViewCurrentStoreOnScroll(ScrollEvent event) {
        if (event.isInertia()) {
            System.out.println("ALT DOWN");
        } else {
            System.out.println("Noting");
        }
    }

    public void viewDetails() {
        //viewFistTenOrderByCategorie

        System.out.println("CLCKED");
        tblViewCurrentStore.setItems(productCurrent.currentProductList);
        tblClmProductId.setCellValueFactory(new PropertyValueFactory<>("IdProduit"));
        tblClmProductName.setCellValueFactory(new PropertyValueFactory<>("NomProduit"));
        //tblClmProductName.setStyle("-fx-background-color:red;");
        tblClmProductquantity.setCellValueFactory(new PropertyValueFactory<>("Quantite"));
        tblClmProductdescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        tblClmProductSupplyer.setCellValueFactory(new PropertyValueFactory<>("fournisseurID"));
        tblClmProductBrand.setCellValueFactory(new PropertyValueFactory<>("marqueID"));
        tblClmProductCatagory.setCellValueFactory(new PropertyValueFactory<>("categorieID"));
        tblClmProductPursesPrice.setCellValueFactory(new PropertyValueFactory<>("Prix"));
        tblClmProductAddBy.setCellValueFactory(new PropertyValueFactory<>("utilisateur"));
        tblClmProductdate.setCellValueFactory(new PropertyValueFactory<>("Date"));

        if (produitsTous.isSelected() == true) {
            System.out.println("Categorie tous selectionné");
            currentProductGetway.viewFistTen(productCurrent);
        } else if (produitsPeintures.isSelected() == true) {
            System.out.println("Categorie peintures selectionné");
            currentProductGetway.viewFistTenOrderByCategorie(productCurrent, "Peinture");
        } else if (produitsToles.isSelected() == true) {
            System.out.println("Categorie toles selectionné");
            currentProductGetway.viewFistTenOrderByCategorie(productCurrent, "Tôles");
        } else if (ProduitsAutres.isSelected() == true) {
            System.out.println("Categorie autres selectionné");
            currentProductGetway.viewFistTenOrderByCategorie(productCurrent, "Autres");
        } else if(stockMin.isSelected() == true){
            System.out.println("Categorie alerte selectionné");
            currentProductGetway.viewFistTenOrderByAlert(productCurrent);
        }       
        else {
            System.out.println("Aucune categorie n'est selectionné");
            currentProductGetway.viewFistTen(productCurrent);
        }
        //tblViewCurrentStore.setRowFactory((final TableView<ListProduct> p)->new TableRow());
        
    }

    private void viewSelected() {
        AddProductController apc = new AddProductController();
        userNameMedia userMedia = new userNameMedia();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/gui/app/stock/AddProduct.fxml"));
        try {
            fxmlLoader.load();
            Parent parent = fxmlLoader.getRoot();
            Scene scene = new Scene(parent);
            scene.setFill(new Color(0, 0, 0, 0));
            AddProductController addProductController = fxmlLoader.getController();
            userMedia.setId(usrId);
            addProductController.setNameMedia(userMedia);
            addProductController.lblHeader.setText("DETAILS DES PRODUITS");
            addProductController.btnUpdate.setVisible(true);
            addProductController.btnSave.setVisible(false);
            addProductController.id = tblViewCurrentStore.getSelectionModel().getSelectedItem().getId();
            addProductController.viewSelected();
            Stage nStage = new Stage();
            nStage.setScene(scene);
            nStage.initModality(Modality.APPLICATION_MODAL);
            nStage.initStyle(StageStyle.TRANSPARENT);
            nStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void settingPermission() {
        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("select * from " + db + ".droitutilisateur where id=?");
            pst.setString(1, usrId);
            rs = pst.executeQuery();
            while (rs.next()) {
                if (rs.getInt(8) == 0) {
                    btnUpdate.setDisable(true);
                    btnDelete.setDisable(true);
                }
                if (rs.getInt(3) == 0) {
                    btnAddNew.setDisable(true);
                }
                if (rs.getInt("SellProduct") == 0) {
                    miSellSelected.setDisable(true);
                } else {

                }
            }
        } catch (SQLException ex) {
            // Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void keyreleased(KeyEvent event) {
        //Fleche haut
        if (event.getCode().equals(KeyCode.UP)) {
        } // fleche bas
        else if (event.getCode().equals(KeyCode.DOWN)) {
            String iyyy = tblViewCurrentStore.getSelectionModel().getSelectedItem().getIdProduit();
            System.out.println(iyyy);
        }
    }

    @FXML
    private void selectectionCategorie(ActionEvent event) {
        viewDetails();
    }
    
}
