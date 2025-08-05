package application;

import java.io.IOException;
import java.util.*;

/**
 * Un ogetto della classe <code>Utente</code> rappresenta
 * un utente che si è registrato inserendo nome, cognome,
 * codice fiscale, mail, password e nome utente univoco.
 * Quando un utente crea una nuova libreria, essa verrà
 * aggiunta alla collezione, quindi alla lista di librerie
 * personali
 * 
 * @author ProgettoLabA
 */

public class Utente {
	
	//CAMPI
	private String nome, cognome;
	private String codFiscale;
	private String email;
	private String password;
	private String userid;
	private LinkedList<Librerie> libreriePersonali = new LinkedList<>();
	
	//CAMPO STATICO
	private static boolean logged = false; //forse da togliere
	
	
	//COSTRUTTORI
	
	/**
	 * Costruisce un oggetto Utente con i parametri in ingresso, 
	 * il primo rappresenta il nome, il secondo il cognome, il terzo
	 * il codice fiscale, il quarto la mail, il quinto lo UserId e 
	 * l'ultimo la password
	 * 
	 * @param nome il nome
	 * @param cognome il cognome
	 * @param codFiscale il codice fiscale
	 * @param email la mail
	 * @param userid lo UserId
	 * @param password la password
	 */
	 public Utente(String nome, String cognome, String codFiscale, String email, String userid, String password ) {

		this.nome = nome;
		this.cognome = cognome;
		this.codFiscale = codFiscale;
		this.email = email;
		this.userid= userid;
		this.password = password;

	}
	
	/**
	 * Costruisce un oggetto vuoto
	 */
	public Utente() {

	}
	
	
	//METODI
	
	/**
	 * aggiunge una libreria alla lista di librerie personali, 
	 * prendendo come parametro la libreria da aggiungere
	 * 
	 * @param libreria libreria da aggiungere
	 */
	public void setlibreriePersonali(Librerie libreria) {
		libreriePersonali.add(libreria);
	}

	public String getPassword() {

		return password;

	}

	public String getUserid() {
		return userid;
	}

	public String getNome() {
		return nome;

	}

	public LinkedList<Librerie> getlibreriePersonali(){

		return libreriePersonali;

	}
	
	//effettua il login
	/**
	 * serve all'utente per effettuare il login
	 * in modo da accedere alle funzioni disponibili
	 * solo se si è già fatto l'accesso, restituisce
	 * l'oggetto utente che corrisponde all'utente
	 * che effettua il login
	 * 
	 * @return l'utente che ha effettuato il login
	 */
	public Utente login(String id, String Password) {

		boolean trovato = false;	
		Utente utenteTrovato = null;
		
		
		for(Utente utente: gestoreDati.DatiUtente) {

			if(utente.getUserid().equals(id) && utente.getPassword().equals(Password)) {

				utenteTrovato = utente;
				trovato = true;
				
			}
		}

		if(trovato ) {
			System.out.println("login effettuato con successo");
			logged = true;

		} else {

			System.out.println("ID o password errati riprovare");
		}	

		return utenteTrovato;
	}
	
	//metodo per la registrazione
	/**
	 * permette ad un nuovo utente di effettuare la
	 * registrazione e restituisce il nuovo oggetto
	 * utente appena creato
	 * 
	 * @return l'utente che ha effettuato la registrazione
	 */
	public Utente registrazione(String nome, String cognome, String cf, String email, String userID, String Password) {	

		Utente utente = new Utente(nome, cognome, cf, email, userID, Password);
		gestoreDati.DatiUtente.add(utente);
		
		try {
			gestoreDati.RegistrazioneUtenti(utente);
		} catch (IOException e) {
			e.printStackTrace();
		}
		logged = true;
		return utente;
	}

	//metodo utilizzato per controllare la correttezza del codice fiscale
	/**
	 * prende come parametro in input una stringa e
	 * controlla che sia un codice fiscale che rispetti
	 * le condizioni generali, restituisce true se è corretto,
	 * false altrimenti
	 * 
	 * @param cf codice fiscale
	 * @return correttezza del codice
	 */
	public boolean ControlloCodFiscale(String cf) {

		cf.toUpperCase().trim();
		boolean corretto = true;

		if(cf.length() != 16) {
			corretto = false;

		} else {
			for(int i = 0; i < cf.length(); i++){
				char c = cf.charAt(i);
				if(i < 6 && !Character.isLetter(c)){
					corretto = false;
					break;
				}
				if((i >= 6 && i<8 || i >= 9 && i<11 || i >= 12 && i<15 ) && !Character.isDigit(c)){
					corretto = false;
					break;
				}
				if((i== 8 || i == 11 || i == 15) && !Character.isLetter(c)){
					corretto = false;
					break;
				}
			}
		}

		if(!corretto)
			System.out.println("IL TUO CODICE FISCALE NON è CORRETTO  ");

		return corretto;

	}
	
	
	//controlla che lo UserId non sia già stato utilizzato
	/**
	 * controlla l'unicità dello UserId in input, in 
	 * modo che non ne venga memorizzato uno
	 * già in uso da un altro utente
	 * 
	 * @param userID UserId
	 * @return unicità dello UserId
	 */
	public boolean ControlloUserId(String userID) {

		boolean presente = false;

		for(Utente utente: gestoreDati.DatiUtente){    

			if(userID.equals(utente.getUserid())){

				presente = true;
				break;
			}
		}
		return presente;
	}
	
