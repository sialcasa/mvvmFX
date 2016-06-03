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

import java.util.function.BiConsumer;

/**
 * A functional interface to define a setter method of type {@link Boolean}.
 *
 * @param <M>
 *            the generic type of the model.
 */
@FunctionalInterface
public interface BooleanSetter<M> extends BiConsumer<M, Boolean> {
	
	/**
	 * @param model
	 *            the model instance.
	 * @param value
	 *            the new value to be set.
	 */
	@Override
	void accept(M model, Boolean value);
}
