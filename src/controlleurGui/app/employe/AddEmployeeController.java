/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlleurGui.app.employe;

import bdd.BddPropreties;
import bdd.bddConnection;
import gettersSetters.CustomTf;
import gettersSetters.UsersGetWay;
import gettersSetters.userNameMedia;
import java.awt.image.BufferedImage;
import java.io.File;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import list.Utilisateurs;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class AddEmployeeController implements Initializable {

    private File file;
    private BufferedImage bufferedImage;
    private Image image;
    
    private String imagePath;
    private String usrId;
    private userNameMedia nameMedia;
    
    @FXML
    private Button btnAttachImage;
    @FXML
    private Button btnSave;
    @FXML
    private TextArea taAddress;
    @FXML
    private ImageView imvUsrImg;
    @FXML
    private TextField tfUserName;
    @FXML
    private TextField tfFullName;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfPhone;
    @FXML
    private TextField tfPassword;
    @FXML
    public Button btnClrUsrName;
    @FXML
    public Button btnClrFullName;
    @FXML
    public Button btnClrEmail;
    @FXML
    public Button btnClrPhone;
    @FXML
    public Button btnClrSalary;
    
    BddPropreties dBProperties = new BddPropreties();
    String db = dBProperties.loadPropertiesFile();

    Utilisateurs users = new Utilisateurs();
    UsersGetWay usersGetway = new UsersGetWay();

    

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
        // TODO
        CustomTf cTf = new CustomTf();
        cTf.clearTextFieldByButton(tfUserName, btnClrUsrName);
        cTf.clearTextFieldByButton(tfFullName, btnClrFullName);
        cTf.clearTextFieldByButton(tfEmail, btnClrEmail);
        cTf.clearTextFieldByButton(tfPhone, btnClrPhone);
       // cTf.clearTextFieldByButton(tfPassword, btnClrPassword);

        
        BooleanBinding binding = tfUserName.textProperty().isEmpty()
                .or(tfFullName.textProperty().isEmpty()
                .or(tfPhone.textProperty().isEmpty())
                .or(tfPassword.textProperty().isEmpty()));
        btnSave.disableProperty().bind(binding);
    }    

    @FXML
    private void btnAttachImageOnAction(ActionEvent event) throws IOException {
         FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG (Joint Photographic Group)", "*.jpg"),
                new FileChooser.ExtensionFilter("JPEG (Joint Photographic Experts Group)", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG (Portable Network Graphics)", "*.png")
        );

        fileChooser.setTitle("Choisir une image");

        file = fileChooser.showOpenDialog(null);

        if (file != null) {
            System.out.println(file);
            bufferedImage = ImageIO.read(file);
            image = SwingFXUtils.toFXImage(bufferedImage, null);
            imvUsrImg.setImage(image);
            imagePath = file.getAbsolutePath();
        }
    }

    @FXML
    private void btnSaveOnAction(ActionEvent event) {
        users.nom = tfUserName.getText();
        users.prenom = tfFullName.getText();
        users.email = tfEmail.getText();
        users.telephone = tfPhone.getText();
        users.addresse = taAddress.getText();
        users.mpd = tfPassword.getText();
        users.imagePath = imagePath;
        users.createurId = usrId;
        usersGetway.save(users);
        basicPermission();
    }
    
    private void basicPermission(){
        bddConnection dbCon = new bddConnection();
        Connection con;
        ResultSet rs;
        PreparedStatement pst;
        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("Select Id FROM "+db+".utilisateur where Nom=?");
            pst.setString(1, tfUserName.getText());
            rs = pst.executeQuery();
            while (rs.next()) {
                pst = con.prepareStatement("insert into "+db+".droitutilisateur values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                pst.setString(1, null);
                pst.setInt(2, 0);
                pst.setInt(3, 0);
                pst.setInt(4, 0);
                pst.setInt(5, 0);
                pst.setInt(6, 0);
                pst.setInt(7, 0);
                pst.setInt(8, 0);
                pst.setInt(9, 0);
                pst.setInt(10, 0);
                pst.setInt(11, 0);
                pst.setInt(12, 0);
                pst.setInt(13, 0);
                pst.setInt(14, 0);
                pst.setInt(15, 0);
                pst.setInt(16, rs.getInt("Id"));

                pst.executeUpdate();

            }

        } catch (SQLException ex) {
            Logger.getLogger(AddEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
