package de.saxsys.mvvmfx.examples.books.backend;

import com.theoryinpractise.halbuilder.api.Link;

public class Book {

    private final String href;
    private final String title;
    private String author;
    private String desc;
    private Integer borrower;
    private final Link relLend;
    private final Link relReturn;

    public Book(String href, String title, String author, String desc, Link relLend, Link relReturn) {
        super();
        this.href = href;
        this.title = title;
        this.author = author;
        this.desc = desc;
        this.relLend = relLend;
        this.relReturn = relReturn;
    }

    public String getHref() {
        return href;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getBorrower() {
        return borrower;
    }

    public void setBorrower(Integer borrower) {
        this.borrower = borrower;
    }

    public Link getRelLend() {
        return relLend;
    }

    public Link getRelReturn() {
        return relReturn;
    }

    public boolean isLent() {
        return borrower != null;
    }

    public boolean isAvailable() {
        return null != relLend;
    }

    public boolean isReturnable() {
        return null != relReturn;
    }
}
