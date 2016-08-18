package de.saxsys.mvvmfx.scopes.example2;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.scopes.example2.views.ScopedViewA;
import de.saxsys.mvvmfx.scopes.example2.views.ScopedViewB;
import de.saxsys.mvvmfx.scopes.example2.views.ScopedViewC;
import de.saxsys.mvvmfx.scopes.example2.views.ScopedViewModelA;
import de.saxsys.mvvmfx.scopes.example2.views.ScopedViewModelB;
import de.saxsys.mvvmfx.scopes.example2.views.ScopedViewModelC;
import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;


/**
 * {@link ScopedViewA} is the parent of {@link ScopedViewB} and {@link ScopedViewC}.
 * Both {@link ScopedViewModelB} and {@link ScopedViewModelB} try to inject an instance of {@link Example2Scope1}.
 * However, only {@link ScopedViewModelB} has a {@link de.saxsys.mvvmfx.ScopeProvider} annotation
 * for this scope type.
 * <p/>
 * Therefore we are expecting an exception because for {@link ScopedViewModelC} there is no valid {@link de.saxsys.mvvmfx.ScopeProvider} available.
 *
 */
@Ignore
public class Example2ScopesTest {

	@Test
	public void testException() {
		try {
			ViewTuple<ScopedViewA, ScopedViewModelA> viewTuple = FluentViewLoader.fxmlView(ScopedViewA.class).load();

			fail("Expected an exception because in branch C there is no scope provider defined");
		} catch (Exception e) {
			assertThat(getRootCause(e)).hasMessageContaining("scope").hasMessageContaining("ScopeProvider");
		}
	}


	private static Throwable getRootCause(Throwable e) {
		if(e.getCause() == null) {
			return e;
		} else {
			return getRootCause(e.getCause());
		}
	}

}
