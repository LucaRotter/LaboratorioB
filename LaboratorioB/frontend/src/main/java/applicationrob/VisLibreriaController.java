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
    private Label nomeLibrLabel;
    @FXML
    private TextField searchBar;
    @FXML
    private Button searchBtn;
    @FXML
    private GridPane bookContainer;
    
    private Libreria selectedLibrary ;
    private Libro libr;
    private List<Libro> libri;
    private ObservableList<Libro> libriLibreria; 
    private ObservableList<Libro> currentLibr;

    @FXML
    public void initialize() {
        libriLibreria = FXCollections.observableArrayList(); 
        currentLibr = FXCollections.observableArrayList(); 

        Model.getIstance().getView().selectedLibraryProperty().addListener((obs, oldLibrary, newLibrary) -> {
            if (newLibrary != null) {
            nomeLibrLabel.setText(newLibrary.getNomeLibreria());
          }
        });

		Libreria library = Model.getIstance().getView().getSelectedLibrary();
		if (library != null) {
			nomeLibrLabel.setText(library.getNomeLibreria());
		}

         libriLibreria.addAll(
        new Libro("Autore 1", "Titolo Libro 1", "Genere 1", "Editore 1", "2020", 1),
        new Libro("Autore 2", "Titolo Libro 2", "Genere 2", "Editore 2", "2021", 2),
        new Libro("Autore 3", "Titolo Libro 3", "Genere 3", "Editore 3", "2022", 3),
        new Libro("Autore 4", "Titolo Libro 4", "Genere 4", "Editore 4", "2023", 4),
        new Libro("Autore 5", "Titolo Libro 5", "Genere 5", "Editore 5", "2024", 5)

         
    );

    // Imposto currentLibr con tutti i libri fittizi
    currentLibr.setAll(libriLibreria);

    // Aggiorno il GridPane con i libri
    rearrangeGrid(currentLibr);

    }

    @FXML
    public void SeachBooksInLibrary(ActionEvent event) throws RemoteException, IOException {
    }

    @FXML
    public void SearchControl(ActionEvent event) throws RemoteException, IOException {
        TokenSession.checkTkSession();
        String textSlib = searchBar.getText().trim().toLowerCase();
        Libreria library = clientBR.getInstance().getLibreria(selectedLibrary.getIdLibreria());
        libriLibreria.setAll(library.getLibreria());
         if (textSlib.isEmpty()) {
            currentLibr.setAll(libriLibreria);
        } else {
            ObservableList<Libro> filteredLibr = FXCollections.observableArrayList();
              for (Libro lib : libriLibreria) {
                        if (lib.getTitolo().toLowerCase().contains(textSlib)) {
                            filteredLibr.add(lib);
                        }
                    }
                currentLibr.setAll(filteredLibr); 
        } 
        rearrangeGrid(currentLibr); 
    }

    //Metodo per mostrare tutti i libri della libreria selezionata
    private void rearrangeGrid() {
        rearrangeGrid(libriLibreria); 
    }
    
    
    //Metodo che si occupa di mostrare il filtraggio dei libri libreria selezionata
    private void rearrangeGrid(ObservableList<Libro> listToShow) {
    bookContainer.getChildren().clear();
     if (listToShow == null || listToShow.isEmpty()) {
        return;
    }

    int columns = 5;
    int row = 0;
    int col = 0;
   
    for (Libro lib : listToShow) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/applicationrob/BookEL.fxml"));
            VBox libPane = loader.load();

            BookController controller = loader.getController();
			
			BookController bookController= loader.getController();
			//Libro libr = clientBR.getInstance().getLibro(lib.getId());
			//bookController.setLabels(libr.getAutore(), libr.getTitolo());
            bookController.setLabels(lib.getAutore(), lib.getTitolo());

            libPane.setPrefSize(120, 120);
            GridPane.setMargin(libPane, new Insets(20, 20, 20, 20));

            bookContainer.add(libPane, col, row);
			
			libPane.setOnMouseClicked(e->{

			    //Model.getIstance().getView().setSelectedBook(libr);
				Model.getIstance().getView().getSideBarSelectionItem().set("VisLibro");
				
			});
			
			    col++;
                    if (col == 4) { 
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
