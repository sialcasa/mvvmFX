package de.saxsys.mvvmfx.internal.viewloader;

import sun.util.ResourceBundleEnumeration;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListResourceBundle;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author manuel.mauky
 */
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
	 * @return the merged resourceBundle.
	 */
	public ResourceBundle mergeWithGlobal(ResourceBundle resourceBundle) {
		if (globalResourceBundle == null) {
			if (resourceBundle == null) {
				return null;
			} else {
				return resourceBundle;
			}
		} else {
			if (resourceBundle == null) {
				return globalResourceBundle;
			} else {
				return merge(resourceBundle, globalResourceBundle);
			}
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
	
}
