package de.saxsys.mvvmfx.scopes.context;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.scopes.context.views.ScopedFxmlView;
import de.saxsys.mvvmfx.scopes.context.views.ScopedFxmlViewModel;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ContextTest {


	/**
	 * Due to a bug (<a href="https://github.com/sialcasa/mvvmFX/issues/426">#426</a>)
	 * the context wasn't injected when an existing ViewModel instance was provided to
	 * the {@link FluentViewLoader}.
	 */
	@Test
	public void testInjectContextWithProvidedViewModel() {

		ScopedFxmlViewModel viewModel = new ScopedFxmlViewModel();

		ScopedFxmlView codeBehind = FluentViewLoader
				.fxmlView(ScopedFxmlView.class)
				.viewModel(viewModel)
				.load()
				.getCodeBehind();

		assertThat(codeBehind.context).isNotNull();

	}

}
