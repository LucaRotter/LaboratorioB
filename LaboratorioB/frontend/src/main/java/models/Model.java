package models;

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
