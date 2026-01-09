package LaboratorioB.common.models;
import java.io.Serializable;
/**
 * Un oggetto della classe <code>ValutazioniLibri</code> rappresenta
 * una valutazione associata ad un libro, composta da 5 parametri
 * più un sesto che ne rappresenta la media, oltrea a delle note
 * relative ad ogni parametro
 * 
 * @author Grassi, Alessandro, 757784, VA
 * @author Kastratovic, Aleksandar, 752468, VA
 * @author Rotter, Luca Giorgio, 757780, VA
 * @author Davide, Bilora, 757011, VA
 * @version 1.0
 */

public class Valutazione implements	Serializable{
	private static final long serialVersionUID = 1L;
	
	//CAMPI
	private int voto_stile;
	private int voto_edizione;
	private int voto_contenuto;
	private int voto_gradevolezza;
	private int voto_originalita;
	private double voto_medio;
	private String stile;
	private String edizione;
	private String contenuto;
	private String gradevolezza;
	private String originalita;
	private int id_utente;
	private int id_libro;


	//COSTRUTTORE
	public Valutazione(int voto_stile, int voto_edizione, int voto_contenuto,
			int voto_gradevolezza, int voto_originalita, double voto_medio, String stile,
			String edizione, String contenuto, String gradevolezza,
			String originalita, int id_utente, int id_libro) {
		this.voto_stile = voto_stile;
		this.voto_edizione = voto_edizione;
		this.voto_contenuto = voto_contenuto;
		this.voto_gradevolezza = voto_gradevolezza;
		this.voto_originalita = voto_originalita;
		this.voto_medio = voto_medio;
		this.stile = stile;
		this.edizione = edizione;
		this.contenuto = contenuto;
		this.gradevolezza = gradevolezza;
		this.originalita = originalita;
		this.id_utente = id_utente;
		this.id_libro = id_libro;
	}

	//METODI GETTER
	public int getVotoStile() {
		return voto_stile;
	}
	public int getVotoEdizione() {
		return voto_edizione;
	}
	public int getVotoContenuto() {
		return voto_contenuto;
	}
	public int getVotoGradevolezza() {
		return voto_gradevolezza;
	}
	public int getVotoOriginalita() {
		return voto_originalita;
	}
	public double getVotoMedio() {
		return voto_medio;
	}
	public String getNoteStile() {
		return stile;
	}
	public String getNoteEdizione() {
		return edizione;
	}
	public String getNoteContenuto() {
		return contenuto;
	}
	public String getNoteGradevolezza() {
		return gradevolezza;
	}
	public String getNoteOriginalita() {
		return originalita;
	}

	public int getIdUtente() {
		return id_utente;
	}
	public int getIdLibro() {
		return id_libro;
	}


}