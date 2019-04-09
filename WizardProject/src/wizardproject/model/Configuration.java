/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizardproject.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Polanco
 */
@XmlRootElement(name="configuration")
@XmlType(propOrder={"name"})
public class Configuration {
    String name;
    
    public Configuration(){
    this(null);
    }
    public Configuration(String ConfigurationName) {
        this.name = ConfigurationName;
    }
    @XmlElement(name="name")
    public String getName() {
        return name;
    }

    public void setName(String ConfigurationName) {
        this.name = ConfigurationName;
    }
    
}
