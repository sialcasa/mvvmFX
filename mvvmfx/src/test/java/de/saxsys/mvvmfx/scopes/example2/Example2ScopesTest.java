package de.saxsys.mvvmfx.scopes.example2;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.scopes.example2.views.ScopedViewA;
import de.saxsys.mvvmfx.scopes.example2.views.ScopedViewB;
import de.saxsys.mvvmfx.scopes.example2.views.ScopedViewC;
import de.saxsys.mvvmfx.scopes.example2.views.ScopedViewModelA;
import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;


@Ignore
public class Example2ScopesTest {

	@Test
	public void test() {
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
