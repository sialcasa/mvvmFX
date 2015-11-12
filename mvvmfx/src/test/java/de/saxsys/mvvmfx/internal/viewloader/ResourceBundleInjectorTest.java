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

import static org.assertj.core.api.Assertions.assertThat;

import java.io.StringReader;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;

import de.saxsys.mvvmfx.InjectResourceBundle;

public class ResourceBundleInjectorTest {
	
	
	private ResourceBundle resourceBundle;
	
	@Before
	public void setup() throws Exception {
		resourceBundle = new PropertyResourceBundle(new StringReader(""));
	}
	
	@Test
	public void success_resourceBundleInjected() {
		class Example {
			@InjectResourceBundle
			ResourceBundle resourceBundle;
		}
		
		Example example = new Example();
		
		ResourceBundleInjector.injectResourceBundle(example, resourceBundle);
		
		assertThat(example.resourceBundle).isEqualTo(resourceBundle);
	}
	
	@Test
	public void success_resourceBundleIsPrivate() {
		class Example {
			@InjectResourceBundle
			private ResourceBundle resourceBundle;
		}
		
		Example example = new Example();
		
		ResourceBundleInjector.injectResourceBundle(example, resourceBundle);
		
		assertThat(example.resourceBundle).isEqualTo(resourceBundle);
	}
	
	@Test(expected = IllegalStateException.class)
	public void fail_wrongType() {
		class Example {
			@InjectResourceBundle
			String resourceBundle;
		}
		
		Example example = new Example();
		
		ResourceBundleInjector.injectResourceBundle(example, resourceBundle);
	}
	
	@Test
	public void success_noAnnotation() {
		class Example {
			ResourceBundle resourceBundle;
		}
		
		Example example = new Example();
		
		ResourceBundleInjector.injectResourceBundle(example, resourceBundle);
		
		assertThat(example.resourceBundle).isNull();
	}
	
	@Test(expected = IllegalStateException.class)
	public void fail_annotationIsPresentButNoResourceBundleProvided() {
		class Example {
			@InjectResourceBundle
			ResourceBundle resourceBundle;
		}
		
		Example example = new Example();
		
		ResourceBundleInjector.injectResourceBundle(example, null);
	}
	
	/**
	 * When no annotation is present in the class, no exception should be thrown when no resourceBundle is provided.
	 */
	@Test
	public void success_noAnnotationAndNoResourceBundleProvided() {
		class Example {
			ResourceBundle resourceBundle;
		}
		
		Example example = new Example();
		
		ResourceBundleInjector.injectResourceBundle(example, null);
		
		assertThat(example.resourceBundle).isNull();
	}
	
	/**
	 * If the annotation is present, even when the type of the field is wrong, an exception has to be thrown when no
	 * resourceBundle was provided.
	 */
	@Test(expected = IllegalStateException.class)
	public void fail_wrongTypeAndNoResourceBundleProvided() {
		class Example {
			@InjectResourceBundle
			String resourceBundle;
		}
		
		Example example = new Example();
		
		ResourceBundleInjector.injectResourceBundle(example, null);
	}
	
	
	/**
	 * When the "optional" attribute is set to true, a resourceBundle is still injected when it is provided.
	 */
	@Test
	public void success_optionalAttributeIsTrue() {
		class Example {
			@InjectResourceBundle(optional = true)
			ResourceBundle resourceBundle;
		}
		
		Example example = new Example();
		
		ResourceBundleInjector.injectResourceBundle(example, resourceBundle);
		
		assertThat(example.resourceBundle).isEqualTo(resourceBundle);
	}
	
	/**
	 * When the "optional" attribute is set to true, no exception is thrown when no resourceBundle is provided.
	 */
	@Test
	public void success_optionalIsTrueAndNoResourceBundleIsProvided() {
		class Example {
			@InjectResourceBundle(optional = true)
			ResourceBundle resourceBundle;
		}
		
		Example example = new Example();
		
		ResourceBundleInjector.injectResourceBundle(example, null);
		
		assertThat(example.resourceBundle).isNull();
	}
	
	/**
	 * When the type of the field is wrong, an exception should be thrown even if the optional attribute is set to true.
	 */
	@Test(expected = IllegalStateException.class)
	public void fail_optionalIsTrueButWrongType() {
		class Example {
			@InjectResourceBundle(optional = true)
			String resourceBundle;
		}
		
		Example example = new Example();
		
		ResourceBundleInjector.injectResourceBundle(example, resourceBundle);
	}
	
	/**
	 * When multiple fields are available with the correct type and the annotation present, every field will get the
	 * resourceBundle injected (even if such a configuration isn't really useful)
	 */
	@Test
	public void success_multipleResourceBundleFields() {
		class Example {
			@InjectResourceBundle(optional = true)
			ResourceBundle resourceBundle;
			
			@InjectResourceBundle
			ResourceBundle resourceBundleToo;
		}
		
		Example example = new Example();
		
		ResourceBundleInjector.injectResourceBundle(example, resourceBundle);
		
		assertThat(example.resourceBundle).isEqualTo(resourceBundle);
		assertThat(example.resourceBundleToo).isEqualTo(resourceBundle);
	}
	
	/**
	 * When multiple fields are available, an exception is thrown when at least one of the fields has a wrong type. This
	 * is true even if one of the fields is correct.
	 */
	@Test(expected = IllegalStateException.class)
	public void fail_multipleResourceBundleFieldsOneHasWrongType() {
		class Example {
			@InjectResourceBundle
			String resourceBundle;
			
			@InjectResourceBundle
			ResourceBundle resourceBundleToo;
		}
		
		Example example = new Example();
		
		ResourceBundleInjector.injectResourceBundle(example, resourceBundle);
	}
	
}
