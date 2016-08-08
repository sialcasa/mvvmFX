package de.saxsys.mvvmfx.internal.viewloader;


/**
 * This class contains logic that is used by the {@link FxmlViewLoader}.
 *
 * The helper methods in this class are <b>not</b> meant to be general purpose but are specific
 * for the {@link FxmlViewLoader} and will be changed whenever it's needed by the view loader.
 * However, additional tools and helpers may use these methods to build helpful utilities that
 * mimic the behaviour of the view loader. *
 *
 */
public class FxmlViewLoaderUtils {

	/**
	 * This method is used to create a String with the path to the FXML file for
	 * a given View class.
	 *
	 * This is done by taking the package of the view class (if any) and replace
	 * "." with "/". After that the Name of the class and the file ending
	 * ".fxml" is appended.
	 *
	 * Example: de.saxsys.myapp.ui.MainView as view class will be transformed to
	 * "/de/saxsys/myapp/ui/MainView.fxml"
	 *
	 * Example 2: MainView (located in the default package) will be transformed
	 * to "/MainView.fxml"
	 *
	 * @param viewType
	 *            the view class type.
	 * @return the path to the fxml file as string.
	 */
	public static String resolveFxmlPath(Class<? extends View> viewType) {
		final StringBuilder pathBuilder = new StringBuilder();

		pathBuilder.append("/");

		if (viewType.getPackage() != null) {
			pathBuilder.append(viewType.getPackage().getName().replaceAll("\\.", "/"));
			pathBuilder.append("/");
		}

		pathBuilder.append(viewType.getSimpleName());
		pathBuilder.append(".fxml");

		return pathBuilder.toString();
	}

}
