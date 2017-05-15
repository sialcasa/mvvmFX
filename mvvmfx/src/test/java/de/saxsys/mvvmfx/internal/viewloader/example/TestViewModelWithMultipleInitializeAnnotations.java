package de.saxsys.mvvmfx.internal.viewloader.example;

import de.saxsys.mvvmfx.Initialize;
import de.saxsys.mvvmfx.ViewModel;

public class TestViewModelWithMultipleInitializeAnnotations implements ViewModel {

	public static boolean init1 = false;
	public static boolean init2 = false;
	public static boolean initialize = false;


	@Initialize
	public void init1() {
		init1 = true;
	}

	@Initialize
	private void init2(){
		init2 = true;
	}

	public void initialize() {
		initialize = true;
	}


}
