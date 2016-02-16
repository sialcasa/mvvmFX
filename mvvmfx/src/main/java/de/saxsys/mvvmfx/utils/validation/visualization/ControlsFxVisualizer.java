/*******************************************************************************
 * Copyright 2015 Alexander Casall, Manuel Mauky
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.saxsys.mvvmfx.utils.validation.visualization;

import de.saxsys.mvvmfx.utils.validation.Severity;
import de.saxsys.mvvmfx.utils.validation.ValidationMessage;
import javafx.scene.control.Control;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;
import org.controlsfx.validation.decoration.ValidationDecoration;

import java.util.Optional;

/**
 * An implementation of {@link ValidationVisualizer} that uses the third-party library <a
 * href="http://fxexperience.com/controlsfx/">ControlsFX</a> to visualize validation messages.
 * <p>
 * <strong>Please Note:</strong> The library ControlsFX is not delivered with the mvvmFX library. If you like to use
 * this visualization you have to add the ControlsFX library to your classpath, otherwise you will get
 * {@link NoClassDefFoundError}s and {@link ClassNotFoundException}s. If you are using a build management system like
 * <i>maven</i> or <i>gradle</i> you simply have to add the library as dependency.
 * 
 * 
 * @author manuel.mauky
 */
public class ControlsFxVisualizer extends ValidationVisualizerBase {
	
	private ValidationDecoration decoration = new GraphicValidationDecoration();
	
	/**
	 * Define a custom ControlsFX {@link ValidationVisualizer} that is used to visualize the validation results.
	 * <p>
	 * By default the {@link GraphicValidationDecoration} is used.
	 */
	public void setDecoration(ValidationDecoration decoration) {
		this.decoration = decoration;
	}
	
	
	@Override
	void applyRequiredVisualization(Control control, boolean required) {
		ValidationSupport.setRequired(control, required);
		if (required) {
			decoration.applyRequiredDecoration(control);
		}
	}
	
	@Override
	void applyVisualization(Control control, Optional<ValidationMessage> messageOptional, boolean required) {
		
		if (messageOptional.isPresent()) {
			final ValidationMessage message = messageOptional.get();
			
			decoration.removeDecorations(control);
			
			if (Severity.ERROR.equals(message.getSeverity())) {
				decoration.applyValidationDecoration(org.controlsfx.validation.ValidationMessage.error(control,
						message.getMessage()));
			} else if (Severity.WARNING.equals(message.getSeverity())) {
				decoration.applyValidationDecoration(org.controlsfx.validation.ValidationMessage.warning(control,
						message.getMessage()));
			}
			
		} else {
			decoration.removeDecorations(control);
		}
		
		if (required) {
			decoration.applyRequiredDecoration(control);
		}
	}
	
}
