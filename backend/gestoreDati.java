package application;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * La classe <code>gestoreDati</code> è finalizzata alla gestione dei dati:
 * si occupa di prelevare i dati dai file e mantenerli durante l'utilizzo del 
 * programma in modo da averli sempre a disposizione, inoltre ha la funzione
 * di registrare sugli stessi file i nuovi dati inseriti e si occupa
 * di modificare quelli già presenti se necessario
 * 
 * @author ProgettoLabA
 */

public class gestoreDati {
	
	//CAMPI
	public static ArrayList<Libro> DatiLibro = new ArrayList<>();
	public static ArrayList<Utente> DatiUtente = new ArrayList<>();
	public static ArrayList<Librerie> DatiLibrerie = new ArrayList<>();
	public static ArrayList<ValutazioniLibri> DatiValutazioni = new ArrayList<>();
	public static ArrayList<LibriConsigliati> DatiConsigliati = new ArrayList<>();

	
	//METODI CARICAMENTO DATI
		
	//metodo per importare le informzazione sui libri
	/**
	 * Legge dal file contenente i libri le informazioni
	 * utili riguardo essi e le memorizza per l'intera
	 * durata dell'utilizzo del programma
	 * 
	 * @throws IOException
	 */
	public static void CreaRepositoryLibri() throws IOException {

		FileReader reader = new FileReader("src//Libri.dati.csv");
		BufferedReader lettore = new BufferedReader(reader);
		String[] content ;
		String letta ="";

		while((letta = lettore.readLine()) != null) {

			content = letta.split(";", 6);

			String titolo = content[0];
			String autore = content[1];
			String categoria = content[2];
			String editore = content[3];
			String anno = content[4];

			Libro libro = new Libro(titolo, autore, categoria, editore, anno);

			DatiLibro.add(libro);
			

		}
	}

	
	//metodo per importare le informazioni sugli utenti
	/**
	 * Legge dal file contenente i dati sugli utenti le informazioni
	 * utili riguardo essi e le memorizza per l'intera
	 * durata dell'utilizzo del programma
	 * 
	 * @throws IOException
	 */
	public static void creaRepositoryUtente() throws IOException {

		FileReader reader = new FileReader("src//UtentiRegistrati.dati.csv");
		BufferedReader lettore = new BufferedReader(reader);
		String[] content ;
		String letta ="";
		
		while((letta = lettore.readLine()) != null) {

			content = letta.split(";",6);

			String nome = content[0];
			String cognome = content[1];
			String cf = content[2];
			String email = content[3];
			String ID = content[4];
			String password = content[5];

			Utente utente = new Utente(nome, cognome, cf, email, ID ,password);

			DatiUtente.add(utente);
			
		}
	}
	
	
	//metodo per importare le informazioni sulle librerie
	/**
	 * Legge dal file contenente i dati sulle librerie le informazioni
	 * utili riguardo esse e le memorizza per l'intera
	 * durata dell'utilizzo del programma
	 * 
	 * @throws IOException
	 */
	public static void creaRepositoryLibrerie() throws IOException {

		FileReader reader = new FileReader("src//Librerie.dati.csv");
		BufferedReader lettore = new BufferedReader(reader);
		String[] content;
		String[] riga1;
		String letta ="";
		LinkedList<Libro> elencoLibri;

		while((letta = lettore.readLine()) != null) {

			elencoLibri = new LinkedList<Libro>();

			riga1 = letta.split(";", 2);
			
			letta = lettore.readLine();
			content = letta.split(";");

			for(String a : content){

				elencoLibri.add(Libro.cercaLibro(a));

			}		

			Librerie nuova = new Librerie(riga1[0], riga1[1], elencoLibri);

			DatiLibrerie.add(nuova);

			for(Utente u : DatiUtente) {
				if(nuova.getNomeUtente().equals(u.getUserid())) {
					u.setlibreriePersonali(nuova);
				}
			}
		}
	}

	
	//metodo per importare le informazioni sulle valutazioni
	/**
	 * Legge dal file contenente i dati sulle valutazioni le informazioni
	 * utili riguardo esse e le memorizza per l'intera
	 * durata dell'utilizzo del programma
	 * 
	 * @throws IOException
	 */
	public static void creaRepositoryValutazioni() throws IOException {

		FileReader reader = new FileReader("src//ValutazioniLibri.dati.csv");
		BufferedReader lettore = new BufferedReader(reader);

		String[] riga1;
		String[] riga2;
		String[] riga3;

		String letta ="";
		String letta1 ="";
		String letta2 = "";
		int[] voti = new int[6];
		String[] note = new String[6];

		while((letta = lettore.readLine()) != null && (letta1 = lettore.readLine()) != null && (letta2 = lettore.readLine()) != null) {

			riga1 = letta.split(";");
		
			riga2 = letta1.split(";", 6);

			for(int i = 0; i< riga2.length; i++) {
				voti[i] = Integer.parseInt(riga2[i]);
			}

			riga3 = letta2.split(";", 6);

			note = riga3;

			ValutazioniLibri valutazioni = new ValutazioniLibri(riga1[0], riga1[1], voti, note);
			DatiValutazioni.add(valutazioni);

			for(Librerie l : DatiLibrerie) {

				if(l.getNomeUtente().equals(valutazioni.getNomeUtente())) {

					l.setValutazioni(valutazioni);

				}
			}
			
			for(Libro libro : DatiLibro) {

				if(libro.getTitolo().equals(valutazioni.getitoloLibro())) {

					libro.setElencoValutazioni(valutazioni);
					break;
				}
			}

		}
	}
	
	//metodo per importare le informazioni sui libri consigliati
	/**
	 * Legge dal file contenente i dati sui libri consigliati le informazioni
	 * utili riguardo essi e le memorizza per l'intera
	 * durata dell'utilizzo del programma
	 * 
	 * @throws IOException
	 */
	public static void creaRepositoryLibriConsigliati() throws IOException {

		FileReader reader = new FileReader("src//ConsigliLibri.dati.csv");
		BufferedReader lettore = new BufferedReader(reader);

		String[] riga1;
		String[] riga2;
		Libro[] libri;

		String letta = "";
		String letta2 = "";
		
		while((letta = lettore.readLine()) != null && (letta2 = lettore.readLine()) != null) {

			libri = new Libro[3];

			riga1 = letta.split(";", 2);
			
			riga2 = letta2.split(";");

			for(int i = 0; i<riga2.length ; i++) {

				if(riga2[i]!= null)
					libri[i] = Libro.cercaLibro(riga2[i]);
			}

			LibriConsigliati Libri = new LibriConsigliati(riga1[0], riga1[1], libri);
			
			
			DatiConsigliati.add(Libri);

			for(Librerie l : DatiLibrerie) {

				if(l.getNomeUtente().equals(Libri.getNomeUtente())) {

					l.setConsigliLibri(Libri);  

				}	
			}

			for(Libro libro : DatiLibro) {
		
				if(Libri.getitoloLibro().equals(libro.getTitolo())) {
					
					System.out.println("libro a cui associare> " + Libri.getitoloLibro());
					System.out.println("nome utente > " + Libri.getNomeUtente());
					System.out.println("lista di consiglaiti associati > " + Libri.getConsigliati());
					libro.setLibriConsigliati(Libri);
					break;
				}
			}
		}
	}
	
	
	
	//METODI DI SALVATAGGIO DATI
	
	//metodo per salvare le informazioni sugli utenti
	/**
	 * scrive sul file relativo agli utenti le
	 * informazioni dell'utente passato come parametro
	 * 
	 * @param utente utente da salvare
	 * @throws IOException
	 */
	public static void RegistrazioneUtenti(Utente utente) throws IOException {

		FileWriter file = new FileWriter("src//UtentiRegistrati.dati.csv",true); //DA SISTEMARE
		PrintWriter wr = new PrintWriter(file);

		wr.println(utente.toString());

		wr.close();
	}	

	//metodo per salvare le informazioni sulle librerie
	/**
	 * scrive sul file relativo alle librerie le
	 * informazioni sulla libreria passata come parametro
	 * 
	 * @param librerie libreria da salvare
	 * @throws IOException
	 */
	public static void aggiuntaLibreria(Librerie libreria) throws IOException {

		FileWriter file = new FileWriter("src//Librerie.dati.csv",true); //DA SISTEMARE
		PrintWriter wr = new PrintWriter(file);

		wr.println(libreria.toString());

		wr.close();
	}

	//metodo per aggiungere nel file un nuovo libro nella prorpia libreria
	/**
	 * aggiunge un libro alla libreria e salva la libreria
	 * modificata con il nuovo libro anche sul file
	 * delle librerie, prendendo come primo parametro l'indice
	 * in cui si deve effettuare la modifica e come secondo 
	 * parametro il libro da inserire
	 * 
	 * @param scambio indice di modifica
	 * @param libro nuovo libro da inserire
	 * @throws IOException
	 */
	public static void aggiuntaLibroLibrerie(int scambio , Libro libro) throws IOException {

		FileWriter file = new FileWriter("src//Librerie.dati.csv"); 
		PrintWriter wr = new PrintWriter(file);

		//con l'indice calcolato prima prendo la libreria in quella posizione e ci aggiungo il libro che ho passato
		Librerie letta = DatiLibrerie.get(scambio-1);
		letta.getLibreria().add(libro);

		for (Librerie librerie : DatiLibrerie) {
			wr.println(librerie.toString());
		}

		wr.close();
	}
	
	//metodo per salvare le informazioni sulle valutazioni
	/**
	 * scrive sul file relativo alle valutazioni le
	 * informazioni delle valutazioni passate come parametro
	 * 
	 * @param valutazioni valutazioni da salvare
	 * @throws IOException
	 */
	public static void aggiuntaValutazioni(ValutazioniLibri valutazioni) throws IOException {
		FileWriter file = new FileWriter("src//ValutazioniLibri.dati.csv",true); 
		PrintWriter wr = new PrintWriter(file);

		wr.println(valutazioni.toString());
		wr.close();
	}
	
	//metodo per salvare le informazioni sui libri consigliati
	/**
	 * scrive sul file relativo ai libri consigliati le
	 * informazioni dei libri consigliati passate come parametro
	 * 
	 * @param Consigliati infomrazioni dei consigliati da salvare
	 * @throws IOException
	 */
	public static void aggiuntaLibriConsigliati(LibriConsigliati Consigliati) throws IOException {

		FileWriter file = new FileWriter("src//ConsigliLibri.dati.csv",true); 
		PrintWriter wr = new PrintWriter(file);

		wr.println(Consigliati.toString());

		wr.close();
	}
	
	//metodo per modificare e aggiungere le informazioni sui libri consigliati nel file
	/**
	 * modifica e salva le informazioni riguardanti un libro
	 * a cui sono associati i libri consigliati, prendendo 
	 * come parametro l'oggetto modificato e trovando
	 * l'indice in cui va inserito
	 * 
	 * @param Consigliati libri consigliati da modificare
	 * @throws IOException
	 */
	public static void aggiuntanuovoLibriConsigliati(LibriConsigliati Consigliati) throws IOException {

		int count = 0;

		FileWriter file = new FileWriter("src//ConsigliLibri.dati.csv"); 
		PrintWriter wr = new PrintWriter(file);

		for(LibriConsigliati consigliati : DatiConsigliati) {

			count++;
			if(Consigliati.getitoloLibro().equals(consigliati.getitoloLibro()) && Consigliati.getNomeUtente().equals(consigliati.getNomeUtente())) {
				break;
			}
		}

		//con l'indice calcolato prima prendo la libreria in quella posizione e ci aggiungo il libro che ho passato
		DatiConsigliati.set(count-1, Consigliati);
		LibriConsigliati l =DatiConsigliati.get(count-1);
		System.out.println(l);
		
		for (LibriConsigliati consigliati : DatiConsigliati) {
	
			wr.println(consigliati.toString());
		}

		wr.close();
	}
}