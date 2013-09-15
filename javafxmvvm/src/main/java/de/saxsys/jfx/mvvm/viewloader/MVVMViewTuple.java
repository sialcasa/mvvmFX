package de.saxsys.jfx.mvvm.viewloader;

/*
 * Copyright 2013 Alexander Casall - Saxonia Systems AG
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
import javafx.scene.Parent;
import de.saxsys.jfx.mvvm.base.MVVMView;

/**
 * Tuple for carriing view / code-behind pair.
 */
public class MVVMViewTuple {

	private final MVVMView<?> codeBehind;
	private final Parent view;

	/**
	 * @param codeBehind
	 *            to set
	 * @param view
	 *            to set
	 */
	public MVVMViewTuple(final MVVMView<?> codeBehind, final Parent view) {
		this.codeBehind = codeBehind;
		this.view = view;
	}

	/**
	 * @return the viewmodel
	 */
	public MVVMView<?> getCodeBehind() {
		return codeBehind;
	}

	/**
	 * @return the view
	 */
	public Parent getView() {
		return view;
	}
}
