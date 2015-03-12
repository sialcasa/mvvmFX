package de.saxsys.mvvmfx.example.todomvc.ui.additems;

import de.saxsys.mvvmfx.FxmlView;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

/**
 * @author manuel.mauky
 */
public class AddItemsView implements FxmlView<AddItemsViewModel> {
	@FXML
	public CheckBox selectAll;
	@FXML
	public TextField addInput;
}
