//import java.rmi.*;

/**
 * Un oggetto della classe <code>Libro</code> rappresenta
 * un libro con titolo, autore, categoria, editore e anno.
 * Possiede inoltre un elenco di libri consigliati e un
 * elenco di valutazioni associate al Libro stesso
 * 
 * @author ProgettoLabB
 */

//classe Libro
public class Libro {
    private static final long serialVersionUID = 1L;

    // Attributi della classe Libro
    private String titolo;
	private String autore;
	private String categoria;
	private String editore;
	private String anno;
    private int id;

    //COSTRUTTORI
	/**
	 * Costruisce un oggetto che rappresenta un libro,
	 * in cui il titolo è il primo parametro, l'autore il secondo,
	 * la categoria il terzo, l'editore il quarto e l'anno il quinto
	 * 
	 * @param titolo il titolo
	 * @param autore l'autore
	 * @param categoria la categoria
	 * @param editore l'editore
	 * @param anno l'anno
	 */
    public Libro(String titolo, String autore, String categoria, String editore, String anno) {
        this.titolo = titolo;
        this.autore = autore;
        this.categoria = categoria;
        this.editore = editore;
        this.anno = anno;
    }

    /**
	 * costruisce un oggetto vuoto
	 */
	public Libro() {

	}

    // METODI GETTER
    public String getTitolo() {
        return titolo;
    }
   
    public String getAutore() {
        return autore;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    public String getEditore() {
        return editore;
    }
    
    public String getAnno() {
        return anno;
    }

    public int getId() {
        return id;
    }

    // METODI SETTER (?)

}