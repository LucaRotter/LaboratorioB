package applicationrob;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

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
		System.out.println(tmp);
		badgeNumber.setText(String.valueOf(tmp));
	}

	
}
