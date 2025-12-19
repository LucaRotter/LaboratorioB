package applicationrob;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.lang.Runnable;
import LaboratorioB.common.models.Libreria;
import models.Model;
import java.rmi.RemoteException;

/**
 * Classe controller per la gestione delle librerie.
 * Contiene metodi per visualizzare e rimuovere le librerie.
 * Utilizza JavaFX per la gestione dell'interfaccia utente.
 * @author Laboratorio B
 * @param removeBtn Bottone per rimuovere la libreria.
 * @param libraryLabel Label che mostra il nome della libreria.
 * @param libreria Libreria corrente.
 * @param editMode Modalità di modifica della libreria.
 * @param onRemove Runnable da eseguire al momento della rimozione della libreria.
 */

public class LibraryController {

    @FXML
    private Button removeBtn;

    @FXML
    private Label libraryLabel;

    private Libreria libreria;

    private boolean editMode;

    private Runnable onRemove;
    

    /**
     * Metodo per impostare i dati della libreria nel controller.
     * @param libreria Libreria da visualizzare.
     * @param editMode Modalità di modifica della libreria.
     * @param onRemove Runnable da eseguire al momento della rimozione della libreria.
     */
    public void setData(Libreria libreria, boolean editMode, Runnable onRemove) {
        this.libreria = libreria;
        this.editMode = editMode;
        this.onRemove = onRemove;

        libraryLabel.setText(libreria.getNomeLibreria());
        removeBtn.setVisible(editMode);

    }

    /**
     * Metodo per gestire il click sulla libreria.
     * Cambia la vista alla libreria selezionata se non si è in modalità di modifica.
     */
    @FXML
    private void clickLibrary() {
        if (!editMode && libreria != null) {
        Model.getIstance().getView().setSelectedLibrary(libreria);
        Model.getIstance().getView().getSideBarSelectionItem().set("VisLibreria"); 
        
    }
   }

   /**
    * Metodo per rimuovere la libreria.
    * Effettua la chiamata al clientBR per eliminare la libreria dal backend.
    * Esegue il Runnable onRemove al termine dell'operazione.
    * @throws RemoteException Se si verifica un errore di comunicazione remota.
    */
    @FXML
    public void removeLibrary() {
        if (libreria != null) {
        try {
            
            clientBR.getInstance().deleteLibreria(libreria.getIdLibreria());

            if (onRemove != null) {
                onRemove.run();
            }


        } catch (RemoteException e) {
            e.printStackTrace();
            if (onRemove != null) {
                onRemove.run();
            }

        }
    }
    }

    

     
}
