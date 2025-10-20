package applicationrob;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class ReviewController implements Initializable{
	
	@FXML
	private Button btnShowMore;

	private VisLibroController visLibro ;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		btnShowMore.setOnAction(e->{
			System.out.println("mi hai schiacciato");
			try {
				visLibro.openModal();
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
	}
	
	public void showModal() {
		
	}

	public void setVislibroController(VisLibroController visLibroController) {
		
		System.out.println(visLibroController);
		this.visLibro = visLibroController;
		
	}
	
}
