/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlleurGui.app.employe;

import bdd.BddPropreties;
import bdd.SQLSyntax;
import bdd.bddConnection;
import gettersSetters.CustomPf;
import gettersSetters.CustomTf;
import gettersSetters.History;
import gettersSetters.ListUtilisateurs;
import gettersSetters.UsersGetWay;
import gettersSetters.userNameMedia;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
public class ViewEmployeController implements Initializable {
    
    CustomPf cPf = new CustomPf();
    CustomTf cTf = new CustomTf();
    Utilisateurs users = new Utilisateurs();
    UsersGetWay usersGetway = new UsersGetWay();
    SQLSyntax sql = new SQLSyntax();
    bddConnection dbCon = new bddConnection();
    
    
    BddPropreties dBProperties = new BddPropreties();
    String db = dBProperties.loadPropertiesFile();

    private File file;
    private BufferedImage bufferedImage;
    private String imagePath;
    private Image image;
    private Blob blobImage;

    Connection con = dbCon.geConnection();
    PreparedStatement pst;
    ResultSet rs;

    private String userId;
    private String name;
    private String id;
    private userNameMedia nameMedia;
    private String creatorId;
    private String creatorName;

    @FXML
    private TextField tfSearch;
    @FXML
    private TableView<ListUtilisateurs> tblEmoyeeList;
    @FXML
    private TableColumn<Object, Object> clmEmployeId;
    @FXML
    private TableColumn<Object, Object> clmEmployeName;
    @FXML
    private TextField tfUserName;
    @FXML
    private TextField tfFullName;
    @FXML
    private TextField tfPhoneNumber;
    @FXML
    private TextField tfEmailAddress;
    @FXML
    private TextField tfDateofJoin;
    @FXML
    private TextField tfCreatedBy;
    @FXML
    private Rectangle recUsrImage;
    @FXML
    private Button btnAttachImage;
    @FXML
    private TextArea taAddress;
    @FXML
    public Button btnClrFulNametf;
    @FXML
    public Button btnClrPhonetf;
    @FXML
    public Button btnClrEmailtf;
    @FXML
    public Button btnClrSalarytf;
    @FXML
    private Button btnClrDatestf;
    @FXML
    public Button btnClrCreatortf;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    private Hyperlink hlChangePassword;
    @FXML
    private Hyperlink hlViewPermission;
    @FXML
    private Label lblCreator;
    
     Image usrImg = new Image("/img/a.png");

    public userNameMedia getNameMedia() {
        return nameMedia;
    }

    public void setNameMedia(userNameMedia nameMedia) {
        userId = nameMedia.getId();
        name = nameMedia.getNom();
        this.nameMedia = nameMedia;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cTf.clearTextFieldByButton(tfFullName, btnClrFulNametf);
        cTf.clearTextFieldByButton(tfEmailAddress, btnClrEmailtf);
        cTf.clearTextFieldByButton(tfPhoneNumber, btnClrPhonetf);
        cTf.clearTextFieldByButton(tfDateofJoin, btnClrDatestf);
        cTf.clearTextFieldByButton(tfCreatedBy, btnClrCreatortf);

    }    

    @FXML
    private void tfSearchOnAction(ActionEvent event) {
    }


    @FXML
    public void tblEmloyeOnClick(MouseEvent event) {
        setselectedView();
    }

