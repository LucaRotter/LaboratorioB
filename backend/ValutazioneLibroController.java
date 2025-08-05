package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;

public class ValutazioneLibroController implements SceneController{

		@FXML
	    private GridPane gridvalut;
	 
	 	@FXML
	    private TextArea AreaContent;

	    @FXML
	    private TextArea AreaEdit;

	    @FXML
	    private TextArea AreaOrig;

	    @FXML
	    private TextArea AreaTotal;
	    
	    @FXML
	    private TextArea AreaGrad;

	    @FXML
	    private TextArea AreaStile;

	    @FXML
	    private ComboBox<Integer> ValContent;

	    @FXML
	    private ComboBox<Integer> ValEdit;

	    @FXML
	    private ComboBox<Integer> ValGrad;

	    @FXML
	    private ComboBox<Integer> ValOrig;

	    @FXML
	    private ComboBox<Integer> ValTotal;

	    @FXML
	    private ComboBox<Integer> Valstile;

	    @FXML
	    private Button backhome;
	    
	    @FXML
	    private Button LibraryButton;
	    
	    @FXML
	    private Label labelLibro;

	private WindowController window;
	private Libro libro;
	private Librerie librerie;
	
	int[] voti = new int[6];
	String[] commenti = new String[6];

	
	
	public void initialize() {
			
		
		
		for(int i= 1; i<voti.length;i++) {
	
			ValEdit.getItems().add((Integer)(i));
			ValContent.getItems().add((Integer)(i));
			ValGrad.getItems().add((Integer)(i));
			ValOrig.getItems().add((Integer)(i));
			ValTotal.getItems().add((Integer)(i));
			Valstile.getItems().add((Integer)(i));
			
		}
		
		AreaStile.setTextFormatter(new TextFormatter<>(change -> {
	        return change.getControlNewText().length() <= 256 ? change : null;
	    }));
		AreaContent.setTextFormatter(new TextFormatter<>(change -> {
	        return change.getControlNewText().length() <= 256 ? change : null;
	    }));
		AreaGrad.setTextFormatter(new TextFormatter<>(change -> {
	        return change.getControlNewText().length() <= 256 ? change : null;
	    }));
		
		AreaEdit.setTextFormatter(new TextFormatter<>(change -> {
	        return change.getControlNewText().length() <= 256 ? change : null;
	    }));
		
		AreaTotal.setTextFormatter(new TextFormatter<>(change -> {
	        return change.getControlNewText().length() <= 256 ? change : null;
	    }));
		
	}
	
	public void onConfirm() throws IOException {
		
		voti[0] =Valstile.getSelectionModel().getSelectedItem(); 
		voti[1] =ValContent.getSelectionModel().getSelectedItem(); 
		voti[2] =ValGrad.getSelectionModel().getSelectedItem(); 
		voti[3] =ValOrig.getSelectionModel().getSelectedItem(); 
		voti[4] =ValEdit.getSelectionModel().getSelectedItem(); 
		voti[5]  =ValTotal.getSelectionModel().getSelectedItem(); 
		
		commenti[0] =AreaStile.getText();
		commenti[1] =AreaContent.getText();
		commenti[2] =AreaGrad.getText();
		commenti[3] =AreaOrig.getText();
		commenti[4] =AreaEdit.getText();
		commenti[5]  =AreaTotal.getText();
		
		for(int j= 0; j< voti.length;j++) {
		System.out.print(voti[j]);
		}
		
		librerie.libroDaValutare(libro, visualizzaLibrerieController.getmodalita(),voti,commenti);
		window.changeWindow("Home.fxml");
		
		//aggiungere banner per riuscita aggiunta quando torna in home
	}
	
	@Override
	public void setWindowController(WindowController windowController) {
		
		window = windowController;
		
	}

	public void setLibro(Libro libro, Librerie librerie) {
		
		this.librerie = librerie;
		this.libro = libro;
		labelLibro.setText(labelLibro.getText()+ " "+ libro.getTitolo());
		
	}
	
public void backtohome() throws IOException {
		
		window.changeWindow("Home.fxml");
		
}

public void backLibrary() throws IOException {
		visualizzaLibrerieController l = window.changeWindow("VisualizzaLibrerie.fxml");;
		l.setmodalità("vislibrerie");
		
	
}

}
