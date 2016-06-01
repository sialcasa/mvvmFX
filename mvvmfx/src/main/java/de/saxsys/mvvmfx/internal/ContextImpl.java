package de.saxsys.mvvmfx.internal;

import java.util.HashMap;
import java.util.Map;

import de.saxsys.mvvmfx.Context;
import de.saxsys.mvvmfx.Scope;

public class ContextImpl implements Context {

    private final Map<Class<? extends Scope>, Object> scopeContext;

    public ContextImpl() {
        this(new HashMap<>());
    }

    private ContextImpl(Map<Class<? extends Scope>, Object> scopeContext) {
        this.scopeContext = scopeContext;
    }

    public void addScopeToContext(Scope scope) {
        scopeContext.put(scope.getClass(), scope);
    }

    public <T extends Scope> Object getScope(Class<T> scopeType) {
        return scopeContext.get(scopeType);
    }
}
