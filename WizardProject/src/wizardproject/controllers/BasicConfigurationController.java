/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizardproject.controllers;

import com.google.inject.Inject;
import com.google.inject.Injector;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import wizardproject.model.Configuration;
import wizardproject.model.ProjectOrganization;
import wizardproject.model.StagePlugin;

/**
 *
 * @author Polanco
 */
public class BasicConfigurationController implements Initializable{


    @FXML private TextField name_field;
    @FXML private Label warning;
    @FXML private BorderPane borderpane;
 //   @FXML private ComboBox FPS; 
  //  @FXML private ComboBox Dimension; 
   // @FXML private ComboBox Screen; 
    @Inject ProjectOrganization model;
    @Inject  Injector injector;
    private Stage dialogStage;
    private StagePlugin Plugin;
    private Configuration Configuration;

    
    //@FXML ObservableList<String> Fraps =  FXCollections.observableArrayList("5","15","30","45","60");
    //@FXML ObservableList<String> pantalla =  FXCollections.observableArrayList("/Display0");
    //@FXML ObservableList<String> Dimen =  FXCollections.observableArrayList("176x144","640x480","800x600","1024x768","1280x720");

    @FXML
    public void handleOk(){
        if(isNotNull()){
            Configuration.setName(name_field.getText());
            Plugin.addConfiguration(Configuration);
            dialogStage.close();    

        }
    }
       @FXML
    public void handleClose(){

            dialogStage.close();    

       
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void setConfiguration(Configuration configuration,StagePlugin Plugin){
        this.Configuration=configuration;
        this.Plugin=Plugin;
        name_field.setText(configuration.getName());
        
    }
        public boolean isNotNull(){
        if(name_field.getText()==null){
            warning.setText("Complete los campos obligatorios (*)");
            warning.setVisible(true);
        return false;
        }
        return true;
    }
  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        warning.setVisible(false);
    }    
    
}
