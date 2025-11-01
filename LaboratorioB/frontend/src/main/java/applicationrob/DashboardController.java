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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
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

	@FXML
	private TextField searchBar;

	@FXML 
	private ChoiceBox<Ricerca> choiceBoxOrder;

	private final int LIST_SIZE = 20;

	private IntegerProperty currentIndex = new SimpleIntegerProperty(0);

	private String MODRICERCA = "VISUALIZZA";

	private List<Libro> Bookserver = new ArrayList<>();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		init();
		initSrollButtons();
		
		try {

			//first loading books
			Bookserver.addAll(clientBR.getInstance().lazyLoadingLibri());
			putBooks(currentIndex.get());
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		//listener for scroll buttons that load more books or go to previous books when index change
		currentIndex.addListener((obs, oldIndex, newIndex) -> {
			
			try {

				System.out.println("Index changed from " + oldIndex + " to " + newIndex);
				//lazy loading books when scrolling forward and in view mode
				if(newIndex.intValue() > oldIndex.intValue() && MODRICERCA.equals("VISUALIZZA")){ 
					
					Bookserver.addAll(clientBR.getInstance().lazyLoadingLibri());
				}

				if((newIndex.intValue() + 2) * LIST_SIZE > Bookserver.size() && MODRICERCA.equals("RICERCA")) {

				} else {

					btnLeft.setText(newIndex.intValue() + 1 + "");
					btnCenter.setText(newIndex.intValue() + 2 + "");
					btnRight.setText(newIndex.intValue() + 3 + "");
					
				}

				System.out.println("facciooooo");
				gridBooks.getChildren().clear();
				putBooks(newIndex.intValue());

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

		searchBar.setOnKeyPressed(e->{

			if(e.getCode() == KeyCode.ENTER){

			try {
				OnResearch();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			}
		});

		choiceBoxOrder.getItems().addAll(Ricerca.TITOLO, Ricerca.AUTORE, Ricerca.ANNO);
	}

	public void initSrollButtons(){

		btnLeft.setText(currentIndex.get()  + 1 + "");
		btnCenter.setText(currentIndex.get()+ 2 +"");
		btnRight.setText(currentIndex.get() + 3 + "");

	}
	
	//metodo utilizzato per aggiungere i libri al gridPane e inizializzarne il contenuto
	private void putBooks(int indice) throws RemoteException, IOException {

		int col= 0;
		int row= 1;
		int i;

		
		
		for(i=indice* LIST_SIZE; i <= indice * LIST_SIZE + 19 ; i++) { 
			
			if(i >= Bookserver.size()) {
				break;
			}

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
			
		}
	}

	public void OnResearch() throws IOException {
		
		String Title = searchBar.getText();

		if(searchBar.getText().isEmpty()){

			MODRICERCA = "VISUALIZZA";
			gridBooks.getChildren().clear();
			Bookserver.clear();
			Bookserver.addAll(clientBR.getInstance().lazyLoadingLibri());

			currentIndex.set(0);
			putBooks(0);
			return;
		}

		MODRICERCA = "RICERCA";
		gridBooks.getChildren().clear();
		List<Libro> ricercaLibri = clientBR.getInstance().cercaLibri("",0,Title, Ricerca.TITOLO);

		Bookserver.clear();
		Bookserver.addAll(ricercaLibri);

		currentIndex.set(0);
		
	}

	public void OnForward(){

		if((currentIndex.get() + 1) * LIST_SIZE <=  Bookserver.size()){

			int count= currentIndex.get() ;
			count += 1;

			currentIndex.set(count); 

		}
	}

	public void OnBack(){

		if(currentIndex.get() - 1 >= 0){

			int count= currentIndex.get() ;
			count -= 1;

			currentIndex.set(count);
		}
		
	}



	
}
