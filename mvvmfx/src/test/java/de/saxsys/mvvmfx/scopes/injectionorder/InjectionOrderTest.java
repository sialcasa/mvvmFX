package de.saxsys.mvvmfx.scopes.injectionorder;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import de.saxsys.mvvmfx.FluentViewLoader;

/**
 * This test-case is used to reproduce bug <a href="https://github.com/sialcasa/mvvmFX/issues/559">#559</a>.
 * <p>
 * When an existing ViewModel instance was provided to the view-loader, then the order of injection of the scope changed compared to the default
 * behaviour. In this case the scope was injected into the viewmodel after the view was initialized.
 * <p>
 * The expected behaviour is that the initialization order is the same for existing viewModels and non-existing viewModels. In both cases the
 * injection of the scope should happen before the initialization of the View.
 */
public class InjectionOrderTest {

	@Test
	public void testWithoutProvidedViewModel() {
		TestView.wasScopeInjected = false;

		FluentViewLoader.fxmlView(TestView.class).providedScopes(new TestScope()).load();

		assertThat(TestView.wasScopeInjected).isTrue();
	}

	@Test
	public void testWithProvidedViewModel() {
		TestView.wasScopeInjected = false;

		TestViewModel viewModel = new TestViewModel();

		FluentViewLoader.fxmlView(TestView.class).viewModel(viewModel).providedScopes(new TestScope()).load();

		assertThat(viewModel.scope).isNotNull();
		assertThat(TestView.wasScopeInjected).isTrue();
	}

}
