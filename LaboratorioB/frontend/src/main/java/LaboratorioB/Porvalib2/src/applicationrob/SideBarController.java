package applicationrob;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import models.Model;

public class SideBarController implements Initializable{

	@FXML
	private Button btnHome;

	@FXML
	private Button btnLibraries;
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		init();
	}
	
	public void init() {
		btnHome.setOnAction(e -> onHome());
		btnLibraries.setOnAction(e -> onLibreries());
	}
	
	public void onHome() {
		Model.getIstance().getView().getSideBarSelectionItem().set("Home");
		System.out.print("davide gay");
	}
	
	public void onLibreries() {
		Model.getIstance().getView().getSideBarSelectionItem().set("VisLibrerie");
		System.out.print("davide gay2");
	}
	
	
}
