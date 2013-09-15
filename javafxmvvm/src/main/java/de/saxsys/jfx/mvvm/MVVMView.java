package de.saxsys.jfx.mvvm;

import javafx.fxml.Initializable;

/**
 * Abstract class for a MVVMView - you have to say which viewmodel it uses. Then
 * you can use the embedded {@link MVVMViewModel} property which is typed
 * correctly.
 * 
 * @author alexander.casall
 * 
 * @param <ViewModel>
 *            type
 */
public abstract class MVVMView<ViewModel extends MVVMViewModel> implements
		Initializable {

	/**
	 * Viewmodel.
	 */
	protected ViewModel viewModel;

	/**
	 * Set the Viewmodel.
	 * 
	 * @param viewModel
	 *            to set
	 */
	public final void setViewModel(final ViewModel viewModel) {
		beforeViewModelInitialization();
		this.viewModel = viewModel;
		afterViewModelInitialization();
	}

	/**
	 * @return the viewmodel
	 */
	public ViewModel getViewModel() {
		return viewModel;
	}

	/**
	 * This method is called before the viewmodel is going to be set.
	 */
	public abstract void beforeViewModelInitialization();

	/**
	 * This method is called after the viewmodel was set.
	 */
	public abstract void afterViewModelInitialization();

}
