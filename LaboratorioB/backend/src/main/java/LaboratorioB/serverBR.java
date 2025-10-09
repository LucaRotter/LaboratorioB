package LaboratorioB;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface serverBR extends Remote {
    /* elenco dei metodi che il server deve implementare */
    public int registrazione(Utente user) throws RemoteException;
    public int login(String email, String password) throws RemoteException;
    public Utente getUtente(int id) throws RemoteException;
    public Libro getLibro(int id) throws RemoteException;
    public List<Valutazione> getValutazione(int id) throws RemoteException;
    public List<Libro> getConsiglio(int id_libro) throws RemoteException;
    public double getVotoMedio(int id_libro) throws RemoteException;
    public List<Libro> cercaLibri(String autore, int anno, String titolo, Ricerca scelta) throws RemoteException;
    //public boolean modificaPassword(String email, String password) throws RemoteException;
    //public int modificaEmail(String email, String password, String nuovaEmail) throws RemoteException;
    //public int modificaNome(String email, String password, String nuovoNome) throws RemoteException;
    //public int modificaCognome(String email, String password, String nuovoCognome) throws RemoteException;
    public Libreria createLibreria(String nome, int user_id) throws RemoteException;
    public Libreria addLibroLibreria(int id_libro, int id_libreria) throws RemoteException;
    public Libreria removeLibroLibreria(int id_libro, int id_libreria) throws RemoteException;
    public Libreria getLibreria(int id_libreria) throws RemoteException;
    public List<Libreria> getLibrerie(int id_utente) throws RemoteException;
    public boolean createConsiglio(int user_id, int libro_id) throws RemoteException;
    public boolean createValutazione(int user_id, int libro_id, Valutazione val) throws RemoteException;
    
    
}