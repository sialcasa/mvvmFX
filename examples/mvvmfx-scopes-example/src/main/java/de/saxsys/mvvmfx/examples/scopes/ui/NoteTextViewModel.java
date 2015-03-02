package de.saxsys.mvvmfx.examples.scopes.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import org.fxmisc.easybind.EasyBind;
import org.fxmisc.easybind.monadic.MonadicObservableValue;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.scopes.model.Note;
import de.saxsys.mvvmfx.scopes.InjectScope;

public class NoteTextViewModel implements ViewModel {
	
	private StringProperty content = new SimpleStringProperty();
	
	
	
	@InjectScope
	private ScopeViewModel scopeViewModel;
	
	
	public void initialize() {
		MonadicObservableValue<Note> note = EasyBind.monadic(scopeViewModel.noteProperty());
		
		content.bind(note.flatMap(Note::textProperty));
	}
	
	public StringProperty contentProperty() {
		return content;
	}
}
