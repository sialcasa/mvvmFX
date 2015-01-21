package de.saxsys.mvvmfx.examples.scopes.ui;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.scopes.model.Note;
import de.saxsys.mvvmfx.examples.scopes.model.NoteStore;

import java.util.Collection;

public class MainViewModel implements ViewModel {
	
	public Collection<Note> getNotes(){
		return NoteStore.getInstance().get();
	}
	
}
