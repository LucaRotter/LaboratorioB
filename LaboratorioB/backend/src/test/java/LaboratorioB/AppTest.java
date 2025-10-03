package LaboratorioB;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void testConnessione() {
         try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/labB", "postgres", "Rluca2004");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT 1")) {

            assertTrue(rs.next(), "La query dovrebbe restituire almeno una riga.");
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false, "Errore durante la connessione o l'esecuzione della query.");
        }
    
    }
}
