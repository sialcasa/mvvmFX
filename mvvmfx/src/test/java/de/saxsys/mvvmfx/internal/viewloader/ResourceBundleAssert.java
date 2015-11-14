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

import org.assertj.core.api.AbstractAssert;

import java.util.ResourceBundle;

/**
 * @author manuel.mauky
 */
public class ResourceBundleAssert extends AbstractAssert<ResourceBundleAssert, ResourceBundle> {

	private ResourceBundleAssert(ResourceBundle actual) {
		super(actual, ResourceBundleAssert.class);
	}
	
	public static ResourceBundleAssert assertThat(ResourceBundle resourceBundle){
		return new ResourceBundleAssert(resourceBundle);
	}
	
	public ResourceBundleAssert hasSameContent(ResourceBundle other){
		isNotNull();
		
		if(other == null) {
			failWithMessage("The given ResourceBundle may not be null");
			return this;
		}
		
		if(actual.equals(other)) {
			return this;
		}

		for (String key : actual.keySet()) {
			final Object actualValue = actual.getObject(key);
			if(!other.containsKey(key)){
				failWithMessage("Expected given ResourceBundle to contain the key <%s>", key);
			}
			
			final Object otherValue = other.getObject(key);
			
			if(!actualValue.equals(otherValue)){
				failWithMessage("For the key <%s> the given ResourceBundle has a value of <%s> but a value of <%s> like in the actual ResourceBundle was expected", key, otherValue, actualValue);
			}
		}
		
		return this;
	}
	
}
