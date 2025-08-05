package application;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WindowController {

	private Stage stage;
	
	public WindowController(Stage stage) {
		this.stage =  stage;
	}
	
	public Stage getStage() {
		return stage;
		
	}
	
	public <T> T  changeWindow(String Filefxml) throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource(Filefxml));
        Parent root = loader.load();
        
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
		T controller = loader.getController();
		((SceneController) controller).setWindowController(this);
		return controller;
		
	}
	
	
	
	
	
	
}
