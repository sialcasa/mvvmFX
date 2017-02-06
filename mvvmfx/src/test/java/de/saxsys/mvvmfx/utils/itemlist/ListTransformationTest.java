package de.saxsys.mvvmfx.utils.itemlist;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ListTransformationTest {

	@Test
	public void testListTransformation() {
		ListTransformation<String, Integer> transformation = new ListTransformation<>(String::length);

		assertThat(transformation.getSourceList()).isEmpty();
		assertThat(transformation.getTargetList()).isEmpty();

		transformation.getSourceList().add("hello");

		assertThat(transformation.getTargetList()).contains(5);


		transformation.getSourceList().add("world");
		assertThat(transformation.getTargetList()).contains(5, 5);


		transformation.getSourceList().add("test");
		assertThat(transformation.getTargetList()).contains(5, 5, 4);
	}

}
