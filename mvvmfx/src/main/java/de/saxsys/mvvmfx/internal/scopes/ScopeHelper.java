package de.saxsys.mvvmfx.internal.scopes;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.internal.ReflectionUtils;
import de.saxsys.mvvmfx.internal.viewloader.DependencyInjector;

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
				.forEach(vm -> injectScope(scope, vm));
	}
	
	private static void injectScope(ViewModel scope, ViewModel target){
		List<Field> scopeFields = Arrays.stream(target.getClass()
				.getDeclaredFields())
				.filter(field -> field.getType().equals(scope.getClass()))
				.collect(Collectors.toList());
		
		if(scopeFields.isEmpty()){
			throw new IllegalStateException("The viewModel [" + target + "] has no field defined for the scope [" + scope +"]");
		}
		
		if(scopeFields.size() > 1){
			throw new IllegalStateException("The viewModel [" + target + "] has more then one field defined for the scope [" + scope + "]");
		}

		Field scopeField = scopeFields.get(0);

		ReflectionUtils.accessField(scopeField, ()->scopeField.set(target, scope), "Can't inject the Scope ["
				+ scope + "] in the viewModel [" + target + "].");
		
		String initializerMethodName = calcInitializerMethodName(scopeField.getName());

		try {
			Method initializer = target.getClass().getMethod(initializerMethodName);
			
			initializer.invoke(target);
		} catch (NoSuchMethodException e) {
			// nothing. It's ok if the target has no initializer for the scope.
		} catch (InvocationTargetException | IllegalAccessException  e) {
			throw new IllegalStateException("Can't invoke initializer method [" + initializerMethodName + "] of viewModel [" + target + "].", e);
		}

	}
	
	static String calcInitializerMethodName(String scopeFieldName){
		StringBuilder methodName = new StringBuilder();
		
		methodName.append("init");
		methodName.append(scopeFieldName.substring(0,1).toUpperCase());
		methodName.append(scopeFieldName.substring(1));
		
		return methodName.toString();
	}
}