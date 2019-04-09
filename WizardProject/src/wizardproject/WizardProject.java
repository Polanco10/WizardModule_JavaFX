/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizardproject;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Polanco
 */

public class WizardProject  extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		
	final Injector injector = Guice.createInjector( new WizardModule() );
	final Parent p= FXMLLoader.load( WizardProject.class.getResource("menu/Menu.fxml"),null, new JavaFXBuilderFactory(),(ac) -> injector.getInstance(ac));		
	
		final Scene scene = new Scene(p);		
		primaryStage.setScene( scene );
                primaryStage.setWidth( 300 );
		primaryStage.setHeight( 500 );
		primaryStage.setTitle("Menu");
		
		primaryStage.show();

	}
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
