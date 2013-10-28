package de.saxsys.mvvm.base.view;

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
import java.net.URL;
import java.util.ResourceBundle;

import org.junit.Test;

import de.saxsys.jfx.mvvm.base.view.View;
import de.saxsys.jfx.mvvm.base.view.ViewWithoutViewModel;
import de.saxsys.jfx.mvvm.base.viewmodel.ViewModel;

/**
 * Tests for the View implementation.
 * 
 * @author sialcasa
 */
public class ViewTest {

	/**
	 * Test whether an exception gets thrown when you use the view without a
	 * specified View Model.
	 */
	@SuppressWarnings("rawtypes")
	@Test(expected = IllegalStateException.class)
	public void createViewWithoutViewModelIncorrect() {
		new View() {
			@Override
			public void initialize(URL arg0, ResourceBundle arg1) {
				// TODO Auto-generated method stub
			}
		};
	}

	/**
	 * Creation of a @ViewWithoutViewModel should not cause an exception.
	 */
	@Test
	public void createViewWithoutViewModelCorrect() throws Exception {
		new ViewWithoutViewModel() {

			@Override
			public void initialize(URL arg0, ResourceBundle arg1) {

			}
		};
	}

	/**
	 * Creation of a @View should not cause an exception.
	 */
	@Test
	public void createView() throws Exception {
		new View<TestViewModel>() {
			@Override
			public void initialize(URL arg0, ResourceBundle arg1) {
				// TODO Auto-generated method stub
			}
		};
	}

	private class TestViewModel implements ViewModel {

	}

}
