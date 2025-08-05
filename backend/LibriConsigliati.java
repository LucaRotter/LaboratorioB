package application;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Un oggetto della classe <code>LibriConsigliati</code> rappresenta
 * un array di massimo 3 libri consigliati da associare ad un libro
 * scelto
 * 
 * @author ProgettoLabA
 */

public class LibriConsigliati {
	
	//CAMPI
	String NomeUtente;
	String Libro;
	private Libro[] consigliati = new Libro[3];
	private int conta = 0;
	
	//COSTRUTTORI
	/**
	 * crea un oggetto LibriConsigliati attraverso dei
	 * parametri in input, il primo è lo UserId, il
	 * secondo è il titolo del libro a cui si associa 
	 * un array di libri, il terzo è l'array di libri
	 * 
	 * @param NomeUtente UserId dell'utente
	 * @param Libro titolo del libro 
	 * @param consigliati elenco di libri da associare
	 */
	public LibriConsigliati(String NomeUtente, String Libro, Libro[] consigliati) {
		
		this.NomeUtente = NomeUtente;
		this.Libro = Libro;
		this.consigliati = consigliati;
		
		while(conta < 3 && consigliati[conta]!= null) {
		conta ++;
		System.out.println("conta nel costruttore > " + conta);
		
		}
	}
	
	/**
	 * costruisce un oggetto LibriConsigliati vuoto
	 */
	public LibriConsigliati() {
		
	}
	

	//METODI
	
	//inserisce un libro nell'elenco, se è pieno lo rimpiazza
	/**
	 * permette di inserire un libro consigliato nell'elenco di 
	 * libri associato ad un libro, il cui titolo è passato
	 * come terzo parametro, mentre il libro da inserire e lo UserId
	 * sono passati come primo e secondo parametro.
	 * Si restituisce il nuovo oggetto creato
	 * 
	 * @param libro il libro da associare
	 * @param NomeUtente userid dell'utente
	 * @param titoloLibro libro in cui si inserisce il consigliato
	 * @return oggetto LibriConsigliati appena creato
	 * @throws IOException
	 */
public LibriConsigliati inserisciSuggerimentoLibro(Libro libro, Utente NomeUtente, String titoloLibro ) throws IOException{
		
		System.out.println("conta è nella posizione > " + conta);
		System.out.println(this.NomeUtente);
		
		Scanner scanner = new Scanner(System.in);
		LibriConsigliati libri = new LibriConsigliati() ;
		
		if(conta <=2) {
			consigliati[conta] = libro;
			
			System.out.print("\nlibro1 :" +consigliati[0] + "libro2 :" + consigliati[1] + "libro3 :" +consigliati[2]);
			
			System.out.println(conta);
			
			if(conta == 0) {
				
				libri = new LibriConsigliati(NomeUtente.getUserid(), titoloLibro, consigliati);
				gestoreDati.DatiConsigliati.add(libri);
				gestoreDati.aggiuntaLibriConsigliati(libri);
				System.out.println("libro inserito > " + libro.getTitolo());
				System.out.println("\n");
				
				
			}else {
				
				System.out.println(this.toString());
				libri = this;
				if(libri!= null) {
				gestoreDati.aggiuntanuovoLibriConsigliati(libri);
				System.out.println("nuovo libro inserito > " + libro.getTitolo());
				System.out.println("\n");
				}
			}
			conta++;
			
		}
		
		else{
			
			/*boolean input;
			int sostituzione=0;
			
			System.out.println("non si possono aggiungere ulteriori libri, sostituirne uno? (0 > no, 1 > si)");
			
			do {
				do{
					try {
						
						input = true;
						sostituzione = scanner.nextInt();
					}
					catch(InputMismatchException e) {
						
						System.err.println("è necessario inserire un numero intero");
						input = false;
						scanner.nextLine();
					}
					
				}while(!input);
			}while(!(sostituzione == 1 || sostituzione==0));
			
			if(sostituzione == 1){
				System.out.println("scegliere il libro da sostituire \n");
				
				//elenco dei liberi con le varie info e numeri da 0 a 2
				for(int i = 0; i< consigliati.length; i++) {
					System.out.println(i + " > " + consigliati[i].getTitolo());
				}
				
				int i = 0;
				
				do{
					do{
						try {
							
							input = true;
							i = scanner.nextInt();
						}
						catch(InputMismatchException e) {
							
							System.err.println("è necessario inserire un numero intero");
							input = false;
							scanner.nextLine();
						}
						
					}while(!input);
				}while(i>2 || i<0);
				
				consigliati[i] = libro;
				
				System.out.println("");
				
				libri = this;
				gestoreDati.aggiuntanuovoLibriConsigliati(libri);
				System.out.println("libro inserito > " + libro.getTitolo());
			}*/
		}
		return libri;
	}

	public String getNomeUtente() {
		
		return NomeUtente;
	}

	public String getitoloLibro() {
		
		return Libro;
	}
	
	//metodo per scrivere sul file
	/**
	 * permette di creare la stringa da
	 * inserire nel file CSV
	 */
	
	@Override
	public String toString() {
		
		String tmp = NomeUtente + ";" + Libro + "\n";
		
		int i = 0;
		
		while(i< 3 && this.consigliati[i] != null) {
			
			if(i == conta - 1 ) {
				
				tmp += this.consigliati[i].getTitolo() ;
				
			} else {
				
				tmp += this.consigliati[i].getTitolo() + ";" ;
			}
			
			i++;
		}
		return tmp;
		
	}
	
	//restituisce l'array di consigliati
	/**
	 * restituisce l'array di libri consigliati
	 * associati al libro scelto come LinkedList
	 * 
	 * @return elenco di libri come LinkedList
	 */
	public LinkedList<Libro> getConsigliati(){
		
		LinkedList<Libro> cons = new LinkedList<>();
		for(int i = 0; i < consigliati.length; i++) {
			
			if(this.consigliati[i]!=null) {
				
				cons.add(consigliati[i]);
			}
		}
			
		return cons;	
	
	}
}