package de.saxsys.mvvmfx.scopes.example6;

import de.saxsys.mvvmfx.Scope;
import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class ParentViewModel implements ViewModel {

	private StringProperty input = new SimpleStringProperty();


	private Consumer<List<Scope>> newTabAction;

	public void addTab() {
		if(newTabAction != null) {

			String value = input.getValue();

			if (value != null && !value.trim().isEmpty()) {

				TabScope tabScope = new TabScope();
				tabScope.contentProperty().set(value);

				newTabAction.accept(Collections.singletonList(tabScope));
				input.setValue("");
			}
		}
	}

	public void setNewTabAction(Consumer<List<Scope>> newTabAction) {
		this.newTabAction = newTabAction;
	}

	public StringProperty inputProperty() {
		return input;
	}
}
