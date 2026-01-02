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


/**
 * Classe controller per la registrazione di un nuovo utente.
 * Contiene metodi per gestire l'interfaccia di registrazione e validare i campi di input.
 * Utilizza JavaFX per la gestione dell'interfaccia utente.
 * @author Laboratorio B
 * @param id_user ID dell'utente registrato.
 */

public class RegisterController implements Initializable {

    @FXML
    private Button backBtn;

    @FXML
    private AnchorPane rootPane;
    
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

    /**
     * Metodo di inizializzazione del controller.
     * Si occupa di configurare il bottone di ritorno alla schermata principale.
     * @param location URL della risorsa FXML.
     * @param resources Risorse localizzate per l'interfaccia utente.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
       backBtn.setOnAction(e -> { 
			
			Model.getIstance().getView().changeToHome();
		});
    }

    /**
     * Metodo per gestire la registrazione di un nuovo utente.
     * Valida i campi di input e, se validi, registra l'utente tramite il clientBR.
     * In caso di successo, imposta l'ID dell'utente nella sessione e cambia alla schermata principale.
     * In caso di errore, mostra un messaggio di errore appropriato.
     */
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

    /**
     * Metodo per cambiare alla schermata di login.
     */
    @FXML
    void changeToLogin(ActionEvent event) {
        Model.getIstance().getView().changeToLogin();
    }

    /**
     * Metodo per validare i campi di input della registrazione.
     * Controlla che tutti i campi siano compilati correttamente e restituisce un messaggio di errore se necessario.
     * @param nome Nome dell'utente.
     * @param cognome Cognome dell'utente.
     * @param cf Codice fiscale dell'utente.
     * @param email Email dell'utente.
     * @param pw Password dell'utente.
     */
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

    /**
     * Metodo per validare il formato del codice fiscale.
     * Controlla che il codice fiscale sia lungo 16 caratteri e segua il formato corretto.
     * @param cf Codice fiscale da validare.
     */
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

/**
 * Metodo di supporto per verificare se una stringa contiene solo lettere.
 * @param tmp Stringa da verificare.
 */
    private boolean letterVerification(String tmp){
        for(int i=0; i< tmp.length(); i++){

        char c = tmp.charAt(i);

    if (!Character.isLetter(c)) {
        return false; 
    }
    }
        return true;
    }

    /**
     * Metodo di supporto per verificare se una stringa contiene solo numeri.
     * @param tmp Stringa da verificare.
     */
    private boolean NumberVerification(String tmp){
        for(int i=0; i< tmp.length(); i++){
        char t = tmp.charAt(i);

        
    if (!Character.isDigit(t)) {
        return false; 
    }
     }
        return true;
    }


    /**
     * Metodo per mostrare un messaggio di errore nella label dedicata.
     * @param message Messaggio di errore da visualizzare.
     */
    private void showErrorMessage(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

}
