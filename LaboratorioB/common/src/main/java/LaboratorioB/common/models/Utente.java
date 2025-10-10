package LaboratorioB.common.models;
import java.io.Serializable;
/**
 * Un ogetto della classe <code>Utente</code> rappresenta
 * un utente che si è registrato inserendo nome, cognome,
 * codice fiscale, mail, password e nome utente univoco.
 * Quando un utente crea una nuova libreria, essa verrà
 * aggiunta alla collezione, quindi alla lista di librerie
 * personali
 * 
 * @author ProgettoLabA
 */

public class Utente implements Serializable {
    private static final long serialVersionUID = 1L;

    // Attributi dell'utente    
    private String nome, cognome;
	private String cf;
	private String email;
	private String password;
	private int id_utente;


    // Costruttore della classe Utente
    /**
	 * Costruisce un oggetto Utente con i parametri in ingresso, 
	 * il primo rappresenta il nome, il secondo il cognome, il terzo
	 * il codice fiscale, il quarto la mail, il quinto lo UserId e 
	 * l'ultimo la password
	 * 
	 * @param nome il nome
	 * @param cognome il cognome
	 * @param cf il codice fiscale
	 * @param email la mail
	 * @param id_utente lo UserId
	 * @param password la password
	 */
    public Utente(String nome, String cognome, String cf, String email, String password, int id_utente) {
        this.nome = nome;
        this.cognome = cognome;
        this.cf = cf;
        this.email = email;
        this.password = password;
        this.id_utente = id_utente;
    }

    //cosrteuttore vuoto
    public Utente() {
    
    }

    // Metodi getter per accedere agli attributi dell'utente
    public String getNome() {
        return nome;
    }
    public String getCognome() {
        return cognome;
    }
    public String getCf() {
        return cf;
    }
    public String getEmail() {
        return email;
    }
    public int getId_utente() {
        return id_utente;
    }
    public String getPassword() {
        return password;
    }

}