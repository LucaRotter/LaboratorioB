package applicationrob;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import LaboratorioB.common.models.Libro;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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
    private VBox containerRec;

    private final int LIST_SIZE = 20;

	private IntegerProperty currentIndex = new SimpleIntegerProperty(0);

	private String MODRICERCA = "VISUALIZZA";

    private Libro selectedBook;

	private List<Libro> Bookserver = new ArrayList<>();

    private List<Libro> reccomendedBooks = new LinkedList<Libro>();

    @FXML
    public void initialize(URL location, ResourceBundle resources)  {

        init();

       try {
        reccomendedBooks = clientBR.getInstance().getConsiglioUtente(Model.getIstance().getView().getSelectedBook().getId(), TokenSession.getUserId());
       } catch (RemoteException e) {
        
        e.printStackTrace();
       }

        for(Libro libr : reccomendedBooks){
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

            containerRec.getChildren().add(vbox);
        }

       try {

			//first loading books
			Bookserver.addAll(clientBR.getInstance().lazyLoadingLibri());
			putBooks(currentIndex.get());
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}

        Model.getIstance().getView().selectedBookProperty().addListener((obs, oldLibr, newLibr) -> {

        containerRec.getChildren().clear();
        
        try {
        reccomendedBooks = clientBR.getInstance().getConsiglioUtente(Model.getIstance().getView().getSelectedBook().getId(), TokenSession.getUserId());
        } catch (RemoteException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
       }

       for(Libro libr : reccomendedBooks){
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

            containerRec.getChildren().add(vbox);
        }

    });

        currentIndex.addListener((obs1, oldIndex, newIndex) -> {
			
			try {

				//lazy loading books when scrolling forward and in view mode
				if(newIndex.intValue() > oldIndex.intValue() && MODRICERCA.equals("VISUALIZZA")){ 
					
					Bookserver.addAll(clientBR.getInstance().lazyLoadingLibri());
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

			if(col == 5) {
				
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
        
        int pos = reccomendedBooks.size();

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
        
        if(containerRec.getChildren().isEmpty() || containerRec.getChildren().size() == pos){

            containerRec.getChildren().add(vbox);

        }else{

            containerRec.getChildren().remove(pos);
            containerRec.getChildren().add(vbox);

        }

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

        reccomendedBooks.add(selectedBook);
        
    }

    //da rivedere
    public void onClean() {
       
       
    }
}