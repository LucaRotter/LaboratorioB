package applicationrob;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;

public class LibraryItemController {

    @FXML
    private CheckBox btnChek;

    @FXML
    private Text txtName;

    private int id_library;
    private boolean initialSelected;

    public void initLibrary(String name,int id_library,boolean selected) {
        this.txtName.setText(name);
        this.btnChek.setSelected(selected);
        this.id_library = id_library;
        this.initialSelected = selected;
    }

    public int getId_library() {
        return id_library;
    }

    public boolean hasStateChanged() {
        return initialSelected != btnChek.isSelected();
    }

    public boolean isSelected() {
        return btnChek.isSelected();
    }

}
