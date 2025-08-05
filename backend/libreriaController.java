package application;

import java.io.IOException;
import java.util.LinkedList;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class libreriaController {

	@FXML
	Label nameLibrary;
	
	Librerie librerie;
	
	WindowController window;
	
	private GridPane grid;
	
	private int col = 0;
	private int row = 1;
	
	public void setLibreria(Librerie librerie) {
		
		this.librerie = librerie;
		nameLibrary.setText(librerie.getNomeLibreria());
		
	}
	
	//creare classe per trasnsition
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
	
	public void onSelection(MouseEvent event) throws IOException{
			
		grid.getChildren().clear();
        visualizzaLibrerieController.setmodalitains("addLibro");
        visualizzaLibrerieController.setLibreriascelta(librerie);
        
		LinkedList<Libro> Libreria = librerie.getLibreria();
		col= 0;
		row = 1;
		
		for (int i = 0; i < Libreria.size(); i++) {
            try {
            	
            	 if (col == 3) {
	                    row++;
	                    col = 0;
	                }
            	 
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Libro.fxml"));
                AnchorPane root = loader.load();        
                libroController controller = loader.getController();
            
                controller.setLibri(Libreria.get(i),librerie);
                controller.setwindow(window);
                
                grid.add(root, col++, row);
                GridPane.setMargin(root, new Insets(12));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		
	}

	public void setgridPane(GridPane grid) {
		
		this.grid = grid;
		
	}
	
	public Librerie getLibreria() {
		return librerie;
		
	}

	public void setwindow(WindowController window) {
		this.window = window;
		
	}
	
	
	
	
	
}
