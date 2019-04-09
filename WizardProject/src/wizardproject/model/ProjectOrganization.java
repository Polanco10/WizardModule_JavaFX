/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizardproject.model;

import com.google.inject.Inject;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Polanco
 */
@XmlRootElement(name="protocol")
@XmlType(propOrder={"project","participants","stages","activities"})

public class ProjectOrganization {
    Project project;
    List<Participant> participants ;
    List<StageModule> stages;
    List<Activity> activities;
    ObservableList<Activity> activitiesObservable = FXCollections.observableArrayList();
    ObservableList<Participant> participantsObservable = FXCollections.observableArrayList();

    
    @Inject
    public ProjectOrganization( Project project, List<Participant> participants, List<StageModule> stages, List<Activity> activities){
    this.project=project;
    this.participants=participants;
    this.stages=stages;
    this.activities=activities;
    }
    public ProjectOrganization(){
    this(null,null,null,null);
    }


    
      public void addParticipant(Participant participant){
      participants.add(participant);   
      participantsObservable.add(participant);
    }
      public List<Participant> removeParticipant(Participant participant){
      participants.remove(participant);
      participantsObservable.remove(participant);
      return participants;
      }
    public ObservableList<Participant> getObservableParticipant(){        
    return participantsObservable;
    }
      
    public Participant getParticipant(int index){
     return participants.get(index);     
    }
    public ObservableList<Activity> getObservableActivity(){        
    return activitiesObservable;
    }
    
    public void addActivity(Activity activity){ 
      activitiesObservable.add(activity);        
      activities.add(activity);  
      
    }
    
    public List<Activity> removeActivity(Activity activity){
        activities.remove(activity);
        activitiesObservable.remove(activity);     
      return activities;       
    }
    @XmlElementWrapper(name="activities")
    @XmlElement(name="activity")
    public List<Activity> getActivities(){
    return activities;
    }
    public Activity getActivity(int index){
     return activities.get(index);     
    }

    @XmlElement(name="project")
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    @XmlElementWrapper(name="participants")
    @XmlElement(name="participant")
    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }
    @XmlElementWrapper(name="stages")
    @XmlElement(name="stage")
    public List<StageModule> getStages() {
        return stages;
    }

    public StageModule getCaptureStage(){
        return stages.get(0);
    }

    public void setStages(List<StageModule> stages) {
        this.stages = stages;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
    
    
}
