package de.saxsys.mvvmfx.examples.books;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableStringValue;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.books.backend.Book;

/**
 * A viewModel for list entries of books.
 * 
 * @author manuel.mauky
 */
public class BookListItemViewModel implements ViewModel {
	
	private ReadOnlyStringWrapper title = new ReadOnlyStringWrapper();
	private ReadOnlyStringWrapper author = new ReadOnlyStringWrapper();
	private Book book;
	
	
	public BookListItemViewModel(Book book) {
		this.book = book;
		title.set(book.getTitle());
		author.set("(" + book.getAuthor() + ")");
	}
	
	public Book getBook() {
		return book;
	}
	
	public ObservableStringValue titleProperty() {
		return title.getReadOnlyProperty();
	}
	
	public ObservableStringValue authorProperty() {
		return author.getReadOnlyProperty();
	}
}
