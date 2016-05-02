package de.saxsys.mvvmfx.internal.viewloader;

import java.util.List;

import de.saxsys.mvvmfx.Context;
import de.saxsys.mvvmfx.Scope;
import de.saxsys.mvvmfx.internal.Impl_Context;

public class ViewLoaderScopeUtils {

    public static Impl_Context prepareContext(Context parentContext, List<Scope> providedScopes) {
        Impl_Context context = null;

        if (parentContext == null) {
            context = new Impl_Context();
        } else {
            if (parentContext instanceof Impl_Context) {
                context = (Impl_Context) parentContext;
            }
        }

        final Impl_Context finalContext = context;

        if (providedScopes != null) {
            providedScopes.forEach(scope -> {
                finalContext.getScopeContext().put(scope.getClass(), scope);
            });
        }
        return context;
    }

}
