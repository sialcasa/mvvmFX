package de.saxsys.mvvmfx.examples.books;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.books.backend.Book;
import de.saxsys.mvvmfx.examples.books.backend.Error;
import de.saxsys.mvvmfx.examples.books.backend.LibraryService;
import eu.lestard.advanced_bindings.api.ObjectBindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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

    private ObservableList<BookViewModel> books = FXCollections.observableArrayList();

    private ObjectProperty<BookViewModel> selectedBook = new SimpleObjectProperty<>();

    private StringProperty error = new SimpleStringProperty();

    public MainViewModel(LibraryService libraryService){
        this.libraryService = libraryService;

        bookTitle.bind(ObjectBindings.map(selectedBook, BookViewModel::getTitle));
        bookAuthor.bind(ObjectBindings.map(selectedBook, BookViewModel::getAuthor));
        bookDescription.bind(ObjectBindings.map(selectedBook, BookViewModel::getDescription));
    }


    public void search(){
        Consumer<Error> errorHandler = err -> error.set(err.getMessage());

        final List<Book> result = libraryService.search(searchString.get(), errorHandler);

        books.clear();
        books.addAll(result
            .stream()
            .map(bookWithoutDescription -> libraryService.showDetails(bookWithoutDescription, errorHandler))
            .map(BookViewModel::new)
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

    public ObservableList<BookViewModel> booksProperty(){
        return books;
    }

    public ObjectProperty<BookViewModel> selectedBookProperty(){
        return selectedBook;
    }

    public StringProperty errorProperty(){
        return error;
    }
}
