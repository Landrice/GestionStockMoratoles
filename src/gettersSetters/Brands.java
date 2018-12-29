/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gettersSetters;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Admin
 */
public class Brands {
     public String id;
    public String brandName;
    public String brandComment;
    public String supplyrId;
    public String creatorId;
    public String date;
    public String supplyerName;
    public String creatorName;

    public ObservableList<ListBrands> brandDitails = FXCollections.observableArrayList();

    
}
