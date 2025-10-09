package applicationrob;


import javafx.application.Application;
import javafx.stage.Stage;
import models.Model;

//main class to lunch the application

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage)  {
		
		Model.getIstance().getView().changeToHome();
		Libro n = new Libro("ciao", "ciao", "cioa", "String editore", "cioa", 1);
		System.out.println("n.getTitolo() = " + n.getTitolo());
		

	}
	
	public static void main(String[] args) {
		launch(args);
	}
}