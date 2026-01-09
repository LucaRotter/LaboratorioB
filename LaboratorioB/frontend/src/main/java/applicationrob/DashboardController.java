package applicationrob;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import LaboratorioB.common.models.Ricerca;  
import LaboratorioB.common.models.Libro;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import models.Model;
import javafx.util.StringConverter;

/** Controller JavaFX per la dashboard principale dell'applicazione.
 * Gestisce la visualizzazione e la navigazione tra i libri, inclusi
 * la ricerca, il filtraggio per categoria e la paginazione.
 * 
 * @author Grassi, Alessandro, 757784, VA
 * @author Kastratovic, Aleksandar, 752468, VA
 * @author Rotter, Luca Giorgio, 757780, VA
 * @author Davide, Bilora, 757011, VA
 * @version 1.0
 */
public class DashboardController implements Initializable{

	@FXML
	private ScrollPane ScrollBooks;

	@FXML
	private ScrollPane ScrollCategories;

	@FXML 
	private GridPane gridBooks;

	@FXML 
	private Button btnSearch;

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

	@FXML HBox ToggleContainer;

	@FXML 
	private ChoiceBox<Ricerca> choiceBoxOrder;

	@FXML
	private TextField Yearfield;

	@FXML
	private Button btnCatLeft;

	@FXML
	private Button btnCatRight;

	@FXML
	private Pane MouseBlocker;

	private final int LIST_SIZE = 20;

	private final int totalPages = 4; 

	private IntegerProperty currentIndex = new SimpleIntegerProperty(0);

	private List<Libro> Bookserver = new ArrayList<>();

	private LoadMode currentMode = LoadMode.LAZY;

	private String selectedCategory = null;

	ToggleGroup group = new ToggleGroup();

	final Toggle[] lastSelected = { null };

	private int currentPage = 0;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		init();
		initNavButtons();
		
