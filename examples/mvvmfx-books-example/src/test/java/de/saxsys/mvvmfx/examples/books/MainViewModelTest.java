package de.saxsys.mvvmfx.examples.books;

import de.saxsys.mvvmfx.examples.books.backend.Error;
import de.saxsys.mvvmfx.examples.books.backend.Book;
import de.saxsys.mvvmfx.examples.books.backend.LibraryService;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.*;
import static eu.lestard.assertj.javafx.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MainViewModelTest {

    private MainViewModel viewModel;
    private LibraryService libraryService;

    @Before
    public void setup(){
        libraryService = mock(LibraryService.class);

        viewModel = new MainViewModel(libraryService);
    }


    @Test
    public void testSelectionOfBooks(){
        // given
        Book book1 = createBook("Das Leben des Horst", "Horst", "Eine geschichte über Horst");
        Book book2 = createBook("Die Verwandlung", "Franz Kafka", "Als Gregor Samsa eines Morgens aus unruhigen Träumen erwachte...");

        final BookViewModel bookViewModel1 = new BookViewModel(book1);
        final BookViewModel bookViewModel2 = new BookViewModel(book2);

        viewModel.booksProperty().add(bookViewModel1);
        viewModel.booksProperty().add(bookViewModel2);

        assertThat(viewModel.bookTitleProperty()).hasNullValue();
        assertThat(viewModel.bookAuthorProperty()).hasNullValue();
        assertThat(viewModel.bookDescriptionProperty()).hasNullValue();

        // when
        viewModel.selectedBookProperty().set(bookViewModel1);

        // then
        assertThat(viewModel.bookTitleProperty()).hasValue("Das Leben des Horst");
        assertThat(viewModel.bookAuthorProperty()).hasValue("Horst");
        assertThat(viewModel.bookDescriptionProperty()).hasValue("Eine geschichte über Horst");

        // when
        viewModel.selectedBookProperty().set(bookViewModel2);

        // then
        assertThat(viewModel.bookTitleProperty()).hasValue("Die Verwandlung");
        assertThat(viewModel.bookAuthorProperty()).hasValue("Franz Kafka");
        assertThat(viewModel.bookDescriptionProperty()).hasValue("Als Gregor Samsa eines Morgens aus unruhigen Träumen erwachte...");
    }

    @Test
    public void testSearch(){
        // given
        assertThat(viewModel.booksProperty()).isEmpty();
        assertThat(viewModel.searchStringProperty()).hasValue("");

        Book book1 = createBook("a book starting with a", "some author", null);
        Book book1WithDescription = createBook("a book starting with a", "some author", "some description 1");
        Book book2 = createBook("another book starting with a", "some author", null);
        Book book2WithDescription = createBook("another book starting with a", "some author", "some description 2");
        Book book3 = createBook("book starting with b", "some author", null);
        Book book3WithDescription = createBook("book starting with b", "some author", "some description 3");

        when(libraryService.search(eq("a"), any())).thenReturn(Arrays.asList(book1, book2));
        when(libraryService.search(eq("b"), any())).thenReturn(Arrays.asList(book3));
        when(libraryService.search(eq(""), any())).thenReturn(Collections.emptyList());

        when(libraryService.showDetails(eq(book1), any())).thenReturn(book1WithDescription);
        when(libraryService.showDetails(eq(book2), any())).thenReturn(book2WithDescription);
        when(libraryService.showDetails(eq(book3), any())).thenReturn(book3WithDescription);

        // when
        viewModel.searchStringProperty().set("a");
        viewModel.search();


        // then
        assertThat(viewModel.booksProperty()).hasSize(2);
        assertThat(viewModel.booksProperty().get(0).getTitle()).isEqualTo("a book starting with a");
        assertThat(viewModel.booksProperty().get(0).getDescription()).isEqualTo("some description 1");
        assertThat(viewModel.booksProperty().get(1).getTitle()).isEqualTo("another book starting with a");
        assertThat(viewModel.booksProperty().get(1).getDescription()).isEqualTo("some description 2");


        // when
        viewModel.searchStringProperty().set("b");
        viewModel.search();

        // then
        assertThat(viewModel.booksProperty()).hasSize(1);
        assertThat(viewModel.booksProperty().get(0).getTitle()).isEqualTo("book starting with b");
        assertThat(viewModel.booksProperty().get(0).getDescription()).isEqualTo("some description 3");


        // when
        viewModel.searchStringProperty().set("");
        viewModel.search();

        assertThat(viewModel.booksProperty()).isEmpty();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testErrors(){
        assertThat(viewModel.errorProperty()).hasNullValue();
        when(libraryService.search(any(), any())).thenAnswer(invocation -> {

            final Consumer<Error> errorHandler = (Consumer<Error>)invocation.getArguments()[1];
            errorHandler.accept(Error.error("error message", "description"));
            return Collections.emptyList();
        });


        viewModel.searchStringProperty().set("a");
        viewModel.search();

        assertThat(viewModel.booksProperty()).isEmpty();
        assertThat(viewModel.errorProperty()).hasValue("error message");
    }

    private Book createBook(String title, String author, String desc){
        return new Book(null, title, author, desc, null, null);
    }

}
