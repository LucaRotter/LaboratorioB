package applicationrob;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.event.ActionEvent;

public class RegisterController {

    @FXML private TextField nameField;
    @FXML private TextField surnameField;
    @FXML private TextField cfField;
    @FXML private TextField emailField;
    @FXML private TextField userIdField;
    @FXML private PasswordField pwField;

    @FXML
    void onRegisterUser(ActionEvent event) {
        String nome = nameField.getText().trim();
        String cognome = surnameField.getText().trim();
        String cf = cfField.getText().trim();
        String email = emailField.getText().trim();
        String userId = userIdField.getText().trim();
        String pw = pwField.getText().trim();
    }

     @FXML
    void onBackPressed(ActionEvent event) {
        
    }

    @FXML
    void changeToLogin(ActionEvent event) {
        
    }
}
