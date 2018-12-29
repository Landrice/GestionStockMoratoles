/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlleurGui.app.vente;

import gettersSetters.Customer;
import gettersSetters.CustomerBLL;
import gettersSetters.CustomerGetway;
import gettersSetters.userNameMedia;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ralande
 */
public class AddCustomerController implements Initializable {

    @FXML
    private TextField tfCustomerName;
    @FXML
    private TextArea taCustomerContact;
    @FXML
    private TextArea taCustomerAddress;
    @FXML
     Button btnSave;
    @FXML
    public Button btnUpdate;
    @FXML
     Label lblCustomerContent;
    @FXML
    private Button btnClose;
    public String customerId;
    
    private String userId;
    
    userNameMedia nameMedia;
    
    public void setNameMedia(userNameMedia nameMedia) {
        userId = nameMedia.getId();
        this.nameMedia = nameMedia;
    }
    
    Customer customer = new Customer();
    CustomerGetway customerGetway = new CustomerGetway();
    CustomerBLL customerBLL = new CustomerBLL();


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnSaveOnAction(ActionEvent event) {
        customer.customerName = tfCustomerName.getText().trim();
        customer.customerAddress = taCustomerAddress.getText().trim();
        customer.customerConNo = taCustomerContact.getText().trim();
        customer.userId = userId;
        customerBLL.save(customer);
    }

    @FXML
    private void btnUpdateOnAction(ActionEvent event) {
        customer.id = customerId;
        customer.customerName = tfCustomerName.getText().trim();
        customer.customerAddress = taCustomerAddress.getText().trim();
        customer.customerConNo = taCustomerContact.getText().trim();
        customerBLL.update(customer);

    }

    @FXML
    private void btnCloseOnAction(ActionEvent event) {
                
         Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
    
        public void viewDetails(){
        customer.id = customerId;
        customerGetway.selectedView(customer);
        tfCustomerName.setText(customer.customerName);
        taCustomerContact.setText(customer.customerConNo);
        taCustomerAddress.setText(customer.customerAddress);
    }
}
