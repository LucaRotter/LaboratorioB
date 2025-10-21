package applicationrob;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import LaboratorioB.common.models.Libro;
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

		if (book != null) {
    		setLibro(book);
		}		

		Model.getIstance().getView().selectedBookProperty().addListener((obs, oldLibr, newLibr) -> {

		if(newLibr != null){
        	setLibro(newLibr);
		}

		});

		int i ;
		for(i=0; i<60; i++) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/applicationrob/BookEl.fxml"));
		
		FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/applicationrob/Review.fxml"));
		
		VBox vbox = null;
		HBox hbox = null;
		try {
			vbox = loader.load();
			hbox = loader1.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ReviewController review =  loader1.getController();
		review.setVislibroController(this);
		
		
		recListBook.getChildren().add(vbox);
		reviewContainer.getChildren().add(hbox);
		
		}
	}
	
	public void openModal() throws IOException {
		
		modalOverlay.setVisible(true);
		root.setDisable(true);
		modalOverlay.toFront();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/applicationrob/TableReview.fxml"));
		VBox modalContent = loader.load();
		
		modalOverlay.getChildren().add(modalContent);
		 	
	}

	public void setLibro(Libro selectedBook) {

		LBTitle.setText(selectedBook.getTitolo());	
		LBAuthor.setText(selectedBook.getAutore());
		LBHouse.setText(selectedBook.getEditore());
		LBGenre.setText(selectedBook.getGenere());
		LBYear.setText(String.valueOf(selectedBook.getAnno()));

	}
}  
