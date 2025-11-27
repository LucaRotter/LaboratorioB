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
    @FXML
    private Label errorLabel;

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

        String validationError = validateField(email, pw);
        if (validationError != null) { 
            showErrorMessage(validationError);
            return;
        }

        id_user = clientBR.getInstance().login(email, pw);

        if (id_user == -1) {
            showErrorMessage("Invalid email or password");
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

    private String validateField(String email, String pw) {

        if (email.isEmpty() && pw.isEmpty()) {
        return "All fields are required";
        }

        if (email.isEmpty()) {
            return "Email is required";
        }

        if (pw.isEmpty()) {
            return "Password is required";
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return "Invalid email format";
        }

        if (pw.length() < 6) { 
            return "Password must be at least 6 characters";
        }

        return null;
    }


    private void showErrorMessage(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

}
