/**
 * 
 */
/**
 * 
 */
module Porvalib2 {
	requires JavaFx;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.controls;
	requires javafx.base;
	
	opens applicationrob to javafx.fxml;
	exports applicationrob;
}