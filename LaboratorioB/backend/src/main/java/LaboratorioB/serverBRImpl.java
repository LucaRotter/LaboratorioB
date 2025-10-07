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

        String hashPassword = user.getPassword().hashCode() + ""; // Semplice hash della password

        // creazione query e inserimento nel database
        String query = "INSERT INTO utenti (nome, cognome, cf, email, password) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getNome());
            ps.setString(2, user.getCognome());
            ps.setString(3, user.getCf());
            ps.setString(4, user.getEmail());
            ps.setString(5, hashPassword);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        return id; // ID generato automaticamente
                    }
                }
            }

            return -1;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int login(String email, String password) throws RemoteException {
        String hashPassword = password.hashCode() + ""; // Semplice hash della password

        // creazione query e verifica nel database
        String query = "SELECT id_utente FROM utenti WHERE email = ? AND password = ?";
        int id = -1; // valore di default (se login fallisce)
        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            // imposta i parametri della query
            ps.setString(1, email);
            ps.setString(2, hashPassword);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    id = rs.getInt("id_utente");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return id;
    }

    @Override
    public Utente getUtente(int id) throws RemoteException {
        Utente user = null;

        String query = "SELECT * FROM utenti WHERE id_utente = ?";
        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            // imposta i parametri della query
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String nome = rs.getString("nome");
                    String cognome = rs.getString("cognome");
                    String cf = rs.getString("cf");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    user = new Utente(nome, cognome, cf, email, password, id);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;// restituisce null se non trova l'utente
    }

    @Override
    public Libro getLibro(int id_libro) throws RemoteException {
        Libro libro = null;
        String query = "SELECT * FROM libri WHERE id_libro = ?";
        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
               // imposta i parametri della query
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String titolo = rs.getString("titolo");
                    String autore = rs.getString("autore");
                    String genere = rs.getString("genere");
                    String editore = rs.getString("editore");
                    String anno = rs.getString("anno");
                    libro = new Libro(titolo, autore, genere, editore, anno, id_libro);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return libro;
    }

    @Override
    public List<Valutazione> getValutazione(int id_libro) throws RemoteException {
        String query = "SELECT * FROM valutazioni WHERE id_libro = ?";
        List<Valutazione> valutazioni = null;
        
        return valutazioni;
    }

    @Override
    public List<Libro> cercaLibri(String autore, int anno, String titolo) throws RemoteException {

        String query = "SELECT * FROM libri WHERE ";
        if (titolo != null) {
            query += "titolo = ?";
        } else if (anno != 0) {
            query += "autore = ? AND anno = ?";
        } else {
            query += "autore = ?";
        }

        List<Libro> libri = null;

        // query

        return libri;

    }

    @Override
    public synchronized Libreria createLibreria(String nome, int id_utente, Libreria libreria) throws RemoteException {
        // inserimento in db
        int libreria_id = 0; // modifica
        String query = "INSERT into librerie (id_utente, nome, id_libro) VALUES (?, ?, ?)";
        // libreria = risultato query;
        return libreria;
    }

    @Override
    public Libreria getLibrerie(int id) throws RemoteException {
        // select da db
        String query = "SELECT * FROM librerie WHERE id_utente = ?";

        Libreria libreria = null;
        // libreria = risultato query
        return libreria;
    }

    @Override
    public synchronized boolean createConsiglio(int id_utente, int id_libro) throws RemoteException {
        int count = 0;
        // count = risultato count per utente e libro
        String query = "SELECT count(*) FROM consigli WHERE id_utente = ? AND id_libro = ?"; // modificare struttura db

        if (count == 3) {
            return false;
        }

        // query per inserire il libro selezionato
        return true;
    }

    @Override
    public synchronized boolean createValutazione(int id_utente, int id_libro, Valutazione val) throws RemoteException {
        // query per inserimento nel db
        String query = "INSERT into valutazioni (id_utente, id_libro, altre cose) VALUES (?, ?, ?, ?)";

        return true;
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