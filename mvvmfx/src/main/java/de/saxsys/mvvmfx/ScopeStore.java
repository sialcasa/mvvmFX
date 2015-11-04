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
