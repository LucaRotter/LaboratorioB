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
        //int id_user;  
        //if(id == -1) { } else { }
        }
    @FXML
    private void changeToRegister() {
    }

     @FXML
    public void onBackPressed() {
       // Model.getIstance().getView().changeToHome();
    }
    
    private void showAlert(String title, String message) {
        
    }

}
