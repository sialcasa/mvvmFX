package de.saxsys.mvvmfx.examples.itemlist.model;

import java.util.UUID;

public class IceCreamFlavor {

	private final String id = UUID.randomUUID().toString();

	private String name;

	public IceCreamFlavor(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
