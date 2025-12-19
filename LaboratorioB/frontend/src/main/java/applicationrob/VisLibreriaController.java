package applicationrob;

import javafx.scene.control.Label;
import LaboratorioB.common.models.Libreria;
import LaboratorioB.common.models.Libro;
import javafx.fxml.FXML;
import models.Model;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import java.rmi.RemoteException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.text.Text;

/**
 * Classe controller per la visualizzazione della libreria e dei suoi libri.
 * Contiene metodi per aggiungere, cercare e visualizzare i libri all'interno della libreria selezionata.
 * Utilizza JavaFX per la gestione dell'interfaccia utente.
 * @author Laboratorio B
 * @param btnSearch Bottone per la ricerca dei libri nella libreria.
 * @param booksContainer GridPane che contiene i libri della libreria.
 * @param searchBar TextField per l'inserimento del testo di ricerca.
 * @param bookNameLabel Text che mostra il nome della libreria selezionata.
 * @param emptyText Label che mostra messaggi quando non ci sono libri da visualizzare.
 * @param selectedLibrary Libreria selezionata dall'utente.
 * @param library Libreria corrente.
 * @param booksLibrary Lista osservabile dei libri presenti nella libreria.
 * @param currentBooks Lista osservabile dei libri attualmente visualizzati.
 * @param filteredBooks Lista osservabile dei libri filtrati in base alla ricerca.
 */

public class VisLibreriaController {

    @FXML
    private Button btnSearch;

    @FXML
    private GridPane booksContainer;

    @FXML
    private TextField searchBar;

    @FXML
    private Text bookNameLabel;

    @FXML
    private Label emptyText;
    
    private Libreria selectedLibrary;
    private Libreria library;

    private ObservableList<Libro> booksLibrary; 
    private ObservableList<Libro> currentBooks;
    private ObservableList<Libro> filteredBooks;


    /**
     * Metodo di inizializzazione del controller.
     * Inizializza le liste osservabili e imposta i listener per la libreria selezionata.
     * Aggiorna la visualizzazione dei libri quando la libreria selezionata cambia.
     * @throws RemoteException Se si verifica un errore di comunicazione remota.
     */
    @FXML
    public void initialize() { 
        booksLibrary = FXCollections.observableArrayList(); 
        currentBooks = FXCollections.observableArrayList(); 
        filteredBooks = FXCollections.observableArrayList();

        Model.getIstance().getView().selectedLibraryProperty().addListener((obs, oldLibrary, newLibrary) -> {
            if (newLibrary != null) {
            try {
            booksLibrary.clear();
            booksLibrary.addAll(clientBR.getInstance().getLibreria(newLibrary.getIdLibreria()).getLibreria());
            } catch (RemoteException e) {
            e.printStackTrace();
            } 

            bookNameLabel.setText(newLibrary.getNomeLibreria());

            // Imposto currentLibr con tutti i libri fittizi
            currentBooks.setAll(booksLibrary); 

            // Aggiorno il GridPane con i libri
            InsertingElements(currentBooks);
        
          }
        });
 
		selectedLibrary = Model.getIstance().getView().getSelectedLibrary();
		if (selectedLibrary  != null) {
		}
        try {
            booksLibrary.clear();
            booksLibrary.addAll(clientBR.getInstance().getLibreria(selectedLibrary.getIdLibreria()).getLibreria());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    bookNameLabel.setText(selectedLibrary.getNomeLibreria());

    // Imposto currentLibr con tutti i libri fittizi
    currentBooks.setAll(booksLibrary);

    // Aggiorno il GridPane con i libri
    InsertingElements(currentBooks);

    }

    /**
     * Metodo per cercare libri nella libreria selezionata in base al testo inserito nella barra di ricerca.
     * Filtra i libri e aggiorna la visualizzazione.
     * @param event Evento di azione del bottone di ricerca.
     * @throws RemoteException Se si verifica un errore di comunicazione remota.
     * @throws IOException Se si verifica un errore di I/O durante la ricerca.
     */
    @FXML
    public void SeachBooksInLibrary(ActionEvent event) throws RemoteException, IOException {
        String textSlib = searchBar.getText().trim().toLowerCase();
        booksLibrary.setAll(clientBR.getInstance().getLibreria(selectedLibrary.getIdLibreria()).getLibreria());
        filteredBooks.clear();
         if (textSlib.isEmpty()) {
            emptyText.setVisible(true);
            currentBooks.setAll(booksLibrary);
        } else {
              for (Libro lib : booksLibrary) {
                        if (lib.getTitolo().toLowerCase().contains(textSlib)) {
                            filteredBooks.add(lib);
                        }
                    }
                currentBooks.setAll(filteredBooks); 
        }  

       if (filteredBooks.isEmpty()) {
        booksContainer.getChildren().clear();
        emptyText.setText("BOOKS NOT FOUND");
        emptyText.setVisible(true);
        return;
    }
        InsertingElements(currentBooks); 
    }

    /**
     * Metodo che si occupa di mostrare tutti i libri della libreria senza filtri.
     */
    private void InsertingElements() {
        InsertingElements(booksLibrary); 
    }
    
    
    /**
     * Metodo che si occupa di mostrare i libri forniti in input.
     * Mostra i libri in una griglia.
     * @param listBooksToShow Lista dei libri da mostrare.
     * @throws IOException Se si verifica un errore di I/O durante il caricamento dei libri.
     * @throws RemoteException Se si verifica un errore di comunicazione remota.
     */
    private void InsertingElements(ObservableList<Libro> listBooksToShow)  {
    booksContainer.getChildren().clear();
     if (listBooksToShow == null || listBooksToShow.isEmpty()) {
        emptyText.setVisible(true);
        return;
    } else {
        emptyText.setVisible(false);
    }

    int columns = 5;
    int row = 0;  
    int col = 0;
   
    for (Libro books : listBooksToShow) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BookEL.fxml"));
            VBox booksPane = loader.load();
			
			BookController bookController= loader.getController();
            bookController.setLabels(books.getAutore(), books.getTitolo());

            booksPane.setPrefSize(120, 170);
            GridPane.setMargin(booksPane, new Insets(7, 10, 10, 19));

            booksContainer.add(booksPane, col, row);
			
			booksPane.setOnMouseClicked(e->{
			    Model.getIstance().getView().setSelectedBook(books);
				Model.getIstance().getView().getSideBarSelectionItem().set("VisLibro");
				
			});
			
			    col++;
                    if (col == 5) { 
                        col = 0;
                        row++;
                    }

        } catch (RemoteException e ) {
            e.printStackTrace();
             views.ViewAlert.showAlert("error", "Book error", "Server error, try again.", booksContainer, "error");
		} catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
