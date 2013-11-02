package de.saxsys.jfx.mvvm.base.view.util.viewlist;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import de.saxsys.jfx.mvvm.base.view.View;
import de.saxsys.jfx.mvvm.base.viewmodel.ViewModel;
import de.saxsys.jfx.mvvm.viewloader.ViewTuple;

/**
 * Cell which displays the {@link View} from a {@link ViewTuple}. You have to
 * provide the mapping.
 * 
 * @author sialcasa
 * 
 * @param <T>
 *            which is used to create get the {@link ViewTuple}
 */
abstract class ViewListCell<T> extends ListCell<T> implements
		ViewTupleMapper<T> {

	@Override
	public abstract ViewTuple<? extends ViewModel> map(T element);

	@Override
	protected void updateItem(T item, boolean empty) {
		super.updateItem(item, empty);
		if (empty) {
			setText(null);
			setGraphic(null);
		} else {
			setText(null);
			Node currentNode = getGraphic();
			Parent view = map(item).getView();
			Node newNode = (Node) view;
			if (currentNode == null || !currentNode.equals(newNode)) {
				setGraphic(newNode);
			}
		}
	}

}
