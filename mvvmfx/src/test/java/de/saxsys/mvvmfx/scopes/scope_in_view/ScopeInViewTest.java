package de.saxsys.mvvmfx.scopes.scope_in_view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.testingutils.ExceptionUtils;

/**
 * Scopes are only allowed in ViewModels. If a user tries to inject them in a View/CodeBehind then
 * an exception should be thrown to notify the user about this error.
 */
public class ScopeInViewTest {

	@Test
	public void testDirectLoading() {
		RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
			FluentViewLoader.fxmlView(ScopeInViewTestView.class)
					.providedScopes(new TestScope())
					.load();
		});

		Throwable rootCause = ExceptionUtils.getRootCause(runtimeException);
		assertThat(rootCause).isInstanceOf(IllegalStateException.class).hasMessageContaining("tries to inject a Scope");
	}

	/**
	 * This test case reproduces a separate branch in the viewloader logic that is used when
	 * a viewModel and a view instance is provided.
	 */
	@Test
	public void testDirectLoadingWithExistingViewModel() {
		RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
			FluentViewLoader.fxmlView(ScopeInViewTestView.class)
					.providedScopes(new TestScope())
					.codeBehind(new ScopeInViewTestView())
					.viewModel(new ScopeInViewTestViewModel())
					.load();
		});

		Throwable rootCause = ExceptionUtils.getRootCause(runtimeException);
		assertThat(rootCause).isInstanceOf(IllegalStateException.class).hasMessageContaining("tries to inject a Scope");
	}


	@Test
	public void testSubviewLoading() {
		RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
			FluentViewLoader.fxmlView(RootView.class)
					.providedScopes(new TestScope())
					.load();
		});


		Throwable rootCause = ExceptionUtils.getRootCause(runtimeException);
		assertThat(rootCause).isInstanceOf(IllegalStateException.class).hasMessageContaining("tries to inject a Scope");
	}
}
