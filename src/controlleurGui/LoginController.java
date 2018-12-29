/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlleurGui;

import bdd.BddPropreties;
import bdd.bddConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
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
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class LoginController implements Initializable {

    @FXML
    private JFXButton btnConnecter;
    @FXML
    private JFXTextField FieldNom;
    @FXML
    private JFXPasswordField FieldPass;
    @FXML
    private Hyperlink creercompte;
    @FXML
    private Hyperlink pmbdd;
    private Connection con;
    private PreparedStatement pst;
    ResultSet rs;
    
    BddPropreties dBProperties = new BddPropreties();
    String db = dBProperties.loadPropertiesFile();
    @FXML
    private BorderPane apMother;
    @FXML
    private ImageView numero;
    @FXML
    private Label num;
    @FXML
    private Label nm;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Tooltip tp=new Tooltip();
        tp.setText("Numero 032 72 807 75 / 032 57 164 63");
        num.setTooltip(tp);
        nm.setTooltip(tp);
        
        TranslateTransition transition=new TranslateTransition();
        transition.setDuration(Duration.seconds(1));
        transition.setNode(btnConnecter);
        transition.setToY(-30);
        transition.setAutoReverse(true);
        transition.setCycleCount(TranslateTransition.INDEFINITE);
        transition.play();
    }    

    @FXML
    private void createCompte(ActionEvent event) {
        
        bddConnection dbCon = new bddConnection();
        con = dbCon.geConnection();
        if (con != null) {
            try {
                pst = con.prepareStatement("SELECT Id FROM " + db + ".Utilisateur ORDER BY Id ASC LIMIT 1");
                rs = pst.executeQuery();
                if (rs.next()) {
                    apMother.setOpacity(0.7);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Erreur");
                    alert.setContentText("Vous ne pouvez pas creer un compte sans \n la permission de l'Administrateur");
                    alert.initStyle(StageStyle.UNDECORATED);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        apMother.setOpacity(1.0);
                    }
                    return;
                }
                con.close();
                pst.close();
                rs.close();
                loadRegistration();

            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur : Serveur Introuvable");
            alert.setContentText("Assurez vous que votre Mysql est activé, \n");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
        }

        
    }

    @FXML
    private void paramBd(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/ConfigureServeur.fxml"));
            Scene scene = new Scene(root);
            Stage nStage = new Stage();
            nStage.setScene(scene);
            nStage.setMaximized(false);
            nStage.setTitle("Status du Serveur");
            nStage.getIcons().add(new Image("/image/OM.png"));
            nStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void actConnecter(ActionEvent event) throws IOException {
                bddConnection dbCon = new bddConnection();
        con = dbCon.geConnection();
        if (con != null) {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/App.fxml"));
            loader.load();
            Parent parent = loader.getRoot();
            Scene adminPanelScene = new Scene(parent);
            Stage adminPanelStage = new Stage();
            adminPanelStage.setMaximized(true);
            if (isValidCondition()) {
                try {
                    pst = con.prepareStatement("select * from " + db + ".utilisateur where nom=? and Mdp=? ");
                    pst.setString(1, FieldNom.getText());
                    pst.setString(2, FieldPass.getText());
                    rs = pst.executeQuery();
                    if (rs.next()) {
                        userNameMedia usrNameMedia = new userNameMedia(rs.getString(1), rs.getString(2));
                        AppController apControl = loader.getController();
                        apControl.setUsrNameMedia(usrNameMedia);
                        apControl.permission();
                        apControl.viewDetails();
                        adminPanelStage.setScene(adminPanelScene);
                        adminPanelStage.setTitle("Utilisateur "+rs.getString(3));
                        adminPanelStage.getIcons().add(new Image("/image/OM.png"));
                        adminPanelStage.show();
                        apControl.btnHomeOnClick(event);

                        Stage stage = (Stage) btnConnecter.getScene().getWindow();
                        stage.close();
                    } else {
                        System.out.println("Password Not Match");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Mot de passe incorect");
                        alert.setHeaderText("Erreur: Login ou le mot de passe est incorrect");
                        alert.setContentText("Verifier le mot de passe ou le nom d'utilisateur \nVeuillez ressayer");
                        alert.initStyle(StageStyle.UNDECORATED);
                        alert.showAndWait();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                           Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur");
                        alert.setHeaderText("Erreur");
                        alert.setContentText(ex.getMessage());
                        alert.showAndWait();
                }

            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur : Serveur Introuvable");
            alert.setContentText("Assurer que le serveur est en marche, \n");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
        }

    
    }
    
        private void loadRegistration() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/NewUser.fxml"));
            Scene scene = new Scene(root);
            Stage nStage = new Stage();
            nStage.setScene(scene);
            nStage.setMaximized(true);
            nStage.setTitle("Inscription");
            nStage.getIcons().add(new Image("/image/OM.png"));
            nStage.show();
            Stage stage = (Stage) creercompte.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
            private boolean isValidCondition() {
        boolean validCondition = false;
        if (FieldNom.getText().trim().isEmpty()
                || FieldPass.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement :");
            alert.setHeaderText("Avertissement !!");
            alert.setContentText("Veuiller entrer votre nom et le mot de passe");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();

            validCondition = false;
        } else {
            validCondition = true;
        }
        return validCondition;
    }
}
