package de.saxsys.mvvmfx.scopes;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.internal.ReflectionUtils;
import de.saxsys.mvvmfx.internal.viewloader.DependencyInjector;
import sun.misc.Ref;

/**
 * TODO Rename
 */
public class ScopeHelper {

	public static <T extends ViewModel> T newScope(Class<T> scopeType, ViewModel...targets){
		T scopeViewModel = DependencyInjector.getInstance().getInstanceOf(scopeType);
		
		newScope(scopeViewModel, targets);
		return scopeViewModel;
	}
	
	public static void newScope(ViewModel scope, ViewModel...targets){
		Arrays.stream(targets)
				.forEach(vm -> initScope(scope, vm));
	}
	
	private static void initScope(ViewModel scope, ViewModel target){
		final List<Method> initMethods = Arrays.stream(target.getClass().getDeclaredMethods())
				.filter(m -> m.isAnnotationPresent(InitScope.class))
				.filter(m -> m.getParameterCount() == 1)
				.filter(m -> m.getParameterTypes()[0].equals(scope.getClass()))
				.collect(Collectors.toList());



		final String errorMessageBegin = "Try to init the scope ["
				+ scope + "] for the viewModel of type ["
				+ target + "]";
	
		
		if(initMethods.isEmpty()){
			throw new IllegalStateException(errorMessageBegin + " but the viewModel has no init method declared. "
					+ "The viewModel should have a method that takes an instance of the scope as parameter and is " 
					+ "annotated with @InitScope");
		}

		if(initMethods.size() > 1) {
			throw new IllegalStateException(errorMessageBegin + " but the viewModel has multiple init method declared for the same scope. "
					+ "The viewModel should have only one method that takes an instance of the scope as parameter and is "
					+ "annotated with @InitScope for a scope");
		}
		
		Method method = initMethods.get(0);

		ReflectionUtils.invokePrivateMethod(method, target, errorMessageBegin
				+ " but an exception was thrown while invoking the init method.", scope);
	}
}