/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gettersSetters;

import bdd.BddPropreties;
import bdd.SQLSyntax;
import bdd.bddConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

/**
 *
 * @author Admin
 */
public class CurrentProductGetway {

    bddConnection dbCon = new bddConnection();
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    BddPropreties dBProperties = new BddPropreties();
    String db = dBProperties.loadPropertiesFile();
    AlertList al=new AlertList();

    SQLSyntax sql = new SQLSyntax();

    public void save(CurrentProduct currentProduct) {
        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("insert into " + db + ".produits values(?,?,?,?,?,?,?,?,?,?,?)");
            pst.setString(1, null);
            pst.setString(2, currentProduct.idProduit);
            pst.setString(3, currentProduct.nomProduit);
            pst.setString(4, currentProduct.quantite);
            pst.setString(5, currentProduct.description);
            pst.setString(6, currentProduct.fournisseurID);
            pst.setString(7, currentProduct.marqueID);
            pst.setString(8, currentProduct.categorieID);
            pst.setString(9, currentProduct.prix);
            pst.setString(10, currentProduct.utilisateurID);
            pst.setString(11, currentProduct.date);
            pst.executeUpdate();
            pst.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CurrentProduct.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Too Many Connection");

        }

    }

    public void view(CurrentProduct currentProduct) {
        currentProduct.currentProductList.clear();
        con = dbCon.geConnection();

        try {
            pst = con.prepareStatement("SELECT SQL_NO_CACHE * FROM " + db + ".produits ORDER BY Quantite");
            rs = pst.executeQuery();
            while (rs.next()) {

                currentProduct.id = rs.getString(1);
                currentProduct.idProduit = rs.getString(2);
                currentProduct.nomProduit = rs.getString(3);
                currentProduct.quantite = rs.getString(4);
                currentProduct.description = rs.getString(5);
                currentProduct.fournisseurID = rs.getString(6);
                currentProduct.marqueID = rs.getString(7);
                currentProduct.categorieID = rs.getString(8);
                currentProduct.prix = rs.getString(9);
                currentProduct.utilisateurID = rs.getString(10);
                currentProduct.date = rs.getString(11);
                currentProduct.fournisseurNom = sql.getName(currentProduct.fournisseurID, currentProduct.fournisseurNom, "fournisseur");
                currentProduct.marqueNom = sql.getName(currentProduct.marqueID, currentProduct.marqueNom, "marque");
                currentProduct.categorieNom = sql.getName(currentProduct.categorieID, currentProduct.categorieNom, "categorie");
                currentProduct.nom = sql.getName(currentProduct.utilisateurID, currentProduct.nom, "utilisateur");
                currentProduct.currentProductList.addAll(new ListProduct(currentProduct.id, currentProduct.idProduit, currentProduct.nomProduit, currentProduct.quantite, currentProduct.description, currentProduct.fournisseurNom, currentProduct.marqueNom, currentProduct.categorieNom, currentProduct.prix, currentProduct.nom, currentProduct.date));
            }
            pst.close();
            con.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void selectedView(CurrentProduct currentProduct) {
        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("select * from " + db + ".produits where id=?");
            pst.setString(1, currentProduct.id);
            rs = pst.executeQuery();
            while (rs.next()) {
//                id = rs.getString(1);
                currentProduct.idProduit = rs.getString(2);
                currentProduct.nomProduit = rs.getString(3);
                currentProduct.quantite = rs.getString(4);
                currentProduct.description = rs.getString(5);
                currentProduct.fournisseurID = rs.getString(6);
                currentProduct.marqueID = rs.getString(7);
                currentProduct.categorieID = rs.getString(8);
                currentProduct.prix = rs.getString(9);
                currentProduct.utilisateurID = rs.getString(10);
                currentProduct.date = rs.getString(11);
                currentProduct.fournisseurNom = sql.getName(currentProduct.fournisseurID, currentProduct.fournisseurNom, "fournisseur");
                currentProduct.marqueNom = sql.getName(currentProduct.marqueID, currentProduct.marqueNom, "marque");
                currentProduct.categorieNom = sql.getName(currentProduct.categorieID, currentProduct.categorieNom, "categorie");
                currentProduct.nom = sql.getName(currentProduct.utilisateurID, currentProduct.nom, "utilisateur");
            }
            pst.close();
            con.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewFistTen(CurrentProduct currentProduct) {
        con = dbCon.geConnection();

        currentProduct.currentProductList.clear();
        try {
            pst = con.prepareStatement("select * from " + db + ".produits ORDER BY Quantite");
            rs = pst.executeQuery();
            while (rs.next()) {

                currentProduct.id = rs.getString(1);
                currentProduct.idProduit = rs.getString(2);
                currentProduct.nomProduit = rs.getString(3);
                currentProduct.quantite = rs.getString(4);
                currentProduct.description = rs.getString(5);
                currentProduct.fournisseurID = rs.getString(6);
                currentProduct.marqueID = rs.getString(7);
                currentProduct.categorieID = rs.getString(8);
                currentProduct.prix = rs.getString(9);
                currentProduct.utilisateurID = rs.getString(10);
                currentProduct.date = rs.getString(11);
                currentProduct.fournisseurNom = sql.getName(currentProduct.fournisseurID, currentProduct.fournisseurNom, "fournisseur");
                currentProduct.marqueNom = sql.getName(currentProduct.marqueID, currentProduct.marqueNom, "marque");
                currentProduct.categorieNom = sql.getName(currentProduct.categorieID, currentProduct.categorieNom, "categorie");
                currentProduct.nom = sql.getName(currentProduct.utilisateurID, currentProduct.nom, "utilisateur");
                currentProduct.currentProductList.addAll(new ListProduct(currentProduct.id, currentProduct.idProduit, currentProduct.nomProduit, currentProduct.quantite, currentProduct.description, currentProduct.fournisseurNom, currentProduct.marqueNom, currentProduct.categorieNom, currentProduct.prix, currentProduct.nom, currentProduct.date));
            }
            pst.close();
            con.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
        public void viewFistTenOrderByCategorie(CurrentProduct currentProduct, String s) {
        con = dbCon.geConnection();

        currentProduct.currentProductList.clear();
        try {
            pst = con.prepareStatement("select * from " + db + ".produits, " + db + ".categorietpa WHERE categorietpa.categorieTPA='"+s+"' and categorietpa.IdProduit=produits.IdProduit");
            rs = pst.executeQuery();
            while (rs.next()) {

                currentProduct.id = rs.getString(1);
                currentProduct.idProduit = rs.getString(2);
                currentProduct.nomProduit = rs.getString(3);
                currentProduct.quantite = rs.getString(4);
                currentProduct.description = rs.getString(5);
                currentProduct.fournisseurID = rs.getString(6);
                currentProduct.marqueID = rs.getString(7);
                currentProduct.categorieID = rs.getString(8);
                currentProduct.prix = rs.getString(9);
                currentProduct.utilisateurID = rs.getString(10);
                currentProduct.date = rs.getString(11);
                currentProduct.fournisseurNom = sql.getName(currentProduct.fournisseurID, currentProduct.fournisseurNom, "fournisseur");
                currentProduct.marqueNom = sql.getName(currentProduct.marqueID, currentProduct.marqueNom, "marque");
                currentProduct.categorieNom = sql.getName(currentProduct.categorieID, currentProduct.categorieNom, "categorie");
                currentProduct.nom = sql.getName(currentProduct.utilisateurID, currentProduct.nom, "utilisateur");
                currentProduct.currentProductList.addAll(new ListProduct(currentProduct.id, currentProduct.idProduit, currentProduct.nomProduit, currentProduct.quantite, currentProduct.description, currentProduct.fournisseurNom, currentProduct.marqueNom, currentProduct.categorieNom, currentProduct.prix, currentProduct.nom, currentProduct.date));
            }
            pst.close();
            con.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
             public void viewFistTenOrderByAlert(CurrentProduct currentProduct) {
        currentProduct.currentProductList.clear();
        try {
            con = dbCon.geConnection();
            pst = con.prepareStatement("SELECT * FROM " + db + ".produits, " + db + ".alert WHERE " + db + ".alert.IdProduit = " + db + ".produits.IdProduit");
            rs = pst.executeQuery();
            while (rs.next()) {
                
               /* String idproduitBase=rs.getString(1);
                int idproduitBasein=Integer.parseInt(idproduitBase);*/

                if (Integer.parseInt(rs.getString(4)) < Integer.parseInt(rs.getString(14))) {
                
                currentProduct.id = rs.getString(1);
                currentProduct.idProduit = rs.getString(2);
                currentProduct.nomProduit = rs.getString(3);
                currentProduct.quantite = rs.getString(4);
                currentProduct.description = rs.getString(5);
                currentProduct.fournisseurID = rs.getString(6);
                currentProduct.marqueID = rs.getString(7);
                currentProduct.categorieID = rs.getString(8);
                currentProduct.prix = rs.getString(9);
                currentProduct.utilisateurID = rs.getString(10);
                currentProduct.date = rs.getString(11);
                currentProduct.fournisseurNom = sql.getName(currentProduct.fournisseurID, currentProduct.fournisseurNom, "fournisseur");
                currentProduct.marqueNom = sql.getName(currentProduct.marqueID, currentProduct.marqueNom, "marque");
                currentProduct.categorieNom = sql.getName(currentProduct.categorieID, currentProduct.categorieNom, "categorie");
                currentProduct.nom = sql.getName(currentProduct.utilisateurID, currentProduct.nom, "utilisateur");
                currentProduct.currentProductList.addAll(new ListProduct(currentProduct.id, currentProduct.idProduit, currentProduct.nomProduit, currentProduct.quantite, currentProduct.description, currentProduct.fournisseurNom, currentProduct.marqueNom, currentProduct.categorieNom, currentProduct.prix, currentProduct.nom, currentProduct.date));
            
           // pst.close();
           // con.close();
           // rs.close();
            }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public void searchView(CurrentProduct currentProduct) {
        con = dbCon.geConnection();

        currentProduct.currentProductList.clear();
        try {
            pst = con.prepareStatement("select * from " + db + ".produits where IdProduit like ? or NomProduit like ?");
            pst.setString(1, "%" + currentProduct.idProduit + "%");
            pst.setString(2, "%" + currentProduct.nomProduit + "%");
            rs = pst.executeQuery();
            while (rs.next()) {

                currentProduct.id = rs.getString(1);
                currentProduct.idProduit = rs.getString(2);
                currentProduct.nomProduit = rs.getString(3);
                currentProduct.quantite = rs.getString(4);
                currentProduct.description = rs.getString(5);
                currentProduct.fournisseurID = rs.getString(6);
                currentProduct.marqueID = rs.getString(7);
                currentProduct.categorieID = rs.getString(8);
                currentProduct.prix = rs.getString(9);
                currentProduct.utilisateurID = rs.getString(10);
                currentProduct.date = rs.getString(11);
                currentProduct.fournisseurNom = sql.getName(currentProduct.fournisseurID, currentProduct.fournisseurNom, "fournisseur");
                currentProduct.marqueNom = sql.getName(currentProduct.marqueID, currentProduct.marqueNom, "marque");
                currentProduct.categorieNom = sql.getName(currentProduct.categorieID, currentProduct.categorieNom, "categorie");
                currentProduct.nom = sql.getName(currentProduct.utilisateurID, currentProduct.nom, "utilisateur");
                currentProduct.currentProductList.addAll(new ListProduct(currentProduct.id, currentProduct.idProduit, currentProduct.nomProduit, currentProduct.quantite, currentProduct.description, currentProduct.fournisseurNom, currentProduct.marqueNom, currentProduct.categorieNom, currentProduct.prix, currentProduct.nom, currentProduct.date));
            }
            pst.close();
            con.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchBySupplyer(CurrentProduct currentProduct) {
        con = dbCon.geConnection();

        currentProduct.currentProductList.clear();
        currentProduct.fournisseurID = sql.getIdNo(currentProduct.fournisseurNom, currentProduct.fournisseurID, "fournisseur", "NomFournisseur");
        try {
            pst = con.prepareStatement("select * from " + db + ".produits where FounisseurId=?");
            pst.setString(1, currentProduct.fournisseurID);
            rs = pst.executeQuery();
            while (rs.next()) {
                currentProduct.id = rs.getString(1);
                currentProduct.idProduit = rs.getString(2);
                currentProduct.nomProduit = rs.getString(3);
                currentProduct.quantite = rs.getString(4);
                currentProduct.description = rs.getString(5);
                currentProduct.fournisseurID = rs.getString(6);
                currentProduct.marqueID = rs.getString(7);
                currentProduct.categorieID = rs.getString(8);
                currentProduct.prix = rs.getString(9);
                currentProduct.utilisateurID = rs.getString(10);
                currentProduct.date = rs.getString(11);
                currentProduct.fournisseurNom = sql.getName(currentProduct.fournisseurID, currentProduct.fournisseurNom, "fournisseur");
                currentProduct.marqueNom = sql.getName(currentProduct.marqueID, currentProduct.marqueNom, "marque");
                currentProduct.categorieNom = sql.getName(currentProduct.categorieID, currentProduct.categorieNom, "Categorie");
                currentProduct.nom = sql.getName(currentProduct.utilisateurID, currentProduct.nom, "utilisateur");
                currentProduct.currentProductList.addAll(new ListProduct(currentProduct.id, currentProduct.idProduit, currentProduct.nomProduit, currentProduct.quantite, currentProduct.description, currentProduct.fournisseurNom, currentProduct.marqueNom, currentProduct.categorieNom, currentProduct.prix, currentProduct.nom, currentProduct.date));
            }
            pst.close();
            con.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CurrentProductGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void searchByBrand(CurrentProduct currentProduct) {
        con = dbCon.geConnection();

        currentProduct.currentProductList.clear();
        currentProduct.fournisseurID = sql.getIdNo(currentProduct.fournisseurNom, currentProduct.fournisseurID, "fournisseur", "NomFournisseur");
        currentProduct.fournisseurID = sql.getBrandID(currentProduct.fournisseurID, currentProduct.marqueID, currentProduct.marqueNom);
        System.out.println("Brand ID: " + currentProduct.marqueID);

        try {
            pst = con.prepareStatement("select * from " + db + ".produits where MarqueId=?");
            pst.setString(1, currentProduct.marqueID);
            rs = pst.executeQuery();
            while (rs.next()) {
                currentProduct.id = rs.getString(1);
                currentProduct.idProduit = rs.getString(2);
                currentProduct.nomProduit = rs.getString(3);
                currentProduct.quantite = rs.getString(4);
                currentProduct.description = rs.getString(5);
                currentProduct.fournisseurID = rs.getString(6);
                currentProduct.marqueID = rs.getString(7);
                currentProduct.categorieID = rs.getString(8);
                currentProduct.prix = rs.getString(9);
                currentProduct.utilisateurID = rs.getString(10);
                currentProduct.date = rs.getString(11);
                currentProduct.fournisseurNom = sql.getName(currentProduct.fournisseurID, currentProduct.fournisseurNom, "fournisseur");
                currentProduct.marqueNom = sql.getName(currentProduct.marqueID, currentProduct.marqueNom, "marque");
                currentProduct.categorieNom = sql.getName(currentProduct.categorieID, currentProduct.categorieNom, "categorie");
                currentProduct.nom = sql.getName(currentProduct.utilisateurID, currentProduct.nom, "utilisateur");
                currentProduct.currentProductList.addAll(new ListProduct(currentProduct.id, currentProduct.idProduit, currentProduct.nomProduit, currentProduct.quantite, currentProduct.description, currentProduct.fournisseurNom, currentProduct.marqueNom, currentProduct.categorieNom, currentProduct.prix, currentProduct.nom, currentProduct.date));
            }
            pst.close();
            con.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CurrentProductGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void searchByCatagory(CurrentProduct currentProduct) {
        con = dbCon.geConnection();

        currentProduct.currentProductList.clear();
        currentProduct.fournisseurID = sql.getIdNo(currentProduct.fournisseurNom, currentProduct.fournisseurID, "fournisseur", "NomFournisseur");
        currentProduct.marqueID = sql.getBrandID(currentProduct.fournisseurID, currentProduct.marqueID, currentProduct.marqueNom);
        currentProduct.categorieID = sql.getCatagoryId(currentProduct.fournisseurID, currentProduct.marqueID, currentProduct.categorieID, currentProduct.categorieNom);
        try {
            pst = con.prepareStatement("select * from " + db + ".produits where CategorieId=?");
            pst.setString(1, currentProduct.categorieID);
            rs = pst.executeQuery();
            while (rs.next()) {
                currentProduct.id = rs.getString(1);
                currentProduct.idProduit = rs.getString(2);
                currentProduct.nomProduit = rs.getString(3);
                currentProduct.quantite = rs.getString(4);
                currentProduct.description = rs.getString(5);
                currentProduct.fournisseurID = rs.getString(6);
                currentProduct.marqueID = rs.getString(7);
                currentProduct.categorieID = rs.getString(8);
                currentProduct.prix = rs.getString(9);
                currentProduct.utilisateurID = rs.getString(10);
                currentProduct.date = rs.getString(11);
                currentProduct.fournisseurNom = sql.getName(currentProduct.fournisseurID, currentProduct.fournisseurNom, "fournisseur");
                currentProduct.marqueNom = sql.getName(currentProduct.marqueID, currentProduct.marqueNom, "marque");
                currentProduct.categorieNom = sql.getName(currentProduct.categorieID, currentProduct.categorieNom, "categorie");
                currentProduct.nom = sql.getName(currentProduct.utilisateurID, currentProduct.nom, "utilisateur");
                currentProduct.currentProductList.addAll(new ListProduct(currentProduct.id, currentProduct.idProduit, currentProduct.nomProduit, currentProduct.quantite, currentProduct.description, currentProduct.fournisseurNom, currentProduct.marqueNom, currentProduct.categorieNom, currentProduct.prix, currentProduct.nom, currentProduct.date));
            }
            pst.close();
            con.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CurrentProductGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sView(CurrentProduct currentProduct) {
        con = dbCon.geConnection();

        try {
            pst = con.prepareStatement("select * from " + db + ".produits where IdProduit=?");
            pst.setString(1, currentProduct.idProduit);
            rs = pst.executeQuery();
            while (rs.next()) {
                currentProduct.id = rs.getString(1);
                currentProduct.idProduit = rs.getString(2);
                currentProduct.nomProduit = rs.getString(3);
                currentProduct.quantite = rs.getString(4);
                currentProduct.description = rs.getString(5);
                currentProduct.fournisseurID = rs.getString(6);
                currentProduct.marqueID = rs.getString(7);
                currentProduct.categorieID = rs.getString(8);
                currentProduct.prix = rs.getString(9);
                currentProduct.utilisateurID = rs.getString(10);
                currentProduct.date = rs.getString(11);
                currentProduct.fournisseurNom = sql.getName(currentProduct.fournisseurID, currentProduct.fournisseurNom, "fournisseur");
                currentProduct.marqueNom = sql.getName(currentProduct.marqueID, currentProduct.marqueNom, "marque");
                currentProduct.categorieNom = sql.getName(currentProduct.categorieID, currentProduct.categorieNom, "Categorie");
                currentProduct.utilisateurID = sql.getName(currentProduct.utilisateurID, currentProduct.nom, "utilisateur");
                /*  currentProduct.rmaDayesss = sql.getDayes(currentProduct.rmaDayesss, currentProduct.rmaId);
                long dateRMA = Long.parseLong(currentProduct.rmaDayesss);

                currentProduct.garantie = LocalDate.now().plusDays(dateRMA).toString();*/

            }
            pst.close();
            con.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cbSupplyer(CurrentProduct currentProduct) {
        con = dbCon.geConnection();

        try {
            pst = con.prepareStatement("select * from " + db + ".fournisseur");
            rs = pst.executeQuery();
            while (rs.next()) {
                currentProduct.fournisseurList = rs.getString(2);
            }
            pst.close();
            con.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(CurrentProduct currentProduct) {
        con = dbCon.geConnection();

        try {
            pst = con.prepareStatement("update " + db + ".produits set IdProduit=?, NomProduit=?, Quantite=?, Description=?, "
                    + "FounisseurId=?, MarqueId=?, CategorieId=?,"
                    + " Prix=?, UtilisateurId=?, Date=?  where Id=?");
            pst.setString(1, currentProduct.idProduit);
            pst.setString(2, currentProduct.nomProduit);
            pst.setString(3, currentProduct.quantite);
            pst.setString(4, currentProduct.description);
            pst.setString(5, currentProduct.fournisseurID);
            pst.setString(6, currentProduct.marqueID);
            pst.setString(7, currentProduct.categorieID);
            pst.setString(8, currentProduct.prix);
            pst.setString(9, currentProduct.utilisateurID);
            pst.setString(10, currentProduct.date);
            pst.setString(11, currentProduct.id);
            pst.executeUpdate();
            pst.close();
            con.close();
            //    rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(CurrentProduct.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Ato erreur sql 01: " + ex);
        }
    }

    public void delete(CurrentProduct currentProduct) {
        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("delete from " + db + ".produits where id=?");
            pst.setString(1, currentProduct.id);
            pst.executeUpdate();
            pst.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CurrentProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isNotSoled(CurrentProduct currentProduct) {
        con = dbCon.geConnection();
        boolean isNotSoled = false;
        try {
            pst = con.prepareStatement("select * from " + db + ".vente where ProduitId=?");
            pst.setString(1, currentProduct.id);
            rs = pst.executeQuery();
            while (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Avertissement");
                alert.setHeaderText("AVERTISSMENT : ");
                alert.setContentText("Le produit est figé, vous ne pouvez pas le supprimer");
                alert.initStyle(StageStyle.UNDECORATED);
                alert.showAndWait();

                return isNotSoled;
            }
            rs.close();
            pst.close();
            con.close();
            isNotSoled = true;
        } catch (SQLException ex) {
            Logger.getLogger(CurrentProductGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isNotSoled;
    }

}
