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

        removeLibrary();
    }

    @FXML
    private void clickLibrary() {
        if (!editMode && libreria != null) {
            openLibraryPage();
        }
   }

   private void openLibraryPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/applicationrob/VisLibreria.fxml"));
            StackPane root = loader.load();

            LibraryDetailController controller = loader.getController();
            controller.setLibrary(libreria);

            javafx.stage.Stage stage = new javafx.stage.Stage();
            stage.setTitle(libreria.getNomeLibreria());
            stage.setScene(new javafx.scene.Scene(root));
            stage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void removeLibrary() {
        if (onRemove != null) {
            onRemove.run();
        }
    }

    public StackPane getRoot() {
        return rootPane;
    }
}
