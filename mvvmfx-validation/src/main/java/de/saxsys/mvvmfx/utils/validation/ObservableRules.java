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
package de.saxsys.mvvmfx.utils.validation;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;

import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * A collection of observable boolean constructors that can be used as rules for the
 * {@link ObservableRuleBasedValidator}.
 */
public class ObservableRules {
	
	
	public static <T> ObservableBooleanValue fromPredicate(ObservableValue<T> source, Predicate<T> predicate) {
		return Bindings.createBooleanBinding(() -> predicate.test(source.getValue()), source);
	}
	
	public static ObservableBooleanValue notEmpty(ObservableValue<String> source) {
		return Bindings.createBooleanBinding(() -> {
			final String s = source.getValue();
			
			return s != null && !s.trim().isEmpty();
		}, source);
	}
	
	public static ObservableBooleanValue matches(ObservableValue<String> source, Pattern pattern) {
		return Bindings.createBooleanBinding(() -> {
			final String s = source.getValue();
			return s != null && pattern.matcher(s).matches();
		}, source);
	}
	
}
