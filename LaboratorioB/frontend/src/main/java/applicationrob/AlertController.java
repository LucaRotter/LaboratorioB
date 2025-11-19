package applicationrob;

import javafx.scene.control.Label;
import javafx.scene.control.Button; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.layout.VBox;




public class AlertController { 

	@FXML
	private AnchorPane rootPane;
	@FXML 
	private Label titleLabel;
	@FXML 
	private Label messageLabel;
	@FXML 
	private Button closeButton;
	@FXML 
	private ImageView iconImage;
	@FXML
	private VBox containerElement;

	
	private String imageText;
	private BorderPane parentContainer;

	@FXML
    public void initialize() {
    }
	
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

	public void setParentContainer(BorderPane parent) {
        this.parentContainer = parent;
    }
	
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
		default : System.out.print("not found");
		    break;
		
		}
	
		iconImage.setImage(new Image(getClass().getResourceAsStream(imageText)));
		setDimensionAndButton(type);
		
	}

	private void setDimensionAndButton(String type) {
	 if (type.equalsIgnoreCase("success") || type.equalsIgnoreCase("error")) {
        closeButton.setVisible(false);  
        rootPane.setPrefSize(250, 100);
    } else if (type.equalsIgnoreCase("info")) {
        closeButton.setVisible(true);
        rootPane.setPrefSize(400, 200);
    }

	containerElement.setPrefWidth(rootPane.getPrefWidth());
	containerElement.setPrefHeight(rootPane.getPrefHeight());

		
	}

 
}