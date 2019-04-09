/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizardproject.model;

import java.io.File;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Polanco
 */    
@XmlRootElement(name="project")
@XmlType(propOrder={"name","path"})
public class Project {
    private static final Logger LOGGER = Logger.getLogger(Project.class.getName());
    private StringProperty name = new SimpleStringProperty();
    private StringProperty path = new SimpleStringProperty();

    

    public Project(String name,String path){
        this.name.set(name);
        this.path.set(path);
    }
    public Project(){
    this(null,null);
    }
    

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String nameProject) {
        this.name.set(nameProject);
    }
    @XmlElement(name="name")
    public String getName(){
        return name.get();
    
    }
    public StringProperty pathProperty() {
        return path;
    }

    public void setPath(String path) {
        this.path.set(path);
    }
    @XmlElement(name="path")
    public String getPath(){
        return path.get();
    }

}
