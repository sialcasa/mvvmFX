package de.saxsys.mvvmfx.examples.books.backend;

import java.util.List;
import java.util.function.Consumer;

public interface LibraryService {
	List<Book> search(String query, Consumer<Error> errorCallback);
	
	Book showDetails(Book book, Consumer<Error> errorCallback);
}
