package de.saxsys.mvvmfx.utils.mapping.accessorfunctions;

import java.util.List;
import java.util.function.Function;

/**
 * A functional interface to define a getter method of a list type.
 *
 * @param <M>
 *            the generic type of the model.
 * @param <E>
 *            the type of the list elements.
 */
@FunctionalInterface
public interface ListGetter<M, E> extends Function<M, List<E>> {

    /**
     * @param model
     *            the model instance.
     * @return the value of the field.
     */
    @Override
    List<E> apply(M model);
}
