package LaboratorioB.common.interfacce;

// importazioni necessarie
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import LaboratorioB.common.models.Libreria;
import LaboratorioB.common.models.Libro;
import LaboratorioB.common.models.Ricerca;
import LaboratorioB.common.models.Utente;
import LaboratorioB.common.models.Valutazione;

/**
 * Interfaccia remota per il server BR.
 * Definisce i metodi che possono essere invocati dai client remoti.
 * @author Laboratorio B
 */
public interface serverBR extends Remote {
    /* elenco dei metodi che il server deve implementare */
    public int registrazione(Utente user) throws RemoteException;
    public int login(String email, String password) throws RemoteException;
    public Utente getUtente(int id) throws RemoteException;
    public List<Libro> lazyLoadingLibri(String genere_ricerca) throws RemoteException;
    public Libro getLibro(int id) throws RemoteException;
    public List<Valutazione> getValutazione(int id) throws RemoteException;
    public List<Valutazione> getValutazioniUtente(int id_utente, int id_libro) throws RemoteException;
    public List<Libro> getConsiglio(int id_libro) throws RemoteException;
    public List<Libro> getConsiglioUtente(int id_libro, int id_utente) throws RemoteException;
    public double getVotoMedio(int id_libro) throws RemoteException;
    public List<Libro> cercaLibri(String autore, int anno, String titolo, Ricerca scelta) throws RemoteException;
    public Libreria createLibreria(String nome, int user_id) throws RemoteException;
    public boolean deleteLibreria(int id_libreria) throws RemoteException;
    public Libreria addLibroLibreria(int id_libro, int id_libreria) throws RemoteException;
    public Libreria removeLibroLibreria(int id_libro, int id_libreria) throws RemoteException;
    public Libreria getLibreria(int id_libreria) throws RemoteException;
    public List<Libreria> getLibrerie(int id_utente) throws RemoteException;
    public boolean createConsiglio(int user_id, int libro_id, int id_consiglio) throws RemoteException;
    public boolean createValutazione( Valutazione val) throws RemoteException;
    public boolean deleteConsiglio(int id_utente, int id_libro, int id_consiglio) throws RemoteException;
    
}
