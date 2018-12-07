package de.saxsys.mvvmfx.utils.mapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PersonImmutable {
	private final String name;
	private final int age;
	private final List<String> nicknames = new ArrayList<>();

	private final Set<String> emailAddresses = new HashSet<>();

	private final Map<String, String> phoneNumbers = new HashMap<>();

	public static PersonImmutable create(){
		return new PersonImmutable("", 0, Collections.emptyList(), Collections.emptySet(), Collections.emptyMap());
	}

	private PersonImmutable(String name, int age, List<String> nicknames, Set<String> emailAddresses, Map<String,
			String> phoneNumbers) {
		this.name = name;
		this.age = age;
		this.nicknames.addAll(nicknames);
		this.emailAddresses.addAll(emailAddresses);
		this.phoneNumbers.putAll(phoneNumbers);
	}

	public String getName() {
		return name;
	}

	public PersonImmutable withName(String name) {
		return new PersonImmutable(name, this.age, this.nicknames, this.emailAddresses, this.phoneNumbers);
	}

	public int getAge() {
		return age;
	}

	public PersonImmutable withAge(int age) {
		return new PersonImmutable(this.name, age, this.nicknames, this.emailAddresses, this.phoneNumbers);
	}

	public List<String> getNicknames() {
		return Collections.unmodifiableList(nicknames);
	}

	public PersonImmutable withNicknames(List<String> nicknames) {
		return new PersonImmutable(this.name, this.age, nicknames, this.emailAddresses, this.phoneNumbers);
	}

	public Set<String> getEmailAddresses() {
		return Collections.unmodifiableSet(emailAddresses);
	}

	public PersonImmutable withEmailAddresses(Set<String> emailAddresses) {
		return new PersonImmutable(this.name, this.age, this.nicknames, emailAddresses, this.phoneNumbers);
	}

	public Map<String, String> getPhoneNumbers() {
		return Collections.unmodifiableMap(phoneNumbers);
	}

	public PersonImmutable withPhoneNumbers(Map<String, String> phoneNumbers) {
		return new PersonImmutable(this.name, this.age, this.nicknames, this.emailAddresses, phoneNumbers);
	}
}
