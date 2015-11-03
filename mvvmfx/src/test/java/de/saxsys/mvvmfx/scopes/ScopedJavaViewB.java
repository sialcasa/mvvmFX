package de.saxsys.mvvmfx.scopes;

import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.JavaView;
import javafx.scene.layout.VBox;

/**
 * This class is used as example View class that is written in pure java.
 * 
 * @author alexander.casall
 */
public class ScopedJavaViewB extends VBox implements JavaView<ScopedViewModelB> {

	@InjectViewModel
	public ScopedViewModelB viewModel;

}
