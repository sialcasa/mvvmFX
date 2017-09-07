package de.saxsys.mvvmfx;

import java.util.Arrays;
import java.util.Collection;
import java.util.ResourceBundle;

import de.saxsys.mvvmfx.internal.viewloader.FxmlViewLoader;
import de.saxsys.mvvmfx.internal.viewloader.JavaViewLoader;
import de.saxsys.mvvmfx.internal.viewloader.ResourceBundleManager;

/**
 * Fluent API for loading Views. <br>
 * 
 * The typical usage will look like this:
 * 
 * <br>
 * 
 * <pre>
 * public class MyCoolViewModel implements ViewModel {...}
 * public class MyCoolView implements FxmlView{@code <MyCoolViewModel>} {...}
 * 
 * 
 * public class MyApp extends Application {
 *     
 *    {@literal @}Override
 *     public void start(Stage stage) {
 * 		ViewTuple{@code <MyCoolView, MyCoolViewModel>} viewTuple 
 * 				= FluentViewLoader.fxmlView(MyCoolView.class).load(); 
 * 			
 * 		Parent root = viewTuple.getView();
 * 		stage.setScene(new Scene(root));
 * 		stage.show();	
 *     }
 * }
 * 
 * </pre>
 * 
 * 
 * This class is implemented as a Step-Builder. You can choose between
 * {@link FxmlView} and {@link JavaView} with the first method call. After that
 * you will only get builder-methods that are suitable for the view type you
 * have chosen.
 * 
 * @author manuel.mauky
 */
public class FluentViewLoader {

    /**
     * This class is the builder step to load a java based view. It is accessed
     * from the {@link FluentViewLoader} with the method
     * {@link FluentViewLoader#javaView(Class)}.
     * 
     * @param <V>
     *            the generic type of the View that should be loaded. This type
     *            has to implement {@link de.saxsys.mvvmfx.JavaView}.
     * @param <VM>
     *            the generic type of the ViewModel. This type has to implement
     *            {@link de.saxsys.mvvmfx.ViewModel}.
     */
    public static class JavaViewStep<V extends JavaView<? extends VM>, VM extends ViewModel> {

        private final Class<? extends V> viewType;
        private ResourceBundle resourceBundle;

        private VM viewModel;
        private V codeBehind;
        private Context context;
        private Collection<Scope> providedScopes;

        JavaViewStep(Class<? extends V> viewType) {
            this.viewType = viewType;
        }

        public JavaViewStep<V, VM> context(Context context) {
            this.context = context;
            return this;
        }

        public JavaViewStep<V, VM> providedScopes(Scope... providedScopes) {
            this.providedScopes = Arrays.asList(providedScopes);
            return this;
        }

        public JavaViewStep<V, VM> providedScopes(Collection<Scope> providedScopes) {
            this.providedScopes = providedScopes;
            return this;
        }



        /**
         * Provide a {@link ResourceBundle} that is used while loading this
         * view. Note: It is possible to provide a global application-wide
         * resourceBundle via
         * {@link MvvmFX#setGlobalResourceBundle(ResourceBundle)} method.
         *
         * If there is a global resourceBundle set it will be merged with the
         * resourceBundle provided by this builder method. The resourceBundle
         * provided by this method will have a higher priority then the global
         * one which means that if there are duplicate keys, the values of the
         * global resourceBundle will be overwritten and the values of this
         * resourceBundle will be used.
         *
         * @param resourceBundle
         *            the resource bundle that is used while loading the view.
         * @return this instance of the builder step.
         */
        public JavaViewStep<V, VM> resourceBundle(ResourceBundle resourceBundle) {
            this.resourceBundle = resourceBundle;
            return this;
        }

        /**
         * This param is used to define an existing viewModel instance to be
         * used when loading the view.<br>
         *
         * A typical use case is when you like to have two or more views that
         * are sharing the same viewModel.
         *
         * @param viewModel
         *            the viewModel instance that is used to load the java view.
         * @return this instance of the builder step.
         */
        public JavaViewStep<V, VM> viewModel(VM viewModel) {
            this.viewModel = viewModel;
            return this;
        }

        /**
         * This param is used to define an existing instance of the codeBehind
         * class that is used instead of creating a new one while loading. <br>
         *
         * This can be useful when creating custom controls.
         *
         * @param codeBehind
         *            the codeBehind instance that is used to load this java
         *            view.
         * @return this instance of the builder step.
         */
        public JavaViewStep<V, VM> codeBehind(V codeBehind) {
            this.codeBehind = codeBehind;
            return this;
        }

        /**
         * The final step of the Fluent API. This method loads the view based on
         * the given params.
         *
         * @return a view tuple containing the loaded view.
         */
        public ViewTuple<V, VM> load() {
            JavaViewLoader javaViewLoader = new JavaViewLoader();

            return javaViewLoader.loadJavaViewTuple(viewType,
                    ResourceBundleManager.getInstance().mergeWithGlobal(resourceBundle), viewModel, codeBehind, context,
                    providedScopes);
        }

    }

    /**
     * This class is the builder step to load a fxml based view. It is accessed
     * from the {@link FluentViewLoader} with the method
     * {@link FluentViewLoader#fxmlView(Class)}.
     *
     * @param <V>
     *            the generic type of the View that should be loaded. This type
     *            has to implement {@link de.saxsys.mvvmfx.FxmlView}.
     * @param <VM>
     *            the generic type of the ViewModel. This type has to implement
     *            {@link de.saxsys.mvvmfx.ViewModel}.
     */
    public static class FxmlViewStep<V extends FxmlView<? extends VM>, VM extends ViewModel> {

        private final Class<? extends V> viewType;
        private ResourceBundle resourceBundle;
        private Object root;
        private V codeBehind;
        private VM viewModel;
        private Context context;
        private Collection<Scope> providedScopes;

        FxmlViewStep(Class<? extends V> viewType) {
            this.viewType = viewType;
        }

        public FxmlViewStep<V, VM> context(Context context) {
            this.context = context;
            return this;
        }

        public FxmlViewStep<V, VM> providedScopes(Scope... providedScopes) {
            this.providedScopes = Arrays.asList(providedScopes);
            return this;
        }

        public FxmlViewStep<V, VM> providedScopes(Collection<Scope> providedScopes) {
            this.providedScopes = providedScopes;
            return this;
        }

        /**
         * Provide a {@link ResourceBundle} that is used while loading this
         * view. Note: It is possible to provide a global application-wide
         * resourceBundle via
         * {@link MvvmFX#setGlobalResourceBundle(ResourceBundle)} method.
         * 
         * If there is a global resourceBundle set it will be merged with the
         * resourceBundle provided by this builder method. The resourceBundle
         * provided by this method will have a higher priority then the global
         * one which means that if there are duplicate keys, the values of the
         * global resourceBundle will be overwritten and the values of this
         * resourceBundle will be used.
         *
         * @param resourceBundle
         *            the resource bundle that is used while loading the view.
         * @return this instance of the builder step.
         */
        public FxmlViewStep<V, VM> resourceBundle(ResourceBundle resourceBundle) {
            this.resourceBundle = resourceBundle;
            return this;
        }

        /**
         * This param is used to define a JavaFX node that is used as the root
         * element when loading the fxml file. <br>
         * 
         * This can be useful when creating custom controls with the fx:root
         * element.
         * 
         * @param root
         *            the root element that is used to load the fxml file.
         * @return this instance of the builder step.
         */
        public FxmlViewStep<V, VM> root(Object root) {
            this.root = root;
            return this;
        }

        /**
         * This param is used to define an existing instance of the codeBehind
         * class that is used instead of creating a new one while loading. <br>
         *
         * This can be useful when creating custom controls with the fx:root
         * element.
         *
         * @param codeBehind
         *            the codeBehind instance that is used to load the fxml
         *            file.
         * @return this instance of the builder step.
         */
        public FxmlViewStep<V, VM> codeBehind(V codeBehind) {
            this.codeBehind = codeBehind;
            return this;
        }

        /**
         * This param is used to define an existing viewModel instance to be
         * used when loading the view.<br>
         * 
         * A typical use case is when you like to have two or more views that
         * are sharing the same viewModel.
         * 
         * @param viewModel
         *            the viewModel instance that is used to load the fxml file.
         * @return this instance of the builder step.
         */
        public FxmlViewStep<V, VM> viewModel(VM viewModel) {
            this.viewModel = viewModel;
            return this;
        }

        /**
         * The final step of the Fluent API. This method loads the view based on
         * the given params.
         * 
         * @return a view tuple containing the loaded view.
         */
        public ViewTuple<V, VM> load() {
            FxmlViewLoader fxmlViewLoader = new FxmlViewLoader();

            return fxmlViewLoader.loadFxmlViewTuple(viewType,
                    ResourceBundleManager.getInstance().mergeWithGlobal(resourceBundle), codeBehind, root, viewModel,
                    context, providedScopes);
        }
    }

    /**
     * This method is the entry point of the Fluent API to load a java based
     * view.
     * 
     * @param viewType
     *            the type of the view that should be loaded.
     * @param <V>
     *            the type of the View that should be loaded. This type has to
     *            implement {@link de.saxsys.mvvmfx.JavaView}.
     * @param <VM>
     *            the type of the ViewModel. This type has to implement
     *            {@link de.saxsys.mvvmfx.ViewModel}.
     * 
     * @return a builder step that can be further configured and then load the
     *         actual view.
     */
    public static <V extends JavaView<? extends VM>, VM extends ViewModel> JavaViewStep<V, VM> javaView(
            Class<? extends V> viewType) {
        return new JavaViewStep<>(viewType);
    }

    /**
     * This method is the entry point of the Fluent API to load a fxml based
     * View.
     * 
     * @param viewType
     *            the type of the view that should be loaded.
     * @param <V>
     *            the generic type of the View that should be loaded. This type
     *            has to implement {@link de.saxsys.mvvmfx.FxmlView}.
     * @param <VM>
     *            the generic type of the ViewModel. This type has to implement
     *            {@link de.saxsys.mvvmfx.ViewModel}.
     * 
     * @return a builder step that can be further configured and then load the
     *         actual view.
     */
    public static <V extends FxmlView<? extends VM>, VM extends ViewModel> FxmlViewStep<V, VM> fxmlView(
            Class<? extends V> viewType) {
        return new FxmlViewStep<>(viewType);
    }


    private FluentViewLoader() {
	}
}
