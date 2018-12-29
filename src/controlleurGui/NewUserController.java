/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlleurGui;

import bdd.SQLSyntax;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import list.Utilisateurs;
import utilisateurRec.UtilRecup;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class NewUserController implements Initializable {

    @FXML
    private Hyperlink hlLogin;
    @FXML
    private JFXTextField tfUserName;
    @FXML
    private JFXTextField tfFullName;
    @FXML
    private JFXTextField pfUserPassword;
    @FXML
    private JFXTextField pfReUserPassword;
    @FXML
    private JFXButton btnSignUp;
    Utilisateurs users=new Utilisateurs();
    UtilRecup usersGetway= new UtilRecup();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void hlLogin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/login.fxml"));
        Scene scene = new Scene(root);
        Stage nStage = new Stage();
        nStage.setScene(scene);
        nStage.setMaximized(true);
        nStage.setTitle("Inscription- Gestion des stock");
        nStage.getIcons().add(new Image("/image/OM.png"));
        nStage.show();

        Stage hlLoginStage = (Stage) hlLogin.getScene().getWindow();
        hlLoginStage.close();
    }

    @FXML
    private void btnRegistration(ActionEvent event) {
        SQLSyntax sql = new SQLSyntax();
        if (isValidCondition()) {
            users.nom = tfUserName.getText();
            users.prenom = tfUserName.getText();
            users.mpd = pfUserPassword.getText();
            usersGetway.save(users);
            sql.basicPermission(tfUserName.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Se connecter maintenant");
            alert.setHeaderText("Se connecter maintenant");
            alert.setContentText("Votre compte administrateur a été ajouté avec succes \n cliquer sur ok pour se connecter");
            alert.initStyle(StageStyle.UNDECORATED);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    hlLogin(event);
                } catch (IOException ex) {
                    Logger.getLogger(NewUserController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            
        }
    }

    @FXML
    private void pfKeyTyped(KeyEvent event) {
        if (pfUserPassword.getText().matches(pfReUserPassword.getText())) {
            System.out.println("Pass Match");
        } else {
            System.out.println("Pass Not Match");
        }
    }
    
    
    
    private boolean isValidCondition() {
        boolean registration = false;
        if (nullChecq() && passMatch()) {
            System.out.println("Condition valid");
            registration = true;
        } else {
            System.out.println("Condition Invalid");
            registration = false;
        }
        return registration;
    }

    private boolean nullChecq() {
        boolean nullChecq = false;
        if (tfUserName.getText().trim().isEmpty()
                || tfFullName.getText().trim().isEmpty()
                || pfUserPassword.getText().isEmpty()
                || pfReUserPassword.getText().isEmpty()) {

            System.out.println("Empty user Name");
            nullChecq = false;
        } else {
            System.out.println("User Name not Empty");
            nullChecq = true;
        }
        return nullChecq;
    }

    private boolean passMatch() {
        boolean passMatch = false;
        String password = pfUserPassword.getText();
        String rePass = pfReUserPassword.getText();

        if (password.matches(rePass)) {
            System.out.println("Password Match");
            passMatch = true;

        } else {
            System.out.println("Password Not Match");
            passMatch = false;
        }
        return passMatch;

    }
}
