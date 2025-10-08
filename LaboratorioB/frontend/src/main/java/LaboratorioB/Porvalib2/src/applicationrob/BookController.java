package applicationrob;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BookController {
	
	@FXML
    private Label labelAuthor;

    @FXML
    private Label labelName;


	public void setLabels(String autore, String nome) {
		
		this.labelAuthor.setText(autore);
		this.labelName.setText(nome);
		
	}

}
