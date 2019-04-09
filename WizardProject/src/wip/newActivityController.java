/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wip;

import com.google.inject.Inject;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import wizardproject.model.ProjectOrganization;

/**
 *
 * @author Polanco
 */
public class newActivityController implements Initializable{
    @FXML private TextField name_field;
    @FXML private TextField path_field;
    @FXML  private BorderPane borderpane;
    @FXML private Label warning;

    private String activityPath,activityName;
    
    @Inject
    ProjectOrganization model;
    
    public void getData(){
    activityName=name_field.getText();
    activityPath=path_field.getText();    
    }
    
    @FXML private void FileChooser(ActionEvent event){
        FileChooser filechooser = new FileChooser();
        filechooser.setTitle("Actividades externas a MO");
        File file = filechooser.showOpenDialog(null);
        if(file!=null){
            path_field.setText(file.getAbsolutePath());
        }  
    } 
     @FXML
    private void AddActivity(ActionEvent event) {
        getData();
        if(activityName.isEmpty() || activityPath.isEmpty()){
                warning.setText("Complete los campos obligatorios (*)");
                warning.setVisible(true); 
        }
        else{
           // model.addActivity(activityName, activityPath);
            Stage dialogStage = (Stage) borderpane.getScene().getWindow();
            dialogStage.close();
        }        
        
     }
    @FXML
    public void closeDialog(ActionEvent event){
        Stage dialogStage = (Stage) borderpane.getScene().getWindow();
        dialogStage.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        warning.setVisible(false);
       


       
    }    
       
    
}
