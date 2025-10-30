package applicationrob;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import LaboratorioB.common.models.Valutazione;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class ReviewController implements Initializable{
	
	@FXML
	private Button btnShowMore;

	private VisLibroController visLibro ;

	private Valutazione Val;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		btnShowMore.setOnAction(e->{
			
			try {

				visLibro.openModal(Val);
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
	}

	public void setVislibroController(VisLibroController visLibroController) {
	
		this.visLibro = visLibroController;
		
	}

	public void setReview(Valutazione valutazione) {
		
		this.Val = valutazione;
	}
	
}
