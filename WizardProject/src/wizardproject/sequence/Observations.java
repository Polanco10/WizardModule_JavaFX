/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizardproject.sequence;

import com.google.inject.Inject;
import com.google.inject.Injector;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import wizardproject.model.Participant;
import wizardproject.model.ProjectOrganization;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import wizardproject.model.Activity;
import javafx.scene.control.Label;
import javax.xml.bind.JAXBException;
import wizardproject.persistence.ProtocolWrite;

/**
 *
 * @author Polanco
 */
public class Observations  {
    @Inject Injector injector;
    @Inject ProjectOrganization model;
    @FXML private TableView<Participant>participants_table;
    @FXML private TableColumn<Participant,String>id_column;    
    @FXML private TableColumn<Participant,String>name_column; 
    @FXML private TableColumn<Participant,Boolean>observed_column; 
    @FXML private AnchorPane anchorObservations;
    @FXML private Label nameProject,activities,durationActivities,totalParticipants,participantsObserved;
    int millis=1000;

    
    @FXML
    public void runActivity() throws InterruptedException{
    Participant selected = participants_table.getSelectionModel().getSelectedItem();
    if(!wasObserved(selected)){
        ActivitiesProcess();
        selected.setObserved(Boolean.TRUE);
        setLabels();
        participants_table.refresh();
        showingWindow();
    }

    }
    public void showMessageDialog(String TypeMessage,String ActivityName,String MessageContent){

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(TypeMessage);
        alert.setHeaderText(ActivityName);
        alert.setContentText(MessageContent); 
        alert.showAndWait();
    
    
    }
   public void hidingWindow(){
           Stage primaryStage = (Stage) anchorObservations.getScene().getWindow(); 
            primaryStage.hide();
   }
       public void showingWindow(){
           Stage primaryStage = (Stage) anchorObservations.getScene().getWindow(); 
            primaryStage.show();
   }
    public void ActivitiesProcess() throws InterruptedException{
        for(Activity activity:model.getActivities()){
 
                showMessageDialog("Mensaje de inicio",activity.getName(),activity.getStartMessage());
                execute(activity.getPath());
                hidingWindow();
               sleepAtLeast(activity.getTimeExecution()*millis);
                showMessageDialog("Mensaje de fin",activity.getName(),activity.getEndMessage());  
                
                   
        }        
    }

    
    public void sleepAtLeast(long millis) throws InterruptedException {
        long t0 = System.currentTimeMillis();
        long millisLeft = millis;
         while (millisLeft > 0) {
             
        Thread.sleep(millisLeft);
        long t1 = System.currentTimeMillis();
        millisLeft = millis - (t1 - t0);
  }
}

    public boolean wasObserved(Participant participant){
        return participant.getObserved();
    }
    public void execute(String path){
        String os = System.getProperty("os.name").toLowerCase();
        Runtime app = Runtime.getRuntime();

    try{
        if (os.indexOf( "win" ) >= 0) {
        app.exec("cmd /c "+path);        
        }else if (os.indexOf( "mac" ) >= 0) {
          app.exec(path);

        } else if (os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0) {
        app.exec(new String[] { "sh", "-c", path });
        }

    }catch(Exception e){
        System.out.println(e);}
    }
    @FXML
    public void closeDialog() throws IOException, JAXBException{
        Stage primaryStage = (Stage) anchorObservations.getScene().getWindow(); 
        primaryStage.close();  
        ProtocolWrite pw = new ProtocolWrite();
        pw.WriteXml(model,model.getProject().getName());
        
    }
    /*public void killProcess(String path){
      
        String osName = System.getProperty("os.name");
        String system =  "";
        String slash_separator = path.substring(path.lastIndexOf("/")+1);
        String[] process = slash_separator.split("\\.",2);
        System.out.println(process[0]);
        
 
        if(osName.toUpperCase().contains("WIN")){   //Windows
            system+="tskill " + process[0];
        } else {                                    //Linux
            system+="killall " + process[0];
        }      
        Process hijo;
        try {
            hijo = Runtime.getRuntime().exec(system);
            System.out.println(system);
            hijo.waitFor();
            if ( hijo.exitValue()==0){
                System.out.println( path + " Killed" );
            }else{
		System.out.println( "Cannot kill " + path + ". Exit code: " + hijo.exitValue() );
            }
        } catch (IOException e) {
            System.out.println("Incapaz de matar soffice.");
        } catch (InterruptedException e) {
            System.out.println("Incapaz de matar soffice.");
        }      
    }*/
    public void setLabels(){
        String nameActivities="";
        int duration=0;
        int countObserved=0;
        for(int i=0;i<model.getActivities().size();i++){
            duration+=model.getActivities().get(i).getTimeExecution();
            if(i!=model.getActivities().size()-1){
            nameActivities+=model.getActivities().get(i).getName()+",";
            }
            else{
            nameActivities+=model.getActivities().get(i).getName()+".";
            }            
        }
        for(int i=0;i<model.getParticipants().size();i++){
            if(model.getParticipants().get(i).getObserved()){
            countObserved+=1;
            }            
        }
        
        
    nameProject.setText(model.getProject().getName());
    activities.setText(nameActivities);
    durationActivities.setText(Integer.toString(duration)+"(s)");
    totalParticipants.setText(Integer.toHexString(model.getParticipants().size()));
    participantsObserved.setText(Integer.toString(countObserved));
    }
    
    
        
    @FXML
    public void initialize() {

        id_column.setCellValueFactory(new PropertyValueFactory<>("id"));    
        name_column.setCellValueFactory(new PropertyValueFactory<>("name"));
        observed_column.setCellValueFactory(new PropertyValueFactory<>("observed"));
        observed_column.setCellFactory(column -> new CheckBoxTableCell());         
        participants_table.getItems().addAll(model.getObservableParticipant());
        setLabels();
    }
}
