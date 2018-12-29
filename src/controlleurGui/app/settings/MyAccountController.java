/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlleurGui.app.settings;

import bdd.BddPropreties;
import bdd.bddConnection;
import gettersSetters.UsersGetWay;
import gettersSetters.userNameMedia;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.BooleanBinding;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import list.Utilisateurs;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class MyAccountController implements Initializable {

    @FXML
    private TextField tfUserName;
    @FXML
    private TextField tfFullName;
    @FXML
    private TextField tfContractNumber;
    @FXML
    private TextField tfEmailAddress;
    @FXML
    private TextArea taAddress;
    @FXML
    private Button btnSave;
    @FXML
    private Hyperlink hlChangePassword;
    @FXML
    private Rectangle retImage;
    @FXML
    private Button attachImage;
    
     private Image image;

    private File file;

    private FileInputStream fileInput;

    private FileOutputStream fileOutput;

    private byte[] userImage;

    private String imgPath;

    Utilisateurs users = new Utilisateurs();
    UsersGetWay usersGetway = new UsersGetWay();

    bddConnection dbCon = new bddConnection();
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    
    BddPropreties dBProperties = new BddPropreties();
    String db = dBProperties.loadPropertiesFile();

    private String userID;
    private userNameMedia usrMediaID;
    
    public userNameMedia getUsrMediaID() {
        return usrMediaID;
    }

    public void setUsrMediaID(userNameMedia usrMediaID) {
        userID = usrMediaID.getId();
        this.usrMediaID = usrMediaID;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        BooleanBinding boolenBinding = tfFullName.textProperty().isEmpty()
                .or(tfEmailAddress.textProperty().isEmpty()
                        .or(tfContractNumber.textProperty().isEmpty()));

        btnSave.disableProperty().bind(boolenBinding);
    }    

    @FXML
    private void btnSaveOnAction(ActionEvent event) {
        users.nom = tfUserName.getText();
        users.prenom = tfFullName.getText();
        users.email = tfEmailAddress.getText();
        users.addresse = taAddress.getText();
        users.telephone = tfContractNumber.getText();
        users.imagePath = imgPath;
        users.image = image;
        usersGetway.update(users);
    }

    @FXML
    private void hlChangePasswordOnClick(ActionEvent event) throws IOException {

                    PassChangeController pcc = new PassChangeController();
                    userNameMedia nameMedia = new userNameMedia();

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/gui/app/settings/PassChange.fxml"));
                    loader.load();
                    Parent root = loader.getRoot();
                    Scene scene = new Scene(root);
                    scene.setFill(new Color(0, 0, 0, 0));
                    PassChangeController passChangeController = loader.getController();
                    nameMedia.setId(userID);
                    passChangeController.setNameMedia(nameMedia);
                    Stage nStage = new Stage();
                    nStage.setScene(scene);
                    nStage.setTitle("gestion des stock");
                    nStage.initModality(Modality.APPLICATION_MODAL);
                    nStage.initStyle(StageStyle.TRANSPARENT);
                    nStage.show();

            }
 
    
    public void showDetails() {
        users.id = userID;
        usersGetway.selectedView(users);
        tfUserName.setText(users.nom);
        tfFullName.setText(users.prenom);
        tfContractNumber.setText(users.telephone);
        tfEmailAddress.setText(users.email);
        taAddress.setText(users.addresse);
        image = users.image;
        retImage.setFill(new ImagePattern(image));
    }

    public void accountPermission() {
        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("select * from "+db+".droitutilisateur where UserId=?");

        } catch (SQLException ex) {
            Logger.getLogger(MyAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void attachImageOnAction(ActionEvent event) throws IOException {
         FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterjpg = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterpng = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");

        fileChooser.getExtensionFilters().addAll(extFilterjpg, extFilterpng);

        file = fileChooser.showOpenDialog(null);

        if (file != null) {
            if (file.length() < 6000000) {
                System.out.print("Condition ok");
                System.out.println(file.length());
                BufferedImage bufferedImage = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                retImage.setFill(new ImagePattern(image));
                imgPath = file.getAbsolutePath();
            } else {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Refusé");
                alert.setHeaderText("Taille de image");
                alert.setContentText("Votre image est volumineux \nVeuiller choisir une autre image");
                alert.initStyle(StageStyle.UNDECORATED);

            }

        }
    }
    
    private boolean nullCheck() {
        boolean notNull;

        if (tfFullName.getText().trim().length() == 0
                || tfContractNumber.getText().trim().length() == 0
                || tfEmailAddress.getText().trim().length() == 0) {
            notNull = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERREUR");
                alert.setHeaderText("ERREUR ");
                alert.setContentText("Veuiller remplir les champs necessaires");
                alert.initStyle(StageStyle.UNDECORATED);
        } else {
            notNull = true;
            System.out.println("Not Null");
        }
        return notNull;
    }
}
