package applicationrob;

import java.net.URL;
import java.util.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.Model;
import java.rmi.RemoteException;
import javafx.scene.layout.AnchorPane;

/**
 * Classe controller per la gestione del login degli utenti.
 * Contiene metodi per gestire l'interfaccia di login e validare i campi di input.
 * Utilizza JavaFX per la gestione dell'interfaccia utente.
 * @author Laboratorio B
 * @param backBtn Bottone per tornare alla schermata principale.
 * @param rootPane AnchorPane principale della schermata di login.
 * @param emailField TextField per l'inserimento dell'email dell'utente.
 * @param pwField PasswordField per l'inserimento della password dell'utente.
 * @param errorLabel Label per la visualizzazione dei messaggi di errore durante il login.
 * @param id_user ID dell'utente loggato.
 */
public class LoginController implements Initializable {

    @FXML
    private Button backBtn;

    @FXML
    private AnchorPane rootPane;

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
     * Metodo per gestire il login dell'utente.
     * Valida i campi di input e, se validi, effettua il login tramite il clientBR.
     * In caso di successo, imposta l'ID dell'utente nella sessione e cambia alla schermata principale.
     * In caso di errore, mostra un messaggio di errore appropriato.
     * @throws RemoteException Se si verifica un errore di comunicazione remota.
     */
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
        Model.getIstance().getView().changeToHome();
    } 

    /**
     * Metodo per cambiare alla schermata di registrazione.
     */
    @FXML
    public void changeToRegister() {
         Model.getIstance().getView().changeToRegister();
    }

    /**
     * Metodo per validare i campi di input del login.
     * Controlla che tutti i campi siano compilati correttamente e restituisce un messaggio di errore se necessario.
     * @param email Email dell'utente.
     * @param pw Password dell'utente.
     * @return Messaggio di errore se la validazione fallisce, altrimenti null.
     */
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

    /**
     * Metodo per mostrare un messaggio di errore nella label dedicata.
     * @param message Messaggio di errore da visualizzare.
     */
    private void showErrorMessage(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

}
