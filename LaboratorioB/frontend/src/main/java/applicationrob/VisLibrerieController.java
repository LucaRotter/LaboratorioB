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
import javafx.scene.Node;

/**
 * Classe controller per la visualizzazione e gestione delle librerie utente.
 * Contiene metodi per aggiungere, cercare e modificare librerie.
 * Utilizza JavaFX per la gestione dell'interfaccia utente.
 * @author Laboratorio B
 * @param btnSearch Bottone per la ricerca delle librerie.
 * @param edit Bottone per attivare/disattivare la modalità di modifica.
 * @param emptyLbtn Bottone per aggiungere una libreria quando non ce ne sono.
 * @param extraBtn Bottone per aggiungere una nuova libreria in modalità di modifica (max 20 librerie).
 * @param modalSendButton Bottone per inviare i dati inseriti nel modal.
 * @param modalOverlay Overlay per il modal di aggiunta libreria.
 * @param scrollLibraries ScrollPane che contiene le librerie.
 * @param librariesContainer GridPane che contiene gli elementi delle librerie.
 * @param modalTextField TextField per inserire il nome della nuova libreria nel modal.
 * @param searchBar TextField per la ricerca delle librerie.
 * @param modalContent Contenuto del modal.
 * @param emptyText Label per mostrare messaggi quando non ci sono librerie o non ne sono state trovate.
 * @param textSlib Testo inserito nella barra di ricerca.
 * @param editMode Flag per indicare se la modalità di modifica è attiva.
 * @param filteredLibr Lista delle librerie filtrate in base alla ricerca.
 * @param librerie Lista osservabile delle librerie dell'utente.
 * @param id_user ID dell'utente corrente.
 */

public class VisLibrerieController {

   
    @FXML
    private Button btnSearch;
    
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
    
    private String textSlib;
    private boolean editMode = false;

    private List<Libreria> filteredLibr;
    private ObservableList<Libreria> librerie;

    private int id_user;

    /**
     * Metodo di inizializzazione del controller.
     * Controlla ID utente, carica le librerie e imposta i gestori di eventi.
     */
    @FXML
    public void initialize() throws RemoteException {
        id_user = TokenSession.getUserId();

        librerie = FXCollections.observableArrayList();

        librerie.setAll(clientBR.getInstance().getLibrerie(id_user));
        updateEmptyState();
    	
    	modalOverlay.setOnMouseClicked(event -> {
    		if (!modalContent.isHover()) {
    	        modalOverlay.setVisible(false);
    	    }
        });

    	InsertingElements(librerie);
    }
    
    /**
     * Metodo per mostrare il modal di aggiunta libreria.
     */
    private void showModal() {
        if(librerie.size () >= 20) {
            modalOverlay.setVisible(false); 
            views.ViewAlert.showAlert("info", "Maximum Libraries", "You have reached the maximum limit of libraries.", extraBtn, "info");
            return;
        }

        modalOverlay.setVisible(true);
        modalTextField.clear();
    }

    /**
     * Metodo per inviare i dati del modal e creare una nuova libreria.
     * Controlla se il nome della libreria esiste già e aggiorna la lista delle librerie.
     * @param event Evento di azione del bottone di invio.
     * @throws RemoteException Se si verifica un errore di comunicazione remota.
     */
    @FXML
    void sendModal(ActionEvent event) {
        textSlib = modalTextField.getText().trim();
        boolean exists = false;

        if (textSlib.isEmpty()) {
            return; 
        } 

        for (Libreria lib : librerie) {
        if (lib.getNomeLibreria().equalsIgnoreCase(textSlib)) {
            exists = true;
            views.ViewAlert.showAlert("error", "Library arleady exist","A library with this name already exists" , modalSendButton, "error");
            return;
            

        }
    }

        try {
        clientBR.getInstance().createLibreria(textSlib, id_user);
        librerie.setAll(FXCollections.observableArrayList(clientBR.getInstance().getLibrerie(id_user)));
        InsertingElements(librerie);
        views.ViewAlert.showAlert("success", "Library added", "Form now on you can save your favorite books.", modalSendButton, "success");
        modalOverlay.setVisible(false); 
        updateEmptyState();
        
         } catch (RemoteException e) {
             e.printStackTrace();
             views.ViewAlert.showAlert("error", "Library not added", "Server error, try again.", modalSendButton, "error");
         }
    }


    /**
     * Metodo per aprire il modal di aggiunta libreria quando non ce ne sono.
     * @param event Evento di azione del bottone di invio.
     * @throws RemoteException Se si verifica un errore di comunicazione remota.
     */
    @FXML 
    void addLibraryEmpty(ActionEvent event) {
    	showModal();
    }
    
