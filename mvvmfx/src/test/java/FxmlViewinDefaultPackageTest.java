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
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.internal.viewloader.example.TestViewModel;
import de.saxsys.mvvmfx.testingutils.jfxrunner.JfxRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This test reproduces the bug no 154 (https://github.com/sialcasa/mvvmFX/issues/156).
 * 
 * A FxmlView located in the default package couldn't be loaded because a NullPointerException was thrown.
 */
@RunWith(JfxRunner.class)
public class FxmlViewinDefaultPackageTest {
	
	
	@Test
	public void test() {
		
		ViewTuple<FxmlViewInDefaultPackage, TestViewModel> viewTuple = FluentViewLoader
				.fxmlView(FxmlViewInDefaultPackage.class).load();
		
		assertThat(viewTuple).isNotNull();
		assertThat(viewTuple.getView()).isNotNull();
	}
	
}
