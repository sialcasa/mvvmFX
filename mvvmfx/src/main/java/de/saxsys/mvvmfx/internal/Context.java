package de.saxsys.mvvmfx.internal;

import java.util.HashMap;
import java.util.Map;

import de.saxsys.mvvmfx.Scope;

public class Context {

    private final Map<Class<? extends Scope>, Object> scopeContext;

    /**
     * Gets private in future
     */
    @Deprecated
    public Context() {
        this(new HashMap<>());
    }

    /**
     * Gets private in future
     */
    @Deprecated
    public Context(Map<Class<? extends Scope>, Object> scopeContext) {
        this.scopeContext = scopeContext;
    }

    /**
     * @return the scopeBottich
     */
    public Map<Class<? extends Scope>, Object> getScopeContext() {
        return scopeContext;
    }

    /**
     * Private!
     * 
     * @return
     */
    @Deprecated
    public Context copy() {
        Map<Class<? extends Scope>, Object> scopeContextCopy = new HashMap<>();
        scopeContextCopy.putAll(scopeContext);
        Context contextCopy = new Context(scopeContextCopy);
        return contextCopy;
    }

}
