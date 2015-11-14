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
package de.saxsys.mvvmfx.internal.viewloader;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.internal.viewloader.example.TestViewModel;

public class ViewLoaderReflectionUtilsTest {
	
	
	@Test
	public void testCreateViewModel() {
		class TestView implements View<TestViewModel> {
		}
		
		ViewModel viewModel = ViewLoaderReflectionUtils.createViewModel(new TestView());
		
		assertThat(viewModel).isNotNull().isInstanceOf(TestViewModel.class);
	}
	
	@Test
	public void testCreateViewModelWithoutViewModelType() {
		class TestView implements View {
		}
		
		ViewModel viewModel = ViewLoaderReflectionUtils.createViewModel(new TestView());
		
		assertThat(viewModel).isNull();
	}
	
	@Test
	public void testCreateViewModelWithGeneticViewModelAsType() {
		class TestView implements View<ViewModel> {
		}
		
		ViewModel viewModel = ViewLoaderReflectionUtils.createViewModel(new TestView());
		
		assertThat(viewModel).isNull();
	}
}
