package applicationrob;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import models.Model;
import views.ViewFactory;
import LaboratorioB.common.models.*;

public class VisLibroController implements Initializable{

	@FXML 
	private AnchorPane root;

	@FXML
    private Label titoloLabel;
	@FXML
    private Label autoreLabel;
	@FXML
    private Label genereLabel;
	@FXML
    private Label editoreLabel;
	@FXML
    private Label annoLabel;
	@FXML
    private Label starLabel;
	
	private Libro selectedBook;
 
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Model.getIstance().getView().selectedBookProperty().addListener((obs, oldLibr, newLibr) -> {
        if (newLibr != null) {
            titoloLabel.setText(newLibr.getTitolo());
            autoreLabel.setText(newLibr.getAutore());
            genereLabel.setText(newLibr.getGenere());
            editoreLabel.setText(newLibr.getEditore());
            annoLabel.setText(String.valueOf(newLibr.getAnno()));
        }
    });

		Libro libro = Model.getIstance().getView().getSelectedBook();
		if (libro != null) {
			titoloLabel.setText(libro.getTitolo());
			autoreLabel.setText(libro.getAutore());
			genereLabel.setText(libro.getGenere());
			editoreLabel.setText(libro.getEditore());
			annoLabel.setText(String.valueOf(libro.getAnno()));
		}
	}

	public void setLibro(Libro selectedBook) {
			this.selectedBook = selectedBook;
			
	}

	public void setSelectedBook(Libro selectedBook) {
		
		this.selectedBook = selectedBook;
	}
}
