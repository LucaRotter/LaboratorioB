package LaboratorioB.db;

//import necessari

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Classe per la gestione del pool di connessioni al database utilizzando HikariCP.
 * Fornisce metodi per ottenere connessioni e chiudere il pool.
 * 
 * @author Grassi, Alessandro, 757784, VA
 * @author Kastratovic, Aleksandar, 752468, VA
 * @author Rotter, Luca Giorgio, 757780, VA
 * @author Davide, Bilora, 757011, VA
 * @version 1.0
 */
public class DatabaseManager {

    private static HikariDataSource dataSource;
    
    /**
     * Ottiene una connessione dal pool di connessioni.
     * @return Una connessione al database.
     * @throws SQLException Se si verifica un errore durante l'ottenimento della connessione.
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void autenticazione(String username, String password){
         try {
            // Configurazione di HikariCP per PostgreSQL
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:postgresql://localhost:5432/LaboratorioB");
            config.setUsername(username);
            config.setPassword(password); 
            config.setDriverClassName("org.postgresql.Driver");

            dataSource = new HikariDataSource(config);

            System.out.println("Pool HikariCP inizializzato con successo!");

        } catch (Exception e) {
            System.err.println("Errore durante l'inizializzazione del pool HikariCP:");
            e.printStackTrace();
        }

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
