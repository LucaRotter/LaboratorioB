package applicationrob;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import models.Model;
import javafx.scene.layout.Pane;

public class SideBarController implements Initializable{ 


	 /**
	 * Controller JavaFX della barra laterale dell'applicazione.
	 * Gestisce la navigazione tra le principali sezioni (Home, Librerie, Login, Registrazione)
	 * e l'azione di logout. Inoltre controlla la visibilità dei componenti in base allo stato
	 * di login dell'utente e abilita/disabilita il pulsante "Back" in base alla cronologia delle pagine.
	 * 
	 * @author Grassi, Alessandro, 757784, VA
	* @author Kastratovic, Aleksandar, 752468, VA
	* @author Rotter, Luca Giorgio, 757780, VA
	* @author Davide, Bilora, 757011, VA
	* @version 1.0
	 */


	@FXML
	private Button btnBack;

	@FXML
	private Button btnHome;

	@FXML
	private Button btnLibraries;

	@FXML
	private Button btnLogIn;

	@FXML
	private Button btnLogOut;

	@FXML
	private Hyperlink linkRegister;

	@FXML
	private Pane loginPane; 

	@FXML
	private Pane logOutPane;

	private int id_user;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		checkLogin();
		init();

		ObservableList<String> history = Model.getIstance().getView().getHistoryPage();

		btnBack.disableProperty().bind(Bindings.size(history).lessThanOrEqualTo(0));
		btnBack.visibleProperty().bind(Bindings.size(history).greaterThan(0));

	} 
	
	public void init() {
		btnBack.setOnAction(e -> onBack());
		btnHome.setOnAction(e -> onHome());
		btnLibraries.setOnAction(e -> onLibreries()); 
		btnLogIn.setOnAction(e -> onLogin());
		btnLogOut.setOnAction(e -> onLogout());
		linkRegister.setOnAction(e -> onRegister());
	}

	public void onBack() {
	
		Model.getIstance().getView().lastHistory();
			
	}
	
	public void onHome() {
		Model.getIstance().getView().getSideBarSelectionItem().set("Home");
	}
	
	public void onLibreries() {
		if (!TokenSession.checkTkSession()) {
			views.ViewAlert.showAlert("info", "Access denied", "You must login to access the libraries.", btnLibraries, "info");
        } else {
		    Model.getIstance().getView().getSideBarSelectionItem().set("VisLibrerie");
	    }
	}

	public void onLogin() {
		Model.getIstance().getView().changeToLogin();
	}
	
	public void onRegister() {
		Model.getIstance().getView().changeToRegister();
	}

	public void onLogout() {
		TokenSession.setUserId(-1);
		Model.getIstance().getView().changeToHome();
	}

	/**
	 * metodo per controllare lo stato di login e aggiornare la visibilità dei componenti
	 */
	public void checkLogin() {
		id_user = TokenSession.getUserId();
		if(id_user != -1) {
			btnLogOut.setVisible(true);
			loginPane.setVisible(false);
			logOutPane.setVisible(true);
		} else {
			btnLogOut.setVisible(false);
			loginPane.setVisible(true);
			logOutPane.setVisible(false);
		}
	}
	
}
