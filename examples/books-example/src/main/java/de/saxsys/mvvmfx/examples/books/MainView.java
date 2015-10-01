package de.saxsys.mvvmfx.examples.books;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.utils.viewlist.CachedViewModelCellFactory;

public class MainView implements FxmlView<MainViewModel> {
	
	
	@FXML
	private Label titleLabel;
	
	@FXML
	private Label authorLabel;
	
	@FXML
	private TextField searchTextField;
	
	@FXML
	private Button searchButton;
	
	@FXML
	private Label descriptionLabel;
	
	@FXML
	private ListView<BookListItemViewModel> bookList;
	
	@FXML
	private Label errorLabel;
	
	@InjectViewModel
	private MainViewModel viewModel;
	
	public void initialize() {
		searchTextField.textProperty().bindBidirectional(viewModel.searchStringProperty());
		titleLabel.textProperty().bind(viewModel.bookTitleProperty());
		authorLabel.textProperty().bind(viewModel.bookAuthorProperty());
		descriptionLabel.textProperty().bind(viewModel.bookDescriptionProperty());
		
		bookList.setItems(viewModel.booksProperty());
		
		bookList.setCellFactory(CachedViewModelCellFactory.createForFxmlView(BookListItemView.class));
		
		viewModel.selectedBookProperty().bind(bookList.getSelectionModel().selectedItemProperty());
		errorLabel.textProperty().bind(viewModel.errorProperty());
		
		searchButton.disableProperty().bind(viewModel.getSearchCommand().executableProperty().not());
		
	}
	
	public void searchButtonPressed() {
		viewModel.getSearchCommand().execute();
	}
}
