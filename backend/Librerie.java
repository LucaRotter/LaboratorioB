package application;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Un oggetto della classe <code>Librerie</code> rappresenta
 * una collezione di libri che personale di un utente, a cui è
 * associato il nome della libreria scelto dall'utente.
 * I libri contenuti in questa classe possono essere recensiti 
 * attraverso valutazioni o libri consigliati assocati
 * 
 * @author ProgettoLabA
 */

public class Librerie {
	
	
	//CAMPI
	private LinkedList<Libro> libreria = new LinkedList<>();
	private String nomeLibreria;
	private String nomeUtente;
	private LinkedList<ValutazioniLibri> valutazioni = new LinkedList<>();  

	
	//COSTRUTTORI
	/**
	 * costruisce un oggetto Librerie attraverso i parametri,
	 * il primo corrisponde allo Userid, il secondo al nome della
	 * libreria mentre il terso è l'elenco dei libri
	 * 
	 * @param nomeUtente UserId
	 * @param nomeLibreria nome della libreria
	 * @param libreria elenco di libri
	 */
	public Librerie(String nomeUtente , String nomeLibreria,LinkedList<Libro> libreria) {

		this.nomeUtente = nomeUtente;
		this.nomeLibreria = nomeLibreria;
		this.libreria = libreria;

	}
	
	/**
	 * costruisce un oggetto Librerie vuoto
	 */
	public Librerie() {

	}
	
	
	//METODI
	
	//aggiunge una valutazione all'elenco
	public void setValutazioni(ValutazioniLibri v) {

		valutazioni.add(v);
	}
	
	//aggiungta consigliati
	/**
	 * aggiunge un oggetto di tipo LibriConsigliati al libro
	 * scelto presente in libreria, prendendo come parametro
	 * l'oggetto da aggiungere
	 * 
	 * @param libri oggetto da aggiungere
	 */
	public void setConsigliLibri(LibriConsigliati libri) {

		for(Libro libro : libreria) {

			if(libro.getTitolo().equals(libri.getitoloLibro())) {

				libro.setLibroConsigliato(libri);
			}
		}

	}

	public String getNomeLibreria() {
		return nomeLibreria;
	}

	public String getNomeUtente() {
		return nomeUtente;
	}


	public LinkedList<Libro> getLibreria(){
		return libreria;
	}
	
	//stampa l'elenco dei libri
	public String stampaLibreria() {

		String tmp = " - ";

		for(Libro libro :libreria) {
			tmp += libro.getTitolo() + " - ";
		}
		return tmp;
	}

	//metodo per inserire il libro
	/**
	 * permette all'utente di inserire un libro in una
	 * libreria personale scelta, aggiungendolo 
	 * all'elenco di libri presenti
	 * 
	 * @param UserID userid dell'utente
	 * @throws IOException
	 */
	public boolean inserisciLibro(String UserID,String inserito) throws IOException {

			boolean aggiunto = true;
			Libro nuovo = null;
			
			 System.out.println(inserito);
			nuovo = Libro.cercaLibro(inserito);

			if(nuovo != null) {

				aggiungiLibro(UserID,nuovo);
				return aggiunto;

			}else {

				System.out.println("il libro inserito non è presente\n\n");
				return aggiunto = false;
			}
	}
	
	//aggiunge il nuovo libro scelto alla lista
	/**
	 * si occupa della modifica in memoria della libreria, trovando
	 * prima l'indice della libreria, poi aggiungendo il nuovo libro
	 * passato come parametro
	 * 
	 * @param userID userid dell'utente
	 * @param libro libro da inserie
	 * @throws IOException
	 */
	public void aggiungiLibro(String userID, Libro libro) throws IOException{

		int count = 0;
		Libro libronuovo = null;
		LinkedList<Libro> librerialibri = null;

		//ricerca l'indice della libreria dove aggiungere il libro
		for(Librerie libreria: gestoreDati.DatiLibrerie ) {

			count++;
			if(libreria.getNomeLibreria().equals(nomeLibreria) && libreria.getNomeUtente().equals(userID)) {
				librerialibri = libreria.getLibreria();
				break;
			}
		}

		//controlla se nella lista di libri della libreria selezionata non è presente il libro


		if(!librerialibri.contains(libro)) {	

			libronuovo = libro;
			gestoreDati.aggiuntaLibroLibrerie(count, libronuovo);
			System.out.println("Libro aggiunto con succcesso\n\n");

		}else {

			System.out.println("il libro " + libro.getTitolo() + " è già presente, quindi non è stato inserito nuovamente\n\n");
		}
	}

	@Override
	public String toString(){

		String tmp = nomeUtente + ";" + nomeLibreria + "\n";	

		//aggiunto default per creare librerie vuote
		if(libreria.size() > 0) {
			
		for(Libro libro : libreria){
			tmp += libro.getTitolo() + ";";
		}

		}else {
			
			tmp += "Metropolis: A Novel";
		}
		
		return tmp;

	}
	
	//metodo per la valutazione dei libri
	/**
	 * permette di far scegliere all'utente se inserire
	 * valutazioni o libri consigliati da associare ad
	 * un libro selezionato
	 * @param commenti 
	 * @param voti 
	 * 
	 * @throws IOException
	 */
	public void libroDaValutare(Libro libro,String modalita, int[] voti, String[] commenti) throws IOException {

		//qua decidiamo se inserire una valutazione oppure libro consigliato
		
		Libro lib = libro;
		
		if(lib != null ) {
			
		
			if(modalita.equals("aggiungi valutazioni")) {
				
				//controllo se è gia presente valutazione associata al libro
				for(ValutazioniLibri valutazione: gestoreDati.DatiValutazioni) {

					if(valutazione.getitoloLibro().equals(libro.getTitolo()) && valutazione.getNomeUtente().equals(nomeUtente)) {
						System.out.println("esiste gia una valutazione associata al tuo id utente\n\n");
						return;
					}

				}

				ValutazioniLibri valutazione = new ValutazioniLibri();
				valutazione = valutazione.inserisciValutazioneLibro(nomeUtente, lib.getTitolo(), voti, commenti);

				for(Libro libro1 : gestoreDati.DatiLibro) {
					if(valutazione.getitoloLibro().equals(libro.getTitolo())){
						libro1.setElencoValutazioni(valutazione);
						
					}
				}
				System.out.println("valutazione aggiunta con successo\n\n");
/* da rimuovere anche quello sopra appena viene sistemato (ci vuole poco)
			}else {

				//for each per controllo e creare nuovo libri consigliati
				//scorre libri libreria prendere 	
				
				
				Libro libricino = Libro.cercaLibro(lib.getTitolo());

				if(libricino != null) {

					if(!libricino.equals(lib)) {

						for(Libro libro2 :libreria) {

							if(libro2.getTitolo().equals(lib.getTitolo())) {

								libro2.inserisciConsiglio(libricino, nomeUtente);  // -> chiama inserisci libro per associare il consiglio ad un libro in particolare nella libreria

								break;
							}
						}

					}else {

						System.out.println("un libro non può essere associato a se stesso\n\n");
					}
					
				}else {

					System.out.println("il libro selezionato non esiste\n\n");
				}
			}*/
			
		}else {
			
			System.out.println("il libro selezionato non esiste\n\n");
			
		}
		}
	}
}
