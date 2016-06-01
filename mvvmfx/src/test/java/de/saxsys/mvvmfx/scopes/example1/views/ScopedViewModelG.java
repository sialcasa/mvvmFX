package de.saxsys.mvvmfx.scopes.example1.views;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.scopes.example1.Example1Scope3;

public class ScopedViewModelG implements ViewModel{

	@InjectScope
	public Example1Scope3 testScope3;


	public ScopedViewModelG() {
		System.out.println("new " + this.getClass().getSimpleName() + "()");
	}

	public void initialize() {
		System.out.println(this.getClass().getSimpleName() + ".initialize()");
		System.out.println("scope:" + System.identityHashCode(testScope3));
	}

}
