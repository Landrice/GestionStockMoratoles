/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdd;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Admin
 */
public class bddModel {

    Properties properties = new Properties();
    InputStream inputStream;
    String db;

    public void loadPropertiesFile() {
        try {
            inputStream = new FileInputStream("bdd.config");
            properties.load(inputStream);
            db = properties.getProperty("db");
        } catch (IOException e) {
            System.out.println("Erreur du Creation/lecture du fichier"+e);
        }
    }

    PreparedStatement pst;

    public void createDataBase() {
        loadPropertiesFile();
        bddConnection con = new bddConnection();
        
        try {
            
            
            pst = con.mkDataBase().prepareStatement("create database if not exists "+db+" DEFAULT CHARACTER SET utf8 \n"
                    + "  DEFAULT COLLATE utf8_general_ci");
            pst.execute();
            pst = con.mkDataBase().prepareStatement("CREATE TABLE if not exists "+db+".`Utilisateur` (\n"
                    + "  `Id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `Nom` VARCHAR(20) NOT NULL,\n"
                    + "  `Prenom` VARCHAR(100) ,\n"
                    + "  `Email` VARCHAR(100) ,\n"
                    + "  `Telephone` VARCHAR(100) ,\n"
                    + "  `Addresse` text,\n"
                    + "  `Mdp` VARCHAR(45),\n"
                    + "  `photo` mediumblob,\n"
                    + "  `CreateurID` int(11),\n"
                    + "  PRIMARY KEY (`Id`),\n"
                    + "  UNIQUE INDEX `Id` (`Id` ASC));");
            pst.execute();
            pst = con.mkDataBase().prepareStatement("CREATE TABLE if not exists "+db+".`DroitUtilisateur` (\n"
                    + "  `Id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `AjoutProduit` tinyint(1) DEFAULT NULL,\n"
                    + "  `AjoutFournisseur` tinyint(1) DEFAULT NULL,\n"
                    + "  `AjoutMarque` tinyint(1) DEFAULT NULL,\n"
                    + "  `AjoutCategory` tinyint(1) DEFAULT NULL,\n"
                    + "  `AjoutQuantite` tinyint(1) DEFAULT NULL,\n"
                    + "  `AjoutClient` tinyint(1) DEFAULT NULL,\n"
                    + "  `MisJourProduit` tinyint(1) DEFAULT NULL,\n"
                    + "  `MisJourFournisseur` tinyint(1) DEFAULT NULL,\n"
                    + "  `MisJourMarque` tinyint(1) DEFAULT NULL,\n"
                    + "  `MisJourCategory` tinyint(1) DEFAULT NULL,\n"
                    + "  `MisJourQuantite` tinyint(1) DEFAULT NULL,\n"
                    + "  `MisJourClient` tinyint(1) DEFAULT NULL,\n"
                    + "  `VendreProduit` tinyint(1) DEFAULT NULL,\n"
                    + "  `GererUtilisateur` tinyint(1) DEFAULT NULL,\n"
                    + "  `UtilisateurId` int(11) NOT NULL, \n"
                    + "  PRIMARY KEY (`Id`),\n"
                    + "  UNIQUE INDEX `Id` (`Id` ASC));");
            pst.execute();

            pst = con.mkDataBase().prepareStatement("CREATE TABLE if not exists "+db+".`Fournisseur` (\n"
                    + "  `Id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `NomFournisseur` varchar(100) DEFAULT NULL,\n"
                    + "  `TelFournisseur` text DEFAULT NULL,\n"
                    + "  `AdressFournisseur` text DEFAULT NULL,\n"
                    + "  `DescripFournisseur` text DEFAULT NULL,\n"
                    + "  `CreateurID` int(11) DEFAULT NULL,\n"
                    + "  `Date` date NOT NULL,\n"
                    + "  PRIMARY KEY (`Id`),\n"
                    + "  UNIQUE INDEX `Id` (`Id` ASC));");
            pst.execute();

            pst = con.mkDataBase().prepareStatement("CREATE TABLE if not exists "+db+".`Marque` (\n"
                    + "  `Id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `NomMarque` varchar(70) DEFAULT NULL,\n"
                    + "  `Description` text DEFAULT NULL,\n"
                    + "  `FournisseurID` varchar(20)  DEFAULT NULL,\n"
                    + "  `CreateurId` int DEFAULT NULL,\n"
                    + "  `Date` date,\n"
                    + "  PRIMARY KEY (`Id`),\n"
                    + "  UNIQUE INDEX `Id` (`Id` ASC));");

            pst.execute();

            pst = con.mkDataBase().prepareStatement("CREATE TABLE if not exists "+db+".`Categorie` (\n"
                    + "  `Id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `NomCategory` varchar(70) DEFAULT NULL,\n"
                    + "  `DescriptionCategory` text DEFAULT NULL,\n"
                    + "  `MarqueId` varchar(20) DEFAULT NULL,\n"
                    + "  `FournisseurId` int(11) DEFAULT NULL,\n"
                    + "  `CreateurId` int(11) DEFAULT NULL,\n"
                    + "  `Date` date,\n"
                    + "  PRIMARY KEY (`Id`),\n"
                    + "  UNIQUE INDEX `Id` (`Id` ASC));");

            pst.execute();

            pst = con.mkDataBase().prepareStatement("CREATE TABLE IF NOT EXISTS "+db+".`Produits` (\n"
                    + "  `Id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `IdProduit` varchar(20) NOT NULL,\n"
                    + "  `NomProduit` varchar(150) NOT NULL,\n"
                    + "  `Quantite` varchar(11) NOT NULL DEFAULT '0', \n"
                    + "  `Description` text ,\n"
                    + "  `FounisseurId` varchar(11) NOT NULL,\n"
                    + "  `MarqueId` varchar(11) NOT NULL,\n"
                    + "  `CategorieId` varchar(11) NOT NULL,\n"
                    + "  `Prix` varchar(100) ,\n"
                    + "  `UtilisateurId` varchar(11) NOT NULL,\n"
                    + "  `Date` date NOT NULL,\n"
                    + "  PRIMARY KEY (`Id`),\n"
                    + "  UNIQUE INDEX `Id` (`Id` ASC));");
            pst.execute();

            pst = con.mkDataBase().prepareStatement("CREATE TABLE IF NOT EXISTS "+db+".`Acheteur` (\n"
                    + "  `Id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `NomAcheteur` varchar(200) NOT NULL,\n"
                    + "  `NumeroAcheteur` varchar(200) DEFAULT NULL,\n"
                    + "  `AdresseAcheteur` text,\n"
                    + "  `AchatTotal` varchar(50) DEFAULT NULL,\n"
                    + "  `CreateurId` varchar(11) DEFAULT NULL,\n"
                    + "  `Date` datetime NOT NULL,\n"
                    + "  PRIMARY KEY (`Id`),\n"
                    + "  UNIQUE INDEX `Id` (`Id` ASC));");
            pst.execute();

            pst = con.mkDataBase().prepareStatement("CREATE TABLE IF NOT EXISTS "+db+".`Vente` (\n"
                    + "  `Id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `VenteId` varchar(10) NOT NULL,\n"
                    + "  `AcheteurId` varchar(11) NOT NULL,\n"
                    + "  `ProduitId` varchar(11) NOT NULL,\n"
                    + "  `Quantite` int(10) NOT NULL,\n"
                    + "  `TotalPrix` double NOT NULL,\n"
                    + "  `Garantie` varchar(20),\n"
                    + "  `DateDVente` datetime NOT NULL,\n"
                    + "  PRIMARY KEY (`Id`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;");
            pst.execute();
            
            pst = con.mkDataBase().prepareStatement("CREATE TABLE IF NOT EXISTS "+db+".`alert` (\n"
                    + "  `Id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `IdProduit` varchar(10) NOT NULL,\n"
                    + "  `seuilAlert` varchar(11) NOT NULL,\n"
                    + "  PRIMARY KEY (`Id`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;");
            pst.execute();
             pst = con.mkDataBase().prepareStatement("CREATE TABLE IF NOT EXISTS "+db+".`categorietpa` (\n"
                    + "  `Id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `IdProduit` varchar(10) NOT NULL,\n"
                    + "  `categorieTPA` varchar(11) NOT NULL,\n"
                    + "  PRIMARY KEY (`Id`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;");
            pst.execute();
            
            System.out.println("Bdd crée avec succes");

        } catch (SQLException e) {
            System.err.println("Ato ai: "+e);
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/gui/ConfigureServeur.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Configuration du serveur de la base de données");
                stage.showAndWait();
            } catch (IOException ex1) {
                Logger.getLogger(bddModel.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
}
