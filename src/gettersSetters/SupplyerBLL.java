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
public class SupplyerBLL {
    SupplyerGetway supplyerGetway = new SupplyerGetway();

    public void save(){
        
    }
    
    public Object delete(Supplyer supplyer){
        if(supplyerGetway.isNotUse(supplyer)){
            supplyerGetway.delete(supplyer);
        }else{
            
        }
        return supplyer;
    }
}
