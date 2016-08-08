package de.saxsys.mvvmfx.devtools.core.fxmlanalyzer;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.warnings.Warning;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * This class represents a node in a FXML tree.
 */
public class ViewNode {

	private String fxmlPath;
	private Class<?> viewType;
	private Class<? extends ViewModel> viewModelType;
	private List<ViewNode> children = new ArrayList<>();
	private List<Warning> warnings = new ArrayList<>();

	public ViewNode(String fxmlPath) {
		this.fxmlPath = fxmlPath;
	}

	void addWarnings(Warning... warnings) {
		switch (warnings.length) {
			case 0:
				break;
			case 1:
				this.warnings.add(warnings[0]);
				break;
			default:
				this.warnings.addAll(Arrays.asList(warnings));
				break;
		}
	}

	void setControllerType(Class<?> controllerType) {
		this.viewType = controllerType;
	}

	void addChildren(Collection<ViewNode> children) {
		this.children.addAll(children);
	}

	void addViewModelType(Class<? extends ViewModel> viewModelType) {
		this.viewModelType = viewModelType;
	}

	public String getFxmlPath() {
		return fxmlPath;
	}


	/**
	 * The Optional returned by this method will contain the
	 * class type of the controller class defined in the fxml root element with "fx:controller".
	 * <br />
	 * If the fxml document doesn't define a controller with the "fx:controller" attribute
	 * the Optional will be empty.
	 * <br />
	 *
	 * The difference between this method and {@link #getViewType()} is that
	 * the Optional returned by this method will contain the class type of the controller even
	 * if it isn't a mvvmFX view class.
	 */
	public Optional<Class<?>> getControllerClass() {
		return Optional.ofNullable(viewType);
	}

	/**
	 * If this {@link ViewNode} represents a mvvmFX fxml view
	 * the returned Optional will contain the Class type of the View/CodeBehind
	 * that implements {@link FxmlView}.
	 * <br />
	 *
	 * Otherwise the returned Optional will be empty.
	 */
	public Optional<Class<? extends FxmlView>> getViewType() {
		return getControllerClass()
				.filter(FxmlView.class::isAssignableFrom)
				.map(type -> (Class<? extends FxmlView>) type);
	}

	public Optional<Class<? extends ViewModel>> getViewModelType() {
		return Optional.ofNullable(viewModelType);
	}

	public List<ViewNode> getChildren() {
		return Collections.unmodifiableList(children);
	}

	public List<Warning> getWarnings() {
		return Collections.unmodifiableList(warnings);
	}
}
