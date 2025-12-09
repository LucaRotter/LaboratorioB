package applicationrob;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import LaboratorioB.common.models.Libreria;
import LaboratorioB.common.models.Libro;
import LaboratorioB.common.models.Valutazione;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.Model;

public class VisLibroController implements Initializable{

	@FXML
    private Button btnCancel;

    @FXML
    private Label LBAuthor;

	@FXML
	private ScrollPane ScrollRec;

    @FXML
    private Label LBGenre;

    @FXML
    private Label LBHouse;

    @FXML
    private Label LBTitle;

    @FXML
    private Label LBYear;

    @FXML
    private Pane ModalLibraries;

    @FXML
    private ScrollPane ScrollLibraries;

    @FXML
    private VBox VBoxLibraries;

    @FXML
    private Button btnComments;

    @FXML
    private Button btnRelated;

    @FXML
    private Button btnSave;

    @FXML
    private StackPane modalOverlay;

    @FXML
    private HBox recListBook;

    @FXML
    private VBox reviewContainer;

    @FXML
    private ScrollPane root1;

	@FXML
	private Button btnConfirm;

	
	@FXML
	private Label lbUserCounter;
	@FXML
	private Label lbAverage;


	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		Libro book = Model.getIstance().getView().getSelectedBook();
		
		init();

		if (book != null) {
    		setLibro(book);
		}		

