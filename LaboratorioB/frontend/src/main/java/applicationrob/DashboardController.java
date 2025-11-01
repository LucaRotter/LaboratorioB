package applicationrob;

import java.io.IOException; 
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import LaboratorioB.common.models.Ricerca;
import LaboratorioB.common.models.Libro;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import models.Model;


public class DashboardController implements Initializable{

	@FXML 
	private GridPane gridBooks;

	@FXML
	private Button btnForward;

	@FXML
	private Button btnLeft;

	@FXML
	private Button btnCenter;

	@FXML
	private Button btnRight;

	@FXML
	private Button btnBack;

	private IntegerProperty currentIndex = new SimpleIntegerProperty(1);

	private List<Libro> Bookserver = new ArrayList<>();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		init();
		
		//inizializzazione libri
		try {
			putBooks(currentIndex.get());
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		btnLeft.setText(currentIndex.get()  + "");
		btnCenter.setText(currentIndex.get()+ 1 +"");
		btnRight.setText(currentIndex.get() + 2 + "");

		currentIndex.addListener((obs, oldIndex, newIndex) -> {
			
			try {

				gridBooks.getChildren().clear();
				putBooks(newIndex.intValue());

				btnLeft.setText(newIndex.intValue() - 1 + "");
				btnCenter.setText(newIndex.intValue() + "");
				btnRight.setText(newIndex.intValue() + 1 + "");

			} catch ( IOException e) {
				
				e.printStackTrace();
			}
		});
		
	}

	public void init(){
		btnForward.setOnAction(e->{

			OnForward();
		});

		btnBack.setOnAction(e->{

			OnBack();
		});
	}
	
	//metodo utilizzato per aggiungere i libri al gridPane e inizializzarne il contenuto
	private void putBooks(int indice) throws RemoteException, IOException {

		Bookserver.addAll(clientBR.getInstance().lazyLoadingLibri());

		int col= 0;
		int row= 1;
		int i;
		
		for(i=indice; i < indice + 20 ; i++) { 

			if(col == 5) {
				
				row += 1;
				col = 0;

			}
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("BookEl.fxml"));
			VBox vbox = loader.load();
			
			BookController bookController= loader.getController();
			Libro libr = Bookserver.get(i);
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
		
	}

	public void OnForward(){

		System.out.println(currentIndex.get() + 3);
		System.out.println( Bookserver.size());

		if(currentIndex.get() + 3 <=  Bookserver.size()){

			System.out.println(currentIndex.get());

			int count= currentIndex.get() ;
			count += 3;

			currentIndex.set(count); 
			;
		}
	}

	public void OnBack(){

		if(currentIndex.get() - 3 >= 1){

			int count= currentIndex.get() ;
			count -= 3;

			currentIndex.set(count); ;
			;
		}
		
	}



	
}
