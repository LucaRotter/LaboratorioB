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




public class RegisterController implements Initializable {

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
        String cf = cfField.getText().trim();
        String email = emailField.getText().trim();
        String pw = pwField.getText().trim();

        Utente newUser = new Utente(nome, cognome, cf, email, pw, 0);

        id_user = clientBR.getInstance().registrazione(newUser);

        if (id_user == -1) {
        }
        TokenSession.setUserId(id_user);

    }

    @FXML
    void changeToLogin(ActionEvent event) {
        Model.getIstance().getView().changeToLogin();
    }

    private boolean validateField(String nome, String cognome, String cf, String email, String pw) {

        if (nome.isEmpty() || cognome.isEmpty() || cf.isEmpty() || email.isEmpty() || pw.isEmpty()) {
            return false;
        }

        if (!isFiscalCodeValidate(cf)) {
            return false;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return false;
        }

        if (pw.length() < 6) {
            return false;
        }

        return true;
    }

    private boolean isFiscalCodeValidate(String cf) {
        if (cf == null || cf.length() != 16)
            return false;

        if (!cf.matches("^[A-Z0-9]+$"))
            return false;

        final String dispari = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final int[] valueD = {
                1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 1, 0, 5, 7, 9, 13, 15, 17, 19, 21,
                1, 0, 5, 7, 9, 13
        };
        final String pari = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final int[] valueP = {
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
                10, 11, 12, 13, 14, 15
        };

        int sum = 0;
        for (int i = 0; i < 15; i++) {
            char c = cf.charAt(i);
            int idx = c >= '0' && c <= '9' ? c - '0' : c - 'A' + 10;
            if (i % 2 == 0) {
                sum += valueD[idx];
            } else {
                sum += valueP[idx];
            }
        }

        char expectedControl = (char) ('A' + (sum % 26));
        return cf.charAt(15) == expectedControl;
    }

}
