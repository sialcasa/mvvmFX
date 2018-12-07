/*******************************************************************************
 * Copyright 2018 Manuel Mauky
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

import java.util.Map;
import java.util.function.BiFunction;

/**
 * A functional interface to define an immutable "setter" method of type {@link Map}. As the model element is immutable
 * this method is not a real "setter". Instead it returns a new immutable copy of the original model element that has
 * the specified field updated to the new value.
 *
 * @param <M> the generic type of the model.
 */
@FunctionalInterface
public interface MapImmutableSetter<M, K, V> extends BiFunction<M, Map<K, V>, M> {

	/**
	 * @param model       the model instance
	 * @param newElements the new elements of this map field.
	 *
	 * @return a new model instance with the new values
	 */
	@Override
	M apply(M model, Map<K, V> newElements);
}