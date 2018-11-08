package de.saxsys.mvvmfx.utils.mapping.accessorfunctions;

import java.util.Map;
import java.util.function.Function;

/**
 * A functional interface to define a getter method of a map type.
 *
 * @param <M> the generic type of the model.
 *
 * @param <K> the type of the key.
 *
 * @param <V> the type of the value.
 */
@FunctionalInterface
public interface MapGetter<M, K, V> extends Function<M, Map<K, V>> {

    /**
     * @param model the model instance
     * @return the value of the field
     */
    @Override
    Map<K, V> apply(M model);
}
