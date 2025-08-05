package application;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class visualizzaLibrerieController implements SceneController {
	
	@FXML
	Button backhome;
	
	@FXML
	GridPane gridpane;
	
	@FXML
	ScrollPane scrollPane;
	
	@FXML
	Label namelabel;
	
	@FXML
	Text textlibreries;
	
	@FXML
	Button addButton;
	
	@FXML
	Button deleteButton;
	
	Utente utente ;
	
	static Librerie libreriascelta;
	
	final int numcol= 3;
	
	private static String modalita = "";
	
	private static String modalitains = "addLibreria";
	
	LinkedList<Librerie> librerie = new LinkedList<Librerie>();
	WindowController window;
	
	int row = 1;
	int col = 0;
	
	public void initialize() throws IOException{
		
		utente = Main.utente;
		librerie= utente.getlibreriePersonali();
				
		namelabel.setText( utente.getNome() + " libraries");
	
		row = 1;
		col = 0;
		
		Platform.runLater(() -> {
		
	        for (int i = 0; i < librerie.size(); i++) {
	            try {
	            	
	            	 if (col == numcol) {
		                    row++;
		                    col = 0;
		                }
	            	 
	                FXMLLoader loader = new FXMLLoader(getClass().getResource("Libreria.fxml"));
	                AnchorPane root = loader.load();

	                libreriaController controller = loader.getController();
	                controller.setLibreria(librerie.get(i));
	                controller.setgridPane(gridpane);
	                controller.setwindow(window);

	                gridpane.add(root, col++, row);
	                GridPane.setMargin(root, new Insets(12));

	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        
	    });
		
		
	}
	
	//metodo usato per l'aggiunta di un nuovo Libro nella Libreria
	
	public void onAdd() throws IOException {
		
		//usare una stringa per stabilire la modalità di inserimento quindi se aggiunta di una nuova libreria o di un libro
		if(modalitains.equals("addLibreria")) {
			
			//caricamento della scena per il dialog pane tramite il quale verraì inserito il libro da inserire nella libreria
			 FXMLLoader loaderdialog = new FXMLLoader(getClass().getResource("dialogLibrerie.fxml")); 
			 DialogPane  panedialog = loaderdialog.load();
			 dialogController dialog = loaderdialog.getController();
			 
			 Stage popupStage = new Stage();
			
			 Window principalwindow = gridpane.getScene().getWindow();
			 
			 popupStage.initStyle(StageStyle.TRANSPARENT);
		     popupStage.initOwner(principalwindow);
		     popupStage.initModality(Modality.APPLICATION_MODAL);
		     
		     Scene scene = new Scene(panedialog);
		     scene.setFill(Color.TRANSPARENT);
		     popupStage.setScene(scene);
		   
		     //setup della posizione del dialogpane
		     popupStage.setX(principalwindow.getX() + (( principalwindow.getWidth() - scrollPane.getWidth())) + 15);
		     popupStage.setY(principalwindow.getY() + 238);
		     
		     popupStage.showAndWait();	
		
		     String read = dialog.getText();
		   
		//calcolo dell'indice della cella del gridpane in cui inserire la libreria   
		if(read != "") {
			
		if (col == 3) {
            row++;
            col = 0;
        }
		
		//caricamento dell'oggetto libreria nel gridpane
		 FXMLLoader loader = new FXMLLoader(getClass().getResource("Libreria.fxml"));
         AnchorPane root = loader.load();
         
         libreriaController controller = loader.getController();
       
         Main.utente.creaNuovaLibreria(read);
         controller.setLibreria(librerie.get(0));
         controller.setgridPane(gridpane);
         
		 gridpane.add(root, col++, row);
         GridPane.setMargin(root, new Insets(10));
         
		} else {
	    	 
			//alert per la segnalazione di una stringa vuota inserita
	    	 Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("WARNING");
				alert.setHeaderText("Syntaxerror");
				alert.setContentText("put name for book");
				alert.showAndWait();	
	     }
         
		}else if(modalitains.equals("addLibro")) {
		
		//come prima caricamento del dialogpane (creare un metodo sotto magari visto che è ripetitivo)
		 FXMLLoader loaderdialog = new FXMLLoader(getClass().getResource("dialogLibrerie.fxml")); 
		 DialogPane  panedialog = loaderdialog.load();
		 dialogController dialog = loaderdialog.getController();
		 
		 Stage popupStage = new Stage();
		
		 Window principalwindow = gridpane.getScene().getWindow();
		 
		 popupStage.initStyle(StageStyle.TRANSPARENT);
	     popupStage.initOwner(principalwindow);
	     popupStage.initModality(Modality.APPLICATION_MODAL);
	     
	     Scene scene = new Scene(panedialog);
	     scene.setFill(Color.TRANSPARENT);
	     popupStage.setScene(scene);
	   
	     popupStage.setX(principalwindow.getX() + (( principalwindow.getWidth() - scrollPane.getWidth())) + 15);
	     popupStage.setY(principalwindow.getY() + 238);
	     
	     popupStage.showAndWait();
	     
	     String read = dialog.getText();
	     System.out.println(read);
	     boolean trovato = true;
	     boolean validation = dialog.getValidation();
	     
	     System.out.println("faccio il controllo 1");
	     System.out.println(validation);
	     if(!read.equals("") && validation) {
	     System.out.println("faccio il controllo se esiste");
	     
	     //contralla se il libro esiste e lo inserisce nella linkedlist
	     trovato = libreriascelta.inserisciLibro(utente.getUserid(), read);
	     
	     if(trovato) {
	    	 
	    // se il libro è stato trovato allora viene inizializzato il suo elemento FXML
	     System.out.println("trovato");
	     FXMLLoader loader = new FXMLLoader(getClass().getResource("Libro.fxml"));
		 AnchorPane root = loader.load();
		 libroController controller = loader.getController();
	       
		 //viene settato il nome del libro (poi verra aggiungto il setup dell'immagine)
		 controller.setname(read);
		 
		 //calocolo dell'indice
		 int totale = gridpane.getChildren().size();
		 
		 int colcalc = totale % numcol;
		 int rowcalc = (totale / numcol) +1;
	   
		 //inserimento nel grid
		 gridpane.add(root, colcalc, rowcalc);
		 GridPane.setMargin(root, new Insets(10));
		 controller.setwindow(window);
		 
	     }else {
	    	 
	    	 //se non è stato trovato alcun libro allora viene mandato un messaggio di errore 
	    	    System.out.println("non trovato");
	    	    Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Book not found");
				alert.setHeaderText("error 404 not found");
				alert.setContentText("Insert a valid name");
				alert.showAndWait();
	     }
	     System.out.println("faccio il controllo 2");
	     } else if(read.equals("") && validation) {
	    	 
	    	 //nessuna stringa inserita esce l'alert
	    	 	System.out.println("inserito niente");
	    	 	System.out.print("ciaooo");
	    	 	Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("WARNING");
				alert.setHeaderText("Syntaxerror");
				alert.setContentText("put name for book");
				alert.showAndWait();	
	     }
	     
		}
         
		
	}
	
	//metodo per la rimozione dei libri
	public void onRemove() throws IOException {
	}
	
	//metodo per il ritorno alla home
	public void backtohome() throws IOException {
		
		window.changeWindow("Home.fxml");
		modalitains = "addLibreria";
		
	}
	
	// setup modalita di inserimento 
	public static void setmodalitains(String mod) {
		 modalitains = mod;
			
	}
	
	@Override
	public void setWindowController(WindowController windowController) {
	
		this.window = windowController;
		
	}

	//setup della modalità scelta per come visualizzare la libreria
	public void setmodalità(String string) {
		
		modalita = string;
		
		if(!modalita.equals("vislibrerie")) {
		textlibreries.setText(string);
		textlibreries.setVisible(true);
		
		addButton.setVisible(false);
		deleteButton.setVisible(false);
		
		}
	}

	//libreria seleziona 
	public static void setLibreriascelta(Librerie scelta) {
		 libreriascelta = scelta;
		
	}

	//restituisce la modalità scelta
	public static String getmodalita() {
		
		return modalita;
	}

	

}
