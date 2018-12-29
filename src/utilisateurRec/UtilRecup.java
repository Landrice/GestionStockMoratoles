package utilisateurRec;

import bdd.BddPropreties;
import bdd.bddConnection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;
import list.Utilisateurs;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Admin
 */
public class UtilRecup {
    bddConnection dbConnection = new bddConnection();
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    BddPropreties dBProperties = new BddPropreties();
    String db = dBProperties.loadPropertiesFile();

    public void save(Utilisateurs users) {

        if (isUniqName(users)) {
            con = dbConnection.geConnection();
            try {
                pst = con.prepareStatement("insert into "+db+".utilisateur values(?,?,?,?,?,?,?,?,?)");
                pst.setString(1, null);
                pst.setString(2, users.nom);
                pst.setString(3, users.prenom);
                pst.setString(4, users.email);
                pst.setString(5, users.telephone);
                pst.setString(6, users.addresse);
                pst.setString(7, users.mpd);
                if (users.imagePath != null) {
                    InputStream is;
                    is = new FileInputStream(new File(users.imagePath));
                    pst.setBlob(8, is);
                } else {
                    pst.setBlob(8, (Blob) null);
                }
                pst.setString(9, users.createurId);
                pst.executeUpdate();
                pst.close();
                con.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès :");
                alert.setHeaderText("Succès");
                alert.setContentText("Utilisateur " + users.nom + " Ajouté avec succès");
                alert.initStyle(StageStyle.UNDECORATED);
                alert.showAndWait();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    
       public boolean isUniqName(Utilisateurs users) {
        con = dbConnection.geConnection();
        boolean isUniqName = false;
        try {
            pst = con.prepareStatement("select * from "+db+".utilisateur where nom=?");
            pst.setString(1, users.nom);
            rs = pst.executeQuery();
            while (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERREUR :");
                alert.setHeaderText("ERREUR : Le nom existe déja");
                alert.setContentText("Nom " + users.nom + " est déja définie");
                alert.initStyle(StageStyle.UNDECORATED);
                alert.showAndWait();
                return isUniqName;
            }
            rs.close();
            pst.close();
            con.close();
            isUniqName = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUniqName;
    }
}
