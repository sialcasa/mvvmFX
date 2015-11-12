/*******************************************************************************
 * Copyright 2015 Alexander Casall, Manuel Mauky
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
