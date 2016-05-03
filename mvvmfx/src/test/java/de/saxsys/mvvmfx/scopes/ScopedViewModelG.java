package de.saxsys.mvvmfx.scopes;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;

public class ScopedViewModelG implements ViewModel{

	@InjectScope
	public TestScope3 testScope3;


	public ScopedViewModelG() {
		System.out.println("new " + this.getClass().getSimpleName() + "()");
	}

	public void initialize() {
		System.out.println(this.getClass().getSimpleName() + ".initialize()");
		System.out.println("scope:" + System.identityHashCode(testScope3));
	}

}
