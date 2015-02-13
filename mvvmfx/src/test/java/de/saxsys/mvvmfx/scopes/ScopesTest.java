package de.saxsys.mvvmfx.scopes;

import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewModel;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;

public class ScopesTest {
	
	public static class ScopeA implements ViewModel{}
	
	public static class ScopeB implements ViewModel{}
	
	public static class MyViewModel implements ViewModel{
		public ScopeA scopeA;
		
		public ScopeB scopeB;
		
		public static boolean initScopeACalled = false;
		public static boolean scopeAWasInjected = false;
		public static boolean initScopeBCalled = false;
		public static boolean scopeBWasInjected = false;
		
		
		@InitScope
		public void initScopeA(ScopeA scopeA){
			this.scopeA = scopeA;
			initScopeACalled = true;
			scopeAWasInjected = scopeA != null;
		}
		
		@InitScope
		private void initScopeB(ScopeB scopeB){
			this.scopeB = scopeB;
			initScopeBCalled = true;
			scopeBWasInjected = scopeA != null;
		}
	}
	
	@Test
	public void testScopeInjection(){
		ScopeA scopeA = new ScopeA();
		
		ScopeB scopeB = new ScopeB();
		
		
		MyViewModel viewModel = new MyViewModel();
		
		ScopeHelper.newScope(scopeA, viewModel);
		
		assertThat(viewModel.scopeA).isEqualTo(scopeA);
		assertThat(viewModel.scopeB).isNull();
		
		assertThat(MyViewModel.initScopeACalled).isTrue();
		assertThat(MyViewModel.initScopeBCalled).isFalse();
		
		assertThat(MyViewModel.scopeAWasInjected).isTrue();
		assertThat(MyViewModel.scopeBWasInjected).isFalse();
		
		
		
		ScopeHelper.newScope(scopeB, viewModel);
		
		assertThat(viewModel.scopeB).isEqualTo(scopeB);

		assertThat(MyViewModel.initScopeACalled).isTrue();
		assertThat(MyViewModel.initScopeBCalled).isTrue();

		assertThat(MyViewModel.scopeAWasInjected).isTrue();
		assertThat(MyViewModel.scopeBWasInjected).isTrue();
	}

	
	@Test(expected = IllegalStateException.class)
	public void fail_initMethodNotAnnotated(){
		class TestViewModel implements ViewModel {
			public void initScope(ScopeA scopeA){
			}
		}

		TestViewModel viewModel = new TestViewModel();
		
		ScopeHelper.newScope(ScopeA.class, viewModel);
	}
	
	@Test(expected = IllegalStateException.class)
	public void fail_initMethodHasNoParam(){
		class TestViewModel implements ViewModel {
			@InitScope
			public void initScope(){
			}
		}

		TestViewModel viewModel = new TestViewModel();

		ScopeHelper.newScope(ScopeA.class, viewModel);
	}


	@Test(expected = IllegalStateException.class)
	public void fail_initMethodHasMultipleParams(){
		class TestViewModel implements ViewModel {
			@InitScope
			public void initScope(ScopeA scopeA, ScopeB scopeB){
			}
		}

		TestViewModel viewModel = new TestViewModel();

		ScopeHelper.newScope(ScopeA.class, viewModel);
	}
	
	@Test
	public void success_initMethodCanBePrivate(){
		class TestViewModel implements ViewModel {
			public boolean wasCalled = false;

			@InitScope
			private void initScope(ScopeA scopeA){
				wasCalled = true;
			}
		}

		TestViewModel viewModel = new TestViewModel();

		ScopeHelper.newScope(ScopeA.class, viewModel);

		assertThat(viewModel.wasCalled).isTrue();
	}


	/**
	 * When the {@link ScopeHelper#newScope(Class, ViewModel...)} method
	 * is called we assume that all given viewModels have init methods for this scope.
	 * Otherwise it's probably a misconfiguration that we want the user to know.
	 */
	@Test(expected = IllegalStateException.class)
	public void fail_oneViewModelDefinesNoInitScopeMethod(){
		class TestViewModel1 implements ViewModel {
			@InitScope
			private void initScope(ScopeA scopeA){
			}
		}

		class TestViewModel2 implements ViewModel {
		}
		
		TestViewModel1 viewModel1 = new TestViewModel1();
		TestViewModel2 viewModel2 = new TestViewModel2();
		
		ScopeHelper.newScope(ScopeA.class ,viewModel1, viewModel2);
	}
	
	@Test(expected = IllegalStateException.class)
	public void fail_multipleInitMethodsForSameScope(){
		class TestViewModel implements ViewModel {
			@InitScope
			public void initScope(ScopeA scopeA){
			}
			
			@InitScope
			public void initAgain(ScopeA scopeA){
			}
		}
		
		TestViewModel viewModel = new TestViewModel();
		
		ScopeHelper.newScope(ScopeA.class, viewModel);
	}
	
	@Test
	public void success_multipleInitMethodsForDifferentScopes(){
		class TestViewModel implements ViewModel {
			@InitScope
			public void initScope(ScopeA scopeA){
			}

			@InitScope
			public void initAgain(ScopeB scopeB){
			}
		}

		TestViewModel viewModel = new TestViewModel();

		ScopeHelper.newScope(ScopeA.class, viewModel);
	}
	
}
