package applicationrob;

import java.net.URL;
import java.util.ResourceBundle;

import LaboratorioB.common.models.Valutazione;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

public class TableReviewController implements Initializable{

	@FXML
    private Text noteContent;

    @FXML
    private Text notePleasantness;

    @FXML
    private Text noteStyle;

    @FXML
    private Text scoreContent;

    @FXML
    private Text scoreEdition;

    @FXML
    private Text scoreFinal;

    @FXML
    private Text scoreOriginality;

    @FXML
    private Text scorePleasantness;

    @FXML
    private Text scoreStyle;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
				
	}

	public void setReview(Valutazione review) {
		
		noteContent.setText(review.getNoteContenuto());
		notePleasantness.setText(review.getNoteGradevolezza());
		noteStyle.setText(review.getNoteStile());
		
		scoreContent.setText(Integer.toString(review.getVotoContenuto()));
		scoreEdition.setText(Integer.toString(review.getVotoEdizione()));
		scoreFinal.setText(Double.toString(review.getVotoMedio()));
		scoreOriginality.setText(Integer.toString(review.getVotoOriginalita()));
		scorePleasantness.setText(Integer.toString(review.getVotoGradevolezza()));
		scoreStyle.setText(Integer.toString(review.getVotoStile()));
		
	}
}
