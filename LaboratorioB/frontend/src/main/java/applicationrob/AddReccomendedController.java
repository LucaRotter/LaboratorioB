package applicationrob;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import LaboratorioB.common.models.Libro;
import LaboratorioB.common.models.Ricerca;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import models.Model;

public class AddReccomendedController implements Initializable {

    @FXML
    private TextField searchBar;
    
    @FXML
    private GridPane grid;

    @FXML
    private Button btnLeft;

    @FXML
	private Button btnCenter;

	@FXML
	private Button btnRight;

	@FXML
	private Button btnBack;

    @FXML
    private Button btnForward;

    @FXML
    private Button btnConfirm;

    @FXML
    private Button btnCancel;

    @FXML
    private GridPane containerRec;

    private final int LIST_SIZE = 20;

	private IntegerProperty currentIndex = new SimpleIntegerProperty(0);

	private String MODRICERCA = "VISUALIZZA";

    private Libro selectedBook;

	private List<Libro> Bookserver = new ArrayList<>();

    private List<Libro> reccomendedBooks = new LinkedList<Libro>();

    private int pos;

    @FXML
    public void initialize(URL location, ResourceBundle resources)  {

        pos= 0;
        init();

       try {
        reccomendedBooks = clientBR.getInstance().getConsiglioUtente(Model.getIstance().getView().getSelectedBook().getId(), TokenSession.getUserId());
       } catch (RemoteException e) {
        
        e.printStackTrace();
       }

       initRecoemmended();

       try {

			//first loading books
			Bookserver.addAll(clientBR.getInstance().cercaLibri("", 0, "", Ricerca.TITOLO));
			putBooks(currentIndex.get());
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}

        Model.getIstance().getView().selectedBookProperty().addListener((obs, oldLibr, newLibr) -> {

        pos= 0;

        containerRec.getChildren().clear();
        
        try {
        reccomendedBooks = clientBR.getInstance().getConsiglioUtente(Model.getIstance().getView().getSelectedBook().getId(), TokenSession.getUserId());
        } catch (RemoteException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
       }

       initRecoemmended();

    });

        currentIndex.addListener((obs1, oldIndex, newIndex) -> {
			
			try {

				//lazy loading books when scrolling forward and in view mode
				if(newIndex.intValue() > oldIndex.intValue() && MODRICERCA.equals("VISUALIZZA")){ 
					
					Bookserver.addAll(clientBR.getInstance().lazyLoadingLibri(null));
				}

				if((newIndex.intValue() + 2) * LIST_SIZE > Bookserver.size() && MODRICERCA.equals("RICERCA")) {

				} else {

					btnLeft.setText(newIndex.intValue() + 1 + "");
					btnCenter.setText(newIndex.intValue() + 2 + "");
					btnRight.setText(newIndex.intValue() + 3 + "");
					
				}

				grid.getChildren().clear();
				putBooks(newIndex.intValue());

			} catch ( IOException e) {
				
				e.printStackTrace();
			}
		});
    }

        private VBox createPlaceHolder() {
            // VBox principale
            VBox box = new VBox();
            box.setPrefWidth(120);
            box.setPrefHeight(180);
            box.setMaxWidth(Region.USE_PREF_SIZE);
            box.setMinWidth(Region.USE_PREF_SIZE);
           

            // Immagine placeholder
            ImageView imageView = new ImageView(new Image(
                getClass().getResourceAsStream("/img/noBook.png")
            ));
            imageView.setFitWidth(122);
            imageView.setFitHeight(122);
            imageView.setPreserveRatio(true);
            imageView.setPickOnBounds(true);

            // Testo "Empty Space"
            Label label = new Label("Empty Space");
            label.setPrefWidth(120);
            label.setPrefHeight(36);

            // Aggiunta dei nodi
            box.getChildren().addAll(imageView, label);

            // Mano come cursor, come nel tuo FXML
            box.setCursor(Cursor.HAND);

            return box;
        }

    public void init(){

		btnForward.setOnAction(e->{

			OnForward();
		});

		btnBack.setOnAction(e->{

			OnBack();
		});

        btnConfirm.setOnAction(e->{

            onConfirm();
        });

        btnCancel.setOnAction(e->{

            onClean();
        });

    }

    private void putBooks(int indice) throws RemoteException, IOException {

		int col= 0;
		int row= 1;
		int i;
		
		for(i=indice* LIST_SIZE; i <= indice * LIST_SIZE + 19 ; i++) { 
			
			if(i >= Bookserver.size()) {
				break;
			}

			if(col == 4) {
				
				row += 1;
				col = 0;

			}
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("BookEl.fxml"));
			VBox vbox = loader.load();
			
			BookController bookController= loader.getController();
			Libro libr = Bookserver.get(i);
			bookController.setLabels(libr.getAutore(), libr.getTitolo());
			
			vbox.setOnMouseClicked(e->{

                selectedBookHandler(libr);
			});
			
			vbox.setPrefSize(120, 180);
			grid.add(vbox, col++, row);
			
		}
	}

    private void selectedBookHandler(Libro libr) {

        if(pos < 3){

        selectedBook = libr;

            VBox vbox = null;

            FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("BookEl.fxml"));

			try {
                 vbox = loader.load();
            } catch (IOException e) {
                
                e.printStackTrace();
            }
			
			BookController bookController= loader.getController();
			bookController.setLabels(libr.getAutore(), libr.getTitolo());

            Node old = getNodeByRowColumnIndex(pos, 0, containerRec);
            if (old != null) {
                containerRec.getChildren().remove(old);
            }
            containerRec.add(vbox, 0, pos);
        }
        
    }

    public void OnForward(){

		if((currentIndex.get() + 1) * LIST_SIZE <=  Bookserver.size()){

			int count= currentIndex.get() ;
			count += 1;

			currentIndex.set(count); 

		}
	}

	public void OnBack(){

		if(currentIndex.get() - 1 >= 0){

			int count= currentIndex.get() ;
			count -= 1;

			currentIndex.set(count);
		}
		
	}

    public void onConfirm() {
       
        try {
            clientBR.getInstance().createConsiglio(TokenSession.getUserId(), Model.getIstance().getView().getSelectedBook().getId(),selectedBook.getId());
        } catch (RemoteException e) {
           
            e.printStackTrace();
        }

        pos++;
        reccomendedBooks.add(selectedBook);
        
    }

    //da rivedere
    public void onClean() {
       
       
    }

    private Node getNodeByRowColumnIndex(int row, int column, GridPane gridPane) {
    for (Node node : gridPane.getChildren()) {
        Integer rowIndex = GridPane.getRowIndex(node);
        Integer colIndex = GridPane.getColumnIndex(node);

        if ((rowIndex == null ? 0 : rowIndex) == row &&
            (colIndex == null ? 0 : colIndex) == column) {
            return node;
        }
    }
    return null;
    }

    public void initRecoemmended(){
        for(int i = 0; i < 3; i++){

            if(i< reccomendedBooks.size()){

            Libro libr = reccomendedBooks.get(i);
            VBox vbox = null;

            FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("BookEl.fxml"));

			try {
                 vbox = loader.load();
            } catch (IOException e) {
                
                e.printStackTrace();
            }
			
			BookController bookController= loader.getController();
			bookController.setLabels(libr.getAutore(), libr.getTitolo());

            containerRec.add(vbox,0,pos++);

            }else{

            containerRec.add(createPlaceHolder(),0,i);
            }
            
        }
    }
}