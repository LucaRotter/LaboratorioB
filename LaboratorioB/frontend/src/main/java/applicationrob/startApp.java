package applicationrob;
import javafx.application.Application;
import javafx.stage.Stage;
import models.Model;

public class startApp extends Application {
    
    @Override
	public void start(Stage primaryStage){
		Model.getIstance().getView().changeToHome();
	}   
}
