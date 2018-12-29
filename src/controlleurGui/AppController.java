/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlleurGui;

import bdd.BddPropreties;
import bdd.bddConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import controlleurGui.app.EmployeController;
import controlleurGui.app.SellController;
import controlleurGui.app.SettingsController;
import controlleurGui.app.StockController;
import gettersSetters.UsersGetWay;
import gettersSetters.userNameMedia;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import list.Utilisateurs;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class AppController implements Initializable {

    @FXML
    private AnchorPane acMain;
    @FXML
    private BorderPane ancprincipal;
    @FXML
    private AnchorPane acDashBord;
    @FXML
    private ScrollPane leftSideBarScroolPan;
    @FXML
    private VBox vbox;
    @FXML
    private JFXButton btnHome;
    @FXML
    private JFXButton btnStore;
    @FXML
    private JFXButton btnSell;
    @FXML
    private JFXButton btnEmplopye;
    @FXML
    private JFXButton btnSettings;
    @FXML
    private JFXButton btnlougout;
    @FXML
    private BorderPane appcontent;
    @FXML
    private AnchorPane acHead;
    @FXML
    private JFXHamburger hamburger;
    private Circle imgUsrTop;
    private Label lblUsrName;
    @FXML
    private StackPane acContent;
    
    bddConnection dbCon = new bddConnection();
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    
     BddPropreties dBProperties = new BddPropreties();
    String db = dBProperties.loadPropertiesFile();
    String usrName;
    String id;
    
    HamburgerSlideCloseTransition hamb;
    private userNameMedia usrNameMedia;
    
    Utilisateurs users = new Utilisateurs();
    UsersGetWay usersGetway = new UsersGetWay();
    
    public userNameMedia getUsrNameMedia() {
        return usrNameMedia;
    }

    public void setUsrNameMedia(userNameMedia usrNameMedia) {
        //lblUserId.setText(usrNameMedia.getId());
     //   lblUsrName.setText(usrNameMedia.getNom());
        id = usrNameMedia.getId();
        usrName = usrNameMedia.getNom();

        this.usrNameMedia = usrNameMedia;
    }
    
    String defultStyle = "-fx-border-width: 0px 0px 0px 5px;"
            + "-fx-border-color:none";

    String activeStyle = "-fx-border-width: 0px 0px 0px 5px;"
            + "-fx-border-color:#FF4E3C";

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        hamb=new HamburgerSlideCloseTransition(hamburger);
       
       hamb.setRate(1);
    }    

     private void employeeActive() {
        btnHome.setStyle(defultStyle);
        btnStore.setStyle(defultStyle);
        btnEmplopye.setStyle(activeStyle);
        btnSettings.setStyle(defultStyle);
    }

    
    @FXML
    public void btnHomeOnClick(ActionEvent event) {
                
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.load(getClass().getResource("/gui/home/home.fxml").openStream());
        } catch (IOException e) {
            
        }
        System.out.println("AIza01");
        AnchorPane root = fxmlLoader.getRoot();
        System.out.println("AIza02");
        acContent.getChildren().clear();
        System.out.println("AIza03");
        acContent.getChildren().add(root);
        System.out.println("AIza04");
