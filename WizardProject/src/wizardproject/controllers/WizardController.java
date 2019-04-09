package wizardproject.controllers;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.inject.Inject;
import com.google.inject.Injector;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.xml.bind.JAXBException;
import wizardproject.model.ProjectOrganization;
import wizardproject.persistence.ProtocolWrite;

public class WizardController {


	private final String CONTROLLER_KEY = "controller";

	@FXML VBox contentPanel;
	@FXML HBox hboxIndicators;
	@FXML Button btnNext, btnBack, btnCancel, btnFinish;
        @FXML private FontAwesomeIconView checkPath, checkParticipant, checkCapture, checkActivity;
	@Inject Injector injector;
	@Inject ProjectOrganization model;
	private final List<Parent> steps = new ArrayList<>();
	private final IntegerProperty currentStep = new SimpleIntegerProperty(-1);
        PathController pathController;
        

	@FXML
	public void initialize() throws Exception {

		buildSteps();
		initButtons();
		setInitialContent();
                
	}

	private void initButtons() {
		btnBack.disableProperty().bind( currentStep.lessThanOrEqualTo(0) );
		btnNext.disableProperty().bind( currentStep.greaterThanOrEqualTo(steps.size()-1) );
                btnFinish.visibleProperty().bind(currentStep.greaterThanOrEqualTo(steps.size()-1));

	}

	private void setInitialContent() {
		currentStep.set( 0 );  // first element
		contentPanel.getChildren().add( steps.get( currentStep.get() ));
	}


	private void buildSteps() throws java.io.IOException {

		final JavaFXBuilderFactory bf = new JavaFXBuilderFactory();

		final Callback<Class<?>, Object> cb = (clazz) -> injector.getInstance(clazz);
		FXMLLoader fxmlLoaderPath = new FXMLLoader( WizardController.class.getResource("/wizardproject/views/Path.fxml"), null, bf, cb);
		Parent path = fxmlLoaderPath.load( );
		path.getProperties().put( CONTROLLER_KEY, fxmlLoaderPath.getController() );

		FXMLLoader fxmlLoaderParticipants = new FXMLLoader( WizardController.class.getResource("/wizardproject/views/Participants.fxml"), null, bf, cb );
		Parent participants = fxmlLoaderParticipants.load();
		participants.getProperties().put( CONTROLLER_KEY, fxmlLoaderParticipants.getController() );

		FXMLLoader fxmlLoaderCapture = new FXMLLoader(WizardController.class.getResource("/wizardproject/views/Capture.fxml"), null, bf, cb );
		Parent capture = fxmlLoaderCapture.load( );
		capture.getProperties().put( CONTROLLER_KEY, fxmlLoaderCapture.getController() );
                               	
                FXMLLoader fxmlLoaderProtocol = new FXMLLoader(WizardController.class.getResource("/wizardproject/views/Activities.fxml"), null, bf, cb );
		Parent activity = fxmlLoaderProtocol.load( );
		activity.getProperties().put( CONTROLLER_KEY, fxmlLoaderProtocol.getController() );
                
                pathController = (PathController)fxmlLoaderPath.getController();
		steps.addAll( Arrays.asList(path, participants, capture,activity));
	}

        public boolean isValid(){          
            
            if(model.getProject().getPath()==null || model.getProject().getName()==null){
                pathController.warning.setVisible(true);
                return false;
            }
            else if(model.getProject().getPath().isEmpty()|| model.getProject().getName().isEmpty()){
                pathController.warning.setVisible(true);
                return false;
            }
            pathController.warning.setVisible(false);

        return true;
        }
	@FXML
	public void next() {
        	Parent p = steps.get(currentStep.get());
                
                if(isValid()){
                    if( currentStep.get() < (steps.size()-1) ) {
                            contentPanel.getChildren().remove( steps.get(currentStep.get()) );
                            currentStep.set( currentStep.get() + 1 );
                            contentPanel.getChildren().add( steps.get(currentStep.get()) );
                            checkPosition(currentStep);
                    }
                    else{
                    }
                    
                }
	}

	@FXML
	public void back() {
		if( currentStep.get() > 0 ) {
			contentPanel.getChildren().remove( steps.get(currentStep.get()) );
			currentStep.set( currentStep.get() - 1 );
			contentPanel.getChildren().add( steps.get(currentStep.get()) );
                        checkPosition(currentStep);

		}
	}

	@FXML
	public void finish() throws IOException, JAXBException {

                ProtocolWrite pw = new ProtocolWrite();
                pw.WriteXml(model,model.getProject().getName());
                Stage primaryStage = (Stage) contentPanel.getScene().getWindow();
                primaryStage.close();

	}
        
        @FXML
        public void cancel(){
            Stage primaryStage = (Stage) contentPanel.getScene().getWindow();
            primaryStage.close();
        }
        public void checkPosition(IntegerProperty currentStep){

            switch(currentStep.getValue()){
                case 0: checkPath.setVisible(true);
                        checkParticipant.setVisible(false);
                break;
                case 1: checkParticipant.setVisible(true);
                        checkCapture.setVisible(false);
                        checkPath.setVisible(false);
                break;
                case 2: checkCapture.setVisible(true);
                        checkActivity.setVisible(false);
                        checkParticipant.setVisible(false);
                break;
                case 3: checkActivity.setVisible(true);  
                        checkCapture.setVisible(false);
                break;                

            }
                    
        
        }


}
