package applicationrob;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.Pane;
import models.Model;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class SideBarController implements Initializable{

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

	private int id_user;
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		checkLogin();
		init();
	}
	
	public void init() {
		btnHome.setOnAction(e -> onHome());
		btnLibraries.setOnAction(e -> onLibreries());
		btnLogIn.setOnAction(e -> onLogin());
		btnLogOut.setOnAction(e -> onLogout());
		linkRegister.setOnAction(e -> onRegister());
	}
	
	public void onHome() {
		Model.getIstance().getView().getSideBarSelectionItem().set("Home");
	}
	
	public void onLibreries() {
		if (!TokenSession.checkTkSession()) {
			showAlert("Login Required", "Please log in to access libraries.");
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


	public void checkLogin() {
		id_user = TokenSession.getUserId();
		if(id_user != -1) {
			btnLogOut.setVisible(true);
			loginPane.setVisible(false);
		} else {
			btnLogOut.setVisible(false);
			loginPane.setVisible(true);
		}
	}

	public void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}
	
}
