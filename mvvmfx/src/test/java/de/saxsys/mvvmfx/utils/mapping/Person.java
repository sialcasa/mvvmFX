package de.saxsys.mvvmfx.utils.mapping;

import java.util.ArrayList;
import java.util.List;

public class Person {
	
	private String name;
	
	private int age;
	
	private List<String> nicknames = new ArrayList<>();

	public List<String> getNicknames() {
		return nicknames;
	}

	public void setNicknames (List<String> nicknames) {
		this.nicknames = nicknames;
	}

	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
