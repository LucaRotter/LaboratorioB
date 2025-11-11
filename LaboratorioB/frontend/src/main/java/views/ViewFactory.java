package views;

import java.io.IOException; 
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane; 
import javafx.stage.Stage;
import LaboratorioB.common.models.*;
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

public class ViewFactory {
	
	private final StringProperty SideBarSelection;
	private ObjectProperty<Libro> selectedLibr;
	private ObjectProperty<Libreria> selectedLibreria;

	private AnchorPane DashboardMain;
	private AnchorPane VisLibrerie;
	private AnchorPane VisLibro;
	private AnchorPane VisLibreria;
	private AnchorPane AddReview;
	private AnchorPane AddReccomended;

	private Stage Stage;

	public ViewFactory() {
		this.SideBarSelection = new SimpleStringProperty("");
		this.selectedLibr = new SimpleObjectProperty<Libro>();
		this.selectedLibreria = new SimpleObjectProperty<Libreria>();

		this.Stage = new Stage();
	}
	
	public StringProperty getSideBarSelectionItem() {
		return SideBarSelection;
	}
	
	public AnchorPane getDashboardMain(){
	    DashboardMain = MoveToPage("/applicationrob/Dashboard.fxml", DashboardMain);
	    return DashboardMain;
	}
	
	public AnchorPane getVisLibrerie(){
		VisLibrerie = MoveToPage("/applicationrob/VisLibrerie.fxml", VisLibrerie);
		return VisLibrerie;
		}
	
	public AnchorPane getVisLibro() {
		VisLibro = MoveToPage("/applicationrob/VisLibro.fxml", VisLibro);
		return VisLibro;
	}       
	 
	public AnchorPane getVisLibreria() {
		VisLibreria = MoveToPage("/applicationrob/VisLibreria.fxml", VisLibreria);
		return VisLibreria;
	}  

	public AnchorPane getAddReview() {
		AddReview = MoveToPage("/applicationrob/AddReview.fxml", AddReview);
		return AddReview;
	}  

	public AnchorPane getAddReccomended() {
		AddReccomended = MoveToPage("/applicationrob/AddReccomended.fxml", AddReccomended);
		return AddReccomended;
	}  
	
		
	public void changeToHome() {
		
		initializeStage("/applicationrob/MainPage.fxml");
		
	}

	public void changeToLogin() {
		
		initializeStage("/applicationrob/LoginPage.fxml");
		
	}

	public void changeToRegister() {
		
		initializeStage("/applicationrob/RegisterPage.fxml");
		
	}

    public ObjectProperty<Libro> selectedBookProperty() { 
		return selectedLibr; 
	}

    public Libro getSelectedBook() { 
		return selectedLibr.get(); 
	}

    public void setSelectedBook(Libro lib) { 
		selectedLibr.set(lib); 
	}


	public ObjectProperty<Libreria> selectedLibraryProperty() { 
		return selectedLibreria; 
	}

    public Libreria getSelectedLibrary() { 
		return selectedLibreria.get(); 
	}

    public void setSelectedLibrary(Libreria lib) { 
		/*if (lib.getIdLibreria() == getSelectedLibrary().getIdLibreria()) {
			selectedLibreria.set(null); 
		} */
		selectedLibreria.set(lib); 
		
	}
	

	public AnchorPane MoveToPage(String fxmlPath, AnchorPane pane) {
        if (pane == null) {
            try {
                pane = new FXMLLoader(getClass().getResource(fxmlPath)).load();
            } catch (IOException e) {
            e.printStackTrace();
            }   
		}
        return pane;
    }

   
	public void initializeStage(String FilePath) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(FilePath));
		Scene scene = null;
		
		try {
			
			scene =new Scene(loader.load()); 
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		Stage.setScene(scene);
		Stage.show(); 
	}

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
				setDurationAlert(overlayGroup, rootPane, 2.5, 1.0);
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
				StackPane.setMargin(alertRoot, new Insets(15, 0, 0, 820));
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
