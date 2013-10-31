package de.saxsys.jfx.mvvm.base.viewmodel.util.itemlist;

import de.saxsys.jfx.mvvm.base.view.View;

public interface ModelToStringMapper<ModelType> {

	/**
	 * Defines how an model element is represented by a String to display it in
	 * the {@link View}.
	 * 
	 * @param object
	 *            to map
	 * @return string representation
	 */
	public abstract String toString(ModelType object);

}
