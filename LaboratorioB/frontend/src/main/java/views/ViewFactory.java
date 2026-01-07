package views;

import java.io.IOException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane; 
import javafx.stage.Stage;
import LaboratorioB.common.models.*;

/** Classe ViewFactory che gestisce la creazione e la navigazione tra le diverse viste dell'applicazione.
 * Fornisce metodi per ottenere le varie schermate (Dashboard, Visualizzazione Librerie, Visualizzazione Libro, ecc.)
 * e mantiene lo stato della selezione corrente nella barra laterale.
 * Gestisce inoltre la cronologia delle pagine visitate per abilitare la funzionalità "Back".
 * 
 * @author Grassi, Alessandro, 757784, VA
 * @author Kastratovic, Aleksandar, 752468, VA
 * @author Rotter, Luca Giorgio, 757780, VA
 * @author Davide, Bilora, 757011, VA
 * @version 1.0
 */

public class ViewFactory {
	
	private final StringProperty SideBarSelection;
	private ObjectProperty<Libro> selectedLibr;
	private ObjectProperty<Libreria> selectedLibreria;
	private BooleanProperty reccommender;
	private BooleanProperty review;
	private AnchorPane DashboardMain;
	private AnchorPane VisLibrerie;
	private AnchorPane VisLibro;
	private AnchorPane VisLibreria;
	private AnchorPane AddReview;
	private AnchorPane AddReccomended;

	private ObservableList<String> historyPage;

	private Stage Stage;

	public ViewFactory() {
		
		this.SideBarSelection = new SimpleStringProperty("");
		this.selectedLibr = new SimpleObjectProperty<Libro>();
		this.selectedLibreria = new SimpleObjectProperty<Libreria>();
		this.reccommender = new SimpleBooleanProperty(false);
		this.review = new SimpleBooleanProperty(false);

		historyPage =  FXCollections.observableArrayList();

		this.Stage = new Stage();
		this.Stage.setResizable(false);
		this.Stage.getIcons().add(new Image(getClass().getResourceAsStream("/img/iconBK.png")));
		this.Stage.setTitle("BookRecommender");
	}

	public BooleanProperty getRecommenderRefresh() {
    return reccommender;
}

	public BooleanProperty getReviewRefresh() {
    return review;
}
	
	public StringProperty getSideBarSelectionItem() {
		return SideBarSelection;
	}
	
	public AnchorPane getDashboardMain(){
	    DashboardMain = MoveToPage("/applicationrob/Dashboard.fxml", DashboardMain);
		historyPage.clear();
	    return DashboardMain;
	}
	
	public AnchorPane getVisLibrerie(){
		VisLibrerie = MoveToPage("/applicationrob/VisLibrerie.fxml", VisLibrerie);
		listControl("VisLibrerie");
		return VisLibrerie;
		}
	
	public AnchorPane getVisLibro() {
		VisLibro = MoveToPage("/applicationrob/VisLibro.fxml", VisLibro);
		listControl("VisLibro");
		return VisLibro;
	}       
	 
	public AnchorPane getVisLibreria() {
		VisLibreria = MoveToPage("/applicationrob/VisLibreria.fxml", VisLibreria);
		listControl("VisLibreria");
		return VisLibreria;
	}  

	public AnchorPane getAddReview() {
		AddReview = MoveToPage("/applicationrob/AddReview.fxml", AddReview);
		listControl("AddReview");
		return AddReview;
	}  

	public AnchorPane getAddReccomended() {
		AddReccomended = MoveToPage("/applicationrob/AddReccomended.fxml", AddReccomended);
		historyPage.add("AddRecommended");
		return AddReccomended;
	}  
		
	public void changeToHome() {
		
		initializeStage("/applicationrob/MainPage.fxml");
		VisLibrerie = null;

	}

	public void changeToLogin() {
		
		initializeStage("/applicationrob/loginPage.fxml");
	
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

	public void lastHistory() {

		if (!historyPage.isEmpty()) {

        historyPage.removeLast();

        if (!historyPage.isEmpty()) {

		
            String previous = historyPage.getLast();
            SideBarSelection.set(previous);

        } else {
            SideBarSelection.set("Dashboard");
        }
    }
	}

	public void listControl(String selectValue) {
    if (historyPage.isEmpty() || !selectValue.equals(historyPage.getLast())) {
        historyPage.add(selectValue);
    }
}

	public ObservableList<String> getHistoryPage() {
		
		return historyPage;
	}
	

}
