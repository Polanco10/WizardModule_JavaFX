/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizardproject.persistence;


import java.io.FileWriter;
import java.io.IOException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import wizardproject.model.ProjectOrganization;


/**
 *
 * @author Polanco
 */
public class ProtocolWrite {
    public void WriteXml(ProjectOrganization ProjectOrganization,String File) throws IOException, JAXBException{
  
        
        
        JAXBContext context = JAXBContext.newInstance(ProjectOrganization.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
        marshaller.marshal(ProjectOrganization,new FileWriter(File+".xml") );
    }
    
    

}
