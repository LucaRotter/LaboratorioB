package views;

import java.io.IOException;

import applicationrob.ClientController;
import applicationrob.VisLibroController;
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
	private AnchorPane DashboardMain;
	private AnchorPane VisLibrerie;
	private AnchorPane VisLibro;
	private ObjectProperty<Libro> selectedLibr;
	private ObjectProperty<Libreria> selectedLibreria;
	
	public ViewFactory() {
		this.SideBarSelection = new SimpleStringProperty("");
		this.selectedLibr = new SimpleObjectProperty<Libro>();
		this.selectedLibreria = new SimpleObjectProperty<Libreria>();
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
		VisLibro = MoveToPage("/applicationrob/VisLibreria.fxml", VisLibro);
		return VisLibro;
	}  
	
		
	public void changeToHome() {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/applicationrob/MainPage.fxml"));
		ClientController controller = new ClientController();
		loader.setController(controller);
		Scene scene = null;
		
		try {
			 
			scene =new Scene(loader.load());
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		Stage stage =new Stage();
		stage.setScene(scene);
		stage.show();
		
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

    /*private AnchorPane ChangeToPage(String fxmlPath, AnchorPane pane) {
        if (pane == null) {
    }
        return pane;
    } */

	
	
	
		
}
