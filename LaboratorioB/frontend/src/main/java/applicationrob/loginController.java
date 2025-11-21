package applicationrob;

import java.net.URL;
import java.util.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.Model;
import java.rmi.RemoteException;
import javafx.scene.layout.AnchorPane;

public class LoginController implements Initializable {

    @FXML
    private AnchorPane rootPane;

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
        String email = emailField.getText().trim();
        String pw = pwField.getText().trim();

        if (email.isEmpty() || pw.isEmpty()) {
        views.ViewAlert.showAlert("error", "Please enter email and password.", "", rootPane, "error");
        return;
        }

        id_user = clientBR.getInstance().login(email, pw);

        if (id_user == -1) {
        views.ViewAlert.showAlert("error", "Invalid email or password.", "", null, "error");
        return;  
        }   

        TokenSession.setUserId(id_user);
        System.out.println(TokenSession.checkTkSession());
        Model.getIstance().getView().changeToHome();
    } 

    @FXML
    public void changeToRegister() {
         Model.getIstance().getView().changeToRegister();
    }

}
