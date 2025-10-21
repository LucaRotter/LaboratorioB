package applicationrob;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import models.Model;

public class SideBarController implements Initializable{

	@FXML
	private Button btnHome;

	@FXML
	private Button btnLibraries;

	@FXML
	private Button btnLogIn;

	@FXML
	private Hyperlink linkRegister;
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		init();
	}
	
	public void init() {
		btnHome.setOnAction(e -> onHome());
		btnLibraries.setOnAction(e -> onLibreries());
		btnLogIn.setOnAction(e -> onLogin());
		linkRegister.setOnAction(e -> onRegister());
	}
	
	public void onHome() {

		Model.getIstance().getView().getSideBarSelectionItem().set("Home");
	}
	
	public void onLibreries() {

		Model.getIstance().getView().getSideBarSelectionItem().set("VisLibrerie");
	}

	public void onLogin() {
		
		Model.getIstance().getView().changeToLogin();
	}
	
	public void onRegister() {
		
		Model.getIstance().getView().changeToRegister();
	}
	
}
