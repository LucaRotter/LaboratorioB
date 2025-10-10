package LaboratorioB.common.models;
import java.io.Serializable;
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
public class Libreria implements Serializable{
    
    // Attributi della classe Libreria
	private static final long serialVersionUID = 1L;
    private List<Libro> libreria = new ArrayList<>();
	private String nome;
	private int id_utente;
	private int id_libreria;


    //COSTRUTTORI
	/**
	 * costruisce un oggetto Librerie attraverso i parametri,
	 * il primo corrisponde allo Userid, il secondo al nome della
	 * libreria mentre il terso è l'elenco dei libri
	 * 
	 * @param id_utente UserId
	 * @param nome nome della libreria
	 * @param libreria elenco di libri
	 * @param id_libreria id della libreria
	 */
	public Libreria(int id_utente, String nome, ArrayList<Libro> libreria, int id_libreria) {

		this.id_utente= id_utente;
		this.nome = nome;
		this.libreria = libreria;
		this.id_libreria = id_libreria;

	}
	
	/**
	 * costruisce un oggetto Librerie vuoto
	 */
	public Libreria() {

	}

    //METODI

    //metodi getter
    public String getNomeLibreria() {
        return nome;
    }
    public int getIdUtente() {
        return id_utente;
    }
    public List<Libro> getLibreria() {
        return libreria;
    }
	public int getIdLibreria(){
		return id_libreria;
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