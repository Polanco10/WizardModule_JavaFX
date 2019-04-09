package wizardproject.controllers;

import com.google.inject.Inject;
import com.google.inject.Injector;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wizardproject.model.Configuration;
import wizardproject.model.ProjectOrganization;
import wizardproject.model.StagePlugin;

public class CaptureController {

    private Logger log = LoggerFactory.getLogger(CaptureController.class);
    private final String CONTROLLER_KEY = "controller";


    @Inject ProjectOrganization model;
    @Inject  Injector injector;
    @FXML private ListView PluginListView;
    @FXML private ListView ConfigListView;    
    @FXML private AnchorPane anchorCapture;
    ObservableList<String> ObservablePlugins = FXCollections.observableArrayList();

    
    public ObservableList<String> addObservablePlugin(){
        for(int i=0;i<model.getCaptureStage().getPlugins().size();i++){
            ObservablePlugins.add(model.getCaptureStage().getPlugins().get(i).getName());
        }
    return ObservablePlugins;    
    }
    
    @FXML 
    private void addConfiguration(){
        try{
        Configuration configuration = new Configuration();
        StagePlugin Plugin;
        for(int i=0;i<model.getCaptureStage().getPlugins().size();i++){
            Plugin=model.getCaptureStage().getPlugins().get(i);
             if(PluginListView.getSelectionModel().getSelectedItem().toString().equals(Plugin.getName())){
                 openDialog(configuration, Plugin); 
                 if(configuration.getName()!=null){                  
                     model.getCaptureStage().addConfigurationPlugin(Plugin, configuration); 

                 }
            }
            
        }
        }catch(Exception e){
        }    
    }
    
    @FXML
    private void removeConfiguration(){
        Configuration configuration;
        StagePlugin Plugin;
        String SelectedItem= ConfigListView.getSelectionModel().getSelectedItem().toString();
        String CompareItem;     
        
        
        for(int i=0; i<model.getCaptureStage().getPlugins().size();i++){
            Plugin=model.getCaptureStage().getPlugins().get(i);
            for(int j=0;j<Plugin.getConfigurations().size();j++){
                configuration=Plugin.getConfigurations().get(j);
                CompareItem=Plugin.getName()+"("+configuration.getName()+")"; 
                if(SelectedItem.equals(CompareItem)){
                    ConfigListView.getItems().remove(SelectedItem);
                    model.getCaptureStage().getConfigPluginObservable().remove(CompareItem);
                    Plugin.removeConfiguration(configuration);
                }
            }

        }
    }
    
    public void openDialog(Configuration configuration,StagePlugin Plugin){
        try{
        Stage primaryStage = (Stage) anchorCapture.getScene().getWindow(); 
         FXMLLoader loader = new FXMLLoader();
        loader.setLocation(WizardController.class.getResource("/wizardproject/views/"+Plugin.getView()));
        BorderPane page = (BorderPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Configuracion");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
  
            
            BasicConfigurationController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setConfiguration(configuration,Plugin);

            dialogStage.showAndWait();     

     } catch (IOException e) {
        e.printStackTrace();
        }
    }
   

    
    @FXML
    public void initialize() {

        addObservablePlugin();
        PluginListView.setItems(ObservablePlugins);
        ConfigListView.setItems(model.getCaptureStage().getConfigPluginObservable());

    }

  

}


