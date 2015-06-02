package de.saxsys.mvvmfx.utils.validation.visualization;

import de.saxsys.mvvmfx.utils.validation.ValidationMessage;
import de.saxsys.mvvmfx.utils.validation.ValidationResult;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Control;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;
import org.controlsfx.validation.decoration.ValidationDecoration;

/**
 * @author manuel.mauky
 */
public class ControlsFxVisualization implements ValidationVisualization{
    
    

	@Override
	public void visualize(final ValidationResult result, final Control control) {
        ValidationDecoration decoration = new GraphicValidationDecoration();

        result.getErrorMessages().addListener(new ListChangeListener<ValidationMessage>() {
            @Override
            public void onChanged(Change<? extends ValidationMessage> c) {
                System.out.println("onChange ");
                while(c.next()) {
                    System.out.println("im While:" + c.getList().size());
                    if(! c.getList().isEmpty()) {
                        decoration.applyValidationDecoration(org.controlsfx.validation.ValidationMessage.error(control, c.getList().get(0).getMessage()));
                    } else {
                        decoration.removeDecorations(control);
                    }
                }
            }
        });


	}
}
