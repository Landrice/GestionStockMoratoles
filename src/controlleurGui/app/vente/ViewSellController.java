/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlleurGui.app.vente;

import bdd.BddPropreties;
import bdd.bddConnection;
import com.jfoenix.controls.JFXDatePicker;
import gettersSetters.ConvertierMontantEnLettre;
import gettersSetters.ListSold;
import gettersSetters.SellCart;
import gettersSetters.SellCartGerway;
import gettersSetters.userNameMedia;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author Ralande
 */
public class ViewSellController implements Initializable {

    @FXML
    private TextField tfSearch;
    @FXML
    private Button btnRefresh;
    @FXML
    private Button btnSellOrder;
    @FXML
    private TableView<ListSold> tblSellView;
    @FXML
    private TableColumn<Object, Object> tblClmSellId;
    @FXML
    private TableColumn<Object, Object> tblClmProductId;
    @FXML
    private TableColumn<Object, Object> tblClmCustomerName;
    @FXML
    private TableColumn<Object, Object> tblClmSoldDate;
    @FXML
    private TableColumn<Object, Object> tblClmQuantity;
    @FXML
    private TableColumn<Object, Object> tblClmTotalPrice;
    @FXML
    private TableColumn<Object, Object> tblClmSoldBy;

    userNameMedia nameMedia;

    SellCart sellCart = new SellCart();
    SellCartGerway sellCartGerway = new SellCartGerway();

    String userId;
    @FXML
    private Button btnFacture;
    @FXML
    private Button btnVenteJounalier;
    @FXML
    private JFXDatePicker dateJ;
    String montant;
    ConvertierMontantEnLettre cnt;
    @FXML
    private Button btnnotedachat;
    @FXML
    private Button btnSellDelete;
    TextInputDialog dialog;

    public void setNameMedia(userNameMedia nameMedia) {
        userId = nameMedia.getId();
        this.nameMedia = nameMedia;
    }

