package de.saxsys.mvvmfx.scopes.example3;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.scopes.example3.views.MainView;
import de.saxsys.mvvmfx.scopes.example3.views.MainViewModel;
import org.junit.Ignore;
import org.junit.Test;

@Ignore("Ignore until fixed")
public class Example3Test {

	@Test
	public void test() {

		ViewTuple<MainView, MainViewModel> viewTuple = FluentViewLoader.fxmlView(MainView.class).load();



	}

}
