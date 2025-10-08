package views;

import java.io.IOException;

import applicationrob.ClientController;
import applicationrob.VisLibroController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewFactory {
	
	private final StringProperty SideBarSelection;
	private AnchorPane DashboardMain;
	private AnchorPane VisLibrerie;
	private AnchorPane VisLibro;
	private String selectedBook;
	
	public ViewFactory() {
		
		this.SideBarSelection = new SimpleStringProperty("");
		
	}
	
	public StringProperty getSideBarSelectionItem() {
		
		return SideBarSelection;
	}
	
	public AnchorPane getDashboardMain(){
		
		if(DashboardMain==null) {
			
			try {
				
				DashboardMain = new FXMLLoader(getClass().getResource("/applicationrob/Dashboard.fxml")).load();
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		return DashboardMain;
	}
	
	public AnchorPane getVisLibrerie(){
			
			if(VisLibrerie==null) {
				
				try {
					
					VisLibrerie = new FXMLLoader(getClass().getResource("/applicationrob/VisLibrerie.fxml")).load();
					
				} catch (IOException e) {
					
					e.printStackTrace();
					System.out.print("davide gay3");
				}
			}
			
			return VisLibrerie;
		}
	
	public AnchorPane getVisLibro() {
		
		
		FXMLLoader loader= new FXMLLoader(getClass().getResource("/applicationrob/VisLibro.fxml"));
		
		try {
			VisLibro = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		VisLibroController visController = loader.getController();
		visController.setLibro(this.selectedBook);
		
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
	
	public void setSelectedBook(String selectedBook) {
		
		this.selectedBook = selectedBook;
	}
	
	
		
}
