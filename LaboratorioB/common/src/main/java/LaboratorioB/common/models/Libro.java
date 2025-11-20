package LaboratorioB.common.models;
import java.io.Serializable;
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
public class Libro implements Serializable {
    private static final long serialVersionUID = 1L;

    // Attributi della classe Libro
    private String titolo;
	private String autore;
	private String genere;
	private String editore;
	private int anno;
    private int id_libro;

    //COSTRUTTORI
	/**
	 * Costruisce un oggetto che rappresenta un libro,
	 * in cui il titolo è il primo parametro, l'autore il secondo,
	 * la categoria il terzo, l'editore il quarto e l'anno il quinto
	 * 
	 * @param titolo il titolo
	 * @param autore l'autore
	 * @param genere la categoria
	 * @param editore l'editore
	 * @param anno l'anno
     * @param id_libro l'id
	 */
    public Libro(String titolo, String autore, String genere, String editore, int anno, int id_libro) {
        this.titolo = titolo;
        this.autore = autore;
        this.genere = genere;
        this.editore = editore;
        this.anno = anno;
        this.id_libro = id_libro;
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
    
    public String getGenere() {
        return genere;
    }
    
    public String getEditore() {
        return editore;
    }
    
    public int getAnno() {
        return anno;
    }

    public int getId() {
        return id_libro;
    }


}