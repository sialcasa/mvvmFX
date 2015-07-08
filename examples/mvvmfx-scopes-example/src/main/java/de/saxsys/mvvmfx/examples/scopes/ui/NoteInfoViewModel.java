package de.saxsys.mvvmfx.examples.scopes.ui;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.scopes.model.Note;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.inject.Inject;

//@LoadingScope
public class NoteInfoViewModel implements ViewModel {
	
	
	private StringProperty title = new SimpleStringProperty();
	private StringProperty id = new SimpleStringProperty();

    @Inject
    private ScopeContext scopeContext;

    public void initialize() {

        scopeContext.noteProperty().addListener((observable, oldValue, newValue) -> {
            initBindings(oldValue, newValue);
        });
        initBindings(null, scopeContext.getNote());
    }

    private void initBindings(Note oldNote, Note newNote) {
        if(oldNote != null) {
            title.unbindBidirectional(oldNote.titleProperty());
        }

        title.bindBidirectional(newNote.titleProperty());
        id.set(newNote.getId());
    }

	public StringProperty titleProperty() {
		return title;
	}

    public ReadOnlyStringProperty idProperty() {
        return id;
    }
}