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
public class ListUtilisateurs {
    
    public String utilisateurId;
    public String utilisateurNom; 

    public ListUtilisateurs(String utilisateurId, String utilisateurNom) {
        this.utilisateurId=utilisateurId;
        this.utilisateurNom=utilisateurNom;
    }

    public String getUtilisateurId() {
        return utilisateurId;
    }

    public String getUtilisateurNom() {
        return utilisateurNom;
    }

    public void setUtilisateurId(String utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public void setUtilisateurNom(String utilisateurNom) {
        this.utilisateurNom = utilisateurNom;
    }

    
}
