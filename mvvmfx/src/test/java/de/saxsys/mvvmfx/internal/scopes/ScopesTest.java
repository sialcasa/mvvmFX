package de.saxsys.mvvmfx.internal.scopes;

import de.saxsys.mvvmfx.ViewModel;
import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ScopesTest {
	
	public static class ScopeA implements ViewModel{}
	
	public static class ScopeB implements ViewModel{}
	
	public static class MyViewModel implements ViewModel{
		@InjectScope
		public ScopeA scopeA;
		
		@InjectScope
		public ScopeB scopeB;
		
		public static boolean initScopeACalled = false;
		public static boolean scopeAWasInjected = false;
		public static boolean initScopeBCalled = false;
		public static boolean scopeBWasInjected = false;
		
		
		public void initScopeA(){
			initScopeACalled = true;
			scopeAWasInjected = scopeA != null;
		}
		
		public void initScopeB(){
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
	
	
	@Test
	public void testCalcInitializerMethodName(){
		String fieldName = "myScope";
	
		assertThat(ScopeHelper.calcInitializerMethodName(fieldName)).isEqualTo("initMyScope");
	}
	
	
}
