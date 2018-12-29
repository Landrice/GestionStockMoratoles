/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionstock;

import bdd.bddModel;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Admin
 */
public class GestionStock extends Application {
    
    public GestionStock() {
        bddModel model = new bddModel();
        model.createDataBase();
}
    
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/gui/login.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Gestionnaires des stocks");
            primaryStage.setResizable(false);
            primaryStage.getIcons().add(new Image("/image/OM.png"));
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(GestionStock.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
