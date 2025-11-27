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
    
    //Metodo che permetta l'apertura del Modal
    private void showModal() {
        if(librerie.size () >= 50) {
            modalOverlay.setVisible(false); 
            views.ViewAlert.showAlert("info", "Maximum Libraries", "You have reached the maximum limit of libraries.", extraBtn, "info");
            return;
        }

        modalOverlay.setVisible(true);
        modalTextField.clear();
    }

    //Metodo per inviare dati inseriti nel Modal
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
        textSlib = searchBar.getText().trim().toLowerCase();
        librerie.setAll(clientBR.getInstance().getLibrerie(id_user));
 
        if (textSlib.isEmpty()) {
            emptyText.setVisible(false);
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
   
    // Metodo di controllo per searchLibraries
    @FXML
    void writeText(ActionEvent event) {
    	textSlib = searchBar.getText().trim().toLowerCase();
        
    	if (!textSlib.matches("[a-zA-Z0-9 ]*")) {
    	    return;
    	}	     
    }

    // Metodo per azionare la modifica/aggiunta librerie 
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