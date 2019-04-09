package wizardproject.menu;


import com.google.inject.Inject;
import com.google.inject.Injector;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.xml.bind.JAXBException;
import wizardproject.controllers.WizardController;
import wizardproject.model.Activity;
import wizardproject.model.Configuration;
import wizardproject.model.Participant;
import wizardproject.model.ProjectOrganization;
import wizardproject.model.StagePlugin;
import wizardproject.persistence.ProtocolRead;


public class Menu {
    @FXML private AnchorPane anchorid;
    private String XmlFile;
    ProjectOrganization PO;
    @Inject Injector injector;
    @Inject ProjectOrganization model;

    private void Wizard(String pathView, int width,int height) throws IOException{
        Stage primaryStage = (Stage) anchorid.getScene().getWindow(); 
        final JavaFXBuilderFactory bf = new JavaFXBuilderFactory();
        final Callback<Class<?>, Object> cb = (clazz) -> injector.getInstance(clazz);
	FXMLLoader fxmlLoaderPath = new FXMLLoader( WizardController.class.getResource(pathView), null, bf, cb);
	Parent path = fxmlLoaderPath.load( );        
        final Scene scene = new Scene(path);	
        primaryStage.setScene( scene );
	primaryStage.setWidth( width );
	primaryStage.setHeight( height );
	primaryStage.setTitle("Wizard");		
	primaryStage.show();        
    }
     @FXML private void OpenWizard() throws IOException{ 
         Wizard("/wizardproject/views/Layout.fxml",910,620);         
    }
    @FXML private void OpenProtocol() throws JAXBException, IOException {
        FileChooser filechooser = new FileChooser();
        filechooser.setTitle("Importar XML");
        File file = filechooser.showOpenDialog(null);
        
        if(file!=null){
            XmlFile=file.getAbsolutePath();
            ProtocolRead pr = new ProtocolRead();            
            PO = pr.ReadXml(model, XmlFile);
            ParseData(PO);
            Wizard("/wizardproject/views/Layout.fxml",910,620);
  
        }
    }
    @FXML private void OpenObservations() throws IOException, JAXBException{
        
        FileChooser filechooser = new FileChooser();
        filechooser.setTitle("Importar XML");
        File file = filechooser.showOpenDialog(null);
        
        if(file!=null){
            XmlFile=file.getAbsolutePath();
            ProtocolRead pr = new ProtocolRead();            
            PO = pr.ReadXml(model, XmlFile);
            ParseData(PO);
            Wizard("/wizardproject/sequence/Observations.fxml",675,505);
  
        }
    }
    private void ParseData(ProjectOrganization ProjectOrganization){
        
        model.getProject().setName(ProjectOrganization.getProject().getName());
        model.getProject().setPath(ProjectOrganization.getProject().getPath());
        for(Activity activity:ProjectOrganization.getActivities()){
            model.addActivity(activity);
        }
        for(Participant participant:ProjectOrganization.getParticipants()){
            model.addParticipant(participant);
        }
        for(StagePlugin stageplugin:ProjectOrganization.getCaptureStage().getPlugins()){
            for(StagePlugin plugin: model.getCaptureStage().getPlugins()){ 
                    if(stageplugin.getName().equals(plugin.getName())){           
                            plugin.setConfigurations(stageplugin.getConfigurations());                        
                    }                                     
            }
        }
        
        for(StagePlugin plugin:model.getCaptureStage().getPlugins()){
            for(Configuration configuration:plugin.getConfigurations()){
                model.getCaptureStage().getConfigPluginObservable().add(plugin.getName()+"("+configuration.getName()+")");
            }
        }
    
    
    }

    

}
