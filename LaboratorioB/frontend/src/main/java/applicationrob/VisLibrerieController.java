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
import java.rmi.RemoteException;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import LaboratorioB.common.models.*;
import java.util.*;

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
    private Label emptyText;
    
    @FXML
    private HBox modalContent;
    
    @FXML
    private Label imgFolder;

    @FXML
    private boolean editMode = false;

    private Libreria libreria;
    private List<Libreria> listaLibrerie;
    private ObservableList<Libreria> librerie = FXCollections.observableArrayList();

    private int id_user;

    private ObservableList<Libreria> currentLibr = FXCollections.observableArrayList();
    private ObservableList<Libreria> filterLibr = FXCollections.observableArrayList();
    
  
    
   
    @FXML
    public void initialize() {
        id_user = TokenSession.getUserId();
        TokenSession.checkTkSession();

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
    void sendModal(ActionEvent event) throws RemoteException {
        TokenSession.checkTkSession();
        String libName = modalTextField.getText().trim();

        if (libName.isEmpty()) {
            return; 
        } 
       libreria = clientBR.createLibreria(libName, id_user);
       listaLibrerie = clientBR.getLibrerie(id_user);
       librerie.setAll(FXCollections.observableArrayList(listaLibrerie));
       currentLibr.setAll(librerie);
        rearrangeGrid(currentLibr); 
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
        librerie.setAll(clientBR.getLibrerie(id_user));
    	
        if (textSlib.isEmpty()) {
            currentLibr.setAll(librerie);
        } else {
                ObservableList<Libreria> filteredLibr = FXCollections.observableArrayList();
                    for (Libreria lib : librerie) {
                        if (lib.getNomeLibreria().toLowerCase().contains(textSlib)) {
                            filterLibr.add(lib);
                        }
                    }
                currentLibr.setAll(filterLibr); 
        } 
        rearrangeGrid(currentLibr);   
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
        listaLibrerie = clientBR.getLibrerie(id_user);
       librerie.setAll(FXCollections.observableArrayList(listaLibrerie));

    	if (!librerie.isEmpty()) {
            editMode = !editMode;
            rearrangeGrid(currentLibr);
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
    
    public void addLibrary(Libreria libr) throws RemoteException {
       listaLibrerie = clientBR.getLibrerie(id_user);
       librerie.setAll(FXCollections.observableArrayList(listaLibrerie));
       updateEmptyState(); 
       rearrangeGrid(currentLibr); 
    } 
    
  //Metodo per mostrare tutte le librerie
    private void rearrangeGrid() {
        rearrangeGrid(librerie); 
    }
    
    
    //Metodo che si occupa di mostrare il filtraggio delle librerie
    private void rearrangeGrid(ObservableList<Libreria> listToShow) {
    librariesContainer.getChildren().clear();

    int columns = 5;
    int row = 0;
    int col = editMode ? 1 : 0;

    if (editMode) {
        librariesContainer.add(extraBtn, 0, 0);
        GridPane.setMargin(extraBtn, new Insets(15, 5, 5, 15));
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
                rearrangeGrid(currentLibr);
            });

            libPane.setPrefSize(120, 120);
            GridPane.setMargin(libPane, new Insets(15, 5, 5, 15));

            librariesContainer.add(libPane, col, row);

            col++;
                if (col >= columns) {
                    col = editMode ? 1 : 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}


