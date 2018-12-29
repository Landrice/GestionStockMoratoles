/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlleurGui.app.settings;

import bdd.BddPropreties;
import bdd.bddConnection;
import com.jfoenix.controls.JFXButton;
import gettersSetters.CustomPf;
import gettersSetters.userNameMedia;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import list.Utilisateurs;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class PassChangeController implements Initializable {

    @FXML
    private PasswordField pfCurrentPass;
    @FXML
    private PasswordField pfNewPass;
    @FXML
    private PasswordField pfRePass;
    @FXML
    private Button btnClrCurrentPf;
    @FXML
    private Button btnClrRePass;
    @FXML
    private Button btnClrNewPass;
    @FXML
    private ImageView imgCurrentPassMatch;
    @FXML
    private ImageView imgNewPassMatch;
    @FXML
    private Button btnChangePass;
    
    Utilisateurs users = new Utilisateurs();
    private String userId;
    private String userName;
    private userNameMedia nameMedia;
    @FXML
    private JFXButton btnClose;
    
     public userNameMedia getNameMedia() {
        return nameMedia;
    }

    public void setNameMedia(userNameMedia nameMedia) {
        userId = nameMedia.getId();
        userName = nameMedia.getNom();
        this.nameMedia = nameMedia;
    }

    CustomPf customPf = new CustomPf();

    bddConnection dbCon = new bddConnection();
    Connection con;
    ResultSet rs;
    PreparedStatement pst;
    
    BddPropreties dBProperties = new BddPropreties();
    String db = dBProperties.loadPropertiesFile();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        customPf.clearPassFieldByButton(pfCurrentPass, btnClrCurrentPf);
        customPf.clearPassFieldByButton(pfNewPass, btnClrNewPass);
        customPf.clearPassFieldByButton(pfRePass, btnClrRePass);

        BooleanBinding binding = pfCurrentPass.textProperty().isEmpty()
                .or(pfNewPass.textProperty().isEmpty()
                        .or(pfRePass.textProperty().isEmpty()));

        btnChangePass.disableProperty().bind(binding);
    }    

    @FXML
    private void pfOnAction(ActionEvent event) {
        btnChangePassOnAction(event);
    }

    @FXML
    private void pfNewPassWordMatch(KeyEvent event) {
        if (pfNewPass.getText().matches(pfRePass.getText())) {
            System.out.println("Match");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERREUR");
            alert.setHeaderText("ERREUR ");
            alert.setContentText("Mot de passe invalide");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
        }
    }

    @FXML
    private void btnChangePassOnAction(ActionEvent event) {
         if (isCurrentPasswordChecqOk()) {
            if (isPasswordMatch()) {
                updatePassword();
            }

        } else {
            System.out.println("ddd");
        }
    }

    @FXML
    private void btnCloseOnAction(ActionEvent event) {
         Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
    
     private boolean isCurrentPasswordChecqOk() {
        boolean conDitionValid = true;
        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("select * from utilisateur where Id=? and Mdp=?");
            pst.setString(1, userId);
            pst.setString(2, pfCurrentPass.getText());
            rs = pst.executeQuery();
            while (rs.next()) {
                System.out.println("Old Password Match");
                return conDitionValid;
            }
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERREUR");
            alert.setHeaderText("ERREUR ");
            alert.setContentText("Mot de passe invalide");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
            conDitionValid = false;

        } catch (SQLException ex) {
            Logger.getLogger(PassChangeController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return conDitionValid;
    }

    private boolean isPasswordMatch() {
        boolean passMatch;
        if (pfNewPass.getText().matches(pfRePass.getText())) {
            System.out.println("New Password match");
            passMatch = true;

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERREUR");
            alert.setHeaderText("ERREUR ");
            alert.setContentText("Les nouveaux mot de passe ne sont pas identiques");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();

            passMatch = false;
        }
        return passMatch;
    }

    private void updatePassword() {

        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("Update "+db+".utilisateur set Mdp=? where Id=?");
            pst.setString(1, pfNewPass.getText());
            pst.setString(2, userId);
            pst.executeUpdate();

            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucess");
            alert.setHeaderText("Sucess ");
            alert.setContentText("Modification du mot de passe avec succès");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
            

        } catch (SQLException ex) {
            Logger.getLogger(PassChangeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
