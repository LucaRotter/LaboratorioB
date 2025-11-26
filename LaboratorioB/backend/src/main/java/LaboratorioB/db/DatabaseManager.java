package LaboratorioB.db;

//import necessari

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Classe per la gestione del pool di connessioni al database utilizzando HikariCP.
 * Fornisce metodi per ottenere connessioni e chiudere il pool.
 * @version 1.0
 * @since 2024-06-10
 * @author Laboratorio B
 */
public class DatabaseManager {

    private static HikariDataSource dataSource;
    //private static Scanner in = new Scanner(System.in);

    static {
        try {
            // Configurazione di HikariCP per PostgreSQL
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:postgresql://localhost:5432/LaboratorioB");
            System.out.println("Inserisci username e password del database:");
            //String username = in.nextLine();
            config.setUsername("postgres");
            //String pwd = in.nextLine();
            config.setPassword("@Aleks13082002"); 
            config.setDriverClassName("org.postgresql.Driver");

            // Parametri opzionali del pool
            /*config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setIdleTimeout(30000);
            config.setConnectionTimeout(30000);
            config.setLeakDetectionThreshold(60000);*/

            dataSource = new HikariDataSource(config);

            System.out.println("Pool HikariCP inizializzato con successo!");

        } catch (Exception e) {
            System.err.println("Errore durante l'inizializzazione del pool HikariCP:");
            e.printStackTrace();
        }
    }

    /**
     * Ottiene una connessione dal pool di connessioni.
     * @return Una connessione al database.
     * @throws SQLException Se si verifica un errore durante l'ottenimento della connessione.
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Chiude il pool di connessioni HikariCP.
     */
    public static void shutdown() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            System.out.println("Pool HikariCP chiuso correttamente.");
        }
    }
}
