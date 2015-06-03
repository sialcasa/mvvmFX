package de.saxsys.mvvmfx.utils.validation.visualization;

import de.saxsys.mvvmfx.utils.validation.ValidationResult;
import javafx.scene.control.Control;

/**
 * @author manuel.mauky
 */
public interface ValidationVisualization {
	
	default void visualize(ValidationResult result, Control control) {
		visualize(result, control, false);
	}
	
	void visualize(ValidationResult result, Control control, boolean mandatory);
	
}
