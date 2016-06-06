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

import org.junit.Before;
import org.junit.Test;

import java.util.ListResourceBundle;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * @author manuel.mauky
 */
public class ResourceBundleManagerTest {
	
	private static final String KEY_COMMON = "key_common";
	private static final String KEY_GLOBAL_SPECIFIC = "key_global";
	private static final String KEY_OTHER_SPECIFIC = "key_other";
	
	private static final String VALUE_1_GLOBAL = "global1";
	private static final String VALUE_2_GLOBAL = "global2";
	
	private static final String VALUE_1_OTHER = "other1";
	private static final String VALUE_2_OTHER = "other2";
	
	
	private ResourceBundleManager manager;
	
	private ResourceBundle global;
	private ResourceBundle other;
	
	@Before
	public void setup(){
		manager = new ResourceBundleManager();
		
		global = new ListResourceBundle() {
			@Override
			protected Object[][] getContents() {
				return new Object[][] {
						{ KEY_COMMON, VALUE_1_GLOBAL},
						{ KEY_GLOBAL_SPECIFIC, VALUE_2_GLOBAL}
				};
			}
		};
		
		other = new ListResourceBundle() {
			@Override
			protected Object[][] getContents() {
				return new Object[][] {
						{ KEY_COMMON, VALUE_1_OTHER},
						{ KEY_OTHER_SPECIFIC, VALUE_2_OTHER}
				};
			}
		};
	}
	
	
	@Test
	public void globalAndOtherExist() {
		
		manager.setGlobalResourceBundle(global);

		final ResourceBundle merged = manager.mergeWithGlobal(other);

		// the common value is used from the other bundle.
		assertThat(merged.getString(KEY_COMMON)).isEqualTo(VALUE_1_OTHER);
		
		// merged bundle contains specific values from both bundles
		assertThat(merged.getString(KEY_GLOBAL_SPECIFIC)).isEqualTo(VALUE_2_GLOBAL);
		assertThat(merged.getString(KEY_OTHER_SPECIFIC)).isEqualTo(VALUE_2_OTHER);
	}


	@Test
	public void noGlobalDefined() {

		final ResourceBundle merged = manager.mergeWithGlobal(other);

		assertThat(merged.getString(KEY_COMMON)).isEqualTo(VALUE_1_OTHER);

		assertThat(merged.getString(KEY_OTHER_SPECIFIC)).isEqualTo(VALUE_2_OTHER);
		
		expectMissingResource(merged, KEY_GLOBAL_SPECIFIC);
	}
	
	@Test
	public void otherBundleIsNull() {
		manager.setGlobalResourceBundle(global);
		
		final ResourceBundle merged = manager.mergeWithGlobal(null);

		assertThat(merged.getString(KEY_COMMON)).isEqualTo(VALUE_1_GLOBAL);

		assertThat(merged.getString(KEY_GLOBAL_SPECIFIC)).isEqualTo(VALUE_2_GLOBAL);
		expectMissingResource(merged, KEY_OTHER_SPECIFIC);
	}
	
	@Test
	public void bothBundlesAreNull() {
		final ResourceBundle merged = manager.mergeWithGlobal(null);
		
		assertThat(merged).isNotNull();
		assertThat(merged.keySet()).isEmpty();
	}
	

	private void expectMissingResource(ResourceBundle bundle, String key) {
		try {
			bundle.getString(key);
			fail("Expected MissingResourceException");
		} catch (MissingResourceException e){
			assertThat(e).hasMessageContaining(key);
		}
	}
	
}
