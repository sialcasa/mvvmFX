package de.saxsys.mvvmfx.examples.books.backend;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Singleton
public class LibraryServiceMockImpl implements LibraryService {
	
	private Set<Book> books = new HashSet<>();
	
	
	public void addBooks(Book... books) {
		Arrays.stream(books).forEach(this.books::add);
	}
	
	public void addSomeBooks() {
		this.addBooks(
				new Book("/1", "A Game of Thrones", "Georg R. R. Martin", "First part of SOIF"),
				new Book("/2", "A Clash of Kings", "Georg R. R. Martin", "Second part of SOIF"),
				new Book("/3", "A Storm of Swords", "Georg R. R. Martin", "Third part of SOIF"),
				new Book("/4", "The Metamorphosis", "Franz Kafka", "A man turns into an insect")
				);
	}
	
	@Override
	public List<Book> search(String query, Consumer<Error> errorCallback) {
		return books.stream()
				.filter(b -> b.getTitle().contains(query))
				.map(b -> new Book(b.getHref(), b.getTitle(), b.getAuthor(), null)) // no desc
				.sorted((a, b) -> a.getTitle().compareTo(b.getTitle()))
				.collect(Collectors.toList());
	}
	
	@Override
	public Book showDetails(Book book, Consumer<Error> errorCallback) {
		return books.stream()
				.filter(b -> b.getHref().equals(book.getHref()))
				.findFirst().orElse(null);
	}
}
