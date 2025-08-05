package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class dialogController {
	
	@FXML
	TextField setupname;
	
	@FXML
	Button confirmbutton;
	
	@FXML
	Button cancelbutton;
	
	boolean validation = true;
	
	public void onConfirm() {
		validation = true;
		Stage stage = (Stage) confirmbutton.getScene().getWindow();
		stage.close();
	}
	
public void onCancel() {
		validation = false;
		Stage stage = (Stage) confirmbutton.getScene().getWindow();
		stage.close();
	}
	
	public String getText() {
		
		return setupname.getText();
	}
	
	public boolean getValidation() {
		return validation ;

	}
}
