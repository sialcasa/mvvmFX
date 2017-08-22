package de.saxsys.mvvmfx.scopes.example5;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.testingutils.jfxrunner.JfxRunner;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Example5Test {

	@Test
	public void test() {

		Example5Scope scope = new Example5Scope();

		ViewTuple<MyView, MyViewModel> viewTuple = FluentViewLoader.fxmlView(MyView.class)
				.providedScopes(scope)
				.load();

		assertThat(viewTuple.getViewModel().scope).isEqualTo(scope);


	}

}
