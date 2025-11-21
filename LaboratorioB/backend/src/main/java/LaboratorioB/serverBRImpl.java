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
import java.util.LinkedList;
import java.util.List;

import LaboratorioB.common.interfacce.serverBR;
import LaboratorioB.common.models.Libreria;
import LaboratorioB.common.models.Libro;
import LaboratorioB.common.models.Ricerca;
import LaboratorioB.common.models.Utente;
import LaboratorioB.common.models.Valutazione;
import LaboratorioB.db.DatabaseManager;

/**
 * Un oggetto della classe <code>serverBRImpl</code> rappresenta
 * una API che permette la comunicazione tra client e server BR.
 * Mette a disposizione metodi per la registrazione, login,
 * gestione utenti, libri, librerie, valutazioni e consigli.
 * 
 * @author ProgettoLabA
 */

public class serverBRImpl extends UnicastRemoteObject implements serverBR {

    private static final long serialVersionUID = 1L;
    private Connection conn;

    // Costruttore
    /**
     * Costruisce un oggetto serverBRImpl e stabilisce la connessione
     * al database PostgreSQL.
     */
    protected serverBRImpl() throws RemoteException, SQLException {
        super();
        conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/LaboratorioB", "postgres", "@Aleks13082002");
        System.out.println("Database connected!");
    }

    // Implementazione dei metodi definiti nell'interfaccia serverBR
    /** 
     * Registra un nuovo utente nel sistema.
     * @param user L'oggetto Utente contenente le informazioni dell'utente da registrare.
     * @return L'ID univoco dell'utente registrato, o -1 in caso di errore.
     * @throws RemoteException In caso di errore di comunicazione remota.
     */
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

    /**
     * Effettua il login di un utente verificando le credenziali.
     * @param email L'email dell'utente.
     * @param password La password dell'utente.
     * @return L'ID univoco dell'utente se il login ha successo, altrimenti -1.
     * @throws RemoteException In caso di errore di comunicazione remota. 
     */

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

    /** 
     * Recupera le informazioni di un utente dato il suo ID.
     * @param id L'ID univoco dell'utente.
     * @return L'oggetto Utente contenente le informazioni dell'utente, o null se non trovato.
     * @throws RemoteException In caso di errore di comunicazione remota.
     */
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

    /** 
     * Implementa il meccanismo di lazy loading per i libri.
     * Restituisce una lista di libri non ancora inviati al client.
     * @param id_libro L'ID del libro di riferimento (non utilizzato in questa implementazione).
     * @return Una lista di oggetti Libro non ancora inviati al client.
     * @throws RemoteException In caso di errore di comunicazione remota.
     */
    @Override
    public List<Libro> lazyLoadingLibri() throws RemoteException {

        final int LIMIT = 20;
        List<Libro> libri = new ArrayList<>();

        // Identifica il client RMI
        String clientHost;
        try {
            clientHost = java.rmi.server.RemoteServer.getClientHost();
        } catch (java.rmi.server.ServerNotActiveException e) {
            clientHost = "unknown";
        }

        String query = """
        SELECT l.id_libro, l.titolo, l.autore, l.genere, l.editore, l.anno
        FROM libri l
        LEFT JOIN libri_inviati li
            ON l.id_libro = li.id_libro AND li.client_host = ?
        WHERE li.id_libro IS NULL
        ORDER BY l.id_libro
        LIMIT ?
    """;

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, clientHost);
            ps.setInt(2, LIMIT);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id_libro");
                    String titolo = rs.getString("titolo");
                    String autore = rs.getString("autore");
                    String genere = rs.getString("genere");
                    String editore = rs.getString("editore");
                    int anno = rs.getInt("anno");
                    libri.add(new Libro(titolo, autore, genere, editore, anno, id));
                }
            }

            // Se non ci sono nuovi libri, resetta (cioè cancella i record e riparti da capo)
            if (libri.isEmpty()) {
                resetLibriInviati(conn, clientHost);
                // Dopo il reset, ricarica una volta
                return lazyLoadingLibri();
            }

            // Registra nel DB i libri inviati a questo client
            registraLibriInviati(conn, clientHost, libri);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return libri;
    }

    /**
     * Registra i libri inviati a un client specifico nel database.
     * @param conn La connessione al database.
     * @param clientHost L'host del client.
     * @param libri La lista di libri inviati.
     * @throws SQLException In caso di errore SQL.
     */
    private void registraLibriInviati(Connection conn, String clientHost, List<Libro> libri) throws SQLException {
        String insert = "INSERT INTO libri_inviati (client_host, id_libro) VALUES (?, ?) ON CONFLICT DO NOTHING";
        try (PreparedStatement ps = conn.prepareStatement(insert)) {
            for (Libro libro : libri) {
                ps.setString(1, clientHost);
                ps.setInt(2, libro.getId());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    /**
     * Resetta i libri inviati a un client specifico cancellando i record dal database.
     * @param conn La connessione al database.
     * @param clientHost L'host del client.
     * @throws SQLException In caso di errore SQL.
     */
    private void resetLibriInviati(Connection conn, String clientHost) throws SQLException {
        String delete = "DELETE FROM libri_inviati WHERE client_host = ?";
        try (PreparedStatement ps = conn.prepareStatement(delete)) {
            ps.setString(1, clientHost);
            ps.executeUpdate();
        }
    }

    /** 
     * Recupera le informazioni di un libro dato il suo ID.
     * @param id_libro L'ID univoco del libro.
     * @return L'oggetto Libro contenente le informazioni del libro, o null se non trovato.
     * @throws RemoteException In caso di errore di comunicazione remota.
     */
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
                    int anno = rs.getInt("anno");
                    libro = new Libro(titolo, autore, genere, editore, anno, id_libro);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return libro;
    }

    /**
     * Recupera le valutazioni di un libro dato il suo ID.
     * @param id_libro L'ID univoco del libro.
     * @return Una lista di oggetti Valutazione associati al libro.
     * @throws RemoteException In caso di errore di comunicazione remota.
     */
    @Override
    public List<Valutazione> getValutazione(int id_libro) throws RemoteException {
        String query = "SELECT * FROM Valutazioni_Libri WHERE id_libro = ?";
        List<Valutazione> valutazioni = new LinkedList<Valutazione>();
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id_libro);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int voto_stile = rs.getInt("voto_stile");
                    int voto_edizione = rs.getInt("voto_edizione");
                    int voto_contenuto = rs.getInt("voto_contenuto");
                    int voto_gradevolezza = rs.getInt("voto_gradevolezza");
                    int voto_originalita = rs.getInt("voto_originalita");
                    double voto_medio = rs.getDouble("voto_medio");
                    String stile = rs.getString("stile");
                    String edizione = rs.getString("edizione");
                    String contenuto = rs.getString("contenuto");
                    String gradevole = rs.getString("gradevolezza");
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

    /**
     * Recupera le valutazioni di un libro fatte da un utente specifico.
     * @param id_utente L'ID univoco dell'utente.
     * @param id_libro L'ID univoco del libro.
     * @return Una lista di oggetti Valutazione associati all'utente e al libro.
     * @throws RemoteException In caso di errore di comunicazione remota.
     */
    @Override
    public List<Valutazione> getValutazioniUtente(int id_utente, int id_libro) throws RemoteException{
        String query = "SELECT * FROM Valutazioni_Libri WHERE id_utente = ? AND id_libro = ?";
        List<Valutazione> valutazioni = new LinkedList<Valutazione>();
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id_utente);
            ps.setInt(2, id_libro);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int voto_stile = rs.getInt("voto_stile");
                    int voto_edizione = rs.getInt("voto_edizione");
                    int voto_contenuto = rs.getInt("voto_contenuto");
                    int voto_gradevolezza = rs.getInt("voto_gradevolezza");
                    int voto_originalita = rs.getInt("voto_originalita");
                    double voto_medio = rs.getDouble("voto_medio");
                    String stile = rs.getString("stile");
                    String edizione = rs.getString("edizione");
                    String contenuto = rs.getString("contenuto");
                    String gradevole = rs.getString("gradevolezza");
                    String originalita = rs.getString("originalita");
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

    /**
     * Recupera i libri consigliati per un dato libro.
     * @param id_libro L'ID univoco del libro di riferimento.
     * @return Una lista di oggetti Libro consigliati.
     * @throws RemoteException In caso di errore di comunicazione remota.
     */
    @Override
    public List<Libro> getConsiglio(int id_libro) throws RemoteException {
        String query = "SELECT * FROM libri_consigliati WHERE id_libro = ?";
        List<Libro> consigli = new LinkedList<Libro>();
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

    /**
     * Recupera i libri consigliati per un dato libro da un utente specifico.
     * @param id_libro L'ID univoco del libro di riferimento.
     * @param id_utente L'ID univoco dell'utente.
     * @return Una lista di oggetti Libro consigliati dall'utente.
     * @throws RemoteException In caso di errore di comunicazione remota.
     */
    @Override
    public List<Libro> getConsiglioUtente(int id_libro, int id_utente) throws RemoteException{
        String query = "SELECT * FROM libri_consigliati WHERE id_libro = ? AND id_utente = ?";
        List<Libro> consigli = new LinkedList<Libro>();
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id_libro);
            ps.setInt(2, id_utente);
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

    /**
     * Recupera il voto medio di un libro dato il suo ID.
     * @param id_libro L'ID univoco del libro.
     * @return Il voto medio del libro.
     * @throws RemoteException In caso di errore di comunicazione remota.
     */
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

    /**
     * Cerca libri in base a autore, anno o titolo.
     * @param autore L'autore del libro.
     * @param anno L'anno di pubblicazione del libro.
     * @param titolo Il titolo del libro.
     * @param scelta Il criterio di ricerca (AUTORE, ANNO, TITOLO).
     * @return Una lista di oggetti Libro che corrispondono ai criteri di ricerca.
     * @throws RemoteException In caso di errore di comunicazione remota.
     */
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
                    int anno_libro = rs.getInt("anno");

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

    /**
     * Crea una nuova libreria per un utente.
     * @param nome Il nome della libreria.
     * @param id_utente L'ID univoco dell'utente proprietario della libreria.
     * @return L'oggetto Libreria appena creata.
     * @throws RemoteException In caso di errore di comunicazione remota.
     */
    @Override
    public synchronized Libreria createLibreria(String nome, int id_utente) throws RemoteException {
        // inserimento in db
        String query = "INSERT into libreria(id_utente, nome) VALUES (?, ?)";

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

    /**
     * Elimina una libreria dato il suo ID.
     * @param id_libreria L'ID univoco della libreria da eliminare.
     * @return true se la libreria è stata eliminata con successo, false altrimenti.
     * @throws RemoteException In caso di errore di comunicazione remota.
     */
    @Override
    public boolean deleteLibreria(int id_libreria) throws RemoteException{
        String query = "DELETE FROM libreria WHERE id_libreria = ?";
        boolean deleted = false;
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id_libreria);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                deleted = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleted;
    }

    /**
     * Aggiunge un libro a una libreria specifica.
     * @param id_libro L'ID univoco del libro da aggiungere.
     * @param id_libreria L'ID univoco della libreria a cui aggiungere il libro.
     * @return L'oggetto Libreria aggiornato dopo l'aggiunta del libro.
     * @throws RemoteException In caso di errore di comunicazione remota.
     */
    @Override
    public Libreria addLibroLibreria(int id_libro, int id_libreria) throws RemoteException {
        String query = "INSERT into libreria_libri(id_libro, id_libreria) VALUES (?, ?)";
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
    /**
     * Rimuove un libro da una libreria specifica.
     * @param id_libro L'ID univoco del libro da rimuovere.
     * @param id_libreria L'ID univoco della libreria da cui rimuovere il libro.
     * @return L'oggetto Libreria aggiornato dopo la rimozione del libro.
     * @throws RemoteException In caso di errore di comunicazione remota.
     */
    public Libreria removeLibroLibreria(int id_libro, int id_libreria) throws RemoteException {
        String query = "DELETE FROM libreria_libri WHERE id_libro = ? AND id_libreria = ?";
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

    /**
     * Recupera le informazioni di una libreria dato il suo ID.
     * @param id_libreria L'ID univoco della libreria.
     * @return L'oggetto Libreria contenente le informazioni della libreria, o null se non trovata.
     * @throws RemoteException In caso di errore di comunicazione remota.
     */
    public Libreria getLibreria(int id_libreria) throws RemoteException {

        String query = "SELECT * FROM libreria L LEFT JOIN libreria_libri M on L.id_libreria = M.id_libreria WHERE L.id_libreria = ?";
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
            } catch (SQLException e) {
                e.printStackTrace();
        }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return libreria;

    }

    /**
     * Recupera tutte le librerie di un utente specifico.
     * @param id_utente L'ID univoco dell'utente.
     * @return Una lista di oggetti Libreria associati all'utente.
     * @throws RemoteException In caso di errore di comunicazione remota.
     */
    @Override
    public List<Libreria> getLibrerie(int id_utente) throws RemoteException {
        // select da db
        String query = "SELECT id_libreria FROM libreria WHERE id_utente = ?";

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

    /**
     * Crea un consiglio associato ad un libro da parte di un utente.
     * @param id_utente L'ID univoco dell'utente che consiglia
     * @param id_libro L'ID univoco del libro di riferimento
     * @param id_consiglio L'ID univoco del libro consigliato
     * @return true se il consiglio è stato creato con successo, false altrimenti.
     * @throws RemoteException In caso di errore di comunicazione remota.
     */
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

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
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

    /**
     * Crea una valutazione per un libro da parte di un utente.
     * @param val L'oggetto Valutazione contenente le informazioni della valutazione da creare.
     * @return true se la valutazione è stata creata con successo, false altrimenti.
     * @throws RemoteException In caso di errore di comunicazione remota.
     */
    @Override
    public synchronized boolean createValutazione(Valutazione val) throws RemoteException {
        // query per inserimento nel db
        String query = "INSERT into Valutazioni_Libri (id_utente, id_libro, edizione, voto_edizione, stile, voto_stile, contenuto, voto_contenuto, gradevolezza, voto_gradevolezza, originalita, voto_originalita, voto_medio) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ?)";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, val.getIdLibro());
            ps.setInt(2, val.getIdUtente());
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

    //eliminazione consiglio
    /**
     * Elimina un consiglio dato l'ID dell'utente, del libro e del consiglio.
     * @param id_utente L'ID univoco dell'utente che ha dato
     * @param id_libro L'ID univoco del libro di riferimento
     * @param id_consiglio L'ID univoco del libro consigliato da eliminare
     * @return true se il consiglio è stato eliminato con successo, false altrimenti.
     * @throws RemoteException In caso di errore di comunicazione remota.
    */
    @Override
    public synchronized boolean deleteConsiglio(int id_utente, int id_libro, int id_consiglio) throws RemoteException {
        // delete da db
        String query = "DELETE FROM Libri_consigliati WHERE id_utente = ? AND id_libro = ? AND id_libro_consigliato = ?";
        boolean deleted = false;
        
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id_utente);
            ps.setInt(2, id_libro);
            ps.setInt(3, id_consiglio);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                deleted = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleted;
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
