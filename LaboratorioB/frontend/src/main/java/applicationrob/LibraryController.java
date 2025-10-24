package applicationrob;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.lang.Runnable;
import LaboratorioB.common.models.Libreria;
import models.Model;


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
        Model.getIstance().getView().setSelectedLibrary(libreria);
        Model.getIstance().getView().getSideBarSelectionItem().set("VisLibreria");
        
    }
   }

    @FXML
    public void removeLibrary() {
        if (onRemove != null) {
            onRemove.run();
        }
    }

    

     
}
