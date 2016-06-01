package de.saxsys.mvvmfx.internal.viewloader;

import java.util.Collection;

import de.saxsys.mvvmfx.Context;
import de.saxsys.mvvmfx.Scope;
import de.saxsys.mvvmfx.internal.ContextImpl;

class ViewLoaderScopeUtils {

    static ContextImpl prepareContext(Context parentContext, Collection<Scope> providedScopes) {
        ContextImpl context = null;

        if (parentContext == null || !(parentContext instanceof ContextImpl)) {
            context = new ContextImpl();
        } else {
            context = (ContextImpl) parentContext;
        }

        if (providedScopes != null) {

            for (Scope scope : providedScopes) {
                context.addScopeToContext(scope);
            }
        }

        return context;
    }

}
