package applicationrob;

import java.io.IOException; 
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.ResourceBundle;
import LaboratorioB.common.models.Ricerca;
import LaboratorioB.common.models.Libro;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
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
		initNavButtons();
		
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

				//da gestire diversamente magari caricare in base alla dimensione della lista 
				if(newIndex.intValue() > oldIndex.intValue() && MODRICERCA.equals("VISUALIZZA")){ 
					
					Bookserver.addAll(clientBR.getInstance().lazyLoadingLibri());
				}	

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
			initNavButtons();

		});

		btnBack.setOnAction(e->{

			OnBack();
			initNavButtons();

		});

		btnLeft.setOnAction(e->{

			onNavButton(e);
			updateindexButton();

		});

		btnCenter.setOnAction(e->{

			onNavButton(e);
			updateindexButton();

		});

		btnRight.setOnAction(e->{

			onNavButton(e);
			updateindexButton();

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

	public void initNavButtons(){

		int index = currentIndex.get();
		System.out.println(index + Bookserver.size());
		System.out.println(index == 0);


		if ((index + 1) * 20 > Bookserver.size() && MODRICERCA.equals("RICERCA")) {

		
		btnCenter.getStyleClass().removeAll("SelectedIndex");
		btnRight.getStyleClass().add("SelectedIndex");

        return;
    	}

		if(index == 0 || index == 1){

			if(index == 0){
			btnCenter.getStyleClass().removeAll("SelectedIndex");
			btnLeft.getStyleClass().add("SelectedIndex");

			} else{

			btnLeft.getStyleClass().removeAll("SelectedIndex");
			btnCenter.getStyleClass().add("SelectedIndex");

			}

			btnLeft.setText( 1 + "");
			btnCenter.setText(2 +"");
			btnRight.setText( 3 + "");

		} else{

			btnRight.getStyleClass().removeAll("SelectedIndex");
			btnCenter.getStyleClass().add("SelectedIndex");

			btnLeft.setText(index + "");
			btnCenter.setText(index + 1 +"");
			btnRight.setText(index + 2 + "");
			
		}
    }
		

	public void updateindexButton(){

		int index = currentIndex.get();
		System.out.println(index + Bookserver.size());

		if ((index + 1) * 20 > Bookserver.size()  ) {
		
		btnCenter.getStyleClass().removeAll("SelectedIndex");
		btnRight.getStyleClass().add("SelectedIndex");
        return;

		} else if(index== 0){

		btnCenter.getStyleClass().removeAll("SelectedIndex");
		btnLeft.getStyleClass().add("SelectedIndex");

		return;
		}

		btnLeft.getStyleClass().removeAll("SelectedIndex");
		btnRight.getStyleClass().removeAll("SelectedIndex");
		btnCenter.getStyleClass().add("SelectedIndex");

		btnLeft.setText(index  + "");
		btnCenter.setText(index + 1+"");
		btnRight.setText(index + 2 + "");

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
		
		String writeText = searchBar.getText();
		Ricerca mod = choiceBoxOrder.getValue();

		String title = "";
		String author = "";
		int year = 0;

		if(mod.equals(Ricerca.TITOLO)){

			title = writeText;

		} else if(mod.equals(Ricerca.AUTORE)){

			author = writeText;

		} else if(mod.equals(Ricerca.ANNO)){

			year = Integer.parseInt(writeText.trim());

		}

		if(searchBar.getText().isEmpty()){

			MODRICERCA = "VISUALIZZA";
			gridBooks.getChildren().clear();
			Bookserver.clear();
			Bookserver.addAll(clientBR.getInstance().lazyLoadingLibri());
			currentIndex.set(0);
			initNavButtons();
			putBooks(0);
			return;
		}

		MODRICERCA = "RICERCA";
		gridBooks.getChildren().clear();
		List<Libro> ricercaLibri = clientBR.getInstance().cercaLibri(author,year,title, mod);

		Bookserver.clear();
		Bookserver.addAll(ricercaLibri);

		currentIndex.set(0);
		putBooks(0);
		initNavButtons();
		
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

	public void onNavButton(ActionEvent event){
	
		Button btn = (Button) event.getSource();
    	int count = Integer.parseInt(btn.getText());

		currentIndex.set(count-1);

	}
	
}
