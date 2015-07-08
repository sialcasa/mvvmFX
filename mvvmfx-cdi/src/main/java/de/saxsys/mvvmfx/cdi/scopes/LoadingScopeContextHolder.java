package de.saxsys.mvvmfx.cdi.scopes;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LoadingScopeContextHolder implements Serializable {

    private static LoadingScopeContextHolder SINGLETON;

    private Map<Class, LoadingScopeInstance> beans;

    private LoadingScopeContextHolder() {
        beans = Collections.synchronizedMap(new HashMap<>());
    }

    public synchronized static LoadingScopeContextHolder getInstance() {
        if(SINGLETON == null) {
            SINGLETON = new LoadingScopeContextHolder();
        }
        return SINGLETON;
    }

    public Map<Class, LoadingScopeInstance> getBeans() {
        return beans;
    }

    public LoadingScopeInstance getBean(Class type) {
        return getBeans().get(type);
    }

    public void putBean(LoadingScopeInstance customInstance) {
        getBeans().put(customInstance.bean.getBeanClass(), customInstance);
    }

    public void destroyBean(LoadingScopeInstance instance) {
        getBeans().remove(instance.bean.getBeanClass());
        instance.bean.destroy(instance.instance, instance.ctx);
    }

    public static class LoadingScopeInstance<T> {
        Bean<T> bean;
        CreationalContext<T> ctx;
        T instance;
    }
}
