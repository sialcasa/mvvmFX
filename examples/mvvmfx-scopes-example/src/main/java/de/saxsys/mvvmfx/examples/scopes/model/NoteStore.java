package de.saxsys.mvvmfx.examples.scopes.model;

public class NoteStore extends AbstractStore<Note> {
	
	private static NoteStore SINGLETON = new NoteStore();
	
	private NoteStore(){
	}
	
	public static NoteStore getInstance(){
		return SINGLETON;
	}
}
