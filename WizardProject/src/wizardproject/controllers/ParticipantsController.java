package wizardproject.controllers;

import com.google.inject.Inject;
import com.google.inject.Injector;
import java.io.IOException;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wizardproject.model.Participant;
import wizardproject.model.ProjectOrganization;

public class ParticipantsController {

    private Logger log = LoggerFactory.getLogger(ParticipantsController.class);
    private final String CONTROLLER_KEY = "controller";
    
    @Inject  ProjectOrganization model;
    @Inject  Injector injector;
    @FXML private AnchorPane anchorParticipant;
    @FXML private TableView <Participant>participants_table;
    @FXML private TableColumn <Participant,String>id_column;    
    @FXML private TableColumn <Participant,String>name_column;    
    @FXML private TableColumn <Participant,Date>date_column;
    @FXML private TableColumn<Participant, String> note_column;

    
   
        @FXML 
    private void handleEditParticipant(ActionEvent event) {         
       Participant selectedParticipant = participants_table.getSelectionModel().getSelectedItem();
        if (selectedParticipant != null) {            
           boolean okClicked = showParticipantEditDialog(selectedParticipant ,"Editar Participante");
           if(okClicked){
               participants_table.refresh();
           }
                            
        } else {
                System.out.println("dialog error");
        }
    }
    @FXML
    private void handleDeleteParticipant() {
        Participant selectedIndex = participants_table.getSelectionModel().getSelectedItem();
        participants_table.getItems().remove(selectedIndex);
        model.removeParticipant(selectedIndex);
        
    }
    @FXML 
    private void handleNewParticipant(ActionEvent event) {               
        int order = model.getActivities().size()+1;
        Participant participant = new Participant();        
        boolean okClicked = showParticipantEditDialog(participant,"Crear participante");
        if (okClicked){
            model.addParticipant(participant);
            participants_table.getItems().clear();
            participants_table.getItems().addAll(model.getObservableParticipant());
        }
    }
    
  
    public boolean showParticipantEditDialog(Participant participant,String Title){
        try{
   
        
        Stage primaryStage = (Stage) anchorParticipant.getScene().getWindow(); 
         FXMLLoader loader = new FXMLLoader();
        loader.setLocation(WizardController.class.getResource("/wizardproject/views/EditParticipantDialog.fxml"));
        BorderPane page = (BorderPane) loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle(Title);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        EditParticipantDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setParticipant(participant);
        controller.isInputValid(model.getParticipants());

        dialogStage.showAndWait();

        return controller.isOkClicked();


    } catch (IOException e) {
        e.printStackTrace();
        return false;
        }
    }
    
    
    
    
    
    
    
    
    
    @FXML
    public void initialize() {

        id_column.setCellValueFactory(new PropertyValueFactory<>("id"));    
        name_column.setCellValueFactory(new PropertyValueFactory<>("name"));      
        note_column.setCellValueFactory(new PropertyValueFactory<>("note"));
        date_column.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
            participants_table.getItems().addAll(model.getObservableParticipant());
    }
}
