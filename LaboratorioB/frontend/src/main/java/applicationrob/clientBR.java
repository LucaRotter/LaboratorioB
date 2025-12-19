package applicationrob;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry; 
import java.util.List;
import LaboratorioB.common.interfacce.serverBR;
import LaboratorioB.common.models.Libreria;
import LaboratorioB.common.models.Libro;
import LaboratorioB.common.models.Ricerca;
import LaboratorioB.common.models.Utente;
import LaboratorioB.common.models.Valutazione;
import javafx.application.Application;


/**
 * Classe client per la comunicazione con il server RMI.
 * Classe main per l'avvio dell'applicazione.
 * Utilizza RMI per la comunicazione remota con il server.
 * @author Laboratorio B
 * @param serverBR Interfaccia del server RMI.
 * @param instance Istanza del clientBR usata nelle classi controller.
 */

public class clientBR{
	private serverBR server;
	private static clientBR instance;

	/**
	 * Costruttore del clientBR.
	 * Inizializza la connessione RMI con il server.
	 * @throws RemoteException Se si verifica un errore di comunicazione remota.
	 */
	public clientBR()  throws RemoteException{

		try {
			Registry reg = LocateRegistry.getRegistry("localhost", 6969);
			server = (serverBR)reg.lookup("serverBR");
			System.out.println("Connessione RMI riuscita");
		}catch(RemoteException | NotBoundException e) {
			e.printStackTrace();
		} 

	} 

	/**
	 * Metodo per ottenere l'istanza del clientBR.
	 * @throws RemoteException Se si verifica un errore di comunicazione remota.
	 * @return Istanza del clientBR.
	 */
	public static clientBR getInstance() throws RemoteException {
		if (instance == null) {
			instance = new clientBR();
		}
		return instance;
	}

	/**
	 * Metodo per registrare un nuovo utente.
	 * Effettua la chiamata al server RMI per registrare l'utente.
	 * @param user Utente da registrare.
	 * @throws RemoteException Se si verifica un errore di comunicazione remota.
	 * @return ID dell'utente registrato.
	 */
	public int registrazione(Utente user)throws RemoteException{
		return server.registrazione(user);
	 }

	/**
	 * Metodo per effettuare il login di un utente.
	 * Effettua la chiamata al server RMI per autenticare l'utente.
	 * @param email Email dell'utente.
	 * @param password Password dell'utente.
	 * @throws RemoteException Se si verifica un errore di comunicazione remota.
	 * @return ID dell'utente loggato.
	 */
	public int login(String email, String password)throws RemoteException{
		return server.login(email, password);
	}

	/**
	 * Metodo per ottenere i dettagli di un utente.
	 * Effettua la chiamata al server RMI per recuperare i dettagli dell'utente.
	 * @param id ID dell'utente.
	 * @throws RemoteException Se si verifica un errore di comunicazione remota.
	 * @return Utente con i dettagli richiesti.
	 */
	public Utente getUtente(int id)throws RemoteException{
		return server.getUtente(id);
	}

	/**
	 * Metodo per il caricamento lazy dei libri.
	 * Effettua la chiamata al server RMI per recuperare una lista di libri in base a un argomento.
	 * @param arg Argomento di ricerca per i libri.
	 * @throws RemoteException Se si verifica un errore di comunicazione remota.
	 * @return Lista di libri corrispondenti all'argomento di ricerca.
	 */
	public List<Libro> lazyLoadingLibri(String arg) throws RemoteException{
		return server.lazyLoadingLibri(arg);
	}

	/**
	 * Metodo per ottenere i dettagli di un libro.
	 * Effettua la chiamata al server RMI per recuperare i dettagli del libro.
	 * @param id ID del libro.
	 * @throws RemoteException Se si verifica un errore di comunicazione remota.
	 * @return Libro con i dettagli richiesti.
	 */
	public Libro getLibro(int id)throws RemoteException{
		return server.getLibro(id);
	}

	/**
	 * Metodo per ottenere le valutazioni di un libro.
	 * Effettua la chiamata al server RMI per recuperare le valutazioni del libro.
	 * @param id ID del libro.
	 * @throws RemoteException Se si verifica un errore di comunicazione remota.
	 * @return Lista di valutazioni del libro.
	 */
	public List<Valutazione> getValutazione(int id)throws RemoteException{
		return server.getValutazione(id);
	}

	/**
	 * Metodo per ottenere i consigli di lettura per un libro.
	 * Effettua la chiamata al server RMI per recuperare i libri consigliati.
	 * @param id_libro ID del libro.
	 * @throws RemoteException Se si verifica un errore di comunicazione remota.
	 * @return Lista di libri consigliati.
	 */
	public List<Libro> getConsiglio(int id_libro)throws RemoteException{
		return server.getConsiglio(id_libro);
	}

	/**
	 * Metodo per ottenere i consigli di lettura dati da un utente per un libro specifico.
	 * Effettua la chiamata al server RMI per recuperare i libri consigliati dall'utente.
	 * @param id_libro ID del libro.
	 * @param id_utente ID dell'utente.
	 * @throws RemoteException Se si verifica un errore di comunicazione remota.
	 * @return Lista di libri consigliati dall'utente.
	 */
	public List<Libro> getConsiglioUtente(int id_libro, int id_utente) throws RemoteException{
		return server.getConsiglioUtente(id_libro, id_utente);
	}

	/**
	 * Metodo per ottenere il voto medio di un libro.
	 * Effettua la chiamata al server RMI per calcolare il voto medio del libro.
	 * @param id_libro ID del libro.
	 * @throws RemoteException Se si verifica un errore di comunicazione remota.
	 * @return Voto medio del libro.
	 */
	public double getVotoMedio(int id_libro)throws RemoteException{
		return server.getVotoMedio(id_libro);
	}

	/**
	 * Metodo per cercare libri in base a criteri specifici.
	 * Effettua la chiamata al server RMI per eseguire la ricerca dei libri.
	 * @param autore Autore del libro.
	 * @param anno Anno di pubblicazione del libro.
	 * @param titolo Titolo del libro.
	 * @param scelta Criterio di ricerca (enum Ricerca).
	 * @throws RemoteException Se si verifica un errore di comunicazione remota.
	 * @return Lista di libri corrispondenti ai criteri di ricerca.
	 */
	public List<Libro> cercaLibri(String autore, int anno, String titolo, Ricerca scelta)throws RemoteException{
		return server.cercaLibri(autore, anno, titolo, scelta);
	}
	
	/**
	 * Metodo per creare una nuova libreria.
	 * Effettua la chiamata al server RMI per creare la libreria.
	 * @param nome Nome della libreria.
	 * @param user_id ID dell'utente proprietario della libreria.
	 * @throws RemoteException Se si verifica un errore di comunicazione remota.
	 * @return Libreria creata.
	 */
	public Libreria createLibreria(String nome, int user_id)throws RemoteException{
		return server.createLibreria(nome, user_id);
	}

	/**
	 * Metodo per aggiungere un libro a una libreria.
	 * Effettua la chiamata al server RMI per aggiungere il libro alla libreria.
	 * @param id_libro ID del libro da aggiungere.
	 * @param id_libreria ID della libreria a cui aggiungere il libro.
	 * @throws RemoteException Se si verifica un errore di comunicazione remota.
	 * @return Libreria aggiornata.
	 */
	public Libreria addLibroLibreria(int id_libro, int id_libreria)throws RemoteException{
		return server.addLibroLibreria(id_libro, id_libreria);
	}

	/**
	 * Metodo per rimuovere un libro da una libreria.
	 * Effettua la chiamata al server RMI per rimuovere il libro dalla libreria.
	 * @param id_libro ID del libro da rimuovere.
	 * @param id_libreria ID della libreria da cui rimuovere il libro.
	 * @throws RemoteException Se si verifica un errore di comunicazione remota.
	 * @return Libreria aggiornata.
	 */
	public Libreria removeLibroLibreria(int id_libro, int id_libreria)throws RemoteException{
		return server.removeLibroLibreria(id_libro, id_libreria);
	}

	/**
	 * Metodo per ottenere i dettagli di una libreria.
	 * Effettua la chiamata al server RMI per recuperare i dettagli della libreria.
	 * @param id_libreria ID della libreria.
	 * @throws RemoteException Se si verifica un errore di comunicazione remota.
	 * @return Libreria con i dettagli richiesti.
	 */
	public Libreria getLibreria(int id_libreria)throws RemoteException{
		return server.getLibreria(id_libreria);
	}

	/**
	 * Metodo per ottenere le librerie di un utente.
	 * Effettua la chiamata al server RMI per recuperare le librerie dell'utente.
	 * @param id_utente ID dell'utente.
	 * @throws RemoteException Se si verifica un errore di comunicazione remota.
	 * @return Lista di librerie dell'utente.
	 */
	public List<Libreria> getLibrerie(int id_utente)throws RemoteException{
		return server.getLibrerie(id_utente);
	}

