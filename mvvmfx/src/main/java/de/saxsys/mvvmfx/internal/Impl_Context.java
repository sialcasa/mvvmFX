package de.saxsys.mvvmfx.internal;

import java.util.HashMap;
import java.util.Map;

import de.saxsys.mvvmfx.Context;
import de.saxsys.mvvmfx.Scope;

public class Impl_Context implements Context {

    private final Map<Class<? extends Scope>, Object> scopeContext;

    public Impl_Context() {
        this(new HashMap<>());
    }

    protected Impl_Context(Map<Class<? extends Scope>, Object> scopeContext) {
        this.scopeContext = scopeContext;
    }

    public Map<Class<? extends Scope>, Object> getScopeContext() {
        return scopeContext;
    }

    /**
     * Private!
     * 
     * @return
     */
    public Impl_Context copy() {
        Map<Class<? extends Scope>, Object> scopeContextCopy = new HashMap<>();
        scopeContextCopy.putAll(scopeContext);
        Impl_Context contextCopy = new Impl_Context(scopeContextCopy);
        return contextCopy;
    }

}
