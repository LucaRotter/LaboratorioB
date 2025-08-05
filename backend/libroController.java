package application;

import java.io.IOException;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class libroController {

	@FXML
	Label nameBook;
	
	private Libro libro;
	
	String modalita = visualizzaLibrerieController.getmodalita();

	private WindowController window;

	private Librerie librerie;
	
	boolean visconsigliati= true;
	
	public void onSelect() throws IOException {
		
		System.out.println(visconsigliati);
		if(modalita.equals("aggiungi consigli")) {
	
			if(visconsigliati) {
			
			ConsigliLibroController consigli =window.changeWindow("ConsigliLibro.fxml");
			consigli.setLibro(libro,librerie); 
			
			 }
			
		}else if(modalita.equals("aggiungi valutazioni")) {
			
			ValutazioneLibroController visualizza =window.changeWindow("ValutazioniLibro.fxml");
			visualizza.setLibro(libro,librerie);
			
		}else if(modalita.equals("vislibrerie")){
			
		VisualizzaController visualizza = window.changeWindow("VisualizzaLibro.fxml");
		visualizza.setLibro(libro);
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
	
	public void setLibri(Libro Libro,Librerie librerie) {
		
		this.libro= Libro;
		this.librerie = librerie;
		nameBook.setText(Libro.getTitolo());
		
	}

	public void setname(String read) {
		nameBook.setText(read);
		
	}
	public void setVisConsigliati(boolean vis) {
		
		this.visconsigliati= vis;
	}

	public void setwindow(WindowController window) {
		this.window =window;
		
	}
}
	
	
