package de.saxsys.mvvmfx.examples.scopes.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import org.fxmisc.easybind.EasyBind;
import org.fxmisc.easybind.monadic.MonadicObservableValue;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.scopes.model.Note;
import de.saxsys.mvvmfx.internal.viewloader.ScopeTarget;

public class NoteTextViewModel implements ViewModel, ScopeTarget<ScopeViewModel> {
	
	private StringProperty content = new SimpleStringProperty();
	
	
	
	@Override
	public void initializeScope(ScopeViewModel scopeViewModel) {
		MonadicObservableValue<Note> note = EasyBind.monadic(scopeViewModel.noteProperty());
		
		content.bind(note.flatMap(Note::textProperty));
	}
	
	public StringProperty contentProperty() {
		return content;
	}
}
