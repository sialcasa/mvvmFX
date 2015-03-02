package de.saxsys.mvvmfx.internal.viewloader;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.internal.ReflectionUtils;
import de.saxsys.mvvmfx.scopes.InjectScope;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author manuel.mauky
 */
class ScopeInjector {
	
	
	static void injectScope(ViewModel target, Object scope) {

		final List<Field> fieldsWithAnnotation = ReflectionUtils.getFieldsWithAnnotation(target, InjectScope.class);

		final List<Field> scopeFields = fieldsWithAnnotation
				.stream()
				.filter(field -> field.getType().isAssignableFrom(scope.getClass()))
				.collect(Collectors.toList());
		
		scopeFields.forEach(field -> ReflectionUtils.setField(field, target, scope));
	}
	
}
