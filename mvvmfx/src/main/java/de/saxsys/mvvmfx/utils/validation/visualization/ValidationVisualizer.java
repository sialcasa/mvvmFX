package de.saxsys.mvvmfx.utils.validation.visualization;

import de.saxsys.mvvmfx.utils.validation.ValidationStatus;
import javafx.scene.control.Control;

/**
 * Common interface for all implementations of validation visualizers.
 *
 * A single instance of a visualizer is connected to a single {@link ValidationStatus} that it visualizes.
 * When the state of the {@link ValidationStatus} changes the visualizer has to react to these changes
 * and update it's decoration accordingly.
 *
 * Besides showing validation messages the job of the visualizer is to mark an input control as mandatory.
 * Note that this mark is only a visual effect and has no effect to the actual validation logic.
 *
 *
 * Instead of directly implementing this interface implementors of custom visualizers should consider to extend from
 * the base class {@link ValidationVisualizerBase}. This base class handles the live cycle
 * of the {@link ValidationStatus} (i.e. listeners on the observable lists of validation messages).
 * The implementor only needs to implement on how a single message should be shown and how a control is marked as mandatory.
 *
 * @author manuel.mauky
 */
public interface ValidationVisualizer {

    /**
     * Initialize this visualization so that it visualizes the given {@link ValidationStatus} on the given input control.
     *
     * @param status the status that is visualized.
     * @param control the control that will be decorated.
     */
	default void initVisualization(ValidationStatus status, Control control) {
		initVisualization(status, control, false);
	}

    /**
     * Initialize this visualization so that it visualizes the given {@link ValidationStatus} on the given input control.
     *
     * @param status the status that is visualized.
     * @param control the control that will be decorated.
     * @param mandatory a boolean flag indicating whether this input value is mandatory or not.
     */
	void initVisualization(ValidationStatus status, Control control, boolean mandatory);
	
}
