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
package de.saxsys.mvvmfx.scopes;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ScopeStore;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.internal.viewloader.example.TestScope;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;

/**
 * 
 * @author alexander.casall
 */
public class ScopedViewModelA implements ViewModel {
	
	@InjectScope
	public TestScope injectedScope1;
	
	@InjectScope("coolId2")
	public TestScope injectedScope2;
	
	@InjectScope("coolId3")
	public TestScope injectedScope3;
	
	public final TestScope lazyScope1;
	public final TestScope lazyScope2;
	public final TestScope lazyScope3;
	
	
	private final ScopeStore scopeStore = new ScopeStore();
	
	private final BooleanProperty reference = new SimpleBooleanProperty();
	
	public ScopedViewModelA() {
		lazyScope1 = scopeStore.getScope(TestScope.class);
		lazyScope2 = scopeStore.getScope(TestScope.class, "coolId2");
		lazyScope3 = scopeStore.getScope(TestScope.class, "coolId3");
	}
	
	public void initialize() {
		// Create Potential Memory Leaks
		injectedScope1.someProperty
				.addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> reference.set(newValue));
		injectedScope2.someProperty
				.addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> reference.set(newValue));
		injectedScope3.someProperty
				.addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> reference.set(newValue));
		lazyScope1.someProperty
				.addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> reference.set(newValue));
		lazyScope2.someProperty
				.addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> reference.set(newValue));
		lazyScope3.someProperty
				.addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> reference.set(newValue));
	}
	
}
