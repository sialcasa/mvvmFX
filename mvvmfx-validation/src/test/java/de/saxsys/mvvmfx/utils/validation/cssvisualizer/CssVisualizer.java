package de.saxsys.mvvmfx.utils.validation.cssvisualizer;

import de.saxsys.mvvmfx.utils.validation.ValidationMessage;
import de.saxsys.mvvmfx.utils.validation.visualization.ValidationVisualizerBase;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.Control;

public class CssVisualizer extends ValidationVisualizerBase {

	private final String errorStyleClass;
	private final String validStyleClass;
	private final String requiredStyleClass;

	public CssVisualizer(String errorStyleClass, String validStyleClass, String requiredStyleClass) {
		this.errorStyleClass = errorStyleClass;
		this.validStyleClass = validStyleClass;
		this.requiredStyleClass = requiredStyleClass;
	}

	@Override
	protected void applyRequiredVisualization(Control control, boolean required) {
		addIfAbsent(control.getStyleClass(), requiredStyleClass);
	}

	@Override
	public void applyVisualization(Control control, Optional<ValidationMessage> messageOptional, boolean required) {
		if (messageOptional.isPresent()) {
			control.getStyleClass().remove(validStyleClass);
			addIfAbsent(control.getStyleClass(), errorStyleClass);
		} else {
			control.getStyleClass().remove(errorStyleClass);
			addIfAbsent(control.getStyleClass(), validStyleClass);
		}
	}

	private static <T> void addIfAbsent(List<T> list, T element) {
		if (!list.contains(element)) {
			list.add(element);
		}
	}
}
