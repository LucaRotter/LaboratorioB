/**
 * Un oggetto della classe <code>ValutazioniLibri</code> rappresenta
 * una valutazione associata ad un libro, composta da 5 parametri
 * più un sesto che ne rappresenta la media, oltrea a delle note
 * relative ad ogni parametro
 * 
 * @author ProgettoLabA
 */

public class Valutazione{
	
	//CAMPI
	private int voto_stile;
	private int voto_edizione;
	private int voto_contenuto;
	private int voto_gradevolezza;
	private int voto_originalita;
	private double voto_medio;
	private String note_stile;
	private String note_edizione;
	private String note_contenuto;
	private String note_gradevolezza;
	private String note_originalita;

	//COSTRUTTORE
	public Valutazione(int voto_stile, int voto_edizione, int voto_contenuto,
			int voto_gradevolezza, int voto_originalita, String note_stile,
			String note_edizione, String note_contenuto, String note_gradevolezza,
			String note_originalita) {
		this.voto_stile = voto_stile;
		this.voto_edizione = voto_edizione;
		this.voto_contenuto = voto_contenuto;
		this.voto_gradevolezza = voto_gradevolezza;
		this.voto_originalita = voto_originalita;
		this.voto_medio = (voto_stile + voto_edizione + voto_contenuto +
				voto_gradevolezza + voto_originalita) / 5.0;
		this.note_stile = note_stile;
		this.note_edizione = note_edizione;
		this.note_contenuto = note_contenuto;
		this.note_gradevolezza = note_gradevolezza;
		this.note_originalita = note_originalita;
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
		return note_stile;
	}
	public String getNoteEdizione() {
		return note_edizione;
	}
	public String getNoteContenuto() {
		return note_contenuto;
	}
	public String getNoteGradevolezza() {
		return note_gradevolezza;
	}
	public String getNoteOriginalita() {
		return note_originalita;
	}

}