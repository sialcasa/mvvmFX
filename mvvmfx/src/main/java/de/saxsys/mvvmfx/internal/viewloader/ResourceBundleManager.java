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

import eu.lestard.doc.Internal;
import sun.util.ResourceBundleEnumeration;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author manuel.mauky
 */
@Internal
public class ResourceBundleManager {
	
	private static final ResourceBundleManager SINGLETON = new ResourceBundleManager();
	private ResourceBundle globalResourceBundle;
	
	ResourceBundleManager() {
	}
	
	public static ResourceBundleManager getInstance() {
		return SINGLETON;
	}
	
	public void setGlobalResourceBundle(ResourceBundle resourceBundle) {
		this.globalResourceBundle = resourceBundle;
	}
	
	public ResourceBundle getGlobalResourceBundle() {
		return globalResourceBundle;
	}
	
	
	/**
	 * Merges the provided ResourceBundle with the global one (if any).
	 * 
	 * The global resourceBundle has a lower priority then the provided one. If there is the same key defined in the
	 * global and the provided resourceBundle, the value from the provided resourceBundle will be used.
	 * 
	 * @param resourceBundle
	 *            a resourceBundle that will be merged.
	 * @return the merged resourceBundle or null, if there is no global resource bundle and not given resource bundle.
	 */
	public ResourceBundle mergeWithGlobal(ResourceBundle resourceBundle) {
		if (globalResourceBundle == null) {
			if (resourceBundle == null) {
				return createEmptyBundle();
			} else {
				return new ResourceBundleWrapper(resourceBundle);
			}
		} else {
			if (resourceBundle == null) {
				return new ResourceBundleWrapper(globalResourceBundle);
			} else {
				return merge(resourceBundle, globalResourceBundle);
			}
		}
	}	
	
	private ResourceBundle merge(ResourceBundle highPriority, ResourceBundle lowPriority) {
		return new MergedResourceBundle(highPriority, lowPriority);
	}
	
	private static class MergedResourceBundle extends ResourceBundle {
		private ResourceBundle highPriority;
		private ResourceBundle lowPriority;
		
		public MergedResourceBundle(ResourceBundle highPriority, ResourceBundle lowPriority) {
			Objects.nonNull(highPriority);
			Objects.nonNull(lowPriority);
			this.highPriority = highPriority;
			this.lowPriority = lowPriority;
		}
		
		@Override
		protected Object handleGetObject(String key) {
			if (highPriority.containsKey(key)) {
				return highPriority.getObject(key);
			}
			
			if (lowPriority.containsKey(key)) {
				return lowPriority.getObject(key);
			}
			
			return null;
		}
		
		@Override
		public Enumeration<String> getKeys() {
			return new MergedEnumeration(highPriority.keySet(), lowPriority.keySet());
		}
	}
	
	private static class MergedEnumeration implements Enumeration<String> {
		
		final Iterator<String> iterator;
		
		public MergedEnumeration(Set<String> highPriority, Set<String> lowPriority) {
			Set<String> allElements = new HashSet<>();
			allElements.addAll(highPriority);
			allElements.addAll(lowPriority);
			
			iterator = allElements.iterator();
		}
		
		@Override
		public boolean hasMoreElements() {
			return iterator.hasNext();
		}
		
		@Override
		public String nextElement() {
			return iterator.next();
		}
	}

	/**
	 * This wrapper is needed due to a bug in FXMLLoader (https://javafx-jira.kenai.com/browse/RT-33764).
	 * 
	 * With this class we make sure that there is a classLoader defined for this class.
	 */
	private static class ResourceBundleWrapper extends ResourceBundle {
		private final ResourceBundle bundle;

		ResourceBundleWrapper(ResourceBundle bundle) {
			this.bundle = bundle;
		}

		@Override
		protected Object handleGetObject(String key) {
			return bundle.getObject(key);
		}

		@Override
		public Enumeration<String> getKeys() {
			return bundle.getKeys();
		}

		@Override
		public boolean containsKey(String key) {
			return bundle.containsKey(key);
		}

		@Override
		public Locale getLocale() {
			return bundle.getLocale();
		}

		@Override
		public Set<String> keySet() {
			return bundle.keySet();
		}
	}
	
	private ResourceBundle createEmptyBundle() {
		return new ListResourceBundle() {
			@Override
			protected Object[][] getContents() {
				return new Object[0][];
			}
		};
	}
}
