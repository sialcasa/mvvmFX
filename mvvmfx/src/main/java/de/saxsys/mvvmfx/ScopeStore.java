package de.saxsys.mvvmfx;

import java.util.HashMap;
import java.util.Map;

import de.saxsys.mvvmfx.internal.viewloader.DependencyInjector;
import de.saxsys.mvvmfx.internal.viewloader.example.TestScope;

/**
 * Scope Store.
 * 
 * @author alexander.casall
 *
 * @param <V>
 */
public class ScopeStore<V extends Scope> {
	
	// TODO Memory Leak fixen?
	private final Map<String, Scope> scopes = new HashMap<>();
	
	private static final ScopeStore INSTANCE = new ScopeStore();
	
	public static ScopeStore<TestScope> getInstance() {
		return INSTANCE;
	}
	
	public V getScope(Class<? extends Scope> scopeType, String id) {
		String mapId = scopeType.getName() + id;
		
		V scope = (V) scopes.get(mapId);
		
		if (scope == null) {
			scope = createScopeInstance(scopeType);
			scopes.put(mapId, scope);
		}
		return scope;
	}
	
	private V createScopeInstance(Class<? extends Scope> scopeType) {
		return (V) DependencyInjector.getInstance().getInstanceOf(scopeType);
	}
}
