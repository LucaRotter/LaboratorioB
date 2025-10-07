//decideremo
package LaboratorioB;

public class Consiglio {
    private int id_libro;
    private int id_utente;
    private int id_consiglio;

    public Consiglio(int id_libro, int id_utente, int id_consiglio) {
        this.id_libro = id_libro;
        this.id_utente = id_utente;
        this.id_consiglio = id_consiglio;
    }

    public int getId_libro() {
        return id_libro;
    }
    public int getId_utente() {
        return id_utente;
    }
    public int getId_consiglio() {
        return id_consiglio;
    }
}