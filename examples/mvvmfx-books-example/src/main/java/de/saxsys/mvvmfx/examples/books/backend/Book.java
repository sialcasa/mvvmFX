package de.saxsys.mvvmfx.examples.books.backend;


public class Book {
	
	private final String href;
	private final String title;
	private String author;
	private String desc;
	
	public Book(String href, String title, String author, String desc) {
		this.href = href;
		this.title = title;
		this.author = author;
		this.desc = desc;
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
	
}
