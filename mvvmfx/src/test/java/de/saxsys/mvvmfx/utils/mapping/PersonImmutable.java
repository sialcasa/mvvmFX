package de.saxsys.mvvmfx.utils.mapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PersonImmutable {
	private final String name;
	private final int age;
	private final List<String> nicknames = new ArrayList<>();

	public static PersonImmutable create(){
		return new PersonImmutable("", 0, Collections.emptyList());
	}

	private PersonImmutable(String name, int age, List<String> nicknames) {
		this.name = name;
		this.age = age;
		this.nicknames.addAll(nicknames);
	}

	public String getName() {
		return name;
	}

	public PersonImmutable withName(String name) {
		return new PersonImmutable(name, this.age, this.nicknames);
	}

	public int getAge() {
		return age;
	}

	public PersonImmutable withAge(int age) {
		return new PersonImmutable(this.name, age, this.nicknames);
	}

	public List<String> getNicknames() {
		return Collections.unmodifiableList(nicknames);
	}

	public PersonImmutable withNicknames(List<String> nicknames) {
		return new PersonImmutable(this.name, this.age, nicknames);
	}


	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		PersonImmutable that = (PersonImmutable) o;

		if (age != that.age)
			return false;
		if (name != null ? !name.equals(that.name) : that.name != null)
			return false;
		return nicknames.equals(that.nicknames);
	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + age;
		result = 31 * result + nicknames.hashCode();
		return result;
	}
}
