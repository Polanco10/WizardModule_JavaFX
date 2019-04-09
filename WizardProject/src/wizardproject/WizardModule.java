package wizardproject;

import wizardproject.model.ProjectOrganization;
import com.google.inject.AbstractModule;
import java.util.ArrayList;
import java.util.List;
import wizardproject.model.Activity;
import wizardproject.model.Configuration;
import wizardproject.model.Participant;
import wizardproject.model.Project;
import wizardproject.model.StageModule;
import wizardproject.model.StagePlugin;

/**
 * Created by carl on 4/30/16.
 */
public class WizardModule extends AbstractModule {
    @Override
    protected void configure() {
        
        Project project = new Project("","");
        List <Participant> participants=new ArrayList<>();
        List <StageModule> stages=new ArrayList<>();
        List<StagePlugin> plugins = new ArrayList<>();
        StageModule StageCapture = new StageModule("captura",plugins);
        stages.add(StageCapture);
        addCapturePlugins(plugins);        
        List <Activity> activities = new ArrayList<>();
        ProjectOrganization model = new ProjectOrganization(project,participants,stages,activities);
        bind(ProjectOrganization.class).toInstance(model);
    }
    
    public List<StagePlugin> addCapturePlugins(List<StagePlugin> plugins){
            List<Configuration> configurations = new ArrayList<>();
            StagePlugin KBplugin = new StagePlugin("Keyboard","BasicConfiguration.fxml",configurations);
            StagePlugin Mplugin = new StagePlugin("Mouse","BasicConfiguration.fxml",configurations);
            plugins.add(KBplugin);
            plugins.add(Mplugin);
            return plugins;
    }
    
}
