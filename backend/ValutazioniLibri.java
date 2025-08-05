package application;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Un oggetto della classe <code>ValutazioniLibri</code> rappresenta
 * una valutazione associata ad un libro, composta da 5 parametri
 * più un sesto che ne rappresenta la media, oltrea a delle note
 * relative ad ogni parametro
 * 
 * @author ProgettoLabA
 */

public class ValutazioniLibri{
	
	//CAMPI
	private int[] voti = new int[6];
	private String[] note = new String[6];
	private String titoloLibro;
	private String NomeUtente;

	//COSTRUTTORI
	/**
	 * costruisce un oggetto ValutazioniLibri attraverso 
	 * parametri forniti, il primo è lo UserId, il secondo il 
	 * titolo del libro a cui si associano le valutazioni, 
	 * il terzo un array di voti e il quarto un array di commenti
	 * 
	 * @param NomeUtente userid
	 * @param titoloLibro il titolo del libro da recensire
	 * @param voti elenco di voti per le categorie
	 * @param note elenco di commenti sulle varie categorie
	 */
	public ValutazioniLibri(String NomeUtente ,String titoloLibro, int[] voti, String[] note) {

		this.NomeUtente = NomeUtente;
		this.titoloLibro = titoloLibro;
		this.voti = voti;
		this.note = note;

	}
	/**
	 * costruisce un oggetto ValutazioniLibri vuoto
	 */
	public ValutazioniLibri() {

	}

	//METODI
	
	//metodo per inserire una valutazione
	/**
	 * permette all'utente di recensire un libro della
	 * sua libreria attraverso un elenco di voti, 
	 * uno per ogni parametro, e un elenco
	 * di commenti, uno per ogni parametro, 
	 * restituendo il nuovo oggetto ValutazioniLibri
	 * appena creato
	 * 
	 * @param userID userId dell'utente
	 * @param nomeLibro nome del libro da recensire
	 * @return la nuova valutazione
	 * @throws IOException
	 */
	public ValutazioniLibri inserisciValutazioneLibro(String userID, String nomeLibro,int[] voti,String[] note ) throws IOException{

		ValutazioniLibri val = new ValutazioniLibri(userID,nomeLibro,voti,note);
		//val.stampaValutazioni();
		gestoreDati.DatiValutazioni.add(val);
		gestoreDati.aggiuntaValutazioni(val);
		return val;

	}

	public void setTitoloLibro(String titoloLibro) {
		this.titoloLibro = titoloLibro;
	}

	public void setNomeUtente(String nomeUtente) {
		NomeUtente = nomeUtente;
	}

	public String getNomeUtente() {

		return NomeUtente;
	}

	public String getitoloLibro() {

		return titoloLibro;
	}
	
	public int[] getVoti(){
		return voti;
	}
	public String[] getNote(){
		return note;
	}
	
	//stampa la valutazione inserita dall'utente da togliere
	/*public void stampaValutazioni(){

		if(voti != null && note != null){

			System.out.println("Voto stile          > " + voti[stile] + "\t Commento > " + note[stile]);
			System.out.println("Voto contenuto      > " + voti[contenuto] + "\t Commento > " + note[contenuto]);
			System.out.println("Voto gradevolezza   > " + voti[gradevolezza] + "\t Commento > " + note[gradevolezza]);
			System.out.println("Voto originalita'   > " + voti[originalita] + "\t Commento > " + note[originalita]);
			System.out.println("Voto edizione       > " + voti[edizione] + "\t Commento > " + note[edizione]);
			System.out.println("Voto complessivo    > " + voti[complessivo] + "\t Commento > " + note[complessivo]);
			System.out.println("");
		}
	}*/

	//serve per la stampa sul file
	/**
	 * utile per la stampa sul file CSV
	 */
	@Override
	// da togliere quando creeremo il db
	public String toString(){

		String tmp = NomeUtente + ";" + titoloLibro + "\n" ;

		for(int i = 0; i<6; i++){
			if(i == 5) {
				tmp+= voti[i];

			} else {

				tmp+=voti[i] + ";";
			}
		}

		tmp += "\n";

		for(String n : note){
			tmp += "\"" + n + "\";";
		}

		return tmp;

	}
}