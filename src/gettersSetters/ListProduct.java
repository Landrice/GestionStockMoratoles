/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gettersSetters;

/**
 *
 * @author Admin
 */
public class ListProduct {
    public String id;
    public String idProduit;
    public String nomProduit;
    public String quantite;
    public String description;
    public String fournisseurID;
    public String marqueID;
    public String categorieID;
    public String prix;
    
    public String discountInCash;
    public String discountInPersent;
    
    public String utilisateur;
    public String date;

    public ListProduct(String id, String idProduit, String nomProduit, String quantite, String description, String fournisseurID, String marqueID, String categorieID, String prix, String utilisateur, String date) {
        this.id = id;
        this.idProduit = idProduit;
        this.nomProduit = nomProduit;
        this.quantite = quantite;
        this.description = description;
        this.fournisseurID = fournisseurID;
        this.marqueID = marqueID;
        this.categorieID = categorieID;
        this.prix = prix;
        this.utilisateur = utilisateur;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getIdProduit() {
        return idProduit;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public String getQuantite() {
        return quantite;
    }

    public String getDescription() {
        return description;
    }

    public String getFournisseurID() {
        return fournisseurID;
    }

    public String getMarqueID() {
        return marqueID;
    }

    public String getCategorieID() {
        return categorieID;
    }

    public String getPrix() {
        return prix;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public String getDate() {
        return date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIdProduit(String idProduit) {
        this.idProduit = idProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public void setQuantite(String quantite) {
        this.quantite = quantite;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFournisseurID(String fournisseurID) {
        this.fournisseurID = fournisseurID;
    }

    public void setMarqueID(String marqueID) {
        this.marqueID = marqueID;
    }

    public void setCategorieID(String categorieID) {
        this.categorieID = categorieID;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }


    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
