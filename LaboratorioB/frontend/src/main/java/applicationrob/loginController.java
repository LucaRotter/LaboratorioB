package applicationrob;

import java.rmi.*;
import java.util.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.rmi.RemoteException;
import LaboratorioB.common.models.*;


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

        id_user = clientBR.login(emailField.getText(), pwField.getText());

        if(id_user == -1) { 
             showAlert("Login Failed", "Invalid email or password");
             return;
        }     

        TokenSession.setUserId(id_user);
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
