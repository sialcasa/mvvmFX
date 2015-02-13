package de.saxsys.mvvmfx.examples.scopes.ui;

import de.saxsys.mvvmfx.scopes.InitScope;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import org.fxmisc.easybind.EasyBind;
import org.fxmisc.easybind.monadic.MonadicObservableValue;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.scopes.model.Note;

public class NoteInfoViewModel implements ViewModel {
	
	
	private StringProperty title = new SimpleStringProperty();
	private StringProperty lastModified = new SimpleStringProperty();
	
	@InitScope
	public void initScope(ScopeViewModel scopeViewModel) {
		MonadicObservableValue<Note> note = EasyBind.monadic(scopeViewModel.noteProperty());
		
		title.bind(note.flatMap(Note::titleProperty));
		
		lastModified.bind(note
				.flatMap(Note::lastUpdateProperty)
				.map(n -> {
					if (n == null) {
						return "";
					} else {
						return n.toString();
					}
				}));
	}
	
	public StringProperty titleProperty() {
		return title;
	}
	
	public StringProperty lastModifiedProperty() {
		return lastModified;
	}
}