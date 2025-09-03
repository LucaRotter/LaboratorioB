import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
//import java.sql.*;
//import javax.sql.*;

public class serverBRImpl extends UnicastRemoteObject implements serverBR {
    
    private static final long serialVersionUID = 1L;

    // Costruttore
    protected serverBRImpl() throws RemoteException {
        super();
    }
    
    // Implementazione dei metodi definiti nell'interfaccia serverBR

    @Override
    public int registrazione(Utente user) throws RemoteException {
        int id = user.getUserId();
        String hashPassword = user.getPassword().hashCode() + ""; // Semplice hash della password

        //creazione query e inserimento nel database
        String query = "INSERT into utenti (nome, cognome, codFiscale, email, password, userid) VALUES (?, ?, ?, ?, ?, ?)";

        // Se la registrazione ha successo, ritorna l'ID dell'utente
        return id;
    }

    @Override
    public int login(String email, String password) throws RemoteException {
        String hashPassword = password.hashCode() + ""; // Semplice hash della password

        //creazione query e verifica nel database
        String query = "SELECT userid FROM utenti WHERE email = ? AND password = ?";
        
        int id = -1; // ID dell'utente, -1 se il login fallisce
        //query per l'id
        return id;
    }

    @Override
    public Libreria createLibreria(String nome, int user_id, Libreria libreria) throws RemoteException {
        //inserimento in db
        int libreria_id = 0; //modifica
        String query = "INSERT into librerie (user_id, nome, libro_id) VALUES (?, ?, ?)";
        //libreria = risultato query;
        return libreria;
    }

    @Override
    public Libreria getLibrerie(int id) throws RemoteException {
        //select da db
        String query = "SELECT * FROM librerie WHERE user_id = ?";

        Libreria libreria = null;
        //libreria = risultato query
        return libreria;
    }

    @Override
    public boolean consigliaLibro(int user_id, int libro_id) throws RemoteException {
        int count = 0;
        //count = risultato count per utente e libro
        String query = "SELECT count(*) FROM consigli WHERE user_id = ? AND libro_id = ?"; //modificare struttura db

        if(count == 3){
            return false;
        }

        //query per inserire il libro selezionato
        return true;
    }

    @Override
    public boolean createValutazione(int user_id, int libro_id, Valutazione val) throws RemoteException {
        //query per inserimento nel db
        String query = "INSERT into valutazioni (user_id, libro_id, altre cose) VALUES (?, ?, ?, ?)";

        return true;
    }

    @Override
    public Libro getLibro(int id) throws RemoteException {
        Libro libro = null;
        
        String query = "SELECT * FROM libri WHERE libro_id = ?";

        return libro;
    }

    @Override
    public List<Valutazione> getValutazione(int id) throws RemoteException {
        String query = "SELECT * FROM valutazioni WHERE libro_id = ?";
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

    public static void main(String[] args){ 
        try {
            serverBRImpl server = new serverBRImpl();
                Naming.rebind("rmi://localhost:1099/serverBR", server);
            System.out.println("Server BR is running...");
            
        } catch (RemoteException | MalformedURLException e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}