package LaboratorioB;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import LaboratorioB.common.interfacce.serverBR;
import LaboratorioB.common.models.Libreria;
import LaboratorioB.common.models.Libro;
import LaboratorioB.common.models.Ricerca;
import LaboratorioB.common.models.Utente;
import LaboratorioB.common.models.Valutazione;
import LaboratorioB.db.DatabaseManager;
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
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

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
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

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
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

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
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            // imposta i parametri della query
            ps.setInt(1, id_libro);
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
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id_libro);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int voto_stile = rs.getInt("voto_stile");
                    int voto_edizione = rs.getInt("voto_edizione");
                    int voto_contenuto = rs.getInt("voto_contenuto");
                    int voto_gradevolezza = rs.getInt("voto_gradevole");
                    int voto_originalita = rs.getInt("voto_originalita");
                    double voto_medio = rs.getDouble("voto_medio");
                    String stile = rs.getString("stile");
                    String edizione = rs.getString("edizione");
                    String contenuto = rs.getString("contenuto");
                    String gradevole = rs.getString("gradevole");
                    String originalita = rs.getString("originalita");
                    int id_utente = rs.getInt("id_utente");
                    Valutazione val = new Valutazione(voto_stile, voto_edizione, voto_contenuto,
                            voto_gradevolezza, voto_originalita, voto_medio, stile, edizione, contenuto,
                            gradevole, originalita, id_utente, id_libro);
                    valutazioni.add(val);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return valutazioni;
    }

    @Override
    public List<Libro> getConsiglio(int id_libro) throws RemoteException {
        String query = "SELECT * FROM libri_consigliati WHERE id_libro = ?";
        List<Libro> consigli = null;
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id_libro);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id_libro_consigliato");
                    Libro libro = getLibro(id);
                    consigli.add(libro);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consigli;
    }

    @Override
    public double getVotoMedio(int id_libro) throws RemoteException {
        String query = "SELECT AVG(voto_medio) FROM valutazioni WHERE id_libro = ?";
        double voto_medio = 0.0;
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id_libro);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    voto_medio = rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return voto_medio;
    }

    @Override
    public List<Libro> cercaLibri(String autore, int anno, String titolo, Ricerca scelta) throws RemoteException {
        List<Libro> libri = new ArrayList<>();
        String query = "SELECT * FROM libri WHERE ";

        if (scelta == Ricerca.TITOLO) {
            query += "titolo ILIKE ?";
        } else if (scelta == Ricerca.ANNO) {
            query += "autore ILIKE ? AND anno = ?";
        } else if (scelta == Ricerca.AUTORE) {
            query += "autore ILIKE ?";
        } else {
            return null;
        }

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            if (scelta == Ricerca.TITOLO) {
                ps.setString(1, "%" + titolo + "%"); // ricerca sottostringa nel titolo
            } else if (scelta == Ricerca.ANNO) {
                ps.setString(1, autore);
                ps.setInt(2, anno);
            } else if (scelta == Ricerca.AUTORE) {
                ps.setString(1, "%" + autore + "%"); // ricerca sottostringa nell'autore
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id_libro = rs.getInt("id_libro");
                    String titolo_libro = rs.getString("titolo");
                    String autore_libro = rs.getString("autore");
                    String genere_libro = rs.getString("genere");
                    String editore_libro = rs.getString("editore");
                    String anno_libro = rs.getString("anno");

                    Libro libro = new Libro(
                            titolo_libro,
                            autore_libro,
                            genere_libro,
                            editore_libro,
                            anno_libro,
                            id_libro);
                    libri.add(libro);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return libri;
    }

    @Override
    public synchronized Libreria createLibreria(String nome, int id_utente) throws RemoteException {
        // inserimento in db

        String query = "INSERT into librerie(id_utente, nome) VALUES (?, ?)";
        Libreria libreria = null;
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, id_utente);
            ps.setString(2, nome);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        libreria = new Libreria(id_utente, nome, null, id);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // libreria = risultato query;
        return libreria;
    }

    @Override
    public Libreria addLibroLibreria(int id_libro, int id_libreria) throws RemoteException {
        String query = "INSERT into libri_librerie(id_libro, id_libreria) VALUES (?, ?)";
        Libreria libreria = null;
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id_libro);
            ps.setInt(2, id_libreria);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                libreria = getLibreria(id_libreria);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libreria;

    }

    public Libreria removeLibroLibreria(int id_libro, int id_libreria) throws RemoteException {
        String query = "DELETE FROM libri_librerie WHERE id_libro = ? AND id_libreria = ?";
        Libreria libreria = null;
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id_libro);
            ps.setInt(2, id_libreria);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                libreria = getLibreria(id_libreria);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return libreria;
    }

    public Libreria getLibreria(int id_libreria) throws RemoteException {

        String query = "SELECT * FROM librerie L JOIN libreria_libri M on L.id_libreria = M.id_libreria WHERE L.id_libreria = ?";
        Libreria libreria = null;
        List<Libro> libri = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id_libreria);
            try (ResultSet rs = ps.executeQuery()) {
                int id_utente = -1;
                String nome = null;

                while (rs.next()) {
                    if (libreria == null) {
                        id_utente = rs.getInt("id_utente");
                        nome = rs.getString("nome");
                    }
                    int id_libro = rs.getInt("id_libro");
                    if (id_libro != 0) {
                        Libro libro = getLibro(id_libro);
                        libri.add(libro);
                    }
                }

                if (id_utente != -1) {
                    libreria = new Libreria(id_utente, nome, (ArrayList<Libro>) libri, id_libreria);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return libreria;

    }

    @Override
    public List<Libreria> getLibrerie(int id_utente) throws RemoteException {
        // select da db
        String query = "SELECT id_libreria FROM librerie WHERE id_utente = ?";

        List<Libreria> libreria = new ArrayList<Libreria>();
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id_utente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id_libreria = rs.getInt("id_libreria");
                    Libreria lib = getLibreria(id_libreria);
                    libreria.add(lib);

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libreria;
    }

    @Override
    public synchronized boolean createConsiglio(int id_utente, int id_libro, int id_consiglio) throws RemoteException {
        int count = 0;
        // count = risultato count per utente e libro
        String query_count = "SELECT count(*) FROM Libri_consigliati WHERE id_utente = ? AND id_libro = ?"; // modificare struttura db

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(query_count)) {
            ps.setInt(1, id_utente);
            ps.setInt(2, id_libro);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        if (count == 3) {
            return false;
        }

        String query = "INSERT into Libri_consigliati (id_utente, id_libro, id_libro_consigliato) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(query_count)) {
            ps.setInt(1, id_utente);
            ps.setInt(2, id_libro);
            ps.setInt(3, id_consiglio);
            int rows = ps.executeUpdate();
            if (rows <= 0) {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public synchronized boolean createValutazione(int id_utente, int id_libro, Valutazione val) throws RemoteException {
        // query per inserimento nel db
        String query = "INSERT into valutazioni (id_utente, id_libro, edizione, voto_edizione, stile, voto_stile, contenuto, voto_contenuto, gradevolezza, voto_gradevolezza, originalita, voto_otiginalita, voto_medio) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ?)";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id_utente);
            ps.setInt(2, id_libro);
            ps.setString(3, val.getNoteEdizione());
            ps.setInt(4, val.getVotoEdizione());
            ps.setString(5, val.getNoteStile());
            ps.setInt(6, val.getVotoStile());
            ps.setString(7, val.getNoteContenuto());
            ps.setInt(8, val.getVotoContenuto());
            ps.setString(9, val.getNoteGradevolezza());
            ps.setInt(10, val.getVotoGradevolezza());
            ps.setString(11, val.getNoteOriginalita());
            ps.setInt(12, val.getVotoOriginalita());
            ps.setDouble(13, val.getVotoMedio());

            int rows = ps.executeUpdate();
            if (rows <= 0) {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            serverBRImpl server = new serverBRImpl();
            Naming.rebind("rmi://localhost:1099/serverBR", server);
            System.out.println("Server BR is running...");

            //questo server solo con l'avvio con Maven
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                System.err.println("Server interrupted: " + e.getMessage());
                e.printStackTrace();
            }

        } catch (RemoteException | MalformedURLException | SQLException e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
