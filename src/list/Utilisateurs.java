/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package list;

import gettersSetters.ListUtilisateurs;
import java.sql.Blob;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

/**
 *
 * @author Admin
 */
public class Utilisateurs {
    public ObservableList<String> allUser = FXCollections.observableArrayList();

    public String id;
    public String nom;
    public String prenom;
    public String email;
    public String telephone;
    public String addresse;
    public String mpd;
    public String imagePath;
    public Blob photo;
    public String createurId;
    public Image image;

    public ObservableList<ListUtilisateurs> employeeLists = FXCollections.observableArrayList();
    
}
