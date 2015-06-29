package de.saxsys.mvvmfx.examples.books;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.books.backend.Book;
import de.saxsys.mvvmfx.examples.books.backend.Error;
import de.saxsys.mvvmfx.examples.books.backend.LibraryService;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import eu.lestard.advanced_bindings.api.ObjectBindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.inject.Singleton;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Singleton
public class MainViewModel implements ViewModel {
	
	private final LibraryService libraryService;
	private StringProperty searchString = new SimpleStringProperty("");
	
	private StringProperty bookTitle = new SimpleStringProperty();
	private StringProperty bookAuthor = new SimpleStringProperty();
	private StringProperty bookDescription = new SimpleStringProperty();
	
	private ObservableList<BookListItemViewModel> books = FXCollections.observableArrayList();
	
	private ObjectProperty<BookListItemViewModel> selectedBook = new SimpleObjectProperty<>();
	
	private StringProperty error = new SimpleStringProperty();

	private Command searchCommand;
	
	public MainViewModel(LibraryService libraryService) {
		this.libraryService = libraryService;

		searchCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() throws Exception {
				search();
			}
		});
		
		bookTitle.bind(ObjectBindings.map(selectedBook, bookItem -> bookItem.getBook().getTitle()));
		bookAuthor.bind(ObjectBindings.map(selectedBook, bookItem -> bookItem.getBook().getAuthor()));
		bookDescription.bind(ObjectBindings.map(selectedBook, bookItem -> bookItem.getBook().getDesc()));
	}
	
	public Command getSearchCommand() {
		return searchCommand;
	}

	void search() {
		Consumer<Error> errorHandler = err -> error.set(err.getMessage());
		
		final List<Book> result = libraryService.search(searchString.get(), errorHandler);
		
		books.clear();
		books.addAll(result
				.stream()
				.map(bookWithoutDescription -> libraryService.showDetails(bookWithoutDescription, errorHandler))
				.map(BookListItemViewModel::new)
				.collect(Collectors.toList()));
	}
	
	
	public StringProperty searchStringProperty() {
		return searchString;
	}
	
	public StringProperty bookTitleProperty() {
		return bookTitle;
	}
	
	public StringProperty bookAuthorProperty() {
		return bookAuthor;
	}
	
	public StringProperty bookDescriptionProperty() {
		return bookDescription;
	}
	
	public ObservableList<BookListItemViewModel> booksProperty() {
		return books;
	}
	
	public ObjectProperty<BookListItemViewModel> selectedBookProperty() {
		return selectedBook;
	}
	
	public StringProperty errorProperty() {
		return error;
	}
}
