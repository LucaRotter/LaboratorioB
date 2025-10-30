package LaboratorioB.db;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseManager {

    private static HikariDataSource dataSource;
    //private static Scanner in = new Scanner(System.in);

    static {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:postgresql://localhost:5432/labB");
            System.out.println("Inserisci username e password del database:");
            //String username = in.nextLine();
            config.setUsername("postgres");
            //String pwd = in.nextLine();
            config.setPassword("Rluca2004"); 
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

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void shutdown() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            System.out.println("Pool HikariCP chiuso correttamente.");
        }
    }
}
