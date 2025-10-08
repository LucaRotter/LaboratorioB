package applicationrob;


import javafx.application.Application;
import javafx.stage.Stage;
import models.Model;

//main class to lunch the application

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage)  {
		
		Model.getIstance().getView().changeToHome();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}