package application;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Un oggetto della classe <code>Libro</code> rappresenta
 * un libro con titolo, autore, categoria, editore e anno.
 * Possiede inoltre un elenco di libri consigliati e un
 * elenco di valutazioni associate al Libro stesso
 * 
 * @author ProgettoLabA
 */

public class Libro {


	//CAMPI
	private String titolo;
	private String autore;
	private String categoria;
	private String editore;
	private String anno;

	private LinkedList <ValutazioniLibri> elencoValutazioni = new LinkedList<>();
	private LinkedList <LibriConsigliati> elencoLibriConsigliati= new LinkedList<>();
	private LibriConsigliati libriConsigliati = new LibriConsigliati();


	//COSTRUTTORI
	/**
	 * Costruisce un oggetto che rappresenta un libro,
	 * in cui il titolo è il primo parametro, l'autore il secondo,
	 * la categoria il terzo, l'editore il quarto e l'anno il quinto
	 * 
	 * @param titolo il titolo
	 * @param autore l'autore
	 * @param categoria la categoria
	 * @param editore l'editore
	 * @param anno l'anno
	 */
	public Libro(String titolo, String autore, String categoria, String editore, String anno) {
		this.titolo = titolo;
		this.autore = autore;
		this.categoria = categoria;
		this.editore = editore;
		this.anno = anno;
	}

	/**
	 * costruisce un oggetto vuoto
	 */
	public Libro() {

	}


	//METODI GET
	public String getTitolo() {
		return titolo;
	}

	public String getAutore() {
		return autore;
	}

	public String getCategoria() {
		return categoria;
	}

	public String getEditore() {
		return editore;
	}

	public String getAnno() {
		return anno;
	}

	//stampa sul file
	@Override
	public String toString() {

		return titolo + ";" + autore + ";" + categoria + ";" + editore + ";" + anno ;

	}




	//funzione per la ricerca di un libro in base all'autore, il titolo o autore e anno
	/**
	 * Ricerca uno o più libri tra quelli presenti in memoria attraverso titolo,
	 * autore o autore e data a seconda della scelta dell'utente e
	 * offre la possibilità di sceglierne uno in particolare per visualizzarlo
	 */
	public LinkedList<Libro> cercaLibro(String barraricerca,String anno, String sceltaRic) {

		LinkedList<Libro> tmp ;
		//do {

			tmp = new LinkedList<>();

			if(sceltaRic == "Autore") {

				for(Libro libro: gestoreDati.DatiLibro ) {

					if(libro.getAutore().toLowerCase().contains(barraricerca.toLowerCase())){
						
						tmp.add(libro);

					}
				}

			} else if(sceltaRic == "titolo"){

				for(Libro libro: gestoreDati.DatiLibro ) {

					if(libro.getTitolo().toLowerCase().contains(barraricerca.toLowerCase())){

						tmp.add(libro);

					}
				}

			} else if(sceltaRic == "autore e anno"){

				for(Libro libro: gestoreDati.DatiLibro ) {
					
					if(libro.getAutore().toLowerCase().contains(barraricerca.toLowerCase()) && libro.getAnno().contains(anno)){
						
						tmp.add(libro);

					} 
				}
			}

			if(tmp.size() == 0) {
				System.out.println("nessun libro corrisponde ai criteri di ricerca");
			}else {
				if(tmp.size() > 1) {

					
					for(Libro libri : tmp) {
						return tmp;
					}
					
				}
			}
		
					
					//System.out.println("");

					/*do {

						System.out.println("\n0 > si vuole continuare a cercare , 1 > per selezionare un libro dall'elenco \n");
						do{
							try {
								
								input = true;
								continua = scanner.nextInt();
							}
							catch(InputMismatchException e) {
								
								System.err.println("è necessario inserire un numero intero");
								input = false;
								scanner.nextLine();
							}
							
						}while(!input);

					}while(continua != 0 && continua != 1);

					if(continua == 1) {

						System.out.println("Seleziona il libro \n");

						for(int i = 0; i< tmp.size(); i++) {
							System.out.println(i + " > " + tmp.get(i));
						}

						int scelta = 0;

						System.out.println("");

						do {
							do{
								try {
									
									input = true;
									scelta = scanner.nextInt();
								}
								catch(InputMismatchException e) {
									
									System.err.println("è necessario inserire un numero intero");
									input = false;
									scanner.nextLine();
								}
								
							}while(!input);
						}while(scelta < 0 || scelta > tmp.size()-1);

						//visualizzaLibro(tmp.get(scelta));
						break;
					}

				} else {

					System.out.println(tmp.get(0));
					//visualizzaLibro(tmp.get(0));

				}
			}*/

		//}while(tmp.size() > 1 && continua != 1);
		return tmp;
	}


	//funzione per la stampa di un libro cercato
	/**
	 * Stampa le informazioni relative al libro selezionato,
	 * passato come parametro, con la possibilità di scegliere 
	 * se visualizzare solo in anteprima le informazioni
	 * aggiuntive o visualizzarle in modo più dettagliato
	 * se esse sono presenti
	 * 
	 * @param libro il libro
	 */
	
	/*public void visualizzaLibro(Libro libro) {

		//aggiungere dati relativi a valutazione libro e suggirimenti quando saranno completate
		LinkedList <LibriConsigliati> consigliati = libro.getElencoConsigliati();
		LinkedList <ValutazioniLibri> valutazioni = libro.getValutazioniLibri();
		Scanner scanner = new Scanner(System.in);
		boolean esistonoConsigliati = true;

		libro.toString();
		System.out.println();

		LinkedList<Libro> nuovaListaConsigliati = new LinkedList<>();
		LinkedList<Libro> daStampare = new LinkedList<>();
		LinkedList<Integer> conta = new LinkedList<>();

		if(!consigliati.isEmpty()) {

			for(LibriConsigliati l : libro.getElencoConsigliati()){
				nuovaListaConsigliati.addAll(l.getConsigliati());
			}

			for(int i = 0; i<nuovaListaConsigliati.size(); i++){

				if(!daStampare.contains(nuovaListaConsigliati.get(i))){
					daStampare.add(nuovaListaConsigliati.get(i));
					conta.add(i,1);

				}else{

					for(int j = 0; j<daStampare.size(); j++){
						if(daStampare.get(j).equals(nuovaListaConsigliati.get(i))) {
							conta.set(j, conta.get(j) + 1);
							break;
						}
					}
				}
			}

		} else {

			System.out.println("non sono disponibili consigliati per questo libro\n");
			esistonoConsigliati = false;
		}

		int[] mediaVal = new int[6];
		boolean esistonoValutazioni = true;

		if(!valutazioni.isEmpty()) {

			int[] voti;
			for(ValutazioniLibri val : valutazioni){
				voti = val.getVoti();

				for(int i=0; i<6; i++)
					mediaVal[i] += voti[i];
			}

			for(int i=0; i<6; i++)
				mediaVal[i] = mediaVal[i] / valutazioni.size();
		} else {
			System.out.println("non sono disponibili valutazioni per questo libro\n");
			esistonoValutazioni=false;
		}

		// prima stampa, quella per le info generali, poi più dettagliate

		int dettagliate=1;
		if(esistonoValutazioni || esistonoConsigliati) {
			do{

				if(esistonoValutazioni) {
					String[] noteValutazioni;
					if(dettagliate == 0){

						System.out.println("sono presenti " + valutazioni.size() + " valutazioni per questo libro" );

						System.out.println("Voto stile          > " + mediaVal[0]);
						System.out.println("Voto contenuto      > " + mediaVal[1]);
						System.out.println("Voto gradevolezza   > " + mediaVal[2]);
						System.out.println("Voto originalita'   > " + mediaVal[3]);
						System.out.println("Voto edizione       > " + mediaVal[4]);

						for(ValutazioniLibri v : valutazioni) {
							noteValutazioni = v.getNote();
							
							System.out.println("");
							
							for(Utente u : gestoreDati.DatiUtente) {
								if(v.getNomeUtente().equals(u.getUserid())) {
									System.out.println(u.getNome() + ":");
									break;
								}
							}
							
							System.out.println("Commento stile          > " + noteValutazioni[0]);
							System.out.println("Commento contenuto      > " + noteValutazioni[1]);
							System.out.println("Commento gradevolezza   > " + noteValutazioni[2]);
							System.out.println("Commento originalita'   > " + noteValutazioni[3]);
							System.out.println("Commento edizione       > " + noteValutazioni[4]);
							System.out.println("Commento complessivo    > " + noteValutazioni[5]);
							System.out.println("");
						}
					} 

					System.out.println("\nil voto medio attribuito a questo libro e'" + mediaVal[5]);
				}

				if(esistonoConsigliati) {

					System.out.println("\ni libri consigliati associati a questo libro sono:");

					for(int i = 0; i<daStampare.size(); i++){

						System.out.print(" - " + daStampare.get(i).getTitolo());

						if(dettagliate == 0)
							System.out.print(" è stato consigliato da " + conta.get(i) + " utenti");
					}	
				}

				if(dettagliate == 1){
					System.out.println("\n\nper visualizzare le infornazioni più in dettaglio digitare 0, altrimenti un qualsiasi altro numero per tornare alla home\n\n");
					boolean input;
					do{
						try {
							
							input = true;
							dettagliate = scanner.nextInt();
						}
						catch(InputMismatchException e) {
							
							System.err.println("è necessario inserire un numero intero");
							input = false;
							scanner.nextLine();
						}
						
					}while(!input);
				} else {
					dettagliate = 1;
				}

			}while(dettagliate == 0);
			
			System.out.println("\n");
		}
	}*/

	//restituisce il libro cercato dal titolo
	/**
	 * Cerca e restituisce il libro corrispondente al titolo
	 * passato come input, in caso il libro non dovesse esistere
	 * il valore che viene restituito è null
	 * 
	 * @param libro il titolo del libro
	 * @return il libro corrispondente
	 */
	public static Libro cercaLibro(String libro) {

		Libro tmp = null;

		for(Libro b : gestoreDati.DatiLibro){

			if(libro.equals(b.getTitolo())){

				tmp = b;
				break;

			}
		}
		return tmp;

	}

	public  void setElencoValutazioni(ValutazioniLibri val) {
		elencoValutazioni.add(val);
	}
	
	public LinkedList<ValutazioniLibri> getValutazioniLibri(){
		return elencoValutazioni;


	}

	public void setLibriConsigliati(LibriConsigliati libro) {
		elencoLibriConsigliati.add(libro);
		

	}

	public void setLibroConsigliato(LibriConsigliati libro) {

		libriConsigliati = libro;
	}

	public LinkedList<LibriConsigliati> getElencoConsigliati(){
		return elencoLibriConsigliati;

	}

	

	//funzione per inserire un libro suggerito nell'array di libri
	/**
	 * restituisce un oggetto di tipo LibriConsigliati
	 * dopo averlo creato attraverso i parametri, dove il 
	 * primo parametro è il libro da inserire, mentre il
	 * secondo è lo Userid dell'utente che sta inserendo
	 * il libro tra i consigliati
	 * 
	 * @param read libro da inserire nei consigliati
	 * @param utente UserId dell'utente
	 * @param libro 
	 * @return oggetto di LibroConsigliati
	 * @throws IOException
	 */
	public LibriConsigliati inserisciConsiglio(String read, Utente utente) throws IOException {

        LibriConsigliati libro = returnConsigliatiPersonali(cercaLibro(read), utente);
        System.out.print("inserimento prima di chiamre inserisci++++++++++++++++++++++++++++++++++ " + libro);

        if(libro.getConsigliati().isEmpty()) {
        	
        	System.out.println("è vuota");
            libro = libro.inserisciSuggerimentoLibro(cercaLibro(read), utente, titolo);
           
        } else {
        	
            for(LibriConsigliati l : elencoLibriConsigliati) {
            	System.out.println("non è vuota");
                if(this.getTitolo().equals(l.getitoloLibro()) && l.getNomeUtente().equals(utente.getUserid())) {
                    libro = libro.inserisciSuggerimentoLibro(cercaLibro(read), utente, titolo);
                }
            }
        }

        System.out.print("libro da aggiungereeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee " +libro);
        int conta = 0;

        for(Libro modLibro : gestoreDati.DatiLibro) {

            if(modLibro.equals(this)) {
                for(LibriConsigliati modCons :modLibro.getElencoConsigliati()) {

                    conta ++;
                    if(modCons.getNomeUtente().equals(libro.getNomeUtente()) && modCons.getitoloLibro().equals(libro.getitoloLibro())) {
                 
                    	System.out.println("");
                        modLibro.getElencoConsigliati().set(conta-1,libro);
                        conta = -1;
                        
                        for(int j= 0; j<elencoLibriConsigliati.size();) {
                        System.out.println("elenco consigliati dopo inserimento in pos "+ j + " > " + elencoLibriConsigliati.get(j));
                       
                        }
                        break;
                        
                    }
                }

                if(conta != -1) {
                    modLibro.setLibriConsigliati(libro);
                	System.out.println("elenco consigliati dopo inserimento ----------------------" + elencoLibriConsigliati);
                break;
    
            }
        }
        
        
    }
        return libro;
	}


	//metodo che restituisce i libri consigliati associati a un libro di un determinato utente

	public LibriConsigliati returnConsigliatiPersonali(Libro libro, Utente utente) {
		
		LibriConsigliati libripersonali = null ;
		for(int i= 0;i< elencoLibriConsigliati.size(); i++) {
			
			System.out.println(libro.getTitolo() + elencoLibriConsigliati.get(i).getitoloLibro());
			System.out.println("libreriee personali nome <<<<<<<<<<<<<<<<<<" + elencoLibriConsigliati.get(i).getitoloLibro() +elencoLibriConsigliati.get(i).getitoloLibro().equals(libro.getTitolo()) + elencoLibriConsigliati.get(i).getNomeUtente().equals(utente.getUserid()));
			if(elencoLibriConsigliati.get(i).getitoloLibro().equals(this.getTitolo()) && elencoLibriConsigliati.get(i).getNomeUtente().equals(utente.getUserid())) {
			libripersonali = elencoLibriConsigliati.get(i);
			System.out.println("libreriee personali trovate" + elencoLibriConsigliati.get(i).getNomeUtente() + elencoLibriConsigliati.get(i).getitoloLibro());
			break;
			
			} else {
				
			libripersonali = new LibriConsigliati();
			
			}
		}
		
		if(libripersonali!= null) {
		System.out.println("librerie personali ritornate" + libripersonali.getNomeUtente() + libripersonali.getitoloLibro());
		}
		return libripersonali;
	
	
	
	
	}
}

