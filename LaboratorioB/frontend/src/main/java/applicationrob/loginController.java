package applicationrob;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class loginController {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField pwField;
    @FXML
    private Button backBtn;

    @FXML
    private void onUserLogin() {
        String email = emailField.getText();
        String pw = pwField.getText();

      /*  if (email.equals() && pw.equals()) {    
        } else {   
        }
        */
    }

    @FXML
    private void changeToRegister() {
    }

     @FXML
    private void onBackPressed() {
    }
    
    private void showAlert(String title, String message) {
       
    }
}
