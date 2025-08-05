package application;
	
import javafx.application.Application;
import javafx.stage.Stage;



public class Main extends Application {
	
	public static Utente utente;
	
	@Override
	public void start(Stage primaryStage) {
		
		try {
			
			System.out.println(primaryStage);
			primaryStage.setResizable(false);
			WindowController window = new WindowController(primaryStage);
			window.changeWindow("Home.fxml");
			
			gestoreDati.CreaRepositoryLibri();
			gestoreDati.creaRepositoryUtente();
			gestoreDati.creaRepositoryLibrerie();
			gestoreDati.creaRepositoryValutazioni();
			gestoreDati.creaRepositoryLibriConsigliati();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		

	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
