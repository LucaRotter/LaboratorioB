package models;

/**
 * Classe singleton che rappresenta il modello dell'applicazione.
 * Fornisce l'accesso alla ViewFactory per la gestione delle viste.
 * 
 * @author Grassi, Alessandro, 757784, VA
 * @author Kastratovic, Aleksandar, 752468, VA
 * @author Rotter, Luca Giorgio, 757780, VA
 * @author Davide, Bilora, 757011, VA
 * @version 1.0
 */

import views.ViewFactory;

public class Model {
	
	private static Model model;
	private final ViewFactory viewfactory;
	
	
	private Model() {
		
		this.viewfactory = new ViewFactory();
	}
	
	public ViewFactory getView() {
		return viewfactory;
	}
	
	public static synchronized Model getIstance() {
		
		if (model==null) {
			model =new Model();
		}
		return model;
	}

}
