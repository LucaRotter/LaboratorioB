package LaboratorioB;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
//import javax.sql.*;

public class serverBRImpl extends UnicastRemoteObject implements serverBR {
    
    private static final long serialVersionUID = 1L;
    private Connection conn;

    // Costruttore
    protected serverBRImpl() throws RemoteException, SQLException {
        super();
        conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/labB", "postgres", "Rluca2004");
        System.out.println("Database connected!");
    }
    
    // Implementazione dei metodi definiti nell'interfaccia serverBR

    @Override
    public synchronized int registrazione(Utente user) throws RemoteException {
        int id = user.getId_utente();
        String hashPassword = user.getPassword().hashCode() + ""; // Semplice hash della password

        //creazione query e inserimento nel database
        String query = "INSERT into utenti (nome, cognome, cf, email, password, id_utente) VALUES (?, ?, ?, ?, ?, ?)";
        try{
            //esecuzione query
            conn.prepareStatement(query);
        } catch(Exception e){
            e.printStackTrace();
            return -1; // Ritorna -1 se la registrazione fallisce
        }

        // Se la registrazione ha successo, ritorna l'ID dell'utente
        return id;
    }

    @Override
    public int login(String email, String password) throws RemoteException {
        String hashPassword = password.hashCode() + ""; // Semplice hash della password

        //creazione query e verifica nel database
        String query = "SELECT id_utente FROM utenti WHERE email = ? AND password = ?";
        
        int id = -1; // ID dell'utente, -1 se il login fallisce
        //query per l'id
        return id;
    }

    @Override
    public synchronized Libreria createLibreria(String nome, int id_utente, Libreria libreria) throws RemoteException {
        //inserimento in db
        int libreria_id = 0; //modifica
        String query = "INSERT into librerie (id_utente, nome, id_libro) VALUES (?, ?, ?)";
        //libreria = risultato query;
        return libreria;
    }

    @Override
    public Libreria getLibrerie(int id) throws RemoteException {
        //select da db
        String query = "SELECT * FROM librerie WHERE id_utente = ?";

        Libreria libreria = null;
        //libreria = risultato query
        return libreria;
    }

    @Override
    public synchronized boolean createConsiglio(int id_utente, int id_libro) throws RemoteException {
        int count = 0;
        //count = risultato count per utente e libro
        String query = "SELECT count(*) FROM consigli WHERE id_utente = ? AND id_libro = ?"; //modificare struttura db

        if(count == 3){
            return false;
        }

        //query per inserire il libro selezionato
        return true;
    }

    @Override
    public synchronized boolean createValutazione(int id_utente, int id_libro, Valutazione val) throws RemoteException {
        //query per inserimento nel db
        String query = "INSERT into valutazioni (id_utente, id_libro, altre cose) VALUES (?, ?, ?, ?)";

        return true;
    }

    @Override
    public Libro getLibro(int id_libro) throws RemoteException {
        Libro libro = null;
        
        String query = "SELECT * FROM libri WHERE id_libro = ?";

        return libro;
    }

    @Override
    public List<Valutazione> getValutazione(int id_libro) throws RemoteException {
        String query = "SELECT * FROM valutazioni WHERE id_libro = ?";
        List<Valutazione> valutazioni = null;
        //query
        return valutazioni;
    }

    @Override
    public List<Libro> cercaLibri(String autore, int anno, String titolo) throws RemoteException {
        
        String query = "SELECT * FROM libri WHERE ";
        if(titolo != null){
            query += "titolo = ?";
        } else if(anno != 0){
            query += "autore = ? AND anno = ?";
        } else {
            query += "autore = ?";
        }

        List<Libro> libri = null;

        //query

        return libri;

    }

    @Override
    public Utente getUtente(int id) throws RemoteException {
        Utente user = null;
        
        String query = "SELECT * FROM utenti WHERE id_utente = ?";

        return user;
    }

    public static void main(String[] args) { 
        try {
            LocateRegistry.createRegistry(1099);
            serverBRImpl server = new serverBRImpl();
            Naming.rebind("rmi://localhost:1099/serverBR", server);
            System.out.println("Server BR is running...");
            
        } catch (RemoteException | MalformedURLException | SQLException e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}