package applicationrob;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.lang.Runnable;
import LaboratorioB.common.models.Libreria;
import models.Model;
import java.rmi.RemoteException;
import applicationrob.clientBR;


public class LibraryController {

    @FXML
    private Button removeBtn;
    @FXML
    private Label libraryLabel;

    private Libreria libreria;
    private boolean editMode;
    private Runnable onRemove;
    
    public void setData(Libreria libreria, boolean editMode, Runnable onRemove) {
        this.libreria = libreria;
        this.editMode = editMode;
        this.onRemove = onRemove;

        libraryLabel.setText(libreria.getNomeLibreria());
        removeBtn.setVisible(editMode);

    }

    @FXML
    private void clickLibrary() {
        if (!editMode && libreria != null) {

           /*  if (Model.getIstance().getView().getSelectedLibrary() == libreria) {
            Model.getIstance().getView().setSelectedLibrary(null);
            }*/

        //Model.getIstance().getView().setSelectedLibrary(null);

        Model.getIstance().getView().setSelectedLibrary(libreria);
        Model.getIstance().getView().getSideBarSelectionItem().set("VisLibreria"); 
        
    }
   }

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
