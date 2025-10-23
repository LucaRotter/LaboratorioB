package applicationrob;

import javafx.scene.control.Label;
import LaboratorioB.common.models.Libreria;
import LaboratorioB.common.models.Libro;
import javafx.fxml.FXML;
import java.util.List;
import models.Model;
import views.ViewFactory;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.rmi.RemoteException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;


public class VisLibreriaController {

    @FXML
    private Button searchBtn;

    @FXML
    private GridPane booksContainer;

    @FXML
    private TextField searchBar;

    @FXML
    private Label bookNameLabel;
    
    private Libreria selectedLibrary;
    private Libreria library;
    private ObservableList<Libro> booksLibrary; 
    private ObservableList<Libro> currentBooks;
    private ObservableList<Libro> filteredBooks;

    @FXML
    public void initialize() {
        booksLibrary = FXCollections.observableArrayList(); 
        currentBooks = FXCollections.observableArrayList(); 
        filteredBooks = FXCollections.observableArrayList();

        Model.getIstance().getView().selectedLibraryProperty().addListener((obs, oldLibrary, newLibrary) -> {
            if (newLibrary != null) {
            bookNameLabel.setText(newLibrary.getNomeLibreria());
          }
        });

		library = Model.getIstance().getView().getSelectedLibrary();
		if (library != null) {
			bookNameLabel.setText(library.getNomeLibreria());
		}
        
         booksLibrary.addAll(
        new Libro("Autore 1", "Titolo Libro 1", "Genere 1", "Editore 1", "2020", 1),
        new Libro("Autore 2", "Titolo Libro 2", "Genere 2", "Editore 2", "2021", 2),
        new Libro("Autore 3", "Titolo Libro 3", "Genere 3", "Editore 3", "2022", 3),
        new Libro("Autore 4", "Titolo Libro 4", "Genere 4", "Editore 4", "2023", 4),
        new Libro("Autore 5", "Titolo Libro 5", "Genere 5", "Editore 5", "2024", 5)

          
    );

    // Imposto currentLibr con tutti i libri fittizi
    currentBooks.setAll(booksLibrary);

    // Aggiorno il GridPane con i libri
    InsertingElements(currentBooks);

    }

    @FXML
    public void SeachBooksInLibrary(ActionEvent event) throws RemoteException, IOException {
        TokenSession.checkTkSession();
        String textSlib = searchBar.getText().trim().toLowerCase();
        //library = clientBR.getInstance().getLibreria(selectedLibrary.getIdLibreria());
        booksLibrary.setAll(clientBR.getInstance().getLibreria(selectedLibrary.getIdLibreria()).getLibreria());
        //booksLibrary.setAll(library.getLibreria());
         if (textSlib.isEmpty()) {
            currentBooks.setAll(booksLibrary);
        } else {
              for (Libro lib : booksLibrary) {
                        if (lib.getTitolo().toLowerCase().contains(textSlib)) {
                            filteredBooks.add(lib);
                        }
                    }
                currentBooks.setAll(filteredBooks); 
        }  
        InsertingElements(currentBooks); 
    }

    //Metodo per mostrare tutti i libri della libreria selezionata
    private void InsertingElements() {
        InsertingElements(booksLibrary); 
    }
    
    
    //Metodo che si occupa di mostrare il filtraggio dei libri libreria selezionata
    private void InsertingElements(ObservableList<Libro> listBooksToShow)  {
    booksContainer.getChildren().clear();
     if (listBooksToShow == null || listBooksToShow.isEmpty()) {
        return;
    }

    int columns = 5;
    int row = 0;  
    int col = 0;
   
    for (Libro books : listBooksToShow) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BookEL.fxml"));
            VBox booksPane = loader.load();
			
			BookController bookController= loader.getController();
			//books = clientBR.getInstance().getLibro(lib.getId());
            Libro currentBook = books;

            bookController.setLabels(currentBook.getAutore(), currentBook.getTitolo());

            booksPane.setPrefSize(120, 120);
            GridPane.setMargin(booksPane, new Insets(20, 20, 20, 20));

            booksContainer.add(booksPane, col, row);
			
			booksPane.setOnMouseClicked(e->{
			    Model.getIstance().getView().setSelectedBook(currentBook);
				Model.getIstance().getView().getSideBarSelectionItem().set("VisLibro");
				
			});
			
			    col++;
                    if (col == 5) { 
                        col = 0;
                        row++;
                    }

        } catch (RemoteException e) {
            e.printStackTrace();
		} catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

        
    



}
