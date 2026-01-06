package applicationrob;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import LaboratorioB.common.models.Valutazione;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/** Controller JavaFX per la visualizzazione di una singola recensione (valutazione) di un libro.
 * Gestisce l'interazione con il pulsante "Mostra di più" per aprire una finestra modale
 * con i dettagli completi della recensione.
 * @author ProgettoLabA
 */
public class ReviewController implements Initializable{
	
	@FXML
	private Button btnShowMore;

	@FXML 
	private Label lbUser;

	@FXML
	private Label overallvote;

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

	/**
	 * Metodo per impostare il controller VisLibroController.
	 */
	public void setVislibroController(VisLibroController visLibroController) {
	
		this.visLibro = visLibroController;
		
	}

	public void setReview(Valutazione valutazione) throws RemoteException {
		
		this.Val = valutazione;
		lbUser.setText(clientBR.getInstance().getUtente(valutazione.getIdUtente()).getNome());
		overallvote.setText(String.valueOf(valutazione.getVotoMedio()));
	}
	
}
