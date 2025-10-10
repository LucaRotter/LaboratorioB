//decideremo
package LaboratorioB.common.models;
import java.io.Serializable;
public class Consiglio implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id_libro;
    private int id_utente;
    private int id_libro_consigliato;

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