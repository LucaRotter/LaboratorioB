//decideremo
package LaboratorioB.common.models;
import java.io.Serializable;
/**
 * Modello per rappresentare un consiglio dato da un utente per un libro.
 * Contiene gli ID del libro originale, dell'utente che consiglia e del libro consigliato.
 * @author Laboratorio B
 */
public class Consiglio implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id_libro;
    private int id_utente;
    private int id_libro_consigliato;

    //costruttore
    /**
     * Costruisce un nuovo oggetto Consiglio con gli ID specificati.
     * @param id_libro L'ID del libro originale.
     * @param id_utente L'ID dell'utente che consiglia.
     * @param id_libro_consigliato L'ID del libro consigliato.
     */
    public Consiglio(int id_libro, int id_utente, int id_libro_consigliato) {
        this.id_libro = id_libro;
        this.id_utente = id_utente;
        this.id_libro_consigliato = id_libro_consigliato;
    }

    public int getId_libro() {
        return id_libro;
    }
    public int getId_utente() {
        return id_utente;
    }
    public int getId_libro_consigliato() {
        return id_libro_consigliato;
    }
}