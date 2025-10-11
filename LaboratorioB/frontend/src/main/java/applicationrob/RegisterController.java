package applicationrob;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.event.ActionEvent;

public class RegisterController {

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

    @FXML
    void onRegisterUser(ActionEvent event) {
        String nome = nameField.getText().trim();
        String cognome = surnameField.getText().trim().toUpperCase();
        String cf = cfField.getText().trim();
        String email = emailField.getText().trim();
        String pw = pwField.getText().trim();

        if (!validateField(nome, cognome, cf, email, pw)) {
            return;
        }

        Utente newUser = new Utente(nome, cognome, cf, email, pw, 0);

         id_user = clientBR.BR.registrazione(newUser);

        if (id_user == -1) {
            showAlert("");
        }

    }

    @FXML
    void onBackPressed(ActionEvent event) {

    }

    @FXML
    void changeToLogin(ActionEvent event) {

    }

    private boolean validateField(String nome, String cognome, String cf, String email, String pw) {

        if (nome.isEmpty() || cognome.isEmpty() || cf.isEmpty() || email.isEmpty() || userId.isEmpty()
                || pw.isEmpty()) {
            showAlert("Campi mancanti", "Tutti i campi sono obbligatori.");
            return false;
        }

        if (!isFiscalCodeValidate(cf)) {
            showAlert("Codice fiscale non valido", "Controlla che il codice fiscale sia corretto.");
            return false;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            showAlert("Email non valida", "Inserisci un'email corretta.");
            return false;
        }

        if (pw.length() < 6) {
            showAlert("Password troppo corta", "La password deve avere almeno 6 caratteri.");
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
