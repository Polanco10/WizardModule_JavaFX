/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizardproject.model;

import java.util.List;
import java.util.logging.Logger;
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
@XmlRootElement(name="plugin")
@XmlType(propOrder={"name","view","configurations"})
public class StagePlugin {
    public final static Logger LOGGER = Logger.getLogger(StagePlugin.class.getName());

  
    String name;
    String view;
    List<Configuration> configurations;

   
    public StagePlugin(String name,String view,List<Configuration> configurations){
        this.name=name;
        this.view=view;
        this.configurations=configurations;    
    }
    public StagePlugin(){
    this(null,null,null);
    }

    public void setName(String PluginName) {
        this.name = PluginName;
    }

    public void setView(String View) {
        this.view = View;
    }

    public void setConfigurations(List<Configuration> configurations) {
        this.configurations = configurations;
    }

    @XmlElement(name="name")
    public String getName() {
        return name;
    }
    @XmlElement(name="view")
    public String getView() {
        return view;
    }
    @XmlElementWrapper(name="configurations")
    @XmlElement(name="configuration")
    public List<Configuration> getConfigurations() {
        return configurations;
    }
    public List<Configuration> addConfiguration(Configuration configuration){
        configurations.add(configuration);
        return configurations;
    
    }
    public List<Configuration> removeConfiguration(Configuration configuration){
        configurations.remove(configuration);
        return configurations;
    
    }

    

}
