package de.saxsys.mvvmfx.internal.viewloader;

import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewModel;
import net.jodah.typetools.TypeResolver;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

/**
 * This class is used to encapsulate operations based on reflection that are needed for 
 * the view loading.
 */
public class ReflectionUtils {
	
	/**
	 * Returns the {@link java.lang.reflect.Field} of the viewModel for a given view type and viewModel type.
	 * If there is no annotated field for the viewModel in the view the returned Optional will be empty.
	 * 
	 * @param viewType the type of the view
	 * @param viewModelType the type of the viewModel
	 * @return an Optional that contains the Field when the field exists.
	 */
	static Optional<Field> getViewModelField(Class<? extends View> viewType, Class<?> viewModelType){
		List<Field> viewModelFields = Arrays.stream(viewType.getDeclaredFields())
				.filter(field -> field.isAnnotationPresent(InjectViewModel.class))
				.filter(field -> field.getType().isAssignableFrom(viewModelType))
				.collect(Collectors.toList());

		if(viewModelFields.isEmpty()){
			return Optional.empty();
		}

		if(viewModelFields.size() > 1){
			throw new RuntimeException("The View <" + viewType + "> may only define one viewModel but there were <" + viewModelFields.size() + "> viewModel fields!");
		}

		return Optional.of(viewModelFields.get(0));
	}


	/**
	 * This method is used to get the ViewModel instance of a given view/codeBehind.
	 *
	 * @param view the view instance where the viewModel will be looked for.
	 * @param <ViewType> the generic type of the View
	 * @param <ViewModelType> the generic type of the ViewModel
	 * @return the ViewModel instance or null if no viewModel could be found.
	 */
	@SuppressWarnings("unchecked")
	static <ViewType extends View<? extends ViewModelType>, ViewModelType extends ViewModel> ViewModelType getViewModel(ViewType view){

		final Class<?> viewModelType = TypeResolver.resolveRawArgument(View.class, view.getClass());
		Optional<Field> fieldOptional = ReflectionUtils.getViewModelField(view.getClass(), viewModelType);

		if(fieldOptional.isPresent()){
			Field field = fieldOptional.get();

			return accessField(field, ()-> (ViewModelType)field.get(view), "Can't get the viewModel of type <" + viewModelType + ">");
		}else{
			return null;
		}
	}

	/**
	 * Helper method to execute a callback on a given field. This method encapsulates the error handling logic and the 
	 * handling of accessibility of the field.
	 */
	static <T> T accessField(final Field field, final Callable<T> callable, String errorMessage){
		return AccessController.doPrivileged((PrivilegedAction<T>) () -> {
			boolean wasAccessible = field.isAccessible();

			try {
				field.setAccessible(true);
				if (callable != null) {
					return callable.call();
				}
			} catch (Exception exception) {
				throw new IllegalStateException(errorMessage, exception);
			} finally {
				field.setAccessible(wasAccessible);
			}
			return null;
		});
	}


	/**
	 * Injects the given viewModel instance into the given view. The injection will only happen when
	 * the class of the given view has a viewModel field that fulfills all requirements for the viewModel injection 
	 * (matching types, no viewModel already existing ...).
	 * 
	 * @param view
	 * @param viewModel
	 */
	static void injectViewModel(final View view, ViewModel viewModel){
		if(viewModel == null){
			return;
		}

		final Optional<Field> fieldOptional = ReflectionUtils.getViewModelField(view.getClass(), viewModel.getClass());

		if(fieldOptional.isPresent()) {
			Field field = fieldOptional.get();

			ReflectionUtils.accessField(field, () -> {
				Object existingViewModel = field.get(view);

				if (existingViewModel == null) {
					field.setAccessible(true);
					field.set(view, viewModel);
				}

				return null;
			}, "Can't inject ViewModel of type <" + viewModel.getClass()
					+ "> into the view <" + view + ">");
		}

	}


	/**
	 * Creates a viewModel instance for a View type. The type of the view is determined by the given view instance. 
	 *
	 * For the creation of the viewModel the {@link de.saxsys.mvvmfx.internal.viewloader.DependencyInjector} is used.

	 * @param view the view instance that is used to find out the type of the ViewModel 
	 * @param <ViewType> the generic view type
	 * @param <ViewModelType> the generic viewModel type
	 * @return the viewModel instance or <code>null</code> if the viewModel type can't be found or the viewModel can't be created.
	 */
	@SuppressWarnings("unchecked")
	static <ViewType extends View<? extends ViewModelType>, ViewModelType extends ViewModel> ViewModelType createViewModel(
			ViewType view){
		final Class<?> viewModelType = TypeResolver.resolveRawArgument(View.class, view.getClass());

		if(viewModelType == ViewModel.class){
			return null;
		}

		if(TypeResolver.Unknown.class == viewModelType){
			return null;
		}
		
		return (ViewModelType)DependencyInjector.getInstance().getInstanceOf(viewModelType);
	}
}
