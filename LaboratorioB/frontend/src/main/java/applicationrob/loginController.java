package applicationrob;

import java.net.URL;
import java.rmi.*;
import java.util.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import models.Model;

import java.rmi.RemoteException;
import LaboratorioB.common.models.*;


public class LoginController implements Initializable {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField pwField;
    @FXML
    private Button backBtn;

    private int id_user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
       backBtn.setOnAction(e -> {
			
			Model.getIstance().getView().changeToHome();
		});
    }

    @FXML
    public void onUserLogin()throws RemoteException {

       
        id_user = clientBR.getInstance().login(emailField.getText(), pwField.getText());
        TokenSession.checkTkSession();    
        TokenSession.setUserId(id_user);
        Model.getIstance().getView().changeToHome();
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
