package applicationrob;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import LaboratorioB.common.models.*;
import java.util.*;
import javafx.scene.control.PasswordField;
import java.rmi.*;
import java.rmi.RemoteException;
    

public class LoginController {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField pwField;
    @FXML
    private Button backBtn;

    @FXML
    private void onUserLogin()throws RemoteException {

        int id_user = clientBR.BR.login(emailField.getText(), pwField.getText());

          
        if(id_user == 1) { } else { }
         
        } 

    @FXML
    private void changeToRegister() {
    }

     @FXML
    public void onBackPressed() {
    }
    
    private void showAlert(String title, String message) {
        
    }

}
