package de.saxsys.mvvmfx.utils.validation.visualization;

import de.saxsys.mvvmfx.utils.validation.ValidationResult;
import javafx.scene.control.Control;

/**
 * @author manuel.mauky
 */
public interface ValidationVisualization {
	
	void visualize(ValidationResult result, Control control);
	
}