	//AGGIUNGERE CONTROLLO FORMATO USERID
	
	//crea una nuova libreria
	/**
	 * permette di creare una nuova libreria ad un utente
	 * che abbia effettuato il login, controllando che
	 * il nome della nuova libreria non corrisponda ad
	 * una che ha già creato e permettendo di inserire
	 * libri a scelta tra quelli disponibili
	 */
	
	
	//questo va fatto dopo aver creato la base di dati cosi non modifichiamo il codice del file excel per salvare nuove librerie che diventa un casino
	public void creaNuovaLibreria(String nomeLib){

		//while(nomeLib.length()>20); controllo nome librerie

		if(controlloNomeLibreria(nomeLib)) {
			
			LinkedList<Libro> nuovaLista = new LinkedList<>();
			/*Libro libro = new Libro();
			String titoloLibro="";
			boolean continuare = true; inutile con la gui
			int n = 0;*/

			/*do{
				//System.out.println("inserie il titolo del libro da aggiungere a " + nomeLib); togliere e mettere un text field

				libro = Libro.cercaLibro(titoloLibro);//magari dividere sta parte dalla creazione della librerie permettendo di creare una libreria vuota
				
				if(libro != null){

					if(!nuovaLista.contains(libro)){
						nuovaLista.add(libro);
						System.out.println("libro aggiunto con successo\n");

					}else{

						System.out.println("il libro fa già parte della libreria\n");
					}

				}else{

					System.out.println("il libro selezionato non esiste\n");
				}

				System.out.println("digitare 0 per finire l'inserimento, un qualsiasi altro numero per continuare ad inserie");
				
				boolean input;
				
				do{
					try {
						
						input = true;
						n = scanner.nextInt();
					}
					catch(InputMismatchException e) {
						
						System.err.println("è necessario inserire un numero intero");
						input = false;
						scanner.nextLine();
					}
					
				}while(!input);
				
				scanner.nextLine();
				System.out.println("");

				if(n==0)
					continuare = false;

			}while(continuare);*/

			//if(!nuovaLista.isEmpty()){quindi tolgiere controllo se la libreria è vuota
				
				Librerie lib = new Librerie(getUserid(), nomeLib, nuovaLista);
				gestoreDati.DatiLibrerie.add(lib);
				this.setlibreriePersonali(lib);
				
				try {
					gestoreDati.aggiuntaLibreria(lib);//lasciare aggiunta magari con un default per evitare problemi col csv
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("libreria creata con successo, ora puoi accedere alla tua libreria e consultare i libri inseriti\n\n");
		}
			/*}else{

				System.out.println("non si può creare una libreria vuota\n\n");
			}
		}else {
			System.out.println("non si puo' nominare una libreria con lo stesso nome di una gia' esistente\n\n");
		}*/

	}
	
	//controllo del nome della libreria
	/**
	 * controlla che il nome della libreria, passato come
	 * parametro in input, non sia già stato utilizzato
	 * per un'altra libreria associata all'utente
	 * 
	 * @param s nome della libreria
	 * @return validità del nome
	 */
	
	private boolean controlloNomeLibreria(String s) {
		for(Librerie l : libreriePersonali) {
			System.out.println(l.getNomeLibreria());
			
			if(s.equals(l.getNomeLibreria())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {

		return nome + ";" + cognome + ";" + codFiscale + ";" + email + ";" + userid + ";" + password;

	}

	//metodi per verificare se esiste un utente loggato
	public boolean isLogged() {
		return logged;

	}

	public void logout() {
		logged = false;

	}
	
	
	//si puo anche togliere volendo
	
	//metodo per scegliere e mostrare una libreria e in caso effettuare modifiche su di essa
	/**
	 * permette di visualizzare le proprie librerie e modificarle
	 * aggiungendo libri quando il parametro in input è true
	 * e aggiungendo delle recensioni quando è false
	 * 
	 * @param scelta aggiunta o recensione libro
	 * @throws IOException
	 */
	/*public void mostraLibrerie(boolean scelta) throws IOException {

		//aggiungi in gestore dati la parte in cui le librerie vengono aggiunte agli utenti

		//prendere userid dell'utente e controllare dove corrisponde nel file 
		//poi prendere la lista di libri associata e stampe i nomi facendo scegliere quale visualizzare
		Scanner scanner = new Scanner(System.in);
		Utente utente = new Utente(); 
		if(!libreriePersonali.isEmpty()) {

			int i = 0;

			System.out.println("selezionare la libreria");

			for(Librerie libreria: libreriePersonali) {
				System.out.println(i++ + " > "+ libreria.getNomeLibreria());
			}
			
			boolean input;
			
			do {

				do{
					try {
						
						input = true;
						i = scanner.nextInt();
					}
					catch(InputMismatchException e) {
						
						System.err.println("è necessario inserire un numero intero");
						input = false;
						scanner.nextLine();
					}
					
				}while(!input);

			}while(i<0 || i > libreriePersonali.size()-1);
			
			System.out.println("");

			Librerie librerie = libreriePersonali.get(i);
			System.out.println(librerie.getNomeLibreria() + " > " + librerie.stampaLibreria());
			
			System.out.println("");
			
			if(scelta) {

				librerie.inserisciLibro(userid);

			}else {
				librerie.libroDaValutare();
				System.out.println("\n");
			}
			
		}else {

			System.out.println("non sono presenti librerie\n\n");
		}
	}*/
}
