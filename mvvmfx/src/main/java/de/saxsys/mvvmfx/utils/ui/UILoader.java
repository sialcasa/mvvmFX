package de.zerotask.voices.ui;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * A small utility class that is capable of loading views. TODO: generic
 * parameter VM could eventually be dropped but the whole thing would be then
 * not type-safe...
 * 
 * @author SirWindfield
 *
 */
public class UILoader {

	public static <V extends FxmlView<VM>, VM extends ViewModel> void load(Class<V> viewClass, Class<VM> viewModelClass,
			Stage parentStage) {
		load(viewClass, viewModelClass, parentStage, "");
	}

	public static <V extends FxmlView<VM>, VM extends ViewModel> void load(Class<V> viewClass, Class<VM> viewModelClass,
			Stage parentStage, String title) {
		load(viewClass, viewModelClass, parentStage, title, true);
	}

	public static <V extends FxmlView<VM>, VM extends ViewModel> void load(Class<V> viewClass, Class<VM> viewModelClass,
			Stage parentStage, String title, String... css) {
		load(viewClass, viewModelClass, parentStage, title, true, (String[]) null);
	}

	public static <V extends FxmlView<VM>, VM extends ViewModel> void load(Class<V> viewClass, Class<VM> viewModelClass,
			Stage parentStage, String title, boolean resizable, String... css) {
		// load our UI
		ViewTuple<V, VM> ui = FluentViewLoader.fxmlView(viewClass).load();
		// retrieve view element
		Parent view = ui.getView();
		// create a stage for displaying
		Stage stage = createStage(view, parentStage, title, resizable, css);
		// if view is instance of Displayable, set stage so it will
		// automatically be available as soon as the view gets loaded
		if (Displayable.class.isAssignableFrom(viewClass)) {
			Displayable displayable = (Displayable) ui.getCodeBehind();
			displayable.setStage(stage);
		}
	}

	private static Stage createStage(Parent view, Stage parentStage, String title, boolean resizable, String... css) {
		// create a new stage
		Stage stage = new Stage(); // the stage style could possibly be set by a
									// system property. Normally, if you want to
									// display additional windows the default
									// style should be used
		// this stage should be displayed over the parent stage
		stage.initOwner(parentStage);
		// this will prevent events from being passed to the parent stage
		stage.initModality(Modality.NONE);
		// customize the stage...
		stage.setTitle(title);
		stage.setResizable(resizable);

		// to further make the window feel more natural, pass all icons from the
		// parent to this stage
		stage.getIcons().addAll(parentStage.getIcons());

		// copied from dialog helper:
		if (stage.getScene() == null) {
			Scene scene = new Scene(view);
			// just use the css property if it is non null
			if (css != null && css.length != 0) {
				// convention: if the first element is null, the var args will be
				// assumed to be empty...
				if (css[0] != null) {
					scene.getStylesheets().addAll(css);
				}
			}
			stage.setScene(scene);

			// finally show the stage
			stage.show();
			// return the stage so it can be used for the code behind part
			return stage;
		}
		// I have no idea if that actually will ever happen!
		return null;
	}

	public static <V extends FxmlView<VM>, VM extends ViewModel> void load(Class<V> viewClass, Class<VM> viewModelClass,
			Stage parentStage, String title, boolean resizable) {
		load(viewClass, viewModelClass, parentStage, title, resizable, (String[]) null);
	}
}
