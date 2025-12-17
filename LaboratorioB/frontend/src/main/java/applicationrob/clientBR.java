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


//main class to lunch the application

public class clientBR{
	private serverBR server;
	private static clientBR instance;

	public clientBR()  throws RemoteException{

		try {
			Registry reg = LocateRegistry.getRegistry("localhost", 1099);
			server = (serverBR)reg.lookup("serverBR");
			System.out.println("Connessione RMI riuscita");
		}catch(RemoteException | NotBoundException e) {
			e.printStackTrace();
		} 

	} 

	public static clientBR getInstance() throws RemoteException {
		if (instance == null) {
			instance = new clientBR();
		}
		return instance;
	}

	public int registrazione(Utente user)throws RemoteException{
		 return server.registrazione(user);
	 }

	public int login(String email, String password)throws RemoteException{
		return server.login(email, password);
	}

	public Utente getUtente(int id)throws RemoteException{
		return server.getUtente(id);
	}

	public List<Libro> lazyLoadingLibri(String arg) throws RemoteException{
		return server.lazyLoadingLibri(arg);
	}

	public Libro getLibro(int id)throws RemoteException{
		return server.getLibro(id);
	}

	public List<Valutazione> getValutazione(int id)throws RemoteException{
		return server.getValutazione(id);
	}

	public List<Libro> getConsiglio(int id_libro)throws RemoteException{
		return server.getConsiglio(id_libro);
	}

	public List<Libro> getConsiglioUtente(int id_libro, int id_utente) throws RemoteException{
		return server.getConsiglioUtente(id_libro, id_utente);
	}

	public double getVotoMedio(int id_libro)throws RemoteException{
		return server.getVotoMedio(id_libro);
	}

	public List<Libro> cercaLibri(String autore, int anno, String titolo, Ricerca scelta)throws RemoteException{
		return server.cercaLibri(autore, anno, titolo, scelta);
	}
	
	public Libreria createLibreria(String nome, int user_id)throws RemoteException{
		return server.createLibreria(nome, user_id);
	}

	public Libreria addLibroLibreria(int id_libro, int id_libreria)throws RemoteException{
		return server.addLibroLibreria(id_libro, id_libreria);
	}

	public Libreria removeLibroLibreria(int id_libro, int id_libreria)throws RemoteException{
		return server.removeLibroLibreria(id_libro, id_libreria);
	}

	public Libreria getLibreria(int id_libreria)throws RemoteException{
		return server.getLibreria(id_libreria);
	}

	public List<Libreria> getLibrerie(int id_utente)throws RemoteException{
		return server.getLibrerie(id_utente);
	}

	public boolean createConsiglio(int user_id, int libro_id, int consiglio_id)throws RemoteException{
		return server.createConsiglio(user_id, libro_id, consiglio_id);
	}


	public void deleteConsiglio(int userId, int libro_id, int consiglio_id) throws RemoteException{
		server.deleteConsiglio( userId, libro_id, consiglio_id);
		
	}

	public boolean createValutazione(Valutazione val)throws RemoteException{
		return server.createValutazione(val);
	}
	public boolean deleteLibreria(int id_libreria)throws RemoteException{
		return server.deleteLibreria(id_libreria);
	}

	public List<Valutazione> getValutazioniUtente(int id_utente, int id_libro) throws RemoteException{
		return server.getValutazioniUtente(id_utente, id_libro);
	}

	public int getNumeroLibriConsigliati(int id_libro) throws RemoteException{
		return server.getNumeroLibriConsigliati(id_libro);
	}

	public int getNumeroLibriConsigliati_libro(int id_libro, int id_libro_consigliato) throws RemoteException{
		return server.getNumeroLibriConsigliati_libro(id_libro, id_libro_consigliato);
	}

	public static void main(String[] args) throws RemoteException, IOException, NotBoundException {

			TokenSession.setUserId(1); 

		Application.launch(startApp.class, args); 	
	}

	
}