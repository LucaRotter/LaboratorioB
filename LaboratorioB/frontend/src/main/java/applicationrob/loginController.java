package applicationrob;

import java.net.URL;
import java.util.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.Model;
import java.rmi.RemoteException;

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
            
        TokenSession.setUserId(id_user);
        System.out.println(TokenSession.checkTkSession());
        Model.getIstance().getView().changeToHome();
    } 

    @FXML
    public void changeToRegister() {
         Model.getIstance().getView().changeToRegister();
    }

}
