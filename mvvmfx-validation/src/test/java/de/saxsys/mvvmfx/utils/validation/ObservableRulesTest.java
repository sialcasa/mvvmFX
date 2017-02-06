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

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableBooleanValue;
import org.junit.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author manuel.mauky
 */
public class ObservableRulesTest {
	
	
	@Test
	public void testNotNullOrEmpty() {
		StringProperty value = new SimpleStringProperty();
		
		final ObservableBooleanValue rule = ObservableRules.notEmpty(value);
		
		assertThat(rule.get()).isFalse();
		
		value.set("test");
		assertThat(rule.get()).isTrue();
		
		value.set("");
		assertThat(rule.get()).isFalse();
		
		value.set("   dds ");
		assertThat(rule.get()).isTrue();
		
		value.set("    ");
		assertThat(rule.get()).isFalse();
		
		value.set("1");
		assertThat(rule.get()).isTrue();
		
		value.set(null);
		assertThat(rule.get()).isFalse();
	}
	
	
	@Test
	public void testMatches() {
		StringProperty value = new SimpleStringProperty();
		
		final ObservableBooleanValue rule = ObservableRules.matches(value, Pattern.compile("[0-9]"));
		
		assertThat(rule.get()).isFalse();
		
		value.set("1");
		
		assertThat(rule.get()).isTrue();
		
		value.set("55");
		assertThat(rule.get()).isFalse();
		
		value.set("5");
		assertThat(rule.get()).isTrue();
		
		value.set(null);
		assertThat(rule.get()).isFalse();
		
	}
}
