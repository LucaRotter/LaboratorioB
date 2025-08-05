package application;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class ConsigliLibroController implements SceneController{

	    @FXML
	    private Button backhome;

	    @FXML
	    private Label labelLibro;

	    @FXML
	    private HBox slot1;

	    @FXML
	    private HBox slot2;

	    @FXML
	    private HBox slot3;

	private WindowController window;
	
	@Override
	public void setWindowController(WindowController windowController) {
		 window = windowController;
		
	}

	public void setLibro(Libro libro, Librerie librerie) throws IOException {
		
		labelLibro.setText(labelLibro.getText()+ " "+ libro.getTitolo());	
		
		System.out.println(libro);
		LibriConsigliati consigliati = libro.returnConsigliatiPersonali(libro, Main.utente);
		
		int i = 0;
		List<Pane> slots = List.of(slot1, slot2, slot3);
		
		if(consigliati!= null) {
		
			LinkedList<Libro> lib = consigliati.getConsigliati();
			
			for ( i = 0; i < lib.size() && i < slots.size(); i++) {
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("Libro.fxml"));
		        Parent root = loader.load();

		        libroController controller = loader.getController();
		        controller.setname(lib.get(i).getTitolo());
		        controller.setVisConsigliati(false);
		        
		        slots.get(i).getChildren().add(root);
		    }
			
			while(i < 3) {
				
				FXMLLoader loader = new FXMLLoader(getClass().getResource("deaultLibro.fxml"));
		        Parent root = loader.load();
		        defaultLibroController controller = loader.getController();
		        
		        controller.setLibri(libro);
				slots.get(i++).getChildren().add(root);
		}
			
	  }else {
		  
		  while(i < 3) {
				
				FXMLLoader loader = new FXMLLoader(getClass().getResource("deaultLibro.fxml"));
		        Parent root = loader.load();
		        defaultLibroController controller = loader.getController();
		        
		        controller.setLibri(libro);
				slots.get(i++).getChildren().add(root);
	  }
	}
	}
	
	
public void backtohome() throws IOException {
		
		window.changeWindow("Home.fxml");
		
}

public void backLibrary() throws IOException {
		visualizzaLibrerieController l = window.changeWindow("VisualizzaLibrerie.fxml");
		l.setmodalità("vislibrerie");
		
	
}
}


