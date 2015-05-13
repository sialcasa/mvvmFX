package de.saxsys.mvvmfx.resourcebundle.included;

import de.saxsys.mvvmfx.FxmlView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * @author manuel.mauky
 */
public class IncludedView implements FxmlView<IncludedViewModel> {
	@FXML
	public Label label;
}
