package de.saxsys.mvvmfx;

import de.saxsys.mvvmfx.internal.viewloader.DependencyInjector;

import java.util.HashMap;
import java.util.Map;

/**
 * Scope Store.
 * 
 * @author alexander.casall
 *		
 */
public class ScopeStore {
	
	// TODO Memory Leak fixen?
	private final Map<String, Scope> scopes = new HashMap<>();
	
	private static final ScopeStore INSTANCE = new ScopeStore();
	
	public static ScopeStore getInstance() {
		return INSTANCE;
	}

    public <V extends Scope> V getScope(Class<V> scopeType) {
        return getScope(scopeType, "");
    }

    public <V extends Scope> V getScope(Class<V> scopeType, String id) {
        String mapId = scopeType.getName() + id.trim();


        if(! getInstance().scopes.containsKey(mapId)) {
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