	/**
	 * Metodo per creare un consiglio di lettura.
	 * Effettua la chiamata al server RMI per creare il consiglio.
	 * @param user_id ID dell'utente che crea il consiglio.
	 * @param libro_id ID del libro per cui viene creato il consiglio.
	 * @param consiglio_id ID del libro consigliato.
	 * @throws RemoteException Se si verifica un errore di comunicazione remota.
	 * @return True se il consiglio è stato creato con successo, altrimenti false.
	 */
	public boolean createConsiglio(int user_id, int libro_id, int consiglio_id)throws RemoteException{
		return server.createConsiglio(user_id, libro_id, consiglio_id);
	}

	/**
	 * Metodo per eliminare un consiglio di lettura.
	 * Effettua la chiamata al server RMI per eliminare il consiglio.
	 * @param userId ID dell'utente che elimina il consiglio.
	 * @param libro_id ID del libro per cui viene eliminato il consiglio.
	 * @param consiglio_id ID del libro consigliato da eliminare.
	 * @throws RemoteException Se si verifica un errore di comunicazione remota.
	 */
	public void deleteConsiglio(int userId, int libro_id, int consiglio_id) throws RemoteException{
		server.deleteConsiglio( userId, libro_id, consiglio_id);
		
	}

	/**
	 * Metodo per creare una nuova valutazione.
	 * Effettua la chiamata al server RMI per creare la valutazione.
	 * @param val Valutazione da creare.
	 * @throws RemoteException Se si verifica un errore di comunicazione remota.
	 * @return True se la valutazione è stata creata con successo, altrimenti false.
	 */
	public boolean createValutazione(Valutazione val)throws RemoteException{
		return server.createValutazione(val);
	}

	/**
	 * Metodo per eliminare una libreria.
	 * Effettua la chiamata al server RMI per eliminare la libreria.
	 * @param id_libreria ID della libreria da eliminare.
	 * @throws RemoteException Se si verifica un errore di comunicazione remota.
	 * @return True se la libreria è stata eliminata con successo, altrimenti false.
	 */
	public boolean deleteLibreria(int id_libreria)throws RemoteException{
		return server.deleteLibreria(id_libreria);
	}

	/**
	 * Metodo per ottenere le valutazioni di un utente per un libro specifico.
	 * Effettua la chiamata al server RMI per recuperare le valutazioni dell'utente.
	 * @param id_utente ID dell'utente.
	 * @param id_libro ID del libro.
	 * @throws RemoteException Se si verifica un errore di comunicazione remota.
	 * @return Lista di valutazioni dell'utente per il libro specifico.
	 */
	public List<Valutazione> getValutazioniUtente(int id_utente, int id_libro) throws RemoteException{
		return server.getValutazioniUtente(id_utente, id_libro);
	}

	/**
	 * Metodo per ottenere il numero di libri consigliati per un libro specifico.
	 * Effettua la chiamata al server RMI per recuperare il numero di libri consigliati.
	 * @param id_libro ID del libro.
	 * @throws RemoteException Se si verifica un errore di comunicazione remota.
	 * @return Numero di libri consigliati per il libro specifico.
	 */
	public int getNumeroLibriConsigliati(int id_libro) throws RemoteException{
		return server.getNumeroLibriConsigliati(id_libro);
	}

	/**
	 * Metodo per ottenere il numero di volte che un libro è stato consigliato in relazione a un altro libro.
	 * Effettua la chiamata al server RMI per recuperare il numero di consigli.
	 * @param id_libro ID del libro principale.
	 * @param id_libro_consigliato ID del libro consigliato.
	 * @throws RemoteException Se si verifica un errore di comunicazione remota.
	 * @return Numero di volte che il libro consigliato è stato suggerito per il libro principale.
	 */
	public int getNumeroLibriConsigliati_libro(int id_libro, int id_libro_consigliato) throws RemoteException{
		return server.getNumeroLibriConsigliati_libro(id_libro, id_libro_consigliato);
	}

	/**
	 * Metodo main per l'avvio dell'applicazione.
	 * @param args Argomenti della riga di comando.
	 * @throws RemoteException Se si verifica un errore di comunicazione remota.
	 * @throws IOException Se si verifica un errore di I/O durante l'avvio.
	 * @throws NotBoundException Se il nome del server RMI non è registrato.
	 */
	public static void main(String[] args) throws RemoteException, IOException, NotBoundException {

			TokenSession.setUserId(1); 

		Application.launch(startApp.class, args); 	
	}

	
}