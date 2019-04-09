/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizardproject.persistence;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import wizardproject.model.ProjectOrganization;


/**
 *
 * @author Polanco
 */
public class ProtocolRead {
    public ProjectOrganization ReadXml(ProjectOrganization ProjectOrganization,String File) throws JAXBException{
       JAXBContext context= JAXBContext.newInstance(ProjectOrganization.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        ProjectOrganization = (ProjectOrganization) unmarshaller.unmarshal(new File(File));
    return ProjectOrganization;
    }

}
