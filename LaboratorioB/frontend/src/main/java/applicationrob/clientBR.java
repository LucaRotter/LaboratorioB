package applicationrob;
import LaboratorioB.common.interfacce.serverBR;
import LaboratorioB.common.models.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;  
import java.util.ArrayList; 
import javafx.application.Application;
import java.io.IOException;


//main class to lunch the application

public class clientBR{
	public static serverBR BR;

	protected clientBR()  throws RemoteException{
		super();
	} 

	 public static int registrazione(Utente user)throws RemoteException{
		 return BR.registrazione(user);
	 }

	public static int login(String email, String password)throws RemoteException{
		return BR.login(email, password);
	}

	public static Utente getUtente(int id)throws RemoteException{
		return BR.getUtente(id);
	}

	public static Libro getLibro(int id)throws RemoteException{
		return BR.getLibro(id);
	}

	public static List<Valutazione> getValutazione(int id)throws RemoteException{
		return BR.getValutazione(id);
	}

	public static List<Libro> getConsiglio(int id_libro)throws RemoteException{
		return BR.getConsiglio(id_libro);
	}

	public static double getVotoMedio(int id_libro)throws RemoteException{
		return BR.getVotoMedio(id_libro);
	}

	public static List<Libro> cercaLibri(String autore, int anno, String titolo, Ricerca scelta)throws RemoteException{
		return BR.cercaLibri(autore, anno, titolo, scelta);
	}
	
	public static Libreria createLibreria(String nome, int user_id)throws RemoteException{
		return BR.createLibreria(nome, user_id);
	}

	public static Libreria addLibroLibreria(int id_libro, int id_libreria)throws RemoteException{
		return BR.addLibroLibreria(id_libro, id_libreria);
	}

	public static Libreria removeLibroLibreria(int id_libro, int id_libreria)throws RemoteException{
		return BR.removeLibroLibreria(id_libro, id_libreria);
	}

	public static Libreria getLibreria(int id_libreria)throws RemoteException{
		return BR.getLibreria(id_libreria);
	}

	public static List<Libreria> getLibrerie(int id_utente)throws RemoteException{
		return BR.getLibrerie(id_utente);
	}

	public static boolean createConsiglio(int user_id, int libro_id)throws RemoteException{
		return BR.createConsiglio(user_id, libro_id);
	}

	public static boolean createValutazione(int user_id, int libro_id, Valutazione val)throws RemoteException{
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