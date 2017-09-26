package de.saxsys.mvvmfx.examples.books.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class LibraryServiceMockTest {
	
	private LibraryServiceMockImpl libraryService;
	
	private Book aGameOfThrones;
	private Book aClashOfKings;
	private Book aStormOfSwords;
	
	private Book theMetamorphosis;
	
	@BeforeEach
	public void setup() {
		libraryService = new LibraryServiceMockImpl();
		
		aGameOfThrones = new Book("/1", "A Game of Thrones", "Georg R. R. Martin", "First part of SOIF");
		aClashOfKings = new Book("/2", "A Clash of Kings", "Georg R. R. Martin", "Second part of SOIF");
		aStormOfSwords = new Book("/3", "A Storm of Swords", "Georg R. R. Martin", "Third part of SOIF");
		
		theMetamorphosis = new Book("/4", "The Metamorphosis", "Franz Kafka", "A man turns into an insect");
		
		libraryService.addBooks(aGameOfThrones, aClashOfKings, aStormOfSwords, theMetamorphosis);
	}
	
	@Test
	public void testSearch() {
		List<Book> resultList = libraryService.search("A ", e -> fail());
		
		assertThat(resultList).hasSize(3);
		assertThat(resultList.get(0).getTitle()).isEqualTo("A Clash of Kings"); // by alphabetical order
		assertThat(resultList.get(1).getTitle()).isEqualTo("A Game of Thrones");
		assertThat(resultList.get(2).getTitle()).isEqualTo("A Storm of Swords");
		
		List<Book> secondSearchResultList = libraryService.search("The ", e -> fail());
		
		assertThat(secondSearchResultList).hasSize(1);
		assertThat(secondSearchResultList.get(0).getTitle()).isEqualTo("The Metamorphosis");
	}
	
	@Test
	public void testShowDetails() {
		
		Book resultWithoutDescription = libraryService.search("A Game", e -> fail()).get(0);
		
		assertThat(resultWithoutDescription.getDesc()).isNull();
		
		Book resultWithDescription = libraryService.showDetails(resultWithoutDescription, e -> fail());
		
		assertThat(resultWithDescription.getDesc()).isEqualTo(aGameOfThrones.getDesc());
	}
	
	@Test
	public void testSearch_NoResults() {
		List<Book> resultList = libraryService.search("Something", e -> fail());
		
		assertThat(resultList).isEmpty();
	}
	
	@Test
	public void testShowDetails_NoResults() {
		Book result = libraryService.showDetails(new Book("/5", "Another book", "another author", null), e -> fail());
		
		assertThat(result).isNull();
	}
}
