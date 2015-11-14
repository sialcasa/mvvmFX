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
package de.saxsys.mvvmfx;

import java.util.Map;

import de.saxsys.mvvmfx.internal.WeakValueHashMap;
import de.saxsys.mvvmfx.internal.viewloader.DependencyInjector;

/**
 * Scope Store.
 * 
 * @author alexander.casall
 * 		
 */
public class ScopeStore {
	
	private final Map<String, Scope> scopes = new WeakValueHashMap();
	
	private static final ScopeStore INSTANCE = new ScopeStore();
	
	public static ScopeStore getInstance() {
		return INSTANCE;
	}
	
	public <V extends Scope> V getScope(Class<V> scopeType) {
		return getScope(scopeType, "");
	}
	
	public <V extends Scope> V getScope(Class<V> scopeType, String id) {
		String mapId = scopeType.getName() + id.trim();
		
		
		if (!getInstance().scopes.containsKey(mapId)) {
			V scope = getInstance().createScopeInstance(scopeType);
			getInstance().scopes.put(mapId, scope);
		}
		
		final V v = (V) getInstance().scopes.get(mapId);
		
		return v;
	}
	
	private <V extends Scope> V createScopeInstance(Class<V> scopeType) {
		return DependencyInjector.getInstance().getInstanceOf(scopeType);
	}
}
