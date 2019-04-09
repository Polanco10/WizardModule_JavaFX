package wizardproject.controllers;

import com.google.inject.Inject;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wizardproject.model.ProjectOrganization;

public class PathController {

    private Logger log = LoggerFactory.getLogger(PathController.class);
    
    @FXML private TextField tfNameProject;
    @FXML private TextField tfFolderProject;
    @Inject ProjectOrganization model;
    @FXML public Label warning;

    @FXML 
    private void DirectoryChooser(ActionEvent event){
        final DirectoryChooser dirchooser = new DirectoryChooser();
        File file = dirchooser.showDialog(null);
        if (file!=null){
            tfFolderProject.setText(file.getAbsolutePath());
        }
      } 
    
    @FXML
    public void initialize() {

       tfNameProject.textProperty().bindBidirectional( model.getProject().nameProperty() );
       tfFolderProject.textProperty().bindBidirectional( model.getProject().pathProperty());
    }

  
}
