package applicationrob;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import LaboratorioB.common.models.Valutazione;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import models.Model;

public class TableReviewController implements Initializable{

	@FXML
    private Button BtnConfirm;

    @FXML
    private Button BtnRestar;

    @FXML
    private ChoiceBox<Integer> ScoreContent;

    @FXML
    private ChoiceBox<Integer> ScoreEdition;

    @FXML
    private ChoiceBox<Integer> ScoreOriginality;

    @FXML
    private ChoiceBox<Integer> ScorePleasentness;

    @FXML
    private ChoiceBox<Integer> ScoreStyle;

    @FXML
    private TextArea TxtContent;

    @FXML
    private TextArea TxtEdition;

    @FXML
    private TextArea TxtOriginality;

    @FXML
    private TextArea TxtPleasentess;

    @FXML
    private TextArea TxtStyle;

    @FXML
    private Label lbAverage;

    List<Valutazione> items = null;

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

        int i=1;

        for(i=1; i<=5; i++){

        ScoreStyle.getItems().add(i);
        ScoreEdition.getItems().add(i);
        ScoreContent.getItems().add(i);
        ScorePleasentness.getItems().add(i);
        ScoreOriginality.getItems().add(i);
        }

        ScoreStyle.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> updateMedia());
        ScoreEdition.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> updateMedia());
        ScoreContent.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> updateMedia());
        ScorePleasentness.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> updateMedia());
        ScoreOriginality.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> updateMedia());

        TxtStyle.setWrapText(true);
        TxtContent.setWrapText(true);
        TxtPleasentess.setWrapText(true);
        TxtOriginality.setWrapText(true);
        TxtEdition.setWrapText(true);
    }

    private void onRestart() {
        ScoreStyle.setValue(null);
        ScoreEdition.setValue(null);    
        ScoreContent.setValue(null);
        ScorePleasentness.setValue(null);
        ScoreOriginality.setValue(null);
        ScoreStyle.setValue(1);
        ScoreEdition.setValue(1);
        ScoreContent.setValue(1);
        ScorePleasentness.setValue(1);
        ScoreOriginality.setValue(1);
    }

    public void initValues(List<Valutazione> vals){

            if(vals.isEmpty()){
            
                        ScoreStyle.setValue(1);
                        ScoreEdition.setValue(1);
                        ScoreContent.setValue(1);
                        ScorePleasentness.setValue(1);
                        ScoreOriginality.setValue(1);

                        BtnRestar.setDisable(false);
                        BtnRestar.setVisible(true);
                        BtnConfirm.setVisible(true);
                        BtnConfirm.setDisable(false);

                        TxtContent.setEditable(true);
                        TxtEdition.setEditable(true);
                        TxtOriginality.setEditable(true);
                        TxtPleasentess.setEditable(true);
                        TxtStyle.setEditable(true);

                        ScoreStyle.setMouseTransparent(false);
                        ScoreStyle.setFocusTraversable(true);

                        ScoreEdition.setMouseTransparent(false);
                        ScoreEdition.setFocusTraversable(true);

                        ScoreContent.setMouseTransparent(false);
                        ScoreContent.setFocusTraversable(true);

                        ScorePleasentness.setMouseTransparent(false);
                        ScorePleasentness.setFocusTraversable(true);

                        ScoreOriginality.setMouseTransparent(false);
                        ScoreOriginality.setFocusTraversable(true);

                } else {
                    
                        setReview(vals.get(0));
                }
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

        System.out.println("Valutazione creata: " + val.getVotoStile());

        if(clientBR.getInstance().createValutazione(val)){
            System.out.println("Valutazione inserita con successo");
            Model.getIstance().getView().getSideBarSelectionItem().set("VisLibro");
        } else {
            System.out.println("Errore nell'inserimento della valutazione");
        }

    }

    private void updateMedia() {
    try {
        Integer style = ScoreStyle.getValue();
        Integer edition = ScoreEdition.getValue();
        Integer content = ScoreContent.getValue();
        Integer pleasentness = ScorePleasentness.getValue();
        Integer originality = ScoreOriginality.getValue();

        if(style != null && edition != null && content != null && pleasentness != null && originality != null) {
            double media = (style + edition + content + pleasentness + originality) / 5.0;
            lbAverage.setText(String.format("%.2f", media));
        }
    } catch (Exception e) {
        // se uno dei campi non è selezionato
        lbAverage.setText("--");
    }
}

    public void setReview(Valutazione valutazione) {

        ScoreStyle.setValue(valutazione.getVotoStile());
        ScoreEdition.setValue(valutazione.getVotoEdizione());
        ScoreContent.setValue(valutazione.getVotoContenuto());
        ScorePleasentness.setValue(valutazione.getVotoGradevolezza());
        ScoreOriginality.setValue(valutazione.getVotoOriginalita());

        TxtStyle.setText(valutazione.getNoteStile());
        TxtEdition.setText(valutazione.getNoteEdizione());
        TxtContent.setText(valutazione.getNoteContenuto());
        TxtPleasentess.setText(valutazione.getNoteGradevolezza());
        TxtOriginality.setText(valutazione.getNoteOriginalita());

        TxtContent.setEditable(false);
        TxtEdition.setEditable(false);
        TxtOriginality.setEditable(false);
        TxtPleasentess.setEditable(false);
        TxtStyle.setEditable(false);

        setReadOnly(ScoreStyle);
        setReadOnly(ScoreEdition);
        setReadOnly(ScoreContent);
        setReadOnly(ScorePleasentness); 
        setReadOnly(ScoreOriginality);

        BtnRestar.setDisable(true);
        BtnRestar.setVisible(false);
        BtnConfirm.setVisible(false);
        BtnConfirm.setDisable(true);
    }

    private void setReadOnly(ChoiceBox<?> choiceBox) {
    choiceBox.getStyleClass().add("read-only");
    choiceBox.setMouseTransparent(true); 
    choiceBox.setFocusTraversable(false);
    
}
}


