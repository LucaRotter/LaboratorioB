package applicationrob;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
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
    private TextField searchBar;
    
    @FXML
    private GridPane grid;

    @FXML
    private Pane spot1;

    @FXML
    private Pane spot2;

    @FXML
    private Pane spot3;

    @FXML
    public void initialize(URL location, ResourceBundle resources)  {
        
        Model.getIstance().getView().selectedBookProperty().addListener((obs, oldLibr, newLibr) -> {

        try {

            List<Libro> List = clientBR.getInstance().getConsiglio(newLibr.getId());

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    });
    }
}