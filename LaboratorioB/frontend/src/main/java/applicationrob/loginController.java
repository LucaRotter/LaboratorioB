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

         if (email.isEmpty() || pw.isEmpty()) {
            return;
        }

        if (checkCredentials(email, pw)) {
             Model.getIstance().getView().changeToHome();
        } else {}    

    }

    @FXML
    private void changeToRegister() {
    }

     @FXML
    public void onBackPressed() {
        Model.getIstance().getView().changeToHome();
    }
    
    private void showAlert(String title, String message) {
       
    }


    public boolean checkCredentials(String email, String password) {
    //  String check = "SELECT * FROM nomeTab WHERE email = ? AND password = ?";


        return false;
    }
}
