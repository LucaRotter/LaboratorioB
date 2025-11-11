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
import javafx.scene.text.Text;
import javafx.scene.text.Font;

public class VisLibreriaController {

    @FXML
    private Button searchBtn;

    @FXML
    private GridPane booksContainer;

    @FXML
    private TextField searchBar;

    @FXML
    private Text bookNameLabel;
    
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

    @FXML
    public void SeachBooksInLibrary(ActionEvent event) throws RemoteException, IOException {
        String textSlib = searchBar.getText().trim().toLowerCase();
        booksLibrary.setAll(clientBR.getInstance().getLibreria(selectedLibrary.getIdLibreria()).getLibreria());
        filteredBooks.clear();
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
            bookController.setLabels(books.getAutore(), books.getTitolo());

            booksPane.setPrefSize(140, 190);
            GridPane.setMargin(booksPane, new Insets(20, 20, 20, 20));

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
             views.ViewFactory.showAlert("error", "Book error", "Server error, try again.", booksContainer, "error");
		} catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