    private Connection con;
    bddConnection dbcon;
    BddPropreties dBProperties = new BddPropreties();
    String db = dBProperties.loadPropertiesFile();
    PreparedStatement pst;
    PreparedStatement pst1;
    PreparedStatement pst3;
    PreparedStatement pst4;
    PreparedStatement pst31;
    PreparedStatement pst41;
    ResultSet rs;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dateJ.setValue(LocalDate.now());
        tblSellView.setOnMouseClicked((MouseEvent event) -> {
            System.out.println("Clicked");
        });
    }

    @FXML
    private void tfSearchOnKeyReleased(KeyEvent event) {
        tblSellView.getItems().clear();
        sellCart.sellID = tfSearch.getText();
        sellCartGerway.searchView(sellCart);
    }

    @FXML
    private void btnRefreshOnAction(ActionEvent event) {
        tblSellView.getItems().clear();
        tfSearch.clear();
        sellCart.carts.clear();
        tblSellView.setItems(sellCart.soldList);
        tblClmCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tblClmSellId.setCellValueFactory(new PropertyValueFactory<>("sellId"));
        tblClmProductId.setCellValueFactory(new PropertyValueFactory<>("productGId"));
        tblClmSoldDate.setCellValueFactory(new PropertyValueFactory<>("sellDate"));
        //  tblClmPursrsPrice.setCellValueFactory(new PropertyValueFactory<>("pursesPrice"));
        //  tblClmSellPrice.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));
        tblClmQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tblClmTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        //  tblClmWarrenty.setCellValueFactory(new PropertyValueFactory<>("warrentyVoidDate"));
        tblClmSoldBy.setCellValueFactory(new PropertyValueFactory<>("productname"));

        sellCartGerway.view(sellCart);
    }

    @FXML
    private void btnSellOrderOnAction(ActionEvent event) {
        System.out.println(userId);
        NewSellController acc = new NewSellController();
        userNameMedia media = new userNameMedia();
        FXMLLoader fXMLLoader = new FXMLLoader();
        fXMLLoader.setLocation(getClass().getResource("/gui/app/vente/NewSell.fxml"));
        try {
            fXMLLoader.load();
            Parent parent = fXMLLoader.getRoot();
            Scene scene = new Scene(parent);
            scene.setFill(new Color(0, 0, 0, 0));
            NewSellController newSellController = fXMLLoader.getController();
            media.setId(userId);
            newSellController.setNameMedia(nameMedia);
            newSellController.genarateSellID();
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ViewCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void viewDetails() {
        tblSellView.setItems(sellCart.soldList);
        tblClmCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tblClmSellId.setCellValueFactory(new PropertyValueFactory<>("sellId"));
        tblClmProductId.setCellValueFactory(new PropertyValueFactory<>("productGId"));
        tblClmSoldDate.setCellValueFactory(new PropertyValueFactory<>("sellDate"));
        //tblClmPursrsPrice.setCellValueFactory(new PropertyValueFactory<>("pursesPrice"));
        // tblClmSellPrice.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));
        tblClmQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tblClmTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        //tblClmWarrenty.setCellValueFactory(new PropertyValueFactory<>("warrentyVoidDate"));
        tblClmSoldBy.setCellValueFactory(new PropertyValueFactory<>("productname"));
        sellCartGerway.firstTenView(sellCart);
    }

    @FXML
    private void btnFactureOnAction(ActionEvent event) throws IOException {
        if (!tblSellView.getSelectionModel().isEmpty()) {
            sortieFacture();
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Vide");
            a.setHeaderText("Selection vide");
            a.setContentText("Veuillez selectionner une vente");
            a.showAndWait();
        }

    }

    public void sortieFacture() throws IOException {

        ListSold lst = tblSellView.getSelectionModel().getSelectedItem();
        String items = lst.getId();
        int itemsInt = Integer.parseInt(items);
        System.out.println("items int est " + itemsInt);
        String lettre;
        try {
            dbcon = new bddConnection();
            try {
                con = dbcon.mkDataBase();
                pst = con.prepareStatement("SELECT * FROM " + db + ".vente WHERE Id= " + itemsInt + "");

                rs = pst.executeQuery();
                while (rs.next()) {
                    lettre = rs.getString(6);
                    String dt = rs.getString(8);
                    pst1 = con.prepareStatement("select sum(TotalPrix) as SommeTotalPrix from bddgestionstock.vente where bddgestionstock.vente.DateDVente='" + dt + "'");
                    rs = pst1.executeQuery();
                    while (rs.next()) {
                        String smTP = rs.getString(1);
                        int a = Integer.parseInt(smTP);
                        cnt = new ConvertierMontantEnLettre();
                        cnt.setMontant("" + a + ".00");
                        cnt.calculer_glob();
                        String chiffreEnLettre = cnt.getMontantLettre();
                        HashMap params = new HashMap();
                        params.put("idvente", dt);
                        params.put("lettre", chiffreEnLettre);
                        params.put("sommeT", smTP);

                        InputStream is = getClass().getResourceAsStream("/reports/facture.jrxml");
                        JasperDesign jd = JRXmlLoader.load(is);
                        JasperReport report = JasperCompileManager.compileReport(jd);
                        JasperPrint print = JasperFillManager.fillReport(report, params, con);
                        JasperViewer.viewReport(print, false);
                    }

                }
            } catch (SQLException ex) {
                Logger.getLogger(ViewSellController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (JRException e) {
            Logger.getLogger(ViewSellController.class.getName()).log(Level.SEVERE, null, e);
            System.out.println(e);
        }
    }

    @FXML
    private void btnVenteJounalierOnAction(ActionEvent event) throws SQLException {
        System.out.println(dateJ.getValue());
        try {
            sortieVenteJounalier();
        } catch (JRException ex) {
            Logger.getLogger(ViewSellController.class.getName()).log(Level.SEVERE, null, ex);
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Vide");
            a.setHeaderText("La date selectionné ne contient aucune vente");
            a.setContentText(ex.getMessage());
            a.showAndWait();
        }
    }

    public void sortieVenteJounalier() throws SQLException, JRException {

        String s = dateJ.getValue().toString();
        String s1=s.substring(0, 10);

        try {
            dbcon = new bddConnection();
            con = dbcon.mkDataBase();
            System.out.println(s);
            pst = con.prepareStatement("SELECT SUM(TotalPrix) AS SommeTotalPrix FROM " + db + ".vente WHERE DateDVente LIKE '"+s1+"%'"); //2018-03-01 16:09:52
            rs = pst.executeQuery();//2018-03-06
            
            while (rs.next()){
                String totalvaleur=rs.getString(1);
                System.out.println(totalvaleur);
            HashMap params = new HashMap();
            params.put("date", s + "%");
            params.put("prixTotal",totalvaleur);
             System.out.println(totalvaleur);

            InputStream is = getClass().getResourceAsStream("/reports/venteJ.jrxml");
            JasperDesign jd = JRXmlLoader.load(is);
            JasperReport report = JasperCompileManager.compileReport(jd);
            JasperPrint print = JasperFillManager.fillReport(report, params, con);
            JasperViewer.viewReport(print, false);}
            
        } catch (JRException e) {
            Logger.getLogger(ViewSellController.class.getName()).log(Level.SEVERE, null, e);
            System.out.println(e);
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Erreur");
            a.setHeaderText("Erreur:");
            a.setContentText(e.getMessage());
            a.showAndWait();
        }
    }

    @FXML
    private void btnnotedAchatOnAction(ActionEvent event) {
        if (!tblSellView.getSelectionModel().isEmpty()) {
            try {
                sortieOV();
            } catch (SQLException | JRException ex) {
                Logger.getLogger(ViewSellController.class.getName()).log(Level.SEVERE, null, ex);
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Erreur");
                a.setHeaderText("Erreur");
                a.setContentText(ex.getMessage());
                a.showAndWait();
            }
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Vide");
            a.setHeaderText("Selection vide");
            a.setContentText("Veuillez selectionner une vente");
            a.showAndWait();
        }
    }

    public void sortieOV() throws SQLException, JRException {

        ListSold lst = tblSellView.getSelectionModel().getSelectedItem();
        String items = lst.getId();
        int itemsInt = Integer.parseInt(items);
        System.out.println("items int est " + itemsInt);
        String lettre;

        try {
            dbcon = new bddConnection();
            con = dbcon.mkDataBase();

            pst = con.prepareStatement("SELECT * FROM " + db + ".vente WHERE Id= " + itemsInt + "");

            rs = pst.executeQuery();
            while (rs.next()) {
                lettre = rs.getString(6);
                String dt = rs.getString(8);
                pst1 = con.prepareStatement("select sum(TotalPrix) as SommeTotalPrix from bddgestionstock.vente where bddgestionstock.vente.DateDVente='" + dt + "'");
                rs = pst1.executeQuery();
                while (rs.next()) {
                    String smTP = rs.getString(1);
                    int a = Integer.parseInt(smTP);
                    cnt = new ConvertierMontantEnLettre();
                    cnt.setMontant("" + a + ".00");
                    cnt.calculer_glob();
                    String chiffreEnLettre = cnt.getMontantLettre();
                    HashMap params = new HashMap();
                    params.put("idvente", dt);
                    params.put("lettre", chiffreEnLettre);
                    params.put("sommeT", smTP);

                    InputStream is = getClass().getResourceAsStream("/reports/facture_1.jrxml");
                    JasperDesign jd = JRXmlLoader.load(is);
                    JasperReport report = JasperCompileManager.compileReport(jd);
                    JasperPrint print = JasperFillManager.fillReport(report, params, con);
                    JasperViewer.viewReport(print, false);
                }

            }

        } catch (JRException e) {
            Logger.getLogger(ViewSellController.class.getName()).log(Level.SEVERE, null, e);
            System.out.println(e);
        }
    }

    @FXML
    private void btnSellDeleteOnAction(ActionEvent event) {
        if (tblSellView.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Confirmation");
            alert.setContentText("Voulez vous vraiment effectuer l'operation d retour pour le vente?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                ListSold lst = tblSellView.getSelectionModel().getSelectedItem();
                String items = lst.getId();
                int itemsInt = Integer.parseInt(items);
                System.out.println("items int est " + itemsInt);
                dialog = new TextInputDialog();
                dialog.setTitle("Nombre");
                dialog.setHeaderText("Entrer le nombre des produits à rendre");
                dialog.setContentText("Nombre(s) de(s) produit(s):");

                Optional<String> r = dialog.showAndWait();

                r.ifPresent(name -> {
                    if (name.matches("0")) {
                        Alert al = new Alert(Alert.AlertType.ERROR);
                        al.setTitle("Erreur");
                        al.setHeaderText("Nombre invalide");
                        al.setContentText("Impossible de faire le retour de produit dont le nombre est zéro (0)");
                        al.showAndWait();
                    } else {
                        int nombreRetour = Integer.parseInt(name);
                        try {
                            dbcon = new bddConnection();
                            con = dbcon.mkDataBase();
                            pst = con.prepareStatement("SELECT * FROM " + db + ".vente WHERE Id= " + itemsInt + "");
                            rs = pst.executeQuery();
                            while (rs.next()) {
                                int idProduit = Integer.parseInt(rs.getString(4));
                                int quantité = Integer.parseInt(rs.getString(5));
                                int prixVente = Integer.parseInt(rs.getString(6));
                                if (nombreRetour <= quantité) {
                                    pst1 = con.prepareStatement("SELECT * FROM " + db + ".produits WHERE Id='" + idProduit + "'");
                                    rs = pst1.executeQuery();
                                    while (rs.next()) {
                                        int qttdProduits = Integer.parseInt(rs.getString(4));
                                        int prixProduits = Integer.parseInt(rs.getString(9));
                                        int prixProduit = Integer.parseInt(rs.getString(9));
                                        int qttproduitactuel = qttdProduits + nombreRetour;
                                        int qttventeActuel = quantité - nombreRetour;
                                        int prixventeactuel = (nombreRetour * prixProduit) + prixVente;
                                        pst3 = con.prepareStatement("UPDATE " + db + ".produits SET Quantite='" + qttproduitactuel + "' WHERE Id='"+idProduit+"'");
                                        pst3.executeUpdate();
                                        if (qttventeActuel == 0) {
                                            pst4 = con.prepareStatement("DELETE FROM " + db + ".vente WHERE Id='" + itemsInt + "'");
                                            pst4.executeUpdate();
                                        } else if (qttventeActuel > 0) {
                                            pst4 = con.prepareStatement("UPDATE " + db + ".vente SET Quantite='" + qttventeActuel + "' WHERE Id='"+itemsInt+"'");
                                            pst41 = con.prepareStatement("UPDATE " + db + ".vente SET TotalPrix='" + prixventeactuel + "' WHERE Id='"+itemsInt+"'");
                                            pst4.executeUpdate();
                                            pst41.executeUpdate();
                                        } else {

                                        }
                                    }
                                    Alert al = new Alert(Alert.AlertType.INFORMATION);
                                    al.setTitle("Reussi");
                                    al.setHeaderText("Reussi");
                                    al.setContentText("Retour de vente reussi");
                                    al.showAndWait();
                                } else {
                                    Alert al = new Alert(Alert.AlertType.WARNING);
                                    al.setTitle("Erreur");
                                    al.setHeaderText("Nombre invalide");
                                    al.setContentText("Le nombre de retour est plus grand que le nombre celle d'achat");
                                    al.showAndWait();
                                }
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(ViewSellController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });

            }
        } else {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("Selection vide");
            al.setHeaderText("Selection invalide");
            al.setContentText("Veuiller selectionner une valeur dans la table");
            al.showAndWait();
        }
    }

}
