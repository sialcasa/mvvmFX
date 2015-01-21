package de.saxsys.mvvmfx.examples.scopes.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import org.fxmisc.easybind.EasyBind;
import org.fxmisc.easybind.monadic.MonadicObservableValue;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.scopes.model.Note;
import de.saxsys.mvvmfx.scopes.InjectScope;
import de.saxsys.mvvmfx.scopes.ScopeTarget;

public class NoteInfoViewModel implements ViewModel, ScopeTarget {
	
	
	private StringProperty title = new SimpleStringProperty("test title");
	private StringProperty lastModified = new SimpleStringProperty("test last modif");
	
	@InjectScope
	private ScopeViewModel scopeViewModel;
	
	@Override
	public void afterScopeInjection() {
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