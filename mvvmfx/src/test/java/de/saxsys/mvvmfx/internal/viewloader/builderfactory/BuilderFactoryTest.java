package de.saxsys.mvvmfx.internal.viewloader.builderfactory;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.internal.viewloader.GlobalBuilderFactory;
import de.saxsys.mvvmfx.testingutils.jfxrunner.JfxRunner;
import javafx.fxml.LoadException;
import javafx.util.Builder;
import javafx.util.BuilderFactory;

@RunWith(JfxRunner.class)
public class BuilderFactoryTest {

	public static BuilderFactory customBuilderFactoryOne = type -> {
		if(CustomTextField.class.isAssignableFrom(type)) {
			return (Builder<CustomTextField>) () -> new CustomTextField("Test 1");
		} else {
			return null;
		}
	};

	public static BuilderFactory customBuilderFactoryTwo = type -> {
		if(CustomTextField.class.isAssignableFrom(type)) {
			return (Builder<CustomTextField>) () -> new CustomTextField("Test 2");
		} else {
			return null;
		}
	};

	@Before
	@After
	public void clearFactories() {
		ArrayList<BuilderFactory> factories = (ArrayList<BuilderFactory>) Whitebox.getInternalState(GlobalBuilderFactory.getInstance(), "factories");

		factories.clear();
	}

	@Test
	public void testWithoutFactory() {
		try {
			FluentViewLoader.fxmlView(BuilderFactoryTestView.class).load();
			fail("expected loading to fail");
		} catch (Exception e) {
			assertThat(e).hasCauseInstanceOf(LoadException.class);
			assertThat(e).hasRootCauseInstanceOf(NoSuchMethodException.class);
		}
	}


	@Test
	public void testWithCustomFactory() {
		MvvmFX.addGlobalBuilderFactory(customBuilderFactoryOne);
		FluentViewLoader.fxmlView(BuilderFactoryTestView.class).load();
	}

	@Test
	public void testWithMultipleFactories() {
		MvvmFX.addGlobalBuilderFactory(customBuilderFactoryOne);
		MvvmFX.addGlobalBuilderFactory(customBuilderFactoryTwo);
		BuilderFactoryTestView codeBehind = FluentViewLoader.fxmlView(BuilderFactoryTestView.class).load().getCodeBehind();

		assertThat(codeBehind.textField.getSpecial()).isEqualTo("Test 2");
	}

	@Test
	public void testWithBuilderAtLoadingTime() {
		MvvmFX.addGlobalBuilderFactory(customBuilderFactoryOne);

		BuilderFactoryTestView codeBehind = FluentViewLoader
				.fxmlView(BuilderFactoryTestView.class)
				.load().getCodeBehind();

		// loading without parameter to FluentViewLoader results into the first factory to be used.
		assertThat(codeBehind.textField.getSpecial()).isEqualTo("Test 1");


		codeBehind = FluentViewLoader
				.fxmlView(BuilderFactoryTestView.class)
				.builderFactory(customBuilderFactoryTwo)
				.load().getCodeBehind();

		// passing a factory as parameter results into this factory being used instead of the global one.
		assertThat(codeBehind.textField.getSpecial()).isEqualTo("Test 2");
	}
}
