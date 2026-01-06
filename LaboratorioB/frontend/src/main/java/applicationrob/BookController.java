package applicationrob;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * Controller JavaFX per la visualizzazione delle informazioni di un libro.
 * Gestisce l'impostazione delle etichette per autore e titolo,
 * nonché la visualizzazione del badge con il numero di raccomandazioni.
 * 
 * @author ProgettoLabA
 */

public class BookController {
	
	@FXML
    private Label labelAuthor;

    @FXML 
    private Label labelName;

	@FXML
	private Label badgeNumber;

	@FXML 
	private StackPane badgePane;


	public void setLabels(String autore, String titolo) {
		this.labelAuthor.setText(cleanAuthor(autore));
		this.labelName.setText(titolo);		
		
	}

	private String cleanAuthor(String autore) {
        if (autore == null){
			return "";
		} 
            return autore.replaceFirst("(?i)^By\\s+", "");
    }

	public void setNumberReccomender(int tmp){

		badgePane.setVisible(true);
		badgeNumber.setText(String.valueOf(tmp));
	}

	
}
