package de.saxsys.mvvmfx.examples.scopesexample.model;

public class Document extends Entity {

	private String title;

	private String description;

	public Document(String title) {
		this.title = title;
	}

	public Document() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return title;
	}
}
