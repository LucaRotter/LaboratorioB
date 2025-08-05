package application;

import java.io.IOException;

import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements SceneController{

	@FXML
	TextField IDfield;
	
	@FXML
	TextField Passwordfield;
	
	@FXML
	Button logButton;
	
	@FXML
	TextField nameField;
	
	@FXML
	TextField surnameField;
	
	@FXML
	TextField cfField;
	
	@FXML
	TextField emailField;
	
	@FXML
	TextField useridField;
	
	@FXML
	PasswordField PasswordField;
	
	@FXML
	PasswordField confirmPwField;
	
	@FXML
	Button singupbutton;
	
	@FXML
	Button backrow;
	
	Utente Registred ;
	boolean cfconfirmed = true;
	boolean userconfirmed = true;
	boolean passwordconfirmed = true;
	WindowController window= null;
	
	public void OnLogin(ActionEvent event) throws IOException {
		
		Registred = new Utente().login(IDfield.getText(), Passwordfield.getText());
		
		if(Registred != null) {
			
		window.changeWindow("Home.fxml");
		Main.utente = Registred;
		
		
		} else {
			
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setHeaderText("ID or Password incorrect");
			alert.setContentText("try another one time");
			alert.showAndWait();			
		}
	}
	
	public void OnRegistration() throws IOException {
		
		if(!nameField.getText().isEmpty() && !surnameField.getText().isEmpty() && !emailField.getText().isEmpty() && cfconfirmed && userconfirmed && passwordconfirmed) {
			
		Registred = new Utente().registrazione(nameField.getText() , surnameField.getText(), cfField.getText(), emailField.getText(),useridField.getText(),PasswordField.getText());
		
		
		if(Registred!=null) {
			
			System.out.println("registrazione avvenuta");
			window.changeWindow("Home.fxml");
			Main.utente = Registred;
		}
		
		}else {
			
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setHeaderText("MISSING DATA");
			alert.setContentText("Insert the missing data to complete the registration");
			alert.showAndWait();
		}
		
		
	}
	
	public void syntaxControll(Event event) {
		
		PseudoClass validClass = PseudoClass.getPseudoClass("valid");
		
		if(event.getTarget() == cfField) {
		
		if(new Utente().ControlloCodFiscale(cfField.getText()) == true) {
			
			cfconfirmed = true;
			cfField.pseudoClassStateChanged(validClass, true);
			
			
		} else {
			cfField.pseudoClassStateChanged(validClass, false);
			cfconfirmed = false;
		}
		
		}else if(event.getTarget() == useridField) {
			
			if((new Utente().ControlloUserId(useridField.getText()) == false) && useridField.getText().length() > 3) {
				
				useridField.pseudoClassStateChanged(validClass, true);
				userconfirmed = true;
				
			} else {
				
				useridField.pseudoClassStateChanged(validClass, false);
				userconfirmed = false;
			}
			
		}else if(event.getTarget()==confirmPwField || event.getTarget()==PasswordField){
			
			if(confirmPwField.getText()!=null && PasswordField.getText().matches(confirmPwField.getText()) && !PasswordField.getText().equals("")) {
				
				PasswordField.pseudoClassStateChanged(validClass, true);
				confirmPwField.pseudoClassStateChanged(validClass, true);
				passwordconfirmed = true;
				
			} else {
				
				PasswordField.pseudoClassStateChanged(validClass, false);
				confirmPwField.pseudoClassStateChanged(validClass, false);
				passwordconfirmed = false;
			}
		}
	}
	
	public void OnlinkLogin() throws IOException {
		
		window.changeWindow("Login.fxml");
		
	}
	

	public void OnlinkRegister() throws IOException {
		
		window.changeWindow("Registration.fxml");
		
	}
	
	public void OnBack() throws IOException {
		window.changeWindow("Home.fxml");
	}
	
	@Override
	public void setWindowController(WindowController windowController) {
		
		this.window = windowController;
		
	}
}
