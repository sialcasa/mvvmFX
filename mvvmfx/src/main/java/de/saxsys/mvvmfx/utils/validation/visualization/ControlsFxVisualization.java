package de.saxsys.mvvmfx.utils.validation.visualization;

import de.saxsys.mvvmfx.utils.validation.ValidationMessage;
import de.saxsys.mvvmfx.utils.validation.ValidationResult;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;
import org.controlsfx.validation.decoration.ValidationDecoration;

/**
 * @author manuel.mauky
 */
public class ControlsFxVisualization implements ValidationVisualization{

    private ValidationDecoration decoration = new GraphicValidationDecoration();

	@Override
	public void visualize(final ValidationResult result, final Control control) {
        applyDecoration(control, result.getErrorMessages());
        result.getMessages().addListener((ListChangeListener<ValidationMessage>) c -> {
			while(c.next()) {
                applyDecoration(control, c.getList());
            }
		});
	}

    private void applyDecoration(Control control, ObservableList<? extends ValidationMessage> list) {
        if(! list.isEmpty()) {
            list.forEach(message -> decoration.applyValidationDecoration(
					org.controlsfx.validation.ValidationMessage.error(control, message.getMessage())));
		} else {
			decoration.removeDecorations(control);
		}
    }


}
