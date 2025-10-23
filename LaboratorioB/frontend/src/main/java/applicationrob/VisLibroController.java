package applicationrob;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import LaboratorioB.common.models.Consiglio;
import LaboratorioB.common.models.Libro;
import LaboratorioB.common.models.Valutazione;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.Model;

public class VisLibroController implements Initializable{

	@FXML
	private ScrollPane root;
	
	@FXML
	private HBox recListBook;
	
	@FXML
	private VBox reviewContainer;
	
	@FXML
	private Button btnRelated;
	
	@FXML
	private Button btnComments;
	
	@FXML
	private Button btnSave;
	
	@FXML 
	private Pane modalOverlay;

	@FXML
	private Label LBTitle;

	@FXML
	private Label LBAuthor;

	@FXML
	private Label LBHouse;

	@FXML
	private Label LBGenre;

	@FXML
	private Label LBYear;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		Libro book = Model.getIstance().getView().getSelectedBook();
		btnComments.setOnAction(e->{
			onComments();
		});	

		if (book != null) {
    		setLibro(book);
		}		

		Model.getIstance().getView().selectedBookProperty().addListener((obs, oldLibr, newLibr) -> {

		if(newLibr != null){
        	setLibro(newLibr);
		}

		});

	
	}

	//function to open the review modal
	public void openModal() throws IOException {
		
		modalOverlay.setVisible(true);
		root.setDisable(true);
		modalOverlay.toFront();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/applicationrob/TableReview.fxml"));
		VBox modalContent = loader.load();
		
		modalOverlay.getChildren().add(modalContent);
		 	
	}

	//function to set the book details 
	public void setLibro(Libro selectedBook) {

		LBTitle.setText(selectedBook.getTitolo());	
		LBAuthor.setText(selectedBook.getAutore());
		LBHouse.setText(selectedBook.getEditore());
		LBGenre.setText(selectedBook.getGenere());
		LBYear.setText(String.valueOf(selectedBook.getAnno()));

		int i ;
		List<Valutazione> review = new LinkedList<>();

		try {
			review = clientBR.getInstance().getValutazione(2);
		} catch (RemoteException e) {

			e.printStackTrace();
		}

		//review initialization

		reviewContainer.getChildren().clear();

		for(i= 0;i<review.size();i++){
		FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/applicationrob/Review.fxml"));
		
		HBox hBox = null;

		try {
			hBox = loader1.load();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		ReviewController reviewc =  loader1.getController();
		reviewc.setVislibroController(this);

		reviewContainer.getChildren().add(hBox);

		//recommended books initialization

		List<Libro> recommendList = new LinkedList<>();

		try {
			recommendList = clientBR.getInstance().getConsiglio(2);
		} catch (RemoteException e) {

			e.printStackTrace();
		}

		recListBook.getChildren().clear();  

		for(i=0; i<recommendList.size(); i++) {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/applicationrob/BookEl.fxml"));
		BookController book = loader.getController();
		book.setLabels(recommendList.get(i).getAutore(), recommendList.get(i).getTitolo());
		VBox vbox = null;
		
		try {
			vbox = loader.load();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		recListBook.getChildren().add(vbox);
		}
	  }

	}

	//function to go to the add reviews section
	public void onComments(){
		Model.getIstance().getView().getSideBarSelectionItem().set("AddReview");
	}
}  
