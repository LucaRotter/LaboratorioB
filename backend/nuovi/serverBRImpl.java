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
    public Libreria createLibreria(String nome, int id) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Libreria getLibrerie(int id) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean consigliaLibro(int user_id, Libro libro) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean createValutazione(int user_id, Libro libro) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Libro getLibro(int id) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Valutazione> getValutazione(int id) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Libro> cercaLibri(String autore, int anno, String titolo) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
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