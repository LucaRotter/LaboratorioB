package applicationrob;

/**
 * Classe per la gestione della sessione dell'utente tramite token.
 * Contiene metodi per ottenere, impostare e verificare l'ID utente della sessione corrente.
 * @author Laboratorio B
 * @param userId ID dell'utente attualmente non loggato nella sessione.
 */

public class TokenSession {
    private static int userId = -1;

    /**
     * Metodo per ottenere l'ID dell'utente della sessione corrente.
     */
    public static int getUserId() {
        return userId;
    }

    /**
     * Metodo per impostare l'ID dell'utente della sessione corrente.
     */
    public static void setUserId(int id) {
        userId = id;
    }

    /**
     * Metodo per verificare se esiste una sessione utente attiva.
     */
    public static boolean checkTkSession() {
        if (userId == -1) {
            return false;
        }
        return true;
    }
    
}
