package de.saxsys.mvvmfx.internal.viewloader.prevent_gc.example1;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PreventGcTestViewModel implements ViewModel {

	private StringProperty input = new SimpleStringProperty();
	private StringProperty output = new SimpleStringProperty();

	public PreventGcTestViewModel() {
		input.bindBidirectional(output);
	}

	public StringProperty inputProperty() {
		return input;
	}

	public StringProperty outputProperty() {
		return output;
	}
}
