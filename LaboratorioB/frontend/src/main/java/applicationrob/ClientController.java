package applicationrob;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import models.Model;

public class ClientController implements Initializable{
	
	@FXML
	public BorderPane MainPage;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		Model.getIstance().getView().getSideBarSelectionItem().addListener((ObservalValue, oldValue, newValue) -> {
			
			switch(newValue) {
			
			case "VisLibrerie" : MainPage.setCenter(Model.getIstance().getView().getVisLibrerie());
			break;
			
			case "VisLibreria" : MainPage.setCenter(Model.getIstance().getView().getVisLibreria());
			break;
			
			case "VisLibro" : MainPage.setCenter(Model.getIstance().getView().getVisLibro());
			break;

			
			
			default: MainPage.setCenter(Model.getIstance().getView().getDashboardMain());
			
			}

			System.out.println("Changed to: " + newValue);
		
		}); 
	}
	
	
}
