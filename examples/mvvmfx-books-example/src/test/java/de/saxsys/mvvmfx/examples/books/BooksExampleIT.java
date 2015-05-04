package de.saxsys.mvvmfx.examples.books;

import static org.assertj.core.api.Assertions.*;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import org.junit.Test;
import org.loadui.testfx.GuiTest;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.examples.books.backend.LibraryService;
import de.saxsys.mvvmfx.examples.books.backend.LibraryServiceMockImpl;
import eu.lestard.easydi.EasyDI;

public class BooksExampleIT extends GuiTest {
	
	
	@Override
	protected Parent getRootNode() {
		System.out.println("getRootNode()");
		EasyDI context = new EasyDI();
		context.bindInterface(LibraryService.class, LibraryServiceMockImpl.class);
		
		LibraryServiceMockImpl libraryService = context.getInstance(LibraryServiceMockImpl.class);
		libraryService.addSomeBooks();
		
		MvvmFX.setCustomDependencyInjector(type -> context.getInstance(type));
		
		return FluentViewLoader.fxmlView(MainView.class).load().getView();
	}
	
	@Test
	public void testSearch() {
		// given
		ListView bookList = find("#bookList");
		assertThat(bookList.getItems()).isEmpty();
		
		// when
		click("#searchTextField").type("A ");
		click("#searchButton");
		
		
		// then
		assertThat(bookList.getItems()).hasSize(3);
	}
	
	@Test
	public void testClickOnBook() {
		// given
		Label titleLabel = find("#titleLabel");
		Label authorLabel = find("#authorLabel");
		Label descriptionLabel = find("#descriptionLabel");
		
		assertThat(titleLabel.getText()).isNullOrEmpty();
		assertThat(authorLabel.getText()).isNullOrEmpty();
		assertThat(descriptionLabel.getText()).isNullOrEmpty();
		
		click("#searchTextField").type("A ");
		click("#searchButton");
		
		// when
		click("A Game of Thrones");
		
		// then
		assertThat(titleLabel.getText()).isEqualTo("A Game of Thrones");
		assertThat(authorLabel.getText()).isEqualTo("Georg R. R. Martin");
		assertThat(descriptionLabel.getText()).isEqualTo("First part of SOIF");
	}
}
