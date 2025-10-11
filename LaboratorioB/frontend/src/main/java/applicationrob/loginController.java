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

    private int id_user;

    @FXML
    public void onUserLogin()throws RemoteException {

        id_user = clientBR.BR.login(emailField.getText(), pwField.getText());

        if(id_user == -1) { 
             showAlert("Login Failed", "Invalid email or password");
             return;
        }     
    } 

    @FXML
    public void changeToRegister() {
    }

     @FXML
    public void onBackPressed() {
    }
    
    public void showAlert(String title, String message) {   
    }

}
