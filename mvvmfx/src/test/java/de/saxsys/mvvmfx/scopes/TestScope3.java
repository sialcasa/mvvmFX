package de.saxsys.mvvmfx.scopes;

import de.saxsys.mvvmfx.Scope;

public class TestScope3 implements Scope {

	public TestScope3() {
		System.out.println("new " + this.getClass().getSimpleName() + "(), "+ System.identityHashCode(this));
	}
}
