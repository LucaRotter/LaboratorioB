package application;

import java.io.IOException;
import java.util.LinkedList;
import javafx.scene.control.ListCell;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

public class RicercaLibroController implements SceneController{

@FXML 
ListView<String> Lista;

@FXML 
Button ricercaButton; 

@FXML 
Pane panefiltri; 

private String SelectedOne;

WindowController window = null;


public void initialize() {
	
		Lista.getSelectionModel().selectedItemProperty().addListener( new ChangeListener <String>() {

		@Override
		public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
			SelectedOne = Lista.getSelectionModel().getSelectedItem();
			LinkedList<Libro> L= new Libro().cercaLibro(SelectedOne,"","titolo");
			System.out.println(L.getFirst().getTitolo());
			
		}
		});
		
		Lista.setCellFactory(lv -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle(""); // Reset dello stile se la cella è vuota
                } else {
                    setText(item);

                    // Alternare i colori tra righe pari e dispari
                    if (getIndex() % 2 == 0) {
                        setStyle("-fx-background-color: #92DADD; -fx-text-fill: black;");
                    } else {
                        setStyle("-fx-background-color: white; -fx-text-fill: black;");
                    }
		
                }
            }
		});
}


public void OnSelection(ActionEvent event) throws IOException {
	
    Libro L = Libro.cercaLibro(SelectedOne);
	System.out.println(L.getTitolo());
	
	VisualizzaController visualizza = window.changeWindow("VisualizzaLibro.fxml");;
	visualizza.setLibro(L);
	
}

public void setString(LinkedList<Libro> libri) {
	
	for (Libro tmp : libri) {
        // Qui puoi aggiungere i libri alla ListView, ad esempio:
        Lista.getItems().add(tmp.getTitolo());
    }
}

public void onFilter() {
	
	if (panefiltri != null && panefiltri.isVisible()) {
		panefiltri.setVisible(false); // Chiude la finestra se è già aperta
        return;
    }
	
	panefiltri.setVisible(true);
	
}

@Override
public void setWindowController(WindowController windowController) {
	
	 this.window = windowController;
	
}
}
