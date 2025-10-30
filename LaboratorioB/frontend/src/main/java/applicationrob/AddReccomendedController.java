package applicationrob;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.ResourceBundle;

import LaboratorioB.common.models.Libro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import models.Model;

public class AddReccomendedController implements Initializable {

    @FXML
    private Pane firstbook;

    @FXML
    private Pane secondbook;  
    
    @FXML
    private Pane thirdbook;

    @FXML
    private TextField searchBar;
    
    @FXML
    private GridPane grid;

    @FXML
    private Pane spot1;

    @FXML
    private Pane spot2;

    @FXML
    public void initialize(URL location, ResourceBundle resources)  {

        

        Model.getIstance().getView().selectedBookProperty().addListener((obs, oldLibr, newLibr) -> {

		if(newLibr != null){

            
		}

		});

        LinkedList<Libro> books = new LinkedList<>();

        int row = 0;
        int col = 0;

        for(int i= 0; i<3; i++) {
        
        Libro book = null;
        
        try {
            book = clientBR.getInstance().getLibro(i);
        } catch (RemoteException e) {
           
            e.printStackTrace();
        }
        books.add(book);

        }

        bookList.addAll(books);

        for (Libro book : bookList) {
            System.out.println(book.getId());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BookEL.fxml"));
            VBox booksPane = loader.load();
			
			BookController bookController= loader.getController();
            
            bookController.setLabels(book.getAutore(), book.getTitolo());

            booksPane.setPrefSize(120, 120);
            GridPane.setMargin(booksPane, new Insets(20, 20, 20, 20));

            grid.add(booksPane, col, row);
        
        } catch (IOException e) {
            
            e.printStackTrace();
        }
    }
}
}