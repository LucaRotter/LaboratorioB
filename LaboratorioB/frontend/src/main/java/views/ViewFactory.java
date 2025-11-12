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
		selectedLibr.set(null);
		selectedLibr.set(lib); 
	}


	public ObjectProperty<Libreria> selectedLibraryProperty() { 
		return selectedLibreria; 
	}

    public Libreria getSelectedLibrary() { 
		return selectedLibreria.get(); 
	}

    public void setSelectedLibrary(Libreria lib) { 
		selectedLibreria.set(null); 
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

}
