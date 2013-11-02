package de.saxsys.jfx.mvvm.base.view.util.viewlist;

import de.saxsys.jfx.mvvm.base.viewmodel.ViewModel;
import de.saxsys.jfx.mvvm.viewloader.ViewTuple;

/**
 * Delares how to map from a <T> to a {@link ViewTuple}.
 * 
 * @author sialcasa
 * 
 * @param <T>
 *            from
 */
public interface ViewTupleMapper<T> {

	/**
	 * Map a <T> to a {@link ViewTuple}.
	 * 
	 * @param element
	 *            to map
	 * @return created {@link ViewTuple}
	 */
	public abstract ViewTuple<? extends ViewModel> map(T element);

}