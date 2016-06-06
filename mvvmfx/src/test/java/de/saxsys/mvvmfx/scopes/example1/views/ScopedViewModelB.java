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
package de.saxsys.mvvmfx.scopes.example1.views;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ScopeProvider;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.scopes.example1.Example1Scope1;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * 
 * @author alexander.casall
 * 
 */
@ScopeProvider(scopes = {Example1Scope1.class})
public class ScopedViewModelB implements ViewModel {

    @InjectScope
    public Example1Scope1 injectedScope1;

    private final BooleanProperty reference = new SimpleBooleanProperty();

    public ScopedViewModelB() {
        System.out.println("new " + this.getClass().getSimpleName() + "()");
    }

    public void initialize() {
        System.out.println(this.getClass().getSimpleName() + ".initialize()");

        // Create Potential Memory Leaks
        injectedScope1.someProperty.addListener((observable, oldValue, newValue) -> reference.set(newValue));
    }

}
