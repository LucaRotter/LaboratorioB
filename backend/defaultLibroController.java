package application;

import java.io.IOException;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;

public class defaultLibroController {
	
	@FXML 
	AnchorPane principale;
	private Librerie librerie;
	private Libro libro;
	
	public void onSelection() throws IOException {
		
		HBox slot = (HBox) principale.getParent();
		FXMLLoader loaderdialog = new FXMLLoader(getClass().getResource("dialogLibrerie.fxml"));
		Parent root = loaderdialog.load();
		dialogController dialog = loaderdialog.getController();
		 
		 Stage popupStage = new Stage();
		
		 Window principalwindow = principale.getScene().getWindow();
		 
		 popupStage.initStyle(StageStyle.TRANSPARENT);
	     popupStage.initOwner(principalwindow);
	     popupStage.initModality(Modality.APPLICATION_MODAL);
	     
	     Scene scene = new Scene(root);
	     scene.setFill(Color.TRANSPARENT);
	     popupStage.setScene(scene);
	   
	     popupStage.setX(principalwindow.getX() + 130);
	     popupStage.setY(principalwindow.getY() + 238);
	     
	     popupStage.showAndWait();
	     String read = dialog.getText();
	     
	     LibriConsigliati trovato ;
	     boolean validation = dialog.getValidation();
	     
	     System.out.println("faccio il controllo 1");
	     System.out.println(validation);
	     if(!read.equals("") && validation) {
	     System.out.println(Main.utente.getUserid());
	     
	     trovato = libro.inserisciConsiglio(read, Main.utente);
	     System.out.println(libro.getElencoConsigliati());
	     
	     if(trovato!= null) {
	    	 
	     System.out.println("trovato");
	     FXMLLoader loader = new FXMLLoader(getClass().getResource("Libro.fxml"));
		 AnchorPane root1 = loader.load();
		 libroController controller = loader.getController();
	       
		 slot.getChildren().remove(0);
		 slot.getChildren().add(root1);
		 
		 controller.setname(read);
		 
		 
	     }
	    }
	}

	public void OnMoveEnter(MouseEvent event) {

		//System.out.println("sono dento");

		ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200)); 

		scaleTransition.setToX(1.1);
		scaleTransition.setToY(1.1);

		Pane sourcePane = (Pane) event.getSource();
		scaleTransition.setNode(sourcePane);
		scaleTransition.playFromStart();
	}
	
	public void OnMoveExit(MouseEvent event) {

		ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200));

		Pane sourcePane = (Pane) event.getSource();
		scaleTransition.setNode(sourcePane); 
		scaleTransition.setToX(1); 
		scaleTransition.setToY(1); 
		scaleTransition.playFromStart();
		
	}

	public void setLibri(Libro libro) {
		this.libro= libro;
		
	}
	
}
