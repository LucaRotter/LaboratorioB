package applicationrob;

import java.io.IOException;
import java.net.URL;
import javafx.geometry.Pos;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javafx.event.ActionEvent;
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
import javafx.scene.control.ScrollPane;
import models.Model;
import javafx.scene.input.KeyCode;

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
    private Button btnRemove;

    @FXML 
    private Button btnSearch;

    @FXML
    private GridPane containerRec;

    @FXML
    private ScrollPane ScrollBooks; 

    private final int LIST_SIZE = 20;

	private IntegerProperty currentIndex = new SimpleIntegerProperty(0);

	private String MODRICERCA = "VISUALIZZA";

    private Libro selectedBook;

    private Libro BookToRemove;

	private List<Libro> Bookserver = new ArrayList<>();

    private List<Libro> reccomendedBooks = new LinkedList<Libro>();

    private int pos;

    private VBox selectedVbox = null;

    private LoadMode currentMode = LoadMode.LAZY;

    @FXML
    public void initialize(URL location, ResourceBundle resources)  {

        init();
        initNavButtons();

       try {
        reccomendedBooks = clientBR.getInstance().getConsiglioUtente(Model.getIstance().getView().getSelectedBook().getId(), TokenSession.getUserId());
       } catch (RemoteException e) {
        
        e.printStackTrace();
       }

       initRecoemmended();

       try {

			putBooks(currentIndex.get());
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}

        Model.getIstance().getView().selectedBookProperty().addListener((obs, oldLibr, newLibr) -> {
        
        if(newLibr!= null){
        
        resetBookList();
        pos= 0;

        containerRec.getChildren().clear();
        
        try {
        reccomendedBooks = clientBR.getInstance().getConsiglioUtente(Model.getIstance().getView().getSelectedBook().getId(), TokenSession.getUserId());
        
        } catch (RemoteException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
       }

       initRecoemmended();
}
    });


        currentIndex.addListener((obs1, oldIndex, newIndex) -> {
			
			try {

				ScrollBooks.setVvalue(0.0);
				grid.getChildren().clear();
				putBooks(newIndex.intValue());

			} catch ( IOException e) {
				
				e.printStackTrace();
			}

		});
    }

        private VBox createPlaceHolder() {
  
            VBox box = new VBox();
            box.setPrefWidth(120);
            box.setPrefHeight(180);
            box.setMaxWidth(Region.USE_PREF_SIZE);
            box.setMinWidth(Region.USE_PREF_SIZE);
           
            box.setAlignment(Pos.CENTER);

            ImageView imageView = new ImageView(new Image(
                getClass().getResourceAsStream("/img/noBook.png")
            ));
            imageView.setFitWidth(122);
            imageView.setFitHeight(122);
            imageView.setPreserveRatio(true);
            imageView.setPickOnBounds(true);

            Label label = new Label("Empty Space");
            label.setPrefWidth(120);
            label.setPrefHeight(36);
            label.setAlignment(Pos.CENTER);         
            label.setMaxWidth(Double.MAX_VALUE); 

            box.getChildren().addAll(imageView, label);

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

        btnLeft.setOnAction(e->{

			onNavButton(e);

		});

		btnCenter.setOnAction(e->{

			onNavButton(e);

		});

		btnRight.setOnAction(e->{

			onNavButton(e);

		});

        btnRemove.setOnAction(e->{

            onRemove();
        });

        searchBar.setOnKeyPressed(e->{

			if(e.getCode() == KeyCode.ENTER){

			try {
				OnResearch();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			}
		});

		btnSearch.setOnAction(e->{
			try {
				OnResearch();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		});

        pos= 0;
    }

    private void putBooks(int index) throws RemoteException, IOException {
		

				if(currentMode == LoadMode.LAZY){	

				if(Bookserver.isEmpty()){

					for(int i = 0; i<3; i++) {
						Bookserver.addAll(clientBR.getInstance().lazyLoadingLibri(""));
					}
				}
				else if((index + 1) * LIST_SIZE > Bookserver.size()) {
            		Bookserver.addAll(clientBR.getInstance().lazyLoadingLibri(""));
        		}
			
		}
			
		if(Bookserver.isEmpty()){
			
			setEmptyResearch("BOOK NOT FOUND");

		}else{

		ScrollBooks.setContent(grid);

		int col= 0;
		int row= 1;
		int i;
		
		for(i=index* LIST_SIZE; i <= index * LIST_SIZE + 19 ; i++) { 
			
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
            
            removeBook(libr,vbox);

            Node old = getNodeByRowColumnIndex(pos, 0, containerRec);
            
            if (old != null) {
                containerRec.getChildren().remove(old);
            }

            btnCancel.setDisable(false);
            btnConfirm.setDisable(false);
            containerRec.add(vbox, 0, pos);
        }
        
    }

    public void OnForward(){

		if((currentIndex.get() + 1) * LIST_SIZE <=  Bookserver.size()){

			int count= currentIndex.get() ;
			count += 1;

			currentIndex.set(count); 
			initNavButtons();
		}
	}

	public void OnBack(){

		if(currentIndex.get() - 1 >= 0){

			int count= currentIndex.get() ;
			count -= 1;

			currentIndex.set(count);
			initNavButtons();
		}
		
	}

    public void setEmptyResearch(String text){

		Label nessunLibro = new Label(text);
		nessunLibro.setStyle("-fx-font-size: 32px; -fx-text-fill: gray;");

		VBox container = new VBox(nessunLibro);
		container.setAlignment(Pos.CENTER);
		container.prefWidthProperty().bind(ScrollBooks.widthProperty());
		container.prefHeightProperty().bind(ScrollBooks.heightProperty());


		ScrollBooks.setContent(container);
	}


    public void onConfirm() {
       
        try {
            clientBR.getInstance().createConsiglio(TokenSession.getUserId(), Model.getIstance().getView().getSelectedBook().getId(),selectedBook.getId());
        } catch (RemoteException e) {
           
            e.printStackTrace();
        }

        pos++;
        btnCancel.setDisable(true);
        btnConfirm.setDisable(true);
        reccomendedBooks.add(selectedBook);
        
    }

    //da rivedere
    public void onClean() {

        // Se non avevo selezionato alcun libro dal grid → fai nulla
    if (selectedBook == null) {
        return;
    }

    // Lo slot dove è stato posizionato il libro selezionato
    int row = pos;

    // Recupero il nodo nella posizione corrente
    Node node = getNodeByRowColumnIndex(row, 0, containerRec);

    if (node != null) {
        containerRec.getChildren().remove(node);
    }

    // Metto il placeholder al posto del libro rimosso
    VBox placeholder = createPlaceHolder();
    containerRec.add(placeholder, 0, row);

    // Reset variabili
    selectedBook = null;
    BookToRemove = null;

    btnCancel.setDisable(true);
    btnConfirm.setDisable(true);
  
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

    public void onRemove(){
        if(BookToRemove != null){

                try {
                    
                    clientBR.getInstance().deleteConsiglio(TokenSession.getUserId(), Model.getIstance().getView().getSelectedBook().getId(), BookToRemove.getId());
                    reccomendedBooks.remove(BookToRemove);
                    containerRec.getChildren().clear();
                    pos=0;
                    btnRemove.setVisible(false);
                    btnRemove.setDisable(true);
                    initRecoemmended();
                } catch (RemoteException e1) {
                   
                    e1.printStackTrace();
                }
                
            }
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

            removeBook(libr,vbox);

            containerRec.add(vbox,0,pos++);

            }else{

            containerRec.add(createPlaceHolder(),0,i);
            }
            
        }
    }

    public void initNavButtons(){

		int index = currentIndex.get();
		boolean update = true;
		boolean controlIndex = (index + 1) * 20 > Bookserver.size();

		btnLeft.getStyleClass().removeAll("SelectedIndex");
		btnCenter.getStyleClass().removeAll("SelectedIndex");
		btnRight.getStyleClass().removeAll("SelectedIndex");

		
		
		if (controlIndex) {

		btnRight.getStyleClass().add("SelectedIndex");
		update=false;

    	}

		if(index == 0 || index == 1 ){

			if(index == 0){
			
			btnRight.getStyleClass().removeAll("SelectedIndex");
			btnLeft.getStyleClass().add("SelectedIndex");

			} else {

			btnRight.getStyleClass().removeAll("SelectedIndex");
			btnCenter.getStyleClass().add("SelectedIndex");
			
			}

			btnLeft.setText( 1 + "");
			btnCenter.setText(2 +"");
			btnRight.setText( 3 + "");

		} else if(update){

			btnCenter.getStyleClass().add("SelectedIndex");

			btnLeft.setText(index + "");
			btnCenter.setText(index + 1 +"");
			btnRight.setText(index + 2 + "");
			
		}
    }
		

	public void updateindexButton(){

		int index = currentIndex.get();
		System.out.println(index + Bookserver.size());

		if ((index + 1) * 20 > Bookserver.size()) {
		
		btnCenter.getStyleClass().removeAll("SelectedIndex");
		btnRight.getStyleClass().add("SelectedIndex");
        return;

		} else if(index == 0){

		btnCenter.getStyleClass().removeAll("SelectedIndex");
		btnLeft.getStyleClass().add("SelectedIndex");

		return;

		}else{

		btnRight.getStyleClass().removeAll("SelectedIndex");
		btnLeft.getStyleClass().removeAll("SelectedIndex");
		btnCenter.getStyleClass().add("SelectedIndex");

		btnLeft.setText(index  + "");
		btnCenter.setText(index + 1+"");
		btnRight.setText(index + 2 + "");
		}
	}

    public void onNavButton(ActionEvent event){
	
		Button btn = (Button) event.getSource();
    	int count = Integer.parseInt(btn.getText());


		if((int) Math.ceil((double) Bookserver.size() / LIST_SIZE) == 1){
			return;
		}

		currentIndex.set(count-1);
		updateindexButton();

	}

    public void removeBook(Libro libr, VBox vbox){

        final Libro thisBook = libr;   
        final VBox vboxFinal = vbox;   

        vboxFinal.setOnMouseClicked(e -> {

        if (!reccomendedBooks.contains(thisBook)) {
        System.out.println("Libro non confermato, non selezionabile: " + thisBook.getTitolo());
        btnRemove.setVisible(false); 
        btnRemove.setDisable(true);
        return;
    }

    if (selectedVbox == vboxFinal) {

        vboxFinal.getStyleClass().remove("selectedRec");

        btnRemove.setVisible(false);
        btnRemove.setDisable(true);

        selectedVbox = null;
        BookToRemove = null;

        return; 
    }
    if (selectedVbox != null) {
        selectedVbox.getStyleClass().remove("selectedRec");
    }

    selectedVbox = vboxFinal;
    BookToRemove = thisBook;

    vboxFinal.getStyleClass().add("selectedRec");

    btnRemove.setVisible(true);
    btnRemove.setDisable(false);

        });
    }

    public void OnResearch() throws IOException {
		
		String writeText = searchBar.getText();

		String title = writeText;

		if(searchBar.getText().isEmpty()){
            currentMode = LoadMode.LAZY;
			grid.getChildren().clear();
			Bookserver.clear();
			currentIndex.set(0);
			initNavButtons();
			putBooks(0);
			return;
		}
		
		grid.getChildren().clear();
		List<Libro> ricercaLibri = clientBR.getInstance().cercaLibri("",0,title, Ricerca.TITOLO);

		Bookserver.clear();
		Bookserver.addAll(ricercaLibri);
        currentMode = LoadMode.FULL;
		currentIndex.set(0);
		initNavButtons();
		putBooks(0);
		
	}

    public void resetBookList(){

        try {
        grid.getChildren().clear();
        Bookserver.clear();
        searchBar.setText("");
        currentMode = LoadMode.LAZY;
        currentIndex.set(0);
        initNavButtons();
        putBooks(0);
        } catch (IOException ex) {
        ex.printStackTrace();
    }
        
    }
}