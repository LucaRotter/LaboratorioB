package applicationrob;

import javafx.scene.control.Label;
import javafx.scene.control.Button; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.layout.VBox;


/**
 * Classe controller per la gestione degli alert dell'applicazione.
 * Contiene metodi per visualizzare diversi tipi di alert (errore, successo, informazione).
 * Utilizza JavaFX per la gestione dell'interfaccia utente.
 * @author Laboratorio B
 * @param imageText Percorso dell'immagine dell'icona.
 * @param parentContainer BorderPane genitore per la gestione della visualizzazione dell'alert nelle varie pagine.
 */

public class AlertController { 

	@FXML 
	private Button closeButton;

	@FXML
	private AnchorPane rootPane;

	@FXML 
	private Label titleLabel;

	@FXML 
	private Label messageLabel;
	
	@FXML 
	private ImageView iconImage;

	@FXML
	private VBox containerElement;

	
	private String imageText;

	private BorderPane parentContainer;

	/**
	 * Metodo di inizializzazione del controller.
	 */
	@FXML
    public void initialize() {
    }
	
	/**
	 * Metodo per chiudere l'alert.
	 * Rimuove l'alert dal container genitore.
	 */
	@FXML 
	private void closeModal() {
		 Node current = rootPane;
    while (current != null && !(current instanceof Group)) {
        current = current.getParent();
    }

    if (current != null && parentContainer != null) {
        parentContainer.getChildren().remove(current);
    }
	}

	/**
	 * Metodo per impostare il container genitore.
	 * @param parent BorderPane genitore.
	 */
	public void setParentContainer(BorderPane parent) {
        this.parentContainer = parent;
    }
	
	/**
	 * Metodo per impostare l'alert.
	 * @param type Tipo di alert (error, success, info).
	 * @param title Titolo dell'alert.	
	 * @param message Messaggio dell'alert.
	 */

	public void setAlert(String type, String title, String message) {
		titleLabel.setText(title);
		messageLabel.setText(message);

		 rootPane.getStyleClass().setAll("alert-pane", type.toLowerCase());
		
		switch (type.toLowerCase()) {
		case "error":
			imageText = "/img/error.png";
			break;
		case "success":
			imageText = "/img/success.png";
			break;
		case "info":
			imageText = "/img/info.png";
			break;
		default :
		    break;
		
		}
	
		iconImage.setImage(new Image(getClass().getResourceAsStream(imageText)));
		setDimensionAndButton(type);
		
	}

	/**
	 * Metodo per impostare le dimensioni dell'alert e la visibilità del bottone di chiusura.
	 * @param type Tipo di alert (error, success, info).
	 */
	private void setDimensionAndButton(String type) {
	 if (type.equalsIgnoreCase("success") || type.equalsIgnoreCase("error")) {
        closeButton.setVisible(false);  
      
    } else if (type.equalsIgnoreCase("info")) {
        closeButton.setVisible(true);
        
    }

		
	}

 
}