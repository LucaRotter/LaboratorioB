package applicationrob;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import models.Model;

public class DashboardController implements Initializable{

	@FXML 
	private GridPane gridBooks;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//impostare btn e enter per searchbar
		
		//inizializzazione libri
		try {
			putBooks();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	//metodo utilizzato per aggiungere i libri al gridPane e inizializzarne il contenuto
	private void putBooks() throws IOException {
		
		int col= 0;
		int row= 1;
		int i;
		
		for(i=0; i<60; i++) {
			
			if(col == 5) {
				
				row += 1;
				col = 0;
			}
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("BookEl.fxml"));
			VBox vbox = loader.load();
			
			BookController Bookcontroller= loader.getController();
			//Libro libro = metodo server
			
			//Bookcontroller.setLabels(Libro libro);
			
			vbox.setOnMouseClicked(e->{

				Model.getIstance().getView().setSelectedBook("ciaoooooo");
				Model.getIstance().getView().getSideBarSelectionItem().set("VisLibro");
				
			});
			
			vbox.setPrefSize(120, 180);
			gridBooks.add(vbox, col++, row);
			
			gridBooks.setPrefSize(i, i);
		}
	}
	
	public void OnResearch() throws IOException {
	 
	  gridBooks.getChildren().clear();
	  putBooks();
	  
	}
}
