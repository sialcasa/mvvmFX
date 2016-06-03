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

import de.saxsys.mvvmfx.utils.validation.ValidationStatus;
import javafx.scene.control.Control;

/**
 * Common interface for all implementations of validation visualizers.
 * <p>
 * A single instance of a visualizer is connected to a single {@link ValidationStatus} that it visualizes. When the
 * state of the {@link ValidationStatus} changes the visualizer has to react to these changes and update it's decoration
 * accordingly.
 * <p>
 * Besides showing validation messages the job of the visualizer is to mark an input control as mandatory. Note that
 * this mark is only a visual effect and has no effect to the actual validation logic.
 * <p>
 *
 * Instead of directly implementing this interface implementors of custom visualizers should consider to extend from the
 * base class {@link ValidationVisualizerBase}. This base class handles the live cycle of the {@link ValidationStatus}
 * (i.e. listeners on the observable lists of validation messages). The implementor only needs to implement on how a
 * single message should be shown and how a control is marked as mandatory.
 *
 * @author manuel.mauky
 */
public interface ValidationVisualizer {
	
	/**
	 * Initialize this visualization so that it visualizes the given {@link ValidationStatus} on the given input
	 * control.
	 *
	 * @param status
	 *            the status that is visualized.
	 * @param control
	 *            the control that will be decorated.
	 */
	default void initVisualization(ValidationStatus status, Control control) {
		initVisualization(status, control, false);
	}
	
	/**
	 * Initialize this visualization so that it visualizes the given {@link ValidationStatus} on the given input
	 * control.
	 *
	 * @param status
	 *            the status that is visualized.
	 * @param control
	 *            the control that will be decorated.
	 * @param mandatory
	 *            a boolean flag indicating whether this input value is mandatory or not.
	 */
	void initVisualization(ValidationStatus status, Control control, boolean mandatory);
	
}
