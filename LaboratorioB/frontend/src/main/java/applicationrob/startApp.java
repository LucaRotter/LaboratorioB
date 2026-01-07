package applicationrob;
import javafx.application.Application;
import javafx.stage.Stage;
import models.Model;

/**
 * Classe principale per l'avvio dell'applicazione JavaFX.
 * Contiene il metodo start che inizializza la vista principale dell'applicazione.
 * @author Grassi, Alessandro, 757784, VA
 * @author Kastratovic, Aleksandar, 752468, VA
 * @author Rotter, Luca Giorgio, 757780, VA
 * @author Davide, Bilora, 757011, VA
 * @version 1.0
 */
public class startApp extends Application {
    
	/**
	 * Metodo per avviare l'applicazione JavaFX.
	 * Inizializza la vista principale dell'applicazione.
	 * @param primaryStage Finestra principale dell'applicazione.
	 */
    @Override
	public void start(Stage primaryStage){
		Model.getIstance().getView().changeToHome();
		primaryStage.setResizable(false);
	}   
}
