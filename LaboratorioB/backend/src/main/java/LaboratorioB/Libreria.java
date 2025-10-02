package LaboratorioB;

import java.util.ArrayList;
import java.util.List;
/**
 * Un oggetto della classe <code>Librerie</code> rappresenta
 * una collezione di libri che personale di un utente, a cui è
 * associato il nome della libreria scelto dall'utente.
 * I libri contenuti in questa classe possono essere recensiti 
 * attraverso valutazioni o libri consigliati assocati
 * 
 * @author ProgettoLabA
 */

//classe Libreria
public class Libreria{
    
    // Attributi della classe Libreria
    private List<Libro> libreria = new ArrayList<>();
	private String nomeLibreria;
	private String nomeUtente;

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
	public Libreria(String nomeUtente, String nomeLibreria, ArrayList<Libro> libreria) {

		this.nomeUtente = nomeUtente;
		this.nomeLibreria = nomeLibreria;
		this.libreria = libreria;

	}
	
	/**
	 * costruisce un oggetto Librerie vuoto
	 */
	public Libreria() {

	}

    //METODI

    //metodi getter
    public String getNomeLibreria() {
        return nomeLibreria;
    }
    public String getNomeUtente() {
        return nomeUtente;
    }
    public List<Libro> getLibreria() {
        return libreria;
    }

    /**
	 * permette all'utente di inserire un libro in una
	 * libreria personale scelta, aggiungendolo 
	 * all'elenco di libri presenti
	 * 
	 * @param libro libro da aggiungere
	 */
    // aggiunge un libro alla libreria
    public void addLibro(Libro libro) {
        libreria.add(libro);
    }

     /**
	 * permette all'utente di eliminare un libro da una
	 * libreria personale scelta, rimuovendolo 
	 * dall'elenco di libri presenti
	 * 
	 * @param libro libro da eliminare
	 */
    // rimuove un libro dalla libreria
    public void removeLibro(Libro libro) {
        libreria.remove(libro);
    }
}