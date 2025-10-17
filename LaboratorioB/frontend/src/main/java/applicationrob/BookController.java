package applicationrob;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BookController {
	
	@FXML
    private Label labelAuthor;

    @FXML 
    private Label labelName;


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

}
