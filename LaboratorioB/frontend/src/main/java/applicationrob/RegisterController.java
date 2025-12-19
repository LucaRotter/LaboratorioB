package applicationrob;

import java.rmi.RemoteException;
import LaboratorioB.common.models.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import models.Model;
import javafx.scene.layout.AnchorPane;




public class RegisterController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button backBtn;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField cfField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField pwField;
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
    void onRegisterUser(ActionEvent event) throws RemoteException {
        String nome = nameField.getText().trim();
        String cognome = surnameField.getText().trim();
        String cf = cfField.getText().trim().toUpperCase();
        String email = emailField.getText().trim();
        String pw = pwField.getText().trim();

        String validationError = validateField(nome, cognome, cf, email, pw);
        if (validationError != null) { 
            showErrorMessage(validationError);
            return;
        }
        Utente newUser = new Utente(nome, cognome, cf, email, pw, 0);

        id_user = clientBR.getInstance().registrazione(newUser);

        if (id_user == -1) {
           showErrorMessage("Registration failed");
           return;
        }
        TokenSession.setUserId(id_user);
        Model.getIstance().getView().changeToHome();

    }

    @FXML
    void changeToLogin(ActionEvent event) {
        Model.getIstance().getView().changeToLogin();
    }

    private String validateField(String nome, String cognome, String cf, String email, String pw) {

        if (nome.isEmpty() && cognome.isEmpty() && cf.isEmpty() && email.isEmpty() && pw.isEmpty()) {
        return "All fields are required";
        }

        if (nome.isEmpty()) {
            return "Name is required";
        }

        if (cognome.isEmpty()) {
            return "Surname is required";
        }

        if (cf.isEmpty()) {
            return "Fiscal code is required";
        }

        if (email.isEmpty()) {
            return "Email is required";
        }

        if (pw.isEmpty()) {
            return "Password is required";
        }
        
        if (!isFiscalCodeValidate(cf)) {
            return "Invalid fiscal code";
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return "Invalid email format";
        }

        if (pw.length() < 6) { 
            return "Password must be at least 6 characters";
        }

        return null;
    }

    private boolean isFiscalCodeValidate(String cf) {
    
    if(cf.length() != 16){
        return false;
    }

    String firstsix = cf.substring(0,6);
    String secondeight = cf.substring(6,8);
    String thirdnine = cf.substring(8,9);
    String fourtheleven = cf.substring(9,11);
    String fifthtwelve  = cf.substring(11,12);
    String sixthfifteen = cf.substring(12,15);
    String last = cf.substring(15,16);
    
    if(!(letterVerification(firstsix) && NumberVerification(secondeight) && letterVerification(thirdnine) && NumberVerification(fourtheleven) && letterVerification(fifthtwelve)
    && NumberVerification(sixthfifteen) && letterVerification(last))){
    return false;
}

return true;
    
}

    private boolean letterVerification(String tmp){
        for(int i=0; i< tmp.length(); i++){

        char c = tmp.charAt(i);

    if (!Character.isLetter(c)) {
        return false; 
    }
    }
        return true;
    }

    private boolean NumberVerification(String tmp){
        for(int i=0; i< tmp.length(); i++){
        char t = tmp.charAt(i);

        
    if (!Character.isDigit(t)) {
        return false; 
    }
     }
        return true;
    }

    private void showErrorMessage(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

}
