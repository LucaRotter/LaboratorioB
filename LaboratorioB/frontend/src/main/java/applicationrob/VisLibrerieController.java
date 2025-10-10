package applicationrob;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class VisLibrerieController {

    @FXML
    private Button edit;

    @FXML
    private ScrollPane scrollLibraries;

    @FXML
    private Button search;
    
    @FXML
    private Button emptyLbtn;
    
    @FXML
    private Label emptyText;
    
    @FXML
    private Button extraBtn;

    @FXML
    private TextField searchBar;
    
    @FXML
    private GridPane librariesContainer; 
    
    @FXML
    private StackPane modalOverlay;

    @FXML
    private TextField modalTextField;

    @FXML
    private Button modalSendButton;
    
    @FXML
    private HBox modalContent;
    
    @FXML
    private Label imgFolder;

    // Libreria libreria;
    // List<Libreria> librerie;
    
  
    
    @FXML
    private boolean editMode = false;
    
    public void initialize() {
    	
    	updateEmptyState();
    	
    	 modalOverlay.setOnMouseClicked(event -> {
    		 if (!modalContent.isHover()) {
    	            modalOverlay.setVisible(false);
    	        }
        });
    	 
    	rearrangeGrid();
    }
    
  //Metodo che permetta l'apertura del Modal
    private void showModal() {
        modalOverlay.setVisible(true);
        modalTextField.clear();
    }
    
 
    
  //Metodo per inviare dati inseriti nel Modal
    @FXML
    void sendModal(ActionEvent event) {
     /*   String libName = modalTextField.getText().trim();
        if (!libName.isEmpty()) {
        Libreria lib = clientBR.BR.getLibreria(libName)   
        if (lib == null) {  
        lib = clientBR.BR.createLibreria(libName, "id utente");
         }
            rearrangeGrid();        
        } else {
        }
        modalOverlay.setVisible(false); 
        */
    }
    
    @FXML
    void addLibraryEmpty(ActionEvent event) {
    	showModal();
    }
    
    @FXML
    void addLibrary(ActionEvent event) {
    	showModal();
    }
    
    
 
// Metodo che cerca le librerie in base alla scritta nel TextField
    @FXML
   void searchLibraries(ActionEvent event) {
    /* 	String textSlib = searchBar.getText().trim().toLowerCase();
    	
        if (textL.isEmpty()) {
            currentView.setAll(librerie);
        } else {
            ObservableList<Libreria> filtered = FXCollections.observableArrayList();
            for (Libreria lib : librerie) {
                if (lib.getNome().toLowerCase().contains(textSlib)) {
                    filtered.add(lib);
                }
            }
            currentView.setAll(filtered); 
        } 
        rearrangeGrid(currentView);   */
    }
    
 // Metodo di controllo per searchLibraries
    @FXML
    void writeText(ActionEvent event) {
    	 String textSlib = searchBar.getText().trim().toLowerCase();
    	    
    	    if (!textSlib.matches("[a-zA-Z0-9 ]*")) {
    	        return;
    	    }
    	    
    	}

 // Metodo per azionare la modifica/aggiunta librerie 
    @FXML
    void useEdit(ActionEvent event) {	
    	 /* if (!librerie.isEmpty()) {
    	
    	editMode = !editMode;
    	
    	rearrangeGrid("listafiltrata");
    	 extraBtn.setVisible(editMode);
    	 }  */
    }
   
 // Metodo per impostare elementi se non ci sono librerie
    private void updateEmptyState() {
    	/*   boolean hasLibraries = librerie != null && !librerie.isEmpty();

        emptyLbtn.setVisible(!hasLibraries);
        emptyText.setVisible(!hasLibraries);
        
        edit.setVisible(hasLibraries);
        
        if (!hasLibraries) {
            editMode = false;       
            extraBtn.setVisible(false);
        }*/
    }
    
    //public void addLibrary(Libro libro) {
      /* librerie.add(libro);
       currentView.setAll(librerie);
       updateEmptyState(); 
       rearrangeGrid(currentView); */
  //  } 
    
  //Metodo per mostrare tutte le librerie
    private void rearrangeGrid() {
        //rearrangeGrid(librerie); 
    }
    
    
    //Metodo che si occupa di mostrare il filtraggio delle librerie
    private void rearrangeGrid(ObservableList<String> listToShow) {
        librariesContainer.getChildren().clear();

        int columns = 5; 
        int row = 0;
        int col = 0;

        if (editMode) {
        	librariesContainer.add(extraBtn, 0, 0); 
        	GridPane.setMargin(extraBtn, new Insets(15, 5, 5, 15));
        	col = 1; 
        }

        for (String libName : listToShow) {
            StackPane libPane = new StackPane();
            libPane.getStyleClass().add("library-tile");
            GridPane.setMargin(libPane, new Insets(15, 5, 5, 15));

            Label label = new Label(libName);
            libPane.getChildren().add(label);

            Button removeBtn = new Button("-");
            removeBtn.getStyleClass().add("remove-btn");
            removeBtn.setVisible(editMode);
            removeBtn.setOnAction(ev -> {
           //    libraries.remove(libName);
                updateEmptyState();
                rearrangeGrid();
            });
            StackPane.setAlignment(removeBtn, Pos.TOP_RIGHT);
            libPane.getChildren().add(removeBtn);

            librariesContainer.add(libPane, col, row);

            col++;
            
            if (col >= columns) {
                col = 0;
                row++;
                
                if (editMode && row == 0) col = 1;
            }
        }
        
    }   

}


