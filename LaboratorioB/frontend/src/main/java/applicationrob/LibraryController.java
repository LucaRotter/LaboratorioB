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
 * 
 * @author Grassi, Alessandro, 757784, VA
 * @author Kastratovic, Aleksandar, 752468, VA
 * @author Rotter, Luca Giorgio, 757780, VA
 * @author Davide, Bilora, 757011, VA
 * @version 1.0
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