		Model.getIstance().getView().selectedBookProperty().addListener((obs, oldLibr, newLibr) -> {

		if(newLibr != null){

			root1.setVvalue(0.0);

        	setLibro(newLibr);
			System.out.println(newLibr.getTitolo());
		}

		});
	
	}
	
	public void init(){

		btnComments.setOnAction(e->{
			onComments();
		});	

		btnRelated.setOnAction(e->{
			onReccomended();
		});

		btnSave.setOnAction(e->{
			OpenModalLibraries();

		});

		btnConfirm.setOnAction(e->{
			onConfirm();
		});

		btnCancel.setOnAction(e->{
			closeModal();
		});
	}

	//function to open the review modal
	public void openModal(Valutazione review) throws IOException {
		
		modalOverlay.setVisible(true);
		root1.setDisable(true);
		modalOverlay.toFront();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/applicationrob/TableReview.fxml"));
		VBox modalContent = loader.load();
		TableReviewController tableReviewController = loader.getController();
		tableReviewController.setReview(review);
		
		modalOverlay.getChildren().add(modalContent);

		 modalOverlay.setOnMouseClicked(event -> {
        if (event.getTarget() == modalOverlay) {
            closeModalReview();
        }
    	});
		 	
	}

	public void closeModalReview(){
		modalOverlay.getChildren().clear();
		modalOverlay.setVisible(false);
		root1.setDisable(false);
	}

	//function to set the book details 
	public void setLibro(Libro selectedBook) {

		LBTitle.setText(selectedBook.getTitolo());	
		LBAuthor.setText(selectedBook.getAutore());
		LBHouse.setText(selectedBook.getEditore());
		LBGenre.setText(selectedBook.getGenere());
		LBYear.setText(String.valueOf(selectedBook.getAnno()));

		
	initRecoemmendedList(selectedBook);

	initReviewList(selectedBook);

		
	}

	public void initReviewList(Libro selectedBook){
		int i ;
		List<Valutazione> review = new LinkedList<>();

		try {
			review = clientBR.getInstance().getValutazione(selectedBook.getId());
		} catch (RemoteException e) {

			e.printStackTrace();
		}

		//review initialization

		reviewContainer.getChildren().clear();

		double avarege = 0;

		for(i= 0;i<review.size();i++){
		FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/applicationrob/Review.fxml"));
		
		HBox hBox = null;

		try {
			hBox = loader1.load();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		ReviewController reviewc =  loader1.getController();
		try {
			reviewc.setReview(review.get(i));
		} catch (RemoteException e) {
			
			e.printStackTrace();
		}
		reviewc.setVislibroController(this);

		avarege += review.get(i).getVotoMedio(); 

		reviewContainer.getChildren().add(hBox);
	}

	
	if( review.size() == 0){

		lbAverage.setText("0.0");
		Label nessunLibro = new Label("NO BOOK REVIEW");
		nessunLibro.setStyle("-fx-font-size: 32px; -fx-text-fill: white;");

		
			reviewContainer.setAlignment(Pos.CENTER);
			reviewContainer.prefHeightProperty().set(200.0);
				
			reviewContainer.getChildren().add(nessunLibro);
				
	} else{

	lbAverage.setText(String.format("%.2f", avarege));	
	reviewContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

	}	
	lbUserCounter.setText(String.valueOf(review.size())); 
	}

	public void initRecoemmendedList(Libro selectedBook){

		ScrollRec.setContent(recListBook);
		List<Libro> recommendList = new LinkedList<>();
		System.out.println("inizializzo consigli");

		try {
			recommendList = clientBR.getInstance().getConsiglio(selectedBook.getId());
		} catch (RemoteException e) {

			e.printStackTrace();
		}

		recListBook.getChildren().clear();  
		System.out.println(recommendList.size());

		for(int i=0; i<recommendList.size(); i++) {
		
		VBox vbox = null;

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/applicationrob/BookEl.fxml"));

		try {
			vbox = loader.load();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		BookController book = loader.getController();
		book.setLabels(recommendList.get(i).getAutore(), recommendList.get(i).getTitolo());
		Libro libr = recommendList.get(i);

		vbox.setOnMouseClicked(e->{

				Model.getIstance().getView().setSelectedBook(libr);
				Model.getIstance().getView().getSideBarSelectionItem().set("VisLibro");
				
			});
		
		recListBook.getChildren().add(vbox);
		
		}

		if(recommendList.size() == 0){

				Label nessunLibro = new Label("NO BOOK RECCOMMENDED");
				nessunLibro.setStyle("-fx-font-size: 32px; -fx-text-fill: gray;");

				VBox container = new VBox(nessunLibro);
				container.setAlignment(Pos.CENTER);
				container.prefWidthProperty().bind(ScrollRec.widthProperty());
				container.prefHeightProperty().set(200.0);
				
				ScrollRec.setContent(container);

		}
	}


	//function to go to the add reviews section
	public void onComments(){

		if(!TokenSession.checkTkSession()){
		views.ViewAlert.showAlert("error", "Library error", "Server error, try again.", btnComments, "error");
		}else{
		Model.getIstance().getView().getSideBarSelectionItem().set("AddReview");
	}
}

	public void onReccomended(){
		if(!TokenSession.checkTkSession()){
		views.ViewAlert.showAlert("error", "Library error", "Server error, try again.", btnComments, "error");
		}else{
		Model.getIstance().getView().getSideBarSelectionItem().set("AddReccomended");
	}	
}

	//function to open/close the modal to add the book to libraries
	public void OpenModalLibraries() {
	
	if(!TokenSession.checkTkSession()){
		views.ViewAlert.showAlert("error", "Library error", "Server error, try again.", btnComments, "error");
		return;
		}

		if(ModalLibraries.isVisible()){
			ModalLibraries.setVisible(false);
			ModalLibraries.setDisable(true);
			return;
		}

		ModalLibraries.setVisible(true);
		ModalLibraries.setDisable(false);
		ModalLibraries.toFront();
	
		//initialization of the libraries list
		VBoxLibraries.getChildren().clear();
		List<Libreria> libraries = new LinkedList<>();

		try {
			libraries = clientBR.getInstance().getLibrerie(TokenSession.getUserId());
		} catch (RemoteException e) {
			
			e.printStackTrace();
		}

		for(int i= 0; i<libraries.size(); i++){

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/applicationrob/LibraryItem.fxml"));
			HBox hbox = null;

			try {
				hbox = loader.load();

			} catch (IOException e) {
				
				e.printStackTrace();
			}

			boolean found = libraries.get(i).getLibreria().stream().anyMatch(libro -> 
			libro.getId() == Model.getIstance().getView().getSelectedBook().getId());

			LibraryItemController libraryItemController = loader.getController();
			libraryItemController.initLibrary(libraries.get(i).getNomeLibreria(), libraries.get(i).getIdLibreria(),found);

			//setting user data to retrieve the controller later
			hbox.setUserData(libraryItemController);

			VBoxLibraries.getChildren().add(hbox);
		}

	}

	public void onConfirm(){

		//adding the book to the selected libraries
		VBoxLibraries.getChildren().size();
		
		for(int i=0; i<VBoxLibraries.getChildren().size(); i++){

			//retrieving the controller
			HBox hbox = (HBox) VBoxLibraries.getChildren().get(i);
			LibraryItemController libraryItemController = (LibraryItemController)hbox.getUserData();
			

			if(libraryItemController.hasStateChanged()){
				
				if(libraryItemController.isSelected()) {
					
					try {
						clientBR.getInstance().addLibroLibreria(
							Model.getIstance().getView().getSelectedBook().getId(),
							libraryItemController.getId_library()
						);
					} catch (RemoteException e) {
						
						e.printStackTrace();
					}
				} else {
					
					try {
						clientBR.getInstance().removeLibroLibreria(
							Model.getIstance().getView().getSelectedBook().getId(),
							libraryItemController.getId_library()
						);
					} catch (RemoteException e) {
						
						e.printStackTrace();
					}
				}

			hbox.setUserData(null);
			closeModal();
		} 
	}  

	}

	public void closeModal() {
		ModalLibraries.setVisible(false);
		ModalLibraries.setDisable(true);
	}
}