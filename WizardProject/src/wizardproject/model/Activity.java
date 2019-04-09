/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizardproject.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Polanco
 */
@XmlRootElement(name="activity")
@XmlType(propOrder={"order","name","path","timeExecution","startMessage","endMessage"})
public class Activity {
    private final IntegerProperty order;
    private final StringProperty name;
    private final StringProperty path;
    private final IntegerProperty timeExecution;
    private final StringProperty startMessage;
    private final StringProperty endMessage;

    
    public Activity() {
        this(null,null,0,0);
    }
    public Activity(String activityName, String Path,int Order,int timeExecution) {
        this.name= new SimpleStringProperty(activityName);
        this.path= new SimpleStringProperty(Path);
        this.order= new SimpleIntegerProperty(Order);
        this.timeExecution= new SimpleIntegerProperty(timeExecution);


        //Default Data
        this.startMessage= new SimpleStringProperty("") ;
        this.endMessage= new SimpleStringProperty("");
    }
    /*Getters*/
    public int getOrder() {
        return order.get();
    }
    public int getTimeExecution() {
        return timeExecution.get();
    }
    public String getName() {
        return name.get();
    }

    public String getPath() {
        return path.get();
    }

    public String getStartMessage() {
        return startMessage.get();
    }

    public String getEndMessage() {
        return endMessage.get();
    }

    
    /*Setters*/
    public void setOrder(int order){
    this.order.set(order);
    }
    public void setTimeExecution(int timeExecution){
    this.timeExecution.set(timeExecution);
    }
    public void setName(String aplicationName){
    this.name.set(aplicationName);
    }
    
    public void setStartMessage(String startMessage){
    this.startMessage.set(startMessage);
    }
    
    public void setEndMessage(String endMessage){
    this.endMessage.set(endMessage);
    }
    

    
    public void setPath(String path){
    this.path.set(path);
    }
    /*Properties*/
    
    public StringProperty NameProperty() {
      return name;
    }
    public StringProperty StartMessageProperty() {
      return startMessage;
    }
    public StringProperty EndMessageProperty() {
      return startMessage;
    }
   public IntegerProperty OrderProperty() {
      return order;
    }
    public IntegerProperty TimeExecutionProperty() {
      return timeExecution;
    }

    
    public StringProperty PathProperty() {
      return path;
    }
}
