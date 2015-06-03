package de.saxsys.mvvmfx.utils.validation.visualization;

import de.saxsys.mvvmfx.utils.validation.ValidationMessage;
import de.saxsys.mvvmfx.utils.validation.ValidationResult;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;
import org.controlsfx.validation.decoration.ValidationDecoration;

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
		
        applyDecoration(control, result.getErrorMessages(), mandatory);
        result.getMessages().addListener((ListChangeListener<ValidationMessage>) c -> {
			while(c.next()) {
                applyDecoration(control, c.getList(), mandatory);
            }
		});
	}

    private void applyDecoration(Control control, ObservableList<? extends ValidationMessage> list, boolean mandatory) {
        if(! list.isEmpty()) {
            list.forEach(message -> decoration.applyValidationDecoration(
					org.controlsfx.validation.ValidationMessage.error(control, message.getMessage())));
		} else {
			decoration.removeDecorations(control);
			if(mandatory) {
				decoration.applyRequiredDecoration(control);
			}
		}
		
    }


}
