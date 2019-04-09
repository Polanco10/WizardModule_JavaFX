package wizardproject.controllers;

import com.google.inject.Inject;
import com.google.inject.Injector;
import java.io.IOException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wizardproject.model.Activity;
import wizardproject.model.ProjectOrganization;

public class ActivitiesController  {

    private Logger log = LoggerFactory.getLogger(ActivitiesController.class);
    
    @FXML private AnchorPane anchorActivity;
    @FXML private TableView<Activity> activityTable;
    @FXML private TableColumn<Activity,String> orderColumn;
    @FXML private TableColumn<Activity, String> activityNameColumn;
    @FXML private Label orderLabel,activityNameLabel,pathLabel,startMessageLabel,endMessageLabel,activityTime;
    @Inject  ProjectOrganization model;
    @Inject  Injector injector;


    @FXML 
    private void handleEditActivity(ActionEvent event) {         
       Activity selectedActivity = activityTable.getSelectionModel().getSelectedItem();
        if (selectedActivity != null) {            
           boolean okClicked = showActivityEditDialog(selectedActivity ,"Editar actividad");
           if(okClicked){
               showActivityDetails(selectedActivity);
               activityTable.refresh();
           }
                            
        } else {
                System.out.println("dialog error");
        }
    }
    @FXML
    private void handleDeleteActivity() {
        Activity selectedIndex = activityTable.getSelectionModel().getSelectedItem();
       
        activityTable.getItems().remove(selectedIndex);
        model.removeActivity(selectedIndex);
        setPositions(model.getActivities());
        
    }
    @FXML 
    private void handleNewActivity(ActionEvent event) {               
        int order = model.getActivities().size()+1;
        Activity activity = new Activity("","",order,0);        
        boolean okClicked = showActivityEditDialog(activity,"Crear actividad");
        if (okClicked){
            model.addActivity(activity);
            activityTable.getItems().clear();
            activityTable.getItems().addAll(model.getObservableActivity());
        }
    }
    
    public void showActivityDetails(Activity activity){
    if (activity != null) {
        orderLabel.setText(Integer.toString(activity.getOrder()));
        activityNameLabel.setText(activity.getName());
        pathLabel.setText(activity.getPath());
        startMessageLabel.setText(activity.getStartMessage());
        endMessageLabel.setText(activity.getEndMessage());
        activityTime.setText(Integer.toString(activity.getTimeExecution()));

        
    } else {
        orderLabel.setText("");
        activityNameLabel.setText("");
        pathLabel.setText("");
        startMessageLabel.setText("");
        endMessageLabel.setText("");
        activityTime.setText("");
    }
}
    public void setPositions(List<Activity> activities){
     int position=1;
    for(Activity activity:activities){
        activity.setOrder(position);
        position++;
    }
    
    }
    public boolean showActivityEditDialog(Activity activity,String Title){
        try{
   
        
        Stage primaryStage = (Stage) anchorActivity.getScene().getWindow(); 
         FXMLLoader loader = new FXMLLoader();
        loader.setLocation(WizardController.class.getResource("/wizardproject/views/EditActivityDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle(Title);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        EditActivityDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setActivity(activity);

        dialogStage.showAndWait();

        return controller.isOkClicked();


    } catch (IOException e) {
        e.printStackTrace();
        return false;
        }
    }

    
    @FXML
    public void initialize() {
 
       orderColumn.setCellValueFactory(new PropertyValueFactory<>("order"));
        activityNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        showActivityDetails(null);
        activityTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showActivityDetails(newValue));
        activityTable.getItems().addAll(model.getObservableActivity());

    }
}

