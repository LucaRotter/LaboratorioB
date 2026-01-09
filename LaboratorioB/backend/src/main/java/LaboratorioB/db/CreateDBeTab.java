package LaboratorioB.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Questa classe viene utilizzata per creare il database e le relative tabelle.
 *
 * @author LabotorioB Team
 * @version 1.0
 */
public class CreateDBeTab {

    // Connessione a PostgreSQL senza un database specifico
    private static final String url = "jdbc:postgresql://localhost:5432/";
    private static String user;
    private static String password;

    private static final String createTableLibri = """
                    create table Libri (
            id_libro int primary key,
            titolo varchar(500) not null,
            autore varchar(500) not null,
            anno int,
            genere varchar(500),
            editore varchar(500)
            );

                    """;

    private static final String createTableUtenti = """
                        create table Utenti (
            id_utente INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
            nome varchar(255) not null,
            cognome varchar(255) not null,
            email varchar(255) unique not null,
            password varchar(255) not null,
            cf varchar(16) unique not null
            );
                    """;

    private static final String createTablelibreria = """
                    CREATE TABLE libreria (
            id_libreria INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
            id_utente INT not null references Utenti(id_utente) on delete cascade on update cascade,
            nome VARCHAR(100) NOT NULL
            );
                    """;

    private static final String createTablelibreria_libri = """
                       CREATE TABLE libreria_libri (
            id_libreria INT NOT NULL  REFERENCES libreria(id_libreria) on delete cascade on update cascade,
            id_libro INT NOT NULL REFERENCES libri(id_libro) on delete cascade on update cascade,
            PRIMARY KEY (id_libreria, id_libro)
            );
                    """;

    private static final String createTableLibri_Consigliati = """
                    create table Libri_Consigliati (
            id_libro int references Libri(id_libro) on delete cascade on update cascade,
            id_utente int DEFAULT 1 references Utenti(id_utente) on delete set default on update cascade,
            id_libro_consigliato int references Libri(id_libro),
            primary key (id_libro, id_utente, id_libro_consigliato)
            );
                    """;

    private static final String createTableValutazioni_Libri = """
                    create table Valutazioni_Libri (
            id_libro int references Libri(id_libro) on delete cascade on update cascade,
            id_utente int DEFAULT 1 references Utenti(id_utente) on delete set default on update cascade,
            primary key (id_libro, id_utente),
            edizione varchar(255) not null,
            voto_edizione int check(voto_edizione >= 1 and voto_edizione <= 5) not null,
            stile varchar(255) not null,
            voto_stile int check(voto_stile >= 1 and voto_stile <= 5) not null,
            contenuto varchar(255) not null,
            voto_contenuto int check(voto_contenuto >= 1 and voto_contenuto <= 5) not null,
            gradevolezza varchar(255) not null,
            voto_gradevolezza int check(voto_gradevolezza >= 1 and voto_gradevolezza <= 5) not null,
            originalita varchar(255) not null,
            voto_originalita int check(voto_originalita >= 1 and voto_originalita <= 5) not null,
            voto_medio numeric(3,2)

            );
                    """;

    private static final String createTableLibri_Inviati = """
                    CREATE TABLE libri_inviati (
            client_host VARCHAR(255) NOT NULL,
            id_libro INT NOT NULL,
            PRIMARY KEY (client_host, id_libro),
            FOREIGN KEY (id_libro) REFERENCES libri(id_libro)
            );
                    """;

    /**
     * Punto di ingresso dell'applicazione Book Recommender.
     *
     * @param args Non usati.
     */
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.print("Inserisci username:");
        String user = in.nextLine();
        System.out.print("Inserisci password:");
        String password = in.nextLine();

        createDatabase(user, password); // il database
        createTables(); // Crea le tabelle
    }

    /**
     * Crea il database.
     */
    public static void createDatabase(String user, String password) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connessione riuscita a PostgreSQL senza database specifico.");
        } catch (SQLException e) {
            System.out.println("Errore nella connessione al database.");
            e.printStackTrace();
        }

        String dropDbSql = "DROP DATABASE IF EXISTS \"LaboratorioB\"";
        String createDbSql = "CREATE DATABASE \"LaboratorioB\"";

        try (PreparedStatement stmt = conn.prepareStatement(dropDbSql)) {
            System.out.println("Database 'LaboratorioB' eliminato, se esistente.");
            stmt.executeUpdate();

            PreparedStatement stmtCreate = conn.prepareStatement(createDbSql);
            stmtCreate.executeUpdate();

            System.out.println("Database 'LaboratorioB' creato con successo.");

            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore nella creazione del database.");
            e.printStackTrace();
        }
    }

    /**
     * Crea le tabelle del database.
     */
    public static void createTables() {
        Connection conn = null;
        try {
            conn = DatabaseManager.getConnection();
        } catch (SQLException e) {
            System.out.println("Errore nella connessione al database specifico: " + e.getMessage());
        }

        System.out.println("Connessione riuscita al database LaboratorioB!");
        // Eliminazione delle tabelle se esistono
        String dropTables = "DROP TABLE IF EXISTS Libri, Utenti, librerie, libreria_libri, Libri_Consigliati, Valutazioni_Libri, Libri_Inviati CASCADE;";
        try {
            // DROP TABLE
            try (PreparedStatement stmt = conn.prepareStatement(dropTables)) {
                stmt.executeUpdate();
                System.out.println("Tabelle eliminate, se esistenti.");
            }

            // CREATE TABLE libri
            try (PreparedStatement stmt = conn.prepareStatement(createTableLibri)) {
                stmt.executeUpdate();
            }

            // CREATE TABLE utenti
            try (PreparedStatement stmt = conn.prepareStatement(createTableUtenti)) {
                stmt.executeUpdate();
            }

            // CREATE TABLE libreria
            try (PreparedStatement stmt = conn.prepareStatement(createTablelibreria)) {
                stmt.executeUpdate();
            }

            // CREATE TABLE libreria_libri
            try (PreparedStatement stmt = conn.prepareStatement(createTablelibreria_libri)) {
                stmt.executeUpdate();
            }

            // CREATE TABLE libri_consigliati
            try (PreparedStatement stmt = conn.prepareStatement(createTableLibri_Consigliati)) {
                stmt.executeUpdate();
            }

            // CREATE TABLE valutazioni_libri
            try (PreparedStatement stmt = conn.prepareStatement(createTableValutazioni_Libri)) {
                stmt.executeUpdate();
            }

            // CREATE TABLE libri_inviati
            try (PreparedStatement stmt = conn.prepareStatement(createTableLibri_Inviati)) {
                stmt.executeUpdate();
            }

            System.out.println("Tabelle create con successo nel database dbCM!");

        } catch (SQLException e) {
            System.out.println("Errore nella creazione delle tabelle.");
            e.printStackTrace();
        }
    }
}
