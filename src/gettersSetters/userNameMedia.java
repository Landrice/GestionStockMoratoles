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
public class userNameMedia {
    String Id;
    String Nom;

    public userNameMedia() {
    }

    public userNameMedia(String Id) {
        this.Id = Id;
    }

    public userNameMedia(String Id, String Nom) {
        this.Id = Id;
        this.Nom = Nom;
    }

    public String getId() {
        return Id;
    }

    public String getNom() {
        return Nom;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public void setNom(String Nom) {
        this.Nom = Nom;
    }
    
    
}
