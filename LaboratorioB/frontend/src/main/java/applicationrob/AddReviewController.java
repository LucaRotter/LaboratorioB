package applicationrob;


import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;
import LaboratorioB.common.models.Valutazione;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import models.Model;

/** Controller JavaFX della schermata per inserire o visualizzare una recensione (valutazione) di un libro.
 * Gestisce i campi di testo e i punteggi (ChoiceBox) relativi alle diverse categorie di valutazione
 * e calcola la media finale mostrata nella label.
 * Permette di creare una nuova valutazione e, se una valutazione esiste già, mostra i dati in sola lettura.
 * 
 * @author ProgettoLabA
 */

public class AddReviewController implements Initializable{

    @FXML
    private TableReviewController tableReviewController; 

    List<Valutazione> vals;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            vals = clientBR.getInstance().getValutazioniUtente(TokenSession.getUserId(), 
                    Model.getIstance().getView().getSelectedBook().getId());
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        tableReviewController.initValues(vals);
    
        Model.getIstance().getView().selectedBookProperty().addListener((obs, oldLibr, newLibr) -> {

        if(newLibr != null){

        try {
           vals = clientBR.getInstance().getValutazioniUtente(TokenSession.getUserId(), 
                    Model.getIstance().getView().getSelectedBook().getId());
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        tableReviewController.initValues(vals);

    }
        
    });
    }

    
}
