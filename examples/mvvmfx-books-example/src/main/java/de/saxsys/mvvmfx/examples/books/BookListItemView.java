package de.saxsys.mvvmfx.examples.books;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BookListItemView implements FxmlView<BookListItemViewModel> {
	@FXML
	public Label titleLabel;
	@FXML
	public Label authorLabel;
	
	@InjectViewModel
	private BookListItemViewModel viewModel;
	
	public void initialize() {
		titleLabel.textProperty().bind(viewModel.titleProperty());
		authorLabel.textProperty().bind(viewModel.authorProperty());
	}
}
