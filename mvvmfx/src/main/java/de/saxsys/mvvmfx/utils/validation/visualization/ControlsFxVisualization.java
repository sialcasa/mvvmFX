package de.saxsys.mvvmfx.utils.validation.visualization;

import de.saxsys.mvvmfx.utils.validation.Severity;
import de.saxsys.mvvmfx.utils.validation.ValidationMessage;
import de.saxsys.mvvmfx.utils.validation.ValidationResult;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;
import org.controlsfx.validation.decoration.ValidationDecoration;

import java.util.List;
import java.util.Optional;

/**
 * @author manuel.mauky
 */
public class ControlsFxVisualization implements ValidationVisualization{

    private ValidationDecoration decoration = new GraphicValidationDecoration();

	/**
	 * Define a custom ControlsFX {@link ValidationVisualization} that is used to visualize the validation results.
	 * 
	 * By default the {@link GraphicValidationDecoration} is used. 
	 */
	public void setDecoration(ValidationDecoration decoration) {
		this.decoration = decoration;
	}
	

	@Override
	public void visualize(final ValidationResult result, final Control control, boolean mandatory) {
		if(mandatory) {
			ValidationSupport.setRequired(control, true);
			decoration.applyRequiredDecoration(control);
		}
		
        applyDecoration(control, result.getMessages(), mandatory);
        result.getMessages().addListener((ListChangeListener<ValidationMessage>) c -> {
			while(c.next()) {
                applyDecoration(control, c.getList(), mandatory);
            }
		});
	}

    private void applyDecoration(Control control, List<? extends ValidationMessage> list, boolean mandatory) {
		Optional<? extends ValidationMessage> messageToShow = getHighestMessage(list);
		
		if(messageToShow.isPresent()) {
			final ValidationMessage message = messageToShow.get();
			
			decoration.removeDecorations(control);
			
			if(Severity.ERROR.equals(message.getSeverity())) {
				decoration.applyValidationDecoration(org.controlsfx.validation.ValidationMessage.error(control, message.getMessage()));
			} else if(Severity.WARNING.equals(message.getSeverity())) {
				decoration.applyValidationDecoration(org.controlsfx.validation.ValidationMessage.warning(control, message.getMessage()));
			}
			
		} else {
			decoration.removeDecorations(control);
		}

		if(mandatory) {
			decoration.applyRequiredDecoration(control);
		}
    }
	
	private Optional<? extends ValidationMessage> getHighestMessage(List<? extends ValidationMessage> allMessages) {
		final Optional<? extends ValidationMessage> errorMessageOptional = allMessages.stream()
				.filter(message -> message.getSeverity().equals(Severity.ERROR))
				.findFirst();
		
		if(errorMessageOptional.isPresent()) {
			return errorMessageOptional;
		} else {
			return allMessages.stream()
					.filter(message -> message.getSeverity().equals(Severity.WARNING))
					.findFirst();
		}
	}


}
