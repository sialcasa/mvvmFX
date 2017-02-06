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

import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener;

import org.junit.Before;
import org.junit.Test;

/**
 * This test case reproduces the bug <a href="https://github.com/sialcasa/mvvmFX/issues/264">#264</a>
 * 
 * @author manuel.mauky
 */
public class HighestMessageBugTest {
	
	
	private StringProperty value;
	private Validator validator;
	private ValidationStatus validationStatus;
	
	
	@Before
	public void setUp() throws Exception {
		value = new SimpleStringProperty("");
		validator = new FunctionBasedValidator<>(value, v -> v != null, ValidationMessage.error("error"));
		
		
		validationStatus = validator.getValidationStatus();
	}
	
	@Test
	public void testValidProperty() throws Exception {
		assertThat(validationStatus.getHighestMessage().isPresent()).isFalse();
		
		CompletableFuture<Void> future = new CompletableFuture<>();
		
		validationStatus.validProperty().addListener((observable, oldValue, newValue) -> {
			assertThat(validationStatus.getHighestMessage().isPresent()).isTrue();
			
			future.complete(null);
		});
		
		
		value.set(null);
		
		future.get(1l, TimeUnit.SECONDS);
	}
	
	@Test
	public void testMessagesList() throws Exception {
		assertThat(validationStatus.getHighestMessage().isPresent()).isFalse();
		
		CompletableFuture<Void> future = new CompletableFuture<>();
		
		validationStatus.getMessages().addListener((ListChangeListener<ValidationMessage>) c -> {
			assertThat(validationStatus.getHighestMessage().isPresent()).isTrue();
			
			future.complete(null);
		});
		
		
		value.set(null);
		
		future.get(1l, TimeUnit.SECONDS);
	}
	
}
