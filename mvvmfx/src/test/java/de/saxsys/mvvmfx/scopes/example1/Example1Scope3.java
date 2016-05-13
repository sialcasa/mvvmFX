package de.saxsys.mvvmfx.scopes.example1;

import de.saxsys.mvvmfx.Scope;

public class Example1Scope3 implements Scope {

	public Example1Scope3() {
		System.out.println("new " + this.getClass().getSimpleName() + "(), "+ System.identityHashCode(this));
	}
}
