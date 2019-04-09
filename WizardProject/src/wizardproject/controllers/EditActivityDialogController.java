/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizardproject.controllers;




import com.jfoenix.controls.JFXRadioButton;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import wizardproject.model.Activity;


public class EditActivityDialogController {

    @FXML private TextField startMessageField, endMessageField,pathField,nameField;
    @FXML private Label warning,labelOrder;
    @FXML private Spinner<Integer> SpinnerTime;
    SpinnerValueFactory<Integer> timeValueFactory;


    private Activity activity;
        
    private boolean okClicked = false;
    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public boolean isOkClicked() {
        return okClicked;
    }
    public void setActivity(Activity activity){
        this.activity = activity;        
        timeValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 3600,1);
        SpinnerTime.setValueFactory(timeValueFactory);
        SpinnerTime.setEditable(true);
        TextFormatter formatter = new TextFormatter(timeValueFactory.getConverter(), timeValueFactory.getValue());
        SpinnerTime.getEditor().setTextFormatter(formatter);
        timeValueFactory.valueProperty().bindBidirectional(formatter.valueProperty());        
        nameField.setText(activity.getName());
        startMessageField.setText(activity.getStartMessage());
        endMessageField.setText(activity.getEndMessage());
        pathField.setText(activity.getPath());
        labelOrder.setText(Integer.toString(activity.getOrder()));

    
    }
    private boolean isInputValid() {
        if(nameField.getText().isEmpty()|| pathField.getText().isEmpty()){
            warning.setVisible(true);
            return false;
        }
        
        return true;
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            activity.setName(nameField.getText());
            activity.setPath(pathField.getText());
            activity.setTimeExecution(SpinnerTime.getValue());
            if (startMessageField.getText()==null) {
                activity.setStartMessage("");
            }else{activity.setStartMessage(startMessageField.getText());}
            if (endMessageField.getText()==null) {            
                activity.setEndMessage("");
            }else{
                activity.setEndMessage(endMessageField.getText());
            }

            okClicked = true;
            dialogStage.close();
        }
    }
    
    @FXML 
    private void FileChooser(ActionEvent event){
        FileChooser filechooser = new FileChooser();
        filechooser.setTitle("Actividades externas a MO");
        File file = filechooser.showOpenDialog(null);
        if(file!=null){
            pathField.setText(file.getAbsolutePath());
        }  
    } 
    
    @FXML
    private void initialize() {
        warning.setVisible(false);
    }


}