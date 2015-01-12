package de.saxsys.mvvmfx.examples.books;


import de.saxsys.mvvmfx.examples.books.backend.Book;

public class BookViewModel {

    private final Book book;

    public BookViewModel(Book book){
        this.book = book;
    }

    public String getTitle(){
        return book.getTitle();
    }

    public String getAuthor(){
        return book.getAuthor();
    }

    public String getDescription(){
        return book.getDesc();
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