		try {

			putBooks(currentIndex.get());
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	
		currentIndex.addListener((obs, oldIndex, newIndex) -> {
			
			try {

				ScrollBooks.setVvalue(0.0);
				
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

		btnLeft.setOnAction(e->{

			onNavButton(e);

		});

		btnCenter.setOnAction(e->{

			onNavButton(e);

		});

		btnRight.setOnAction(e->{

			onNavButton(e);

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

		btnSearch.setOnAction(e->{
			try {
				OnResearch();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		});

		btnCatLeft.setOnAction(e -> {
			goToPage(currentPage - 1);
		});

		btnCatRight.setOnAction(e -> {
			goToPage(currentPage + 1);
		});


	// ENTER frecce
    btnCatLeft.setOnMouseEntered(e -> MouseBlocker.setMouseTransparent(false));
    btnCatRight.setOnMouseEntered(e -> MouseBlocker.setMouseTransparent(false));

    // EXIT frecce
    btnCatLeft.setOnMouseExited(e -> MouseBlocker.setMouseTransparent(true));
    btnCatRight.setOnMouseExited(e -> MouseBlocker.setMouseTransparent(true));

	
		choiceBoxOrder.getItems().addAll(Ricerca.TITOLO, Ricerca.AUTORE, Ricerca.ANNO);

		choiceBoxOrder.setConverter(new StringConverter<Ricerca>() {
			@Override
			public String toString(Ricerca object) {
				if (object == null) return "";
				return switch(object) {
					case TITOLO -> "TITLE";
					case AUTORE -> "AUTHOR";
					case ANNO -> "AUTHOR & YEAR";
				};
			}

			@Override
			public Ricerca fromString(String string) {
				return null;
			}
		});
		
		choiceBoxOrder.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {

        if (newVal == null) return;

        if (newVal.equals(Ricerca.ANNO)) {
            Yearfield.setDisable(false);
        } else {
            Yearfield.setDisable(true);
			Yearfield.clear();
        }
    });

	 for (javafx.scene.Node node : ToggleContainer.getChildren()) {
    if (node instanceof ToggleButton btn) {
        btn.setToggleGroup(group);

        // intercetta sempre il click, anche sullo stesso bottone
        btn.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            if (btn.equals(lastSelected[0])) {
                // deseleziona
                group.selectToggle(null);
                lastSelected[0] = null;

                // reset books
                try {
                    Bookserver.clear();
                    gridBooks.getChildren().clear();
                    currentIndex.set(0);
					selectedCategory = null;
                    initNavButtons();
                    putBooks(currentIndex.get());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                e.consume(); 
            }
        });
    }
}

// listener sul gruppo per nuove selezioni
group.selectedToggleProperty().addListener((obs, oldT, newT) -> {
    if (newT != null) {
        lastSelected[0] = (Toggle) newT;
        ToggleButton selected = (ToggleButton) newT;
        selectedCategory = selected.getText();

        // aggiorna libri filtrati
        try {
            Bookserver.clear();
            gridBooks.getChildren().clear();
            currentIndex.set(0);
            initNavButtons();
            putBooks(currentIndex.get());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
});
		choiceBoxOrder.setValue(Ricerca.TITOLO);
	}

	public void initNavButtons(){

		int index = currentIndex.get();
		boolean update = true;
		boolean controlIndex = (index + 1) * 20 > Bookserver.size();

		btnLeft.getStyleClass().removeAll("SelectedIndex");
		btnCenter.getStyleClass().removeAll("SelectedIndex");
		btnRight.getStyleClass().removeAll("SelectedIndex");

		
		
		if (controlIndex) {

		btnRight.getStyleClass().add("SelectedIndex");
		update=false;

    	}

		if(index == 0 || index == 1 ){

			if(index == 0){
			
			btnRight.getStyleClass().removeAll("SelectedIndex");
			btnLeft.getStyleClass().add("SelectedIndex");

			} else {

			btnRight.getStyleClass().removeAll("SelectedIndex");
			btnCenter.getStyleClass().add("SelectedIndex");
			
			}

			btnLeft.setText( 1 + "");
			btnCenter.setText(2 +"");
			btnRight.setText( 3 + "");

		} else if(update){

			btnCenter.getStyleClass().add("SelectedIndex");

			btnLeft.setText(index + "");
			btnCenter.setText(index + 1 +"");
			btnRight.setText(index + 2 + "");
			
		}
    }
		

	public void updateindexButton(){

		int index = currentIndex.get();

		if ((index + 1) * 20 > Bookserver.size()) {
		
		btnCenter.getStyleClass().removeAll("SelectedIndex");
		btnRight.getStyleClass().add("SelectedIndex");
        return;

		} else if(index == 0){

		btnCenter.getStyleClass().removeAll("SelectedIndex");
		btnLeft.getStyleClass().add("SelectedIndex");

		return;

		}else{

		btnRight.getStyleClass().removeAll("SelectedIndex");
		btnLeft.getStyleClass().removeAll("SelectedIndex");
		btnCenter.getStyleClass().add("SelectedIndex");

		btnLeft.setText(index  + "");
		btnCenter.setText(index + 1+"");
		btnRight.setText(index + 2 + "");
		}
	}

	
	//metodo utilizzato per aggiungere i libri al gridPane e inizializzarne il contenuto
	private void putBooks(int index) throws RemoteException, IOException {

		
		if(currentMode == LoadMode.LAZY){	

				if(Bookserver.isEmpty()){

					for(int i = 0; i<3; i++) {
						Bookserver.addAll(clientBR.getInstance().lazyLoadingLibri(selectedCategory));
					}
				}
				else if((index + 1) * LIST_SIZE > Bookserver.size()) {
            		Bookserver.addAll(clientBR.getInstance().lazyLoadingLibri(selectedCategory));
        		}
			
		}
		
		if(Bookserver.isEmpty()){
			
			setEmptyResearch("BOOK NOT FOUND");

		}else{

		ScrollBooks.setContent(gridBooks);

		int col= 0;
		int row= 1;
		int i;
		
		for(i=index* LIST_SIZE; i <= index * LIST_SIZE + 19 ; i++) { 
			
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

			author = writeText;
			year = Integer.parseInt(Yearfield.getText().trim());
		}

		if(searchBar.getText().isEmpty()){

			gridBooks.getChildren().clear();
			Bookserver.clear();
			currentMode = LoadMode.LAZY;
			currentIndex.set(0);
			initNavButtons();
			putBooks(0);
			return;
		}

		
		gridBooks.getChildren().clear();
		currentMode = LoadMode.FULL;
		List<Libro> ricercaLibri = clientBR.getInstance().cercaLibri(author,year,title, mod);

		Bookserver.clear();
		Bookserver.addAll(ricercaLibri);

		currentIndex.set(0);
		initNavButtons();
		putBooks(0);
		
	}

	public void OnForward(){

		if((currentIndex.get() + 1) * LIST_SIZE <=  Bookserver.size()){

			int count= currentIndex.get() ;
			count += 1;

			currentIndex.set(count); 
			initNavButtons();
		}
	}

	public void OnBack(){

		if(currentIndex.get() - 1 >= 0){

			int count= currentIndex.get() ;
			count -= 1;

			currentIndex.set(count);
			initNavButtons();
		}
		
	}

	public void onNavButton(ActionEvent event){
	
		Button btn = (Button) event.getSource();
    	int count = Integer.parseInt(btn.getText());


		if((int) Math.ceil((double) Bookserver.size() / LIST_SIZE) == 1){
			return;
		}

		currentIndex.set(count-1);
		updateindexButton();

	}

	public void setEmptyResearch(String text){

		Label nessunLibro = new Label(text);
		nessunLibro.setStyle("-fx-font-size: 32px; -fx-text-fill: gray;");

		VBox container = new VBox(nessunLibro);
		container.setAlignment(Pos.CENTER);
		container.prefWidthProperty().bind(ScrollBooks.widthProperty());
		container.prefHeightProperty().bind(ScrollBooks.heightProperty());

		ScrollBooks.setContent(container);
	}

	private void goToPage(int page) {

    if(page < 0) page = 0;
    if(page > totalPages - 1) page = totalPages - 1;

    currentPage = page;

    double target = (double) currentPage / (totalPages - 1);

    Timeline tl = new Timeline(
            new KeyFrame(Duration.ZERO,
                    new KeyValue(ScrollCategories.hvalueProperty(), ScrollCategories.getHvalue())
            ),
            new KeyFrame(Duration.millis(260),
                    new KeyValue(ScrollCategories.hvalueProperty(), target, Interpolator.EASE_BOTH)
            )
    );
    tl.play();

	btnCatLeft.setVisible(currentPage > 0);

    btnCatRight.setVisible(currentPage < totalPages - 1);
}
	
}


