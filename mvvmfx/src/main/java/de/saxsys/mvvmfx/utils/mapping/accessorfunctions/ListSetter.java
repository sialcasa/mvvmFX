package de.saxsys.mvvmfx.utils.mapping.accessorfunctions;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * A functional interface to define a setter method of a list type.
 *
 * @param <M>
 *            the generic type of the model.
 * @param <E>
 *            the type of the list elements.
 */
@FunctionalInterface
public interface ListSetter<M, E> extends BiConsumer<M, List<E>> {

    /**
     * @param model
     *            the model instance.
     * @param value
     *            the new value to be set.
     */
    @Override
    void accept(M model, List<E> value);
}
