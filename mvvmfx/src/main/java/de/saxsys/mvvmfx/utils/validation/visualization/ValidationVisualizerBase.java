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
import de.saxsys.mvvmfx.utils.validation.ValidationStatus;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Control;

import java.util.Optional;

/**
 * A base class for implementations of {@link ValidationVisualizer}s.
 * <p>
 * Implementors using this base class only need to implement logic on how to visualize a single
 * {@link ValidationMessage} (see {@link #applyVisualization(Control, Optional, boolean)}) and the required flag (see
 * {@link #applyRequiredVisualization(Control, boolean)}).
 * <p>
 * This base class takes care for the handling of the {@link ValidationStatus} and the reaction to it's changing message
 * lists.
 * 
 * @author manuel.mauky
 */
public abstract class ValidationVisualizerBase implements ValidationVisualizer {
	
	
	@Override
	public void initVisualization(final ValidationStatus result, final Control control, boolean required) {
		if (required) {
			applyRequiredVisualization(control, required);
		}
		
		applyVisualization(control, result.getHighestMessage(), required);
		
		result.getMessages().addListener((ListChangeListener<ValidationMessage>) c -> {
			while (c.next()) {
				Platform.runLater(() -> applyVisualization(control, result.getHighestMessage(), required));
			}
		});
	}
	
	/**
	 * Apply a visualization to the given control that indicates that it is a mandatory field.
	 * <p>
	 * This method is called when the validator is initialized.
	 * 
	 * @param control
	 *            the controls that has to be decorated.
	 * @param required
	 *            a boolean indicating whether the given control is mandatory or not.
	 */
	abstract void applyRequiredVisualization(Control control, boolean required);
	
	/**
	 * Apply a visualization to the given control that shows a validation message.
	 * <p>
	 * This method will be called every time the validation state changes. If the given {@link Optional} for the
	 * {@link ValidationMessage} is empty, no validation rule is violated at the moment and therefore no error/warning
	 * should be shown.
	 * <p>
	 * A visualizer can handle the {@link Severity} that is provided in the visualization message (
	 * {@link ValidationMessage#getSeverity()}).
	 * <p>
	 * The given boolean parameter indicates whether this controls is mandatory or not. It can be used if a violation
	 * for a mandatory field should be visualized differently than a non-mandatory field.
	 * 
	 * 
	 * @param control
	 *            the control that will be decorated.
	 * @param messageOptional
	 *            an optional containing the validation message with the highest priority, or an empty Optional if no
	 *            validation rule is violated at the moment.
	 * @param required
	 *            a boolean flag indicating whether this control is mandatory or not.
	 */
	abstract void applyVisualization(Control control, Optional<ValidationMessage> messageOptional, boolean required);
	
}