     /**
     * Metodo per aprire il modal di aggiunta libreria se ne esiste gia almeno una libreria.
     * @param event Evento di azione del bottone di invio.
     */
    @FXML
    void addLibrary(ActionEvent event) {
    	showModal();
    }
    
    /**
     * Metodo per cercare librerie in base al testo inserito nella barra di ricerca.
     * Filtra le librerie e aggiorna la visualizzazione.
     * @param event Evento di azione del bottone di ricerca.
     * @throws RemoteException Se si verifica un errore di comunicazione remota.
     */
    @FXML
    void searchLibraries(ActionEvent event) throws RemoteException {
        textSlib = searchBar.getText().trim().toLowerCase();
        librerie.setAll(clientBR.getInstance().getLibrerie(id_user));
 
        if (textSlib.isEmpty()) {
            emptyText.setVisible(false);
            //filteredLibr = null;              
            InsertingElements(librerie);       
            return;
        } 
            filteredLibr = librerie.stream()
            .filter(lib -> lib.getNomeLibreria().toLowerCase().contains(textSlib))
            .toList();

            if (filteredLibr.isEmpty()) {
        librariesContainer.getChildren().clear();
        emptyText.setText("LIBRARIES NOT FOUND");
        emptyText.setVisible(true);
        return;
    }

        InsertingElements(FXCollections.observableArrayList(filteredLibr));   
    }
   
    /**
     * Metodo per aggiornare il testo della barra di ricerca.
     * @param event Evento di azione del bottone di ricerca.
     */
    @FXML
    void writeText(ActionEvent event) {
    	textSlib = searchBar.getText().trim().toLowerCase();
        
    	if (!textSlib.matches("[a-zA-Z0-9 ]*")) {
    	    return;
    	}	     
    }

    /**
     * Metodo per attivare/disattivare la modalità di modifica delle librerie.
     * Aggiorna la visualizzazione delle librerie in base alla modalità.
     * @param event Evento di azione del bottone di modifica.
     * @throws RemoteException Se si verifica un errore di comunicazione remota.
     */ 
    @FXML
    void useEdit(ActionEvent event) throws RemoteException {
        editMode = !editMode;
        extraBtn.setVisible(editMode);

        textSlib = searchBar.getText().trim().toLowerCase();

        if (!textSlib.isEmpty() && filteredLibr != null) {
            InsertingElements(FXCollections.observableArrayList(filteredLibr));
        } else {
            InsertingElements(librerie);
        }
    }
   
    /**
     * Metodo per aggiornare lo stato di visualizzazione quando non ci sono librerie.
     * Mostra o nasconde i componenti appropriati in base alla presenza di librerie.
     */
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
    
    /**
     * Metodo che si occupa di mostrare tutte le librerie senza filtri.
     */
    private void InsertingElements() {
        InsertingElements(librerie); 
    }
    
    
    /**
     * Metodo che si occupa di mostrare le librerie fornite in input.
     * Mostra le librerie in una griglia, gestendo la modalità di modifica e l'aggiunta di nuove librerie.
     * Permette di rimuovere librerie in modalità di modifica tramite un callback.
     * @param listToShow Lista delle librerie da mostrare.
     * @throws IOException Se si verifica un errore di I/O durante il caricamento delle librerie.
     * @throws RemoteException Se si verifica un errore di comunicazione remota.
     */
    private void InsertingElements(ObservableList<Libreria> listToShow) {
    librariesContainer.getChildren().clear();

    int columns = 5;
    int row = 0;
    int col = 0; 
   

    if (editMode) {
        extraBtn.setPrefSize(120, 120);
        librariesContainer.add(extraBtn, 0, 0);
        GridPane.setMargin(extraBtn, new Insets(15, 15, 15, 20));
        col = 1;
        
    }

    for (Libreria lib : listToShow) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/applicationrob/LibraryEL.fxml"));
            StackPane libPane = loader.load();

            LibraryController controller = loader.getController();
            controller.setData(lib, editMode, () -> {
              try {
                    librerie.remove(lib);              
                    updateEmptyState();                 
                    InsertingElements(librerie);       

                    views.ViewAlert.showAlert("success", "Library removed","The library has been removed successfully.", librariesContainer, "success");

                } catch (Exception e) {
                    e.printStackTrace();
                    views.ViewAlert.showAlert("error", "Library not removed","Server error, try again.",librariesContainer, "error");
                }
            });

            libPane.setPrefSize(120, 170);
            GridPane.setMargin(libPane, new Insets(7, 10, 10, 19));

            librariesContainer.add(libPane, col, row);
      
            col++;
                if (col >= columns) {
                   col = 0; 
                   row++;
                   if (editMode && row == 0) col = 1;
                }
            } catch (IOException e) {
                e.printStackTrace();
                views.ViewAlert.showAlert("error", "Library error", "Server error, try again.", librariesContainer, "error");
            }
        }
    }

}