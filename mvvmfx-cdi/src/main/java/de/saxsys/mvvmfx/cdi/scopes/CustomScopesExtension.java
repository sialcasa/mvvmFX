package de.saxsys.mvvmfx.cdi.scopes;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import java.io.Serializable;

public class CustomScopesExtension implements Extension, Serializable {

    public void addScope(@Observes final BeforeBeanDiscovery event) {
        event.addScope(LoadingScope.class, true, false);
    }

    public void registerContext(@Observes final AfterBeanDiscovery event) {
        event.addContext(new LoadingScopeContext());
    }

}