    @FXML
    private void tblViewOnClick(KeyEvent event) {
         if (event.getCode().equals(KeyCode.UP)) {
            setselectedView();
        } else if (event.getCode().equals(KeyCode.DOWN)) {
            setselectedView();
        }
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
            recUsrImage.setFill(new ImagePattern(image));
            imagePath = file.getAbsolutePath();
        }

    }

    @FXML
    private void btnUpdateOnAction(ActionEvent event) {
        users.nom = tfUserName.getText();
        users.prenom = tfFullName.getText();
        users.email = tfEmailAddress.getText();
        users.telephone = tfPhoneNumber.getText();
        users.addresse = taAddress.getText();
        users.image = usrImg;
        users.imagePath = imagePath;
        users.createurId = userId;
        usersGetway.update(users);
    }

    @FXML
    private void btnDeleteOnAction(ActionEvent event) {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression");
        alert.setHeaderText("Etes vous sur ?");
        alert.setContentText("Pour comfirmer la suppression \n Cliquer sur ok");
        alert.initStyle(StageStyle.UNDECORATED);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            usersGetway.selectedView(users);
            usersGetway.delete(users);
        }
        
        tblEmoyeeList.getItems().clear();
        showDetails();
    }

    @FXML
    private void hlViewUpdateHistory(ActionEvent event) {
        String emp = "Employee";
        History history = new History();
        history.viewText(emp, tfUserName.getText(), name);
        System.out.println("view");
    }


    @FXML
    private void hlViewPermissionOnAction(ActionEvent event) throws IOException {
        usersGetway.selectedView(users);
        id = users.id;

        EmployeePermissionController pcc = new EmployeePermissionController();
        userNameMedia usrID = new userNameMedia();
        FXMLLoader loader = new FXMLLoader();
        System.out.println(id);
        loader.setLocation(getClass().getResource("/gui/app/employe/EmployeePermission.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        scene.setFill(new Color(0, 0, 0, 0));
        EmployeePermissionController PermissionController = loader.getController();
        nameMedia.setId(id);
        PermissionController.setMedia(nameMedia);
        PermissionController.checqPermission();
        Stage nStage = new Stage();
        nStage.setScene(scene);
        nStage.initModality(Modality.APPLICATION_MODAL);
        nStage.initStyle(StageStyle.TRANSPARENT);
        nStage.show();
    }
    
     public void setselectedView() {
        clearAll();
        ListUtilisateurs employeeList = tblEmoyeeList.getSelectionModel().getSelectedItem();
        if (employeeList != null) {
            users.id = employeeList.getUtilisateurId();
            usersGetway.selectedView(users);
            id = users.id;
            tfUserName.setText(users.nom);
            tfFullName.setText(users.prenom);
            tfPhoneNumber.setText(users.telephone);
            tfEmailAddress.setText(users.email);
            creatorId = users.createurId;
            taAddress.setText(users.addresse);
            image = users.image;
            recUsrImage.setFill(new ImagePattern(image));
            sql.creatorNameFindar(creatorId, lblCreator);
            tfCreatedBy.setText(lblCreator.getText());
            
            if (users.id.matches("1")) {
                btnUpdate.setVisible(false);
                btnDelete.setVisible(false);
                hlChangePassword.setVisible(false);
                hlViewPermission.setVisible(false);
            } else {
                btnUpdate.setVisible(true);
                btnDelete.setVisible(true);
                hlChangePassword.setVisible(true);
                hlViewPermission.setVisible(true);
            }

        }
    }

    public void showDetails() {
        tblEmoyeeList.setItems(users.employeeLists);
        clmEmployeId.setCellValueFactory(new PropertyValueFactory<>("utilisateurId"));
        clmEmployeName.setCellValueFactory(new PropertyValueFactory<>("utilisateurNom"));
        usersGetway.view(users);

    }

   /* public void checqPermission() {
        try {
            pst = con.prepareStatement("select * from "+db+".droitutilisateur where UserId=?");
            pst.setString(1, userId);
            rs = pst.executeQuery();
            while (rs.next()) {
                if (rs.getInt(13) != 1) {
                    hlChangePassword.setDisable(true);
                } else {
                    hlChangePassword.setDisable(false);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ViewEmployeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/

    private void clearAll() {
        tfUserName.clear();
        tfFullName.clear();
        tfCreatedBy.clear();
        tfEmailAddress.clear();
        tfDateofJoin.clear();
        tfPhoneNumber.clear();
        taAddress.clear();
    }
}
