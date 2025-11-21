package views;

import applicationrob.AlertController; 
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.layout.Pane; 
import javafx.scene.layout.StackPane; 
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.Group;
import javafx.geometry.Insets;	
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class ViewAlert {
    


public static void showAlert(String type, String title, String message, Node node, String position) {
	    try {
	        FXMLLoader loader = new FXMLLoader(AlertController.class.getResource("/applicationrob/AlertMsg.fxml"));
	        AnchorPane alertRoot = loader.load();
				
	        AlertController controller = loader.getController();
	        controller.setAlert(type, title, message);
				
			BorderPane rootPane = (BorderPane) node.getScene().getRoot();
            controller.setParentContainer(rootPane);

			 if (rootPane.lookup("#alertOverlay") != null) {
            return;
        }

			Pane grayLayer = new Pane();
			grayLayer.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4);");
			grayLayer.setPrefSize(rootPane.getWidth(), rootPane.getHeight());

			StackPane overlay = new StackPane();
			overlay.setId("alertOverlay");
			overlay.setStyle("-fx-background-color: transparent;");
			overlay.setAlignment(Pos.CENTER);
			overlay.getChildren().add(alertRoot);

			positionAlert(position, alertRoot, grayLayer);

			Group overlayGroup = new Group(grayLayer, overlay);
			rootPane.getChildren().add(overlayGroup);

			if (type.equalsIgnoreCase("success") || type.equalsIgnoreCase("error")) {
			    grayLayer.setMouseTransparent(true);
				setDurationAlert(overlayGroup, rootPane, 0.7, 1.0);
			}


	        } catch (IOException e) {
				
	        }
	    }


	private static void positionAlert(String position, AnchorPane alertRoot, Pane grayLayer) {
		switch (position.toLowerCase()) {
			case "info":
				StackPane.setMargin(alertRoot, new Insets(15, 0, 0, 450));
				grayLayer.setMouseTransparent(false);
				break;
			case "success":
			case "error":
				StackPane.setMargin(alertRoot, new Insets(15, 0, 0, 450));
				grayLayer.setMouseTransparent(true);
				break;
			default:
				System.out.println("Position not found, using default center.");
				grayLayer.setMouseTransparent(false);
				break;
        }
    }

	public static void setDurationAlert(Node overlayGroup, BorderPane rootPane, double delaySeconds, double fadeSeconds) {
        FadeTransition fade = new FadeTransition(Duration.seconds(fadeSeconds), overlayGroup);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setDelay(Duration.seconds(delaySeconds)); 

        fade.setOnFinished(e -> rootPane.getChildren().remove(overlayGroup));
        fade.play();    
	}
}