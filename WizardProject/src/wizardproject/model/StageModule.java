/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizardproject.model;

import java.io.File;
import java.util.LinkedList;
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
@XmlRootElement(name="stage")
@XmlType(propOrder={"name","plugins"})

public class StageModule {
    
    String name;
    List<StagePlugin> plugins;
    ObservableList<String> configPluginObservable = FXCollections.observableArrayList();


    public StageModule(String name, List<StagePlugin> plugins){
        this.name=name;
        this.plugins=plugins;       
    }
    public StageModule(){
    this(null,null);
    }
    @XmlElement(name="name")
    public String getName() {
        return name;
    }
    @XmlElementWrapper(name="plugins")
    @XmlElement(name="plugin")
    public List<StagePlugin> getPlugins(){ 
        if (plugins == null) {
            plugins =  new LinkedList<>();
        }
        return plugins;
    }
    public ObservableList<String> getConfigPluginObservable(){
    return configPluginObservable;
    }
    public ObservableList<String> addConfigurationPlugin(StagePlugin plugin,Configuration configuration){
        String pluginName=plugin.getName();
        String configurationName=configuration.getName();
        configPluginObservable.add(pluginName+"("+configurationName+")");
           
    return configPluginObservable;
    }
}
