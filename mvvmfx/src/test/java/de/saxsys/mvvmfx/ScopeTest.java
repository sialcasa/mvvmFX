package de.saxsys.mvvmfx;


import org.junit.Assert;
import org.junit.Test;

import de.saxsys.mvvmfx.internal.viewloader.example.ScopedFxmlViewA;
import de.saxsys.mvvmfx.internal.viewloader.example.ScopedFxmlViewB;
import de.saxsys.mvvmfx.internal.viewloader.example.ScopedJavaViewA;
import de.saxsys.mvvmfx.internal.viewloader.example.ScopedJavaViewB;
import de.saxsys.mvvmfx.internal.viewloader.example.ScopedViewModelA;
import de.saxsys.mvvmfx.internal.viewloader.example.ScopedViewModelB;
import de.saxsys.mvvmfx.internal.viewloader.example.TestScope;

public class ScopeTest {
	
	
	@Test
	public void testJavaScopedView() throws Exception {
		
		ViewTuple<ScopedJavaViewA, ScopedViewModelA> load1 = FluentViewLoader.javaView(ScopedJavaViewA.class).load();
		ViewTuple<ScopedJavaViewB, ScopedViewModelB> load2 = FluentViewLoader.javaView(ScopedJavaViewB.class).load();
		
		TestScope scope1a = load1.getViewModel().getScope();
		TestScope scope2a = load1.getViewModel().getScope2();
		TestScope scope3a = load1.getViewModel().getScope3();
		
		TestScope scope1b = load2.getViewModel().getScope();
		TestScope scope2b = load2.getViewModel().getScope2();
		TestScope scope3b = load2.getViewModel().getScope3();
		
		Assert.assertNotNull(scope1a);
		Assert.assertNotNull(scope2a);
		Assert.assertNotNull(scope3a);
		Assert.assertNotNull(scope1b);
		Assert.assertNotNull(scope2b);
		Assert.assertNotNull(scope3b);
		
		Assert.assertEquals(scope1a, scope1b);
		Assert.assertEquals(scope2a, scope2b);
		Assert.assertEquals(scope3a, scope3b);
		
		Assert.assertNotEquals(scope1a, scope2a);
		Assert.assertNotEquals(scope1a, scope3a);
		Assert.assertNotEquals(scope2a, scope3a);
	}
	
	@Test
	public void testFxmlScopedView() throws Exception {
		
		ViewTuple<ScopedFxmlViewA, ScopedViewModelA> load1 = FluentViewLoader.fxmlView(ScopedFxmlViewA.class).load();
		ViewTuple<ScopedFxmlViewB, ScopedViewModelB> load2 = FluentViewLoader.fxmlView(ScopedFxmlViewB.class).load();
		
		TestScope scope1a = load1.getViewModel().getScope();
		TestScope scope2a = load1.getViewModel().getScope2();
		TestScope scope3a = load1.getViewModel().getScope3();
		
		TestScope scope1b = load2.getViewModel().getScope();
		TestScope scope2b = load2.getViewModel().getScope2();
		TestScope scope3b = load2.getViewModel().getScope3();
		
		Assert.assertNotNull(scope1a);
		Assert.assertNotNull(scope2a);
		Assert.assertNotNull(scope3a);
		Assert.assertNotNull(scope1b);
		Assert.assertNotNull(scope2b);
		Assert.assertNotNull(scope3b);
		
		Assert.assertEquals(scope1a, scope1b);
		Assert.assertEquals(scope2a, scope2b);
		Assert.assertEquals(scope3a, scope3b);
		
		Assert.assertNotEquals(scope1a, scope2a);
		Assert.assertNotEquals(scope1a, scope3a);
		Assert.assertNotEquals(scope2a, scope3a);
	}
	
}
