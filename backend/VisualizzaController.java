package application;

import java.io.IOException;
import java.util.LinkedList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class VisualizzaController implements SceneController{

	@FXML
	private Label nameLibro;
	
	@FXML
	private Label yearLabel;
	
	@FXML
	private Label authorLabel;
	
	@FXML
	private Label genresLabel;
	
	@FXML
	private Label publisherLabel;
	
	@FXML
    private GridPane gridpane;
	
	WindowController window = null;
	
	public void setLibro(Libro libro) throws IOException {
	
		nameLibro.setText(libro.getTitolo());
		yearLabel.setText(libro.getAnno());
		authorLabel.setText(libro.getAutore());
		genresLabel.setText(libro.getCategoria());
		publisherLabel.setText(libro.getEditore());
		
		LinkedList<LibriConsigliati> consigliati = libro.getElencoConsigliati();
		
		int i = 0;
		for(LibriConsigliati consigli :consigliati ) {
			
			LinkedList<Libro> libriconsigliati= consigli.getConsigliati();
				for(Libro libri: libriconsigliati) {
					
					FXMLLoader loader = new FXMLLoader(getClass().getResource("Libro.fxml"));
					
					AnchorPane root1 = loader.load();
					
					libroController controller = loader.getController();
					controller.setname(libri.getTitolo());
					
					gridpane.add(root1, i++, 1);
					GridPane.setMargin(root1, new Insets(12));
				}
		}
	}
	
    public void backtohome() throws IOException {
		
		window.changeWindow("Home.fxml");
		
	}

	@Override
	public void setWindowController(WindowController windowController) {
		window = windowController;
		
	}
	
}

