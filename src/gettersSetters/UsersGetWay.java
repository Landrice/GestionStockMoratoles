/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gettersSetters;

import bdd.BddPropreties;
import bdd.bddConnection;
import java.io.ByteArrayInputStream;
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
import javafx.scene.image.Image;
import javafx.stage.StageStyle;
import list.Utilisateurs;

/**
 *
 * @author Admin
 */
public class UsersGetWay {
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

    public void view(Utilisateurs users) {
        con = dbConnection.geConnection();
        try {
            pst = con.prepareStatement("select * from "+db+".utilisateur");
            rs = pst.executeQuery();
            while (rs.next()) {
                users.id = rs.getString(1);
                users.nom = rs.getString(2);
                users.employeeLists.addAll(new ListUtilisateurs(users.id, users.nom));
            }
            rs.close();
            pst.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void selectedView(Utilisateurs users) {
        con = dbConnection.geConnection();
        try {
            pst = con.prepareCall("select * from "+db+".utilisateur where id=?");
            pst.setString(1, users.id);
            rs = pst.executeQuery();
            while (rs.next()) {
                users.id = rs.getString(1);
                users.nom = rs.getString(2);
                users.prenom = rs.getString(3);
                users.email = rs.getString(4);
                users.telephone = rs.getString(5);
                users.addresse = rs.getString(6);
                users.mpd = rs.getString(7);
                users.photo = rs.getBlob(8);
                if (users.photo != null) {
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(users.photo.getBytes(1, (int) users.photo.length()));
                    users.image = new Image(byteArrayInputStream);
                } else {
                    users.image = new Image("/img/a.png"); 
                  System.out.println("Image non définie");
                }
                users.createurId = rs.getString(9);

            }
            rs.close();
            pst.close();
            con.close();

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void update(Utilisateurs users) {
        con = dbConnection.geConnection();
        try {
            pst = con.prepareStatement("UPDATE "+db+".utilisateur SET Prenom=?, Email=?,Telephone=?,Addresse=?,Mdp=COALESCE(?, Mdp), photo=COALESCE(?, photo) WHERE Nom=?");
            pst.setString(1, users.prenom);
            pst.setString(2, users.email);
            pst.setString(3, users.telephone);
            pst.setString(4, users.addresse);
            pst.setString(5, users.mpd);
            if (users.imagePath != null) {
                InputStream is;
                is = new FileInputStream(new File(users.imagePath));
                pst.setBlob(6, is);
            } else if (users.imagePath == null) {
                pst.setBlob(6, (Blob) null);
            }
            pst.setString(7, users.nom);
            pst.executeUpdate();
            pst.close();
            con.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès :");
            alert.setHeaderText("A jour !!");
            alert.setContentText("L'utlisateur " + users.nom + " a été modifié avec succès");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void delete(Utilisateurs users) {
        con = dbConnection.geConnection();
        try {
            pst = con.prepareStatement("delete from "+db+".utilisateur where Id=?");
            pst.setString(1, users.id);
            pst.executeUpdate();
            pst.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
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
                alert.setHeaderText("ERREUR : Nom existe");
                alert.setContentText("Nom " + users.nom + " existe deja");
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
