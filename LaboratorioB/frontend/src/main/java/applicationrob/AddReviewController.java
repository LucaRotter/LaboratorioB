package applicationrob;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import LaboratorioB.common.models.Valutazione;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import models.Model;

public class AddReviewController implements Initializable{

    @FXML
    private Button BtnConfirm;

    @FXML
    private Button BtnRestar;

    @FXML
    private ChoiceBox<?> ScoreContent;

    @FXML
    private ChoiceBox<?> ScoreEdition;

    @FXML
    private ChoiceBox<?> ScoreFinal;

    @FXML
    private ChoiceBox<?> ScoreOriginality;

    @FXML
    private ChoiceBox<?> ScorePleasentness;

    @FXML
    private ChoiceBox<?> ScoreStyle;

    @FXML
    private TextArea TxtContent;

    @FXML
    private TextArea TxtEdition;

    @FXML
    private TextArea TxtFinal;

    @FXML
    private TextArea TxtOriginality;

    @FXML
    private TextArea TxtPleasentess;

    @FXML
    private TextArea TxtStyle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    public void init(){
        BtnConfirm.setOnAction(e->{
            try {
                onConfirm();
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });

        BtnRestar.setOnAction(e->{
            onRestart();
        });
    }

    private void onRestart() {
        ScoreStyle.setValue(null);
        ScoreEdition.setValue(null);    
        ScoreContent.setValue(null);
        ScorePleasentness.setValue(null);
        ScoreOriginality.setValue(null);
        TxtContent.clear();
        TxtEdition.clear();
        TxtFinal.clear();
        TxtOriginality.clear();
        TxtPleasentess.clear();
        TxtStyle.clear();
    }

    public void onConfirm() throws RemoteException {

        //aggiungere controlli sui campi
        int style = Integer.parseInt(ScoreStyle.getValue().toString());
        int edition = Integer.parseInt(ScoreEdition.getValue().toString());
        int content = Integer.parseInt(ScoreContent.getValue().toString());
        int pleasentness = Integer.parseInt(ScorePleasentness.getValue().toString());
        int originality = Integer.parseInt(ScoreOriginality.getValue().toString());
        double finalScore = (style + edition + content + pleasentness + originality) / 5.0;

        Valutazione val = new Valutazione(style, edition, content, pleasentness, originality, finalScore,
            TxtContent.getText(), TxtEdition.getText(), TxtStyle.getText(), TxtPleasentess.getText(), TxtOriginality.getText(), Model.getIstance().getView().getSelectedBook().getId(),
            TokenSession.getUserId()
        );

        clientBR.getInstance().createValutazione(val);
    }

    
}
