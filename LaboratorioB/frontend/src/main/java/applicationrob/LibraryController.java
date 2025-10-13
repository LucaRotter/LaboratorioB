package applicationrob;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import java.lang.Runnable;
import LaboratorioB.common.models.Libreria;


public class LibraryController {

    @FXML
    private Button removeBtn;

    @FXML
    private StackPane rootPane;

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

        removeBtn.setOnAction(e -> {
            if (onRemove != null){ onRemove.run();}
        });
    }

    public StackPane getRoot() {
        return rootPane;
    }
}
