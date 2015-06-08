package de.saxsys.mvvmfx.utils.validation.visualization;

import de.saxsys.mvvmfx.utils.validation.ValidationResult;
import javafx.scene.control.Control;

/**
 * @author manuel.mauky
 */
public interface ValidationVisualization {
	
	default void initVisualization(ValidationResult result, Control control) {
		initVisualization(result, control, false);
	}
	
	void initVisualization(ValidationResult result, Control control, boolean mandatory);
	
}
