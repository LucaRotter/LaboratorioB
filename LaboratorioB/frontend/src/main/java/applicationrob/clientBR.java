package applicationrob;

import java.io.IOException;

import javafx.application.Application;


//main class to lunch the application

public class clientBR extends UnicastRemoteObject{
	public static serverBR BR;

	protected clientBR()  {
		super();
	}

	 public int registrazione(Utente user){
		 BR.registrazione(user);
	 }

	public int login(String email, String password){
		BR.login(email, password);
	}

	public Utente getUtente(int id){
		BR.getUtente(id);
	}

	public Libro getLibro(int id){
		BR.getLibro(id);
	}

	public List<Valutazione> getValutazione(int id){
		BR.getValutazione(id);
	}

	public List<Libro> getConsiglio(int id_libro){
		BR.getConsiglio(id_libro);
	}

	public double getVotoMedio(int id_libro){
		BR.getVotoMedio(id_libro);
	}

	public List<Libro> cercaLibri(String autore, int anno, String titolo, Ricerca scelta){
		BR.cercaLibri(autore, anno, titolo, scelta);
	}
	
	public Libreria createLibreria(String nome, int user_id){
		BR.createLibreria(nome, user_id);
	}

	public Libreria addLibroLibreria(int id_libro, int id_libreria){
		BR.addLibroLibreria(id_libro, id_libreria);
	}

	public Libreria removeLibroLibreria(int id_libro, int id_libreria){
		BR.removeLibroLibreria(id_libro, id_libreria);
	}

	public Libreria getLibreria(int id_libreria){
		BR.getLibreria(id_libreria);
	}

	public List<Libreria> getLibrerie(int id_utente){
		BR.getLibrerie(id_utente);
	}

	public boolean createConsiglio(int user_id, int libro_id){
		BR.createConsiglio(user_id, libro_id);
	}

	public boolean createValutazione(int user_id, int libro_id, Valutazione val){
		BR.createValutazione(user_id, libro_id, val);
	}
	public static void main(String[] args) {
		/*try {
			Registry reg = LocateRegistry.getRegistry("");
			BR = (serverBR)reg.lookup("serverBR");
			System.out.println("Connessione RMI riuscita");
		}catch(RemoteException | NotBoundException e) {
			e.printStackTrace();
		} */
		Application.launch(startApp.class, args); 	
	}
}