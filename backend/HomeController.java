package application;

import java.io.IOException;
import java.util.LinkedList;
import javafx.animation.ScaleTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class HomeController implements SceneController{

	@FXML
	AnchorPane StylePane1;

	@FXML
	AnchorPane StylePane2;

	@FXML
	AnchorPane StylePane3;

	@FXML
	TextField barraRicerca;

	@FXML
	ComboBox <String> choiceBox;

	@FXML
	Label testoPane;
	
	@FXML
	Button utentepane;
	
	@FXML
	ContextMenu menuutente;
	
	@FXML
	ComboBox <String> yearpicker;
	
	@FXML
	VBox popuputente;
	
	@FXML
	Button popsingin;
	
	@FXML
	Button popexit;
	
	@FXML
	Button popviewprofile;
	
	WindowController window = null;

	private ScaleTransition scaleTransition;

	public void initialize(){

		choiceBox.getItems().addAll("Autore", "titolo", "autore e anno");	
		choiceBox.setValue("titolo");
		
		for(int i = 1930; i< 2020; i++) {
			
	       yearpicker.getItems().add(""+ i);
	       
		}

		Rectangle clip = new Rectangle(139, 124);
		Rectangle clip1 = new Rectangle(139, 124);
		Rectangle clip3 = new Rectangle(139, 124);
		
		clip.setArcWidth(56);  // Arrotonda gli angoli
		clip.setArcHeight(56);
		clip1.setArcWidth(56);  
		clip1.setArcHeight(56);
		clip3.setArcWidth(56);  
		clip3.setArcHeight(56);
		
		StylePane2.setClip(clip);
		StylePane1.setClip(clip1);
		StylePane3.setClip(clip3);


	}

	public void OnResearch(Event event) throws IOException {

		String barra = barraRicerca.getText();
		String choice = choiceBox.getSelectionModel().getSelectedItem(); 
		String year = yearpicker.getSelectionModel().getSelectedItem(); 
		
		if (event instanceof MouseEvent || ((KeyEvent) event).getCode() == KeyCode.ENTER) {

			if(!barra.isEmpty() && choice!= null) {
				LinkedList<Libro> libri = new Libro().cercaLibro(barra,year,choice);
				
				if(libri.size() > 1 && libri != null) {
				
				RicercaLibroController RicercaLibro = window.changeWindow("RicercaLibro.fxml");
				event.consume();
				RicercaLibro.setString(libri);
				
				}else if(!libri.isEmpty()) {
					
				VisualizzaController visualizza = window.changeWindow("VisualizzaLibro.fxml");;
				visualizza.setLibro(libri.getFirst());
				event.consume();


				} else {
					
					System.out.println("non sono presenti libri");
				}
			}
		}
	}

		
	
	
	public void onLogClick(Event event) throws IOException {
		
		if(Main.utente!=null) {
			
		if (popuputente != null && popuputente.isVisible()) {
			popuputente.setVisible(false); // Chiude la finestra se è già aperta
            return;
        }
		
		popuputente.setVisible(true);
	       
		} else {
		
		window.changeWindow("Login.fxml");
		
		}
	}
	
public void ifselected() {
	
	String choice = choiceBox.getSelectionModel().getSelectedItem();
	if(choice.equals("autore e anno")) {
		
		yearpicker.setVisible(true);
		
	} else {
		
		yearpicker.setVisible(false);
	}
}

public void onLogReg(Event event) throws IOException {
		
		window.changeWindow("Registration.fxml");
		
	}
	
public void OnMoveEnter(MouseEvent event) {

	scaleTransition = new ScaleTransition(Duration.millis(200)); 

	scaleTransition.setToX(1.1);
	scaleTransition.setToY(1.1);

	Pane sourcePane = (Pane) event.getSource();
	scaleTransition.setNode(sourcePane);
	scaleTransition.playFromStart();

	if(sourcePane.equals(StylePane1)) {

		testoPane.setText("consulta le tue librerie");

	}

	if(sourcePane.equals(StylePane2)) {

		testoPane.setText("aggiungi suggerimenti ai tuoi libri");
		
	}

	if(sourcePane.equals(StylePane3)) {

		testoPane.setText("aggiungi valutazioni ai tuoi libri");

	}
}

	public void OnMoveExit(MouseEvent event) {

		scaleTransition = new ScaleTransition(Duration.millis(200));

		Pane sourcePane = (Pane) event.getSource();
		scaleTransition.setNode(sourcePane); 
		scaleTransition.setToX(1); 
		scaleTransition.setToY(1); 
		scaleTransition.playFromStart();
		testoPane.setText("");

	}


	public void OnChangeWindow(MouseEvent event) throws IOException {

		Pane sourcePane = (Pane) event.getSource();
		visualizzaLibrerieController l;
		
		if(Main.utente ==null) {
			
			window.changeWindow("Login.fxml");
			
		}else {
			
			if(sourcePane.equals(StylePane1)) {
	
				 l = window.changeWindow("VisualizzaLibrerie.fxml");
				 l.setmodalità("vislibrerie");
			}
	
			if(sourcePane.equals(StylePane2)) {
	
				l = window.changeWindow("VisualizzaLibrerie.fxml");
				l.setmodalità("aggiungi consigli");
			}
	
			if(sourcePane.equals(StylePane3)) {
	
				l = window.changeWindow("VisualizzaLibrerie.fxml");
				l.setmodalità("aggiungi valutazioni");
			}
		}
	}

	@Override
	public void setWindowController(WindowController windowController) {
		
		window = windowController;
		
	}



}