//        System.out.println(lblUsrName.getText());
       // System.out.println(lblUserId.getText());
    }

    @FXML
    private void btnStoreOnClick(ActionEvent event) {
        userNameMedia nm = new userNameMedia();
        FXMLLoader fXMLLoader = new FXMLLoader();
        try {
            fXMLLoader.load(getClass().getResource("/gui/app/Stock.fxml").openStream());
            nm.setId(id);
        StockController stockController = fXMLLoader.getController();
        stockController.bpStore.getStylesheets().add("/css/MainStyle.css");
        stockController.setUserId(usrNameMedia);
       stockController.btnStockOnAction(event);
        stockController.settingPermission();
        AnchorPane acPane = fXMLLoader.getRoot();
        acContent.getChildren().clear();
        acContent.getChildren().add(acPane);
        } catch (IOException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Notre erreur est sur: "+ex);
        }
        
        
    }


    @FXML
    private void btnEmplopyeOnClick(ActionEvent event) throws IOException {
        employeeActive();
        EmployeController ec = new EmployeController();
        userNameMedia nm = new userNameMedia();
        FXMLLoader fXMLLoader = new FXMLLoader();
        fXMLLoader.load(getClass().getResource("/gui/app/Employe.fxml").openStream());
        nm.setId(id);
        EmployeController employeController = fXMLLoader.getController();
        employeController.bpContent.getStylesheets().add("/css/MainStyle.css");
        employeController.setNameMedia(usrNameMedia);
        employeController.btnViewEmployeeOnAction(event);

        AnchorPane acPane = fXMLLoader.getRoot();

        acContent.getChildren().clear();

        acContent.getChildren().add(acPane);
    }

    @FXML
    private void btnSettingsOnClick(ActionEvent event) throws IOException {
        //inithilize Setting Controller
        SettingsController settingController = new SettingsController();
        //inithilize UserNameMedia
        userNameMedia usrMedia = new userNameMedia();
        // Define a loader to inithilize Settings.fxml controller
        FXMLLoader settingLoader = new FXMLLoader();
        //set the location of Settings.fxml by fxmlloader
        settingLoader.load(getClass().getResource("/gui/app/Settings.fxml").openStream());

        //Send id to userMedia
        usrMedia.setId(id);
        //take control of settingController elements or node
        SettingsController settingControl = settingLoader.getController();
        settingControl.bpSettings.getStylesheets().add("/css/MainStyle.css");

        settingControl.setUsrMedia(usrMedia);
        settingControl.miMyASccountOnClick(event);
        //settingControl.settingPermission();

        AnchorPane acPane = settingLoader.getRoot();
        //acContent clear and make useul for add next node
        acContent.getChildren().clear();
        //add a node call "acPane" to acContent
        acContent.getChildren().add(acPane);
    }

    @FXML
    private void btnlougout_act(ActionEvent event) throws IOException {
        acContent.getChildren().clear();
        acContent.getChildren().add(FXMLLoader.load(getClass().getResource("/gui/login.fxml")));
        acDashBord.getChildren().clear();
        acHead.getChildren().clear();
        acHead.setMaxHeight(0.0);
    }

    @FXML
    private void hamburger_act(MouseEvent event) {
         hamb.setRate(hamb.getRate()*-1);
      TranslateTransition sideMenu = new TranslateTransition(Duration.millis(200.0), acDashBord);
      hamb.play();
      if(hamb.getRate()==-1){
            sideMenu.setByX(-200);
            sideMenu.play();
            acDashBord.getChildren().clear();

      }else{    
            sideMenu.setByX(200);
            sideMenu.play();
            acDashBord.getChildren().add(leftSideBarScroolPan);
      }
    }

    
   /* public void setUsrNameMedia(userNameMedia usrNameMedia) {
        lblUserId.setText(usrNameMedia.getId());
        lblUsrName.setText(usrNameMedia.getUsrName());
        id = usrNameMedia.getId();
        usrName = usrNameMedia.getUsrName();

        this.usrNameMedia = usrNameMedia;
    }*/
    
        public void permission() {
        con = dbCon.geConnection();

        try {
            pst = con.prepareStatement("select * from "+db+".droitutilisateur where UtilisateurId=?");
            pst.setString(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                if (rs.getInt(15) == 0) {
                    btnEmplopye.setDisable(true);
                }
                 else {

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        Image image;
        public void viewDetails() {
        users.id = id;
        usersGetway.selectedView(users);
       // image = users.image;
      //  circleImgUsr.setFill(new ImagePattern(image));
       // imgUsrTop.setFill(new ImagePattern(image));
       // lblFullName.setText(users.fullName);
       // lblUsrNamePopOver.setText(users.userName);
    }

    @FXML
    private void btnSellOnClick(ActionEvent event) {
        SellController controller = new SellController();
        userNameMedia nm = new userNameMedia();
        try {

            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.load(getClass().getResource("/gui/app/Sell.fxml").openStream());
            nm.setId(id);
            SellController sellController = fXMLLoader.getController();
            sellController.setNameMedia(usrNameMedia);
            sellController.acMainSells.getStylesheets().add("/css/MainStyle.css");
            sellController.tbtnSellOnAction(event);
            AnchorPane anchorPane = fXMLLoader.getRoot();
            acContent.getChildren().clear();
            acContent.getChildren().add(anchorPane);
        } catch (IOException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
