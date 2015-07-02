package de.saxsys.mvvmfx.examples.fx_root_example;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import de.saxsys.mvvmfx.ViewModel;

public class LabeledTextFieldViewModel implements ViewModel {
	private StringProperty labelText = new SimpleStringProperty("default");
	
	private BooleanProperty buttonDisabled = new SimpleBooleanProperty();
	
	private StringProperty inputText = new SimpleStringProperty("");
	
	public LabeledTextFieldViewModel() {
		buttonDisabled.bind(inputText.isEmpty());
	}
	
	public void changeLabel() {
		labelText.set(inputText.get());
		inputText.set("");
	}
	
	public ReadOnlyStringProperty labelTextProperty() {
		return labelText;
	}
	
	public ReadOnlyBooleanProperty buttonDisabledProperty() {
		return buttonDisabled;
	}
	
	public StringProperty inputTextProperty() {
		return inputText;
	}
}
