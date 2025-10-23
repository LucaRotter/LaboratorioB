package applicationrob;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import LaboratorioB.common.models.Libreria;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class VisLibrerieController {

   
    @FXML
    private Button search;
    
    @FXML
    private Button edit;

    @FXML
    private Button emptyLbtn;

    @FXML
    private Button extraBtn;

    @FXML 
    private Button modalSendButton;

    @FXML
    private StackPane modalOverlay;

    @FXML
    private ScrollPane scrollLibraries;

    @FXML
    private GridPane librariesContainer; 

    @FXML
    private TextField modalTextField;

    @FXML
    private TextField searchBar;

    @FXML
    private HBox modalContent;
    
    @FXML
    private Label emptyText;
    
    @FXML
    private Label imgFolder;

    
    private boolean editMode = false;

    private Libreria libreria;
    private List<Libreria> listaLibrerie;
    private ObservableList<Libreria> librerie;
    private ObservableList<Libreria> currentLibr;
    private ObservableList<Libreria> filteredLibr;

    private int id_user;

    @FXML
    public void initialize() throws RemoteException {
        id_user = TokenSession.getUserId();
        TokenSession.checkTkSession();

        librerie = FXCollections.observableArrayList();
        currentLibr = FXCollections.observableArrayList();
        filteredLibr = FXCollections.observableArrayList();

        listaLibrerie = clientBR.getInstance().getLibrerie(id_user);
        if (listaLibrerie != null) {
            librerie.setAll(FXCollections.observableArrayList(listaLibrerie));
            currentLibr.setAll(librerie);
        }

    	updateEmptyState();
    	
    	    modalOverlay.setOnMouseClicked(event -> {
    		    if (!modalContent.isHover()) {
    	            modalOverlay.setVisible(false);
    	        }
            });

    	InsertingElements();
    }
    
    //Metodo che permetta l'apertura del Modal
    private void showModal() {
        modalOverlay.setVisible(true);
        modalTextField.clear();
    }

    //Metodo per inviare dati inseriti nel Modal
    @FXML
    void sendModal(ActionEvent event) throws RemoteException {
        TokenSession.checkTkSession();
        String libName = modalTextField.getText().trim();

        if (libName.isEmpty()) {
            return; 
        } 

        libreria = clientBR.getInstance().createLibreria(libName, id_user);
        listaLibrerie = clientBR.getInstance().getLibrerie(id_user);
        librerie.setAll(FXCollections.observableArrayList(listaLibrerie));
        currentLibr.setAll(librerie);
        InsertingElements(currentLibr); 
        modalOverlay.setVisible(false); 
        updateEmptyState();       
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
    void searchLibraries(ActionEvent event) throws RemoteException {
        TokenSession.checkTkSession();
        String textSlib = searchBar.getText().trim().toLowerCase();
        librerie.setAll(clientBR.getInstance().getLibrerie(id_user));
    	
        if (textSlib.isEmpty()) {
            currentLibr.setAll(librerie);
        } else {
                filteredLibr = FXCollections.observableArrayList();
                    for (Libreria lib : librerie) {
                        if (lib.getNomeLibreria().toLowerCase().contains(textSlib)) {
                            filteredLibr.add(lib);
                        }
                    }
                currentLibr.setAll(filteredLibr); 
        } 
        InsertingElements(currentLibr);   
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
    void useEdit(ActionEvent event) throws RemoteException {
        TokenSession.checkTkSession();
        listaLibrerie = clientBR.getInstance().getLibrerie(id_user);
        librerie.setAll(FXCollections.observableArrayList(listaLibrerie));

    	if (!librerie.isEmpty()) {
            editMode = !editMode;
            InsertingElements(currentLibr);
            extraBtn.setVisible(editMode);
    	}  
    }
   
 // Metodo per impostare elementi se non ci sono librerie
    private void updateEmptyState() {
    	boolean hasLibraries = librerie != null && !librerie.isEmpty();

        emptyLbtn.setVisible(!hasLibraries);
        emptyText.setVisible(!hasLibraries);
        
        edit.setVisible(hasLibraries);
        
        if (!hasLibraries) {
            editMode = false;       
            extraBtn.setVisible(false);
        }
    }
    
  //Metodo per mostrare tutte le librerie
    private void InsertingElements() {
        InsertingElements(librerie); 
    }
    
    
    //Metodo che si occupa di mostrare il filtraggio delle librerie
    private void InsertingElements(ObservableList<Libreria> listToShow) {
    librariesContainer.getChildren().clear();

    int columns = 4;
    int row = 0;
    int col = 0;
   

    if (editMode) {
         extraBtn.setPrefSize(120, 120);
        librariesContainer.add(extraBtn, 0, 0);
        GridPane.setMargin(extraBtn, new Insets(20, 20, 20, 20));
        col = 1;
        
    }

    for (Libreria lib : listToShow) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/applicationrob/LibraryEL.fxml"));
            StackPane libPane = loader.load();

            LibraryController controller = loader.getController();
            controller.setData(lib, editMode, () -> {
                librerie.remove(lib);
                currentLibr.remove(lib);
                updateEmptyState();
                InsertingElements(currentLibr);
            });

            libPane.setPrefSize(120, 120);
            GridPane.setMargin(libPane, new Insets(20, 20, 20, 20));

            librariesContainer.add(libPane, col, row);
      
            col++;
                if (col >= columns) {
                   col = 0; 
                   row++;
                   if (editMode && row == 0) col = 1;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}


