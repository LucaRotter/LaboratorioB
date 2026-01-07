package applicationrob;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * Controller JavaFX per la visualizzazione delle informazioni di un libro.
 * Gestisce l'impostazione delle etichette per autore e titolo,
 * nonché la visualizzazione del badge con il numero di raccomandazioni.
 * 
 * @author Grassi, Alessandro, 757784, VA
 * @author Kastratovic, Aleksandar, 752468, VA
 * @author Rotter, Luca Giorgio, 757780, VA
 * @author Davide, Bilora, 757011, VA
 * @version 1.0
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
