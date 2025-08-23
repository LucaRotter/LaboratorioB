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

public class Utente {
    // Attributi dell'utente    
    private String nome, cognome;
	private String codFiscale;
	private String email;
	private String password;
	private int userid;


    // Costruttore della classe Utente
    /**
	 * Costruisce un oggetto Utente con i parametri in ingresso, 
	 * il primo rappresenta il nome, il secondo il cognome, il terzo
	 * il codice fiscale, il quarto la mail, il quinto lo UserId e 
	 * l'ultimo la password
	 * 
	 * @param nome il nome
	 * @param cognome il cognome
	 * @param codFiscale il codice fiscale
	 * @param email la mail
	 * @param userid lo UserId
	 * @param password la password
	 */
    public Utente(String nome, String cognome, String codFiscale, String email, String password, int userid) {
        this.nome = nome;
        this.cognome = cognome;
        this.codFiscale = codFiscale;
        this.email = email;
        this.password = password;
        this.userid = userid;
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
    public String getCodFiscale() {
        return codFiscale;
    }
    public String getEmail() {
        return email;
    }
    public int getUserId() {
        return userid;
    }
    public String getPassword() {
        return password;
    }

}