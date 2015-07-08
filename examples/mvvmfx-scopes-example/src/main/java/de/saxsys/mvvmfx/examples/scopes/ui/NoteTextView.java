package de.saxsys.mvvmfx.examples.scopes.ui;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class NoteTextView implements FxmlView<NoteTextViewModel>{
	
	@FXML
	public TextArea content;

    @FXML
    public Label idLabel;

	
	@InjectViewModel
	private NoteTextViewModel viewModel;
	
	public void initialize(){
        content.setEditable(false);
		content.textProperty().bindBidirectional(viewModel.contentProperty());

        idLabel.textProperty().bind(viewModel.idProperty());
	}
}
