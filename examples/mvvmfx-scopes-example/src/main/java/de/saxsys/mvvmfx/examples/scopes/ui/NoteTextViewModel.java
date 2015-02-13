package de.saxsys.mvvmfx.examples.scopes.ui;

import de.saxsys.mvvmfx.scopes.InitScope;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import org.fxmisc.easybind.EasyBind;
import org.fxmisc.easybind.monadic.MonadicObservableValue;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.scopes.model.Note;

public class NoteTextViewModel implements ViewModel {
	
	private StringProperty content = new SimpleStringProperty();
	
	@InitScope
	public void initMyNoteScope(ScopeViewModel myNoteScope) {
		MonadicObservableValue<Note> note = EasyBind.monadic(myNoteScope.noteProperty());
		
		content.bind(note.flatMap(Note::textProperty));
	}
	
	public StringProperty contentProperty() {
		return content;
	}
}
