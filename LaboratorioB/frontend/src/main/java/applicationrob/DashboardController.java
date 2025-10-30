package applicationrob;

import java.io.IOException; 
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import LaboratorioB.common.models.Libro;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
	private void putBooks() throws RemoteException, IOException {
		 
		int col= 0;
		int row= 1;
		int i;
		
		for(i=1; i<60; i++) { 
			if(col == 5) {
				
				row += 1;
				col = 0;
			}
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("BookEl.fxml"));
			VBox vbox = loader.load();
			
			BookController bookController= loader.getController();
			Libro libr = clientBR.getInstance().getLibro(i);
			bookController.setLabels(libr.getAutore(), libr.getTitolo());
			
			vbox.setOnMouseClicked(e->{

				Model.getIstance().getView().setSelectedBook(libr);
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
