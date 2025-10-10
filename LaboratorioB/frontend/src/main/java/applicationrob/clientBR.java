package applicationrob;
import LaboratorioB.common.interfacce.serverBR;
import LaboratorioB.common.models.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;  // per List
import java.util.ArrayList; // se usi ArrayList
import javafx.application.Application;
import java.io.IOException;


//main class to lunch the application

public class clientBR{
	public static serverBR BR;

	protected clientBR()  throws RemoteException{
		super();
	}

	 public int registrazione(Utente user)throws RemoteException{
		 return BR.registrazione(user);
	 }

	public int login(String email, String password)throws RemoteException{
		return BR.login(email, password);
	}

	public Utente getUtente(int id)throws RemoteException{
		return BR.getUtente(id);
	}

	public Libro getLibro(int id)throws RemoteException{
		return BR.getLibro(id);
	}

	public List<Valutazione> getValutazione(int id)throws RemoteException{
		return BR.getValutazione(id);
	}

	public List<Libro> getConsiglio(int id_libro)throws RemoteException{
		return BR.getConsiglio(id_libro);
	}

	public double getVotoMedio(int id_libro)throws RemoteException{
		return BR.getVotoMedio(id_libro);
	}

	public List<Libro> cercaLibri(String autore, int anno, String titolo, Ricerca scelta)throws RemoteException{
		return BR.cercaLibri(autore, anno, titolo, scelta);
	}
	
	public Libreria createLibreria(String nome, int user_id)throws RemoteException{
		return BR.createLibreria(nome, user_id);
	}

	public Libreria addLibroLibreria(int id_libro, int id_libreria)throws RemoteException{
		return BR.addLibroLibreria(id_libro, id_libreria);
	}

	public Libreria removeLibroLibreria(int id_libro, int id_libreria)throws RemoteException{
		return BR.removeLibroLibreria(id_libro, id_libreria);
	}

	public Libreria getLibreria(int id_libreria)throws RemoteException{
		return BR.getLibreria(id_libreria);
	}

	public List<Libreria> getLibrerie(int id_utente)throws RemoteException{
		return BR.getLibrerie(id_utente);
	}

	public boolean createConsiglio(int user_id, int libro_id)throws RemoteException{
		return BR.createConsiglio(user_id, libro_id);
	}

	public boolean createValutazione(int user_id, int libro_id, Valutazione val)throws RemoteException{
		return BR.createValutazione(user_id, libro_id, val);
	}
	public static void main(String[] args) {
		try {
			Registry reg = LocateRegistry.getRegistry("localhost", 1099);
			BR = (serverBR)reg.lookup("serverBR");
			System.out.println("Connessione RMI riuscita");
		}catch(RemoteException | NotBoundException e) {
			e.printStackTrace();
		} 
		Application.launch(startApp.class, args); 	
	}
}