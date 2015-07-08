package de.saxsys.mvvmfx.examples.scopes.ui;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.scopes.model.Note;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.inject.Inject;

//@LoadingScope
public class NoteTextViewModel implements ViewModel {
	
	private StringProperty content = new SimpleStringProperty();

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
            content.unbindBidirectional(oldNote.textProperty());
        }

        content.bindBidirectional(newNote.textProperty());
        id.set(newNote.getId());
    }

    public StringProperty contentProperty() {
		return content;
	}

    public ReadOnlyStringProperty idProperty() {
        return id;
    }

}
