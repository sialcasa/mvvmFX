package de.saxsys.mvvmfx.utils.mapping.accessorfunctions;

import java.util.function.BiFunction;

@FunctionalInterface
public interface StringImmutableSetter<M> extends BiFunction<M, String, M> {

	@Override
	M apply(M model, String newValue);
}
