package de.saxsys.mvvmfx.cdi.scopes;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.cdi.scopes.LoadingScopeContextHolder.LoadingScopeInstance;
import de.saxsys.mvvmfx.internal.viewloader.FxmlViewLoader;
import de.saxsys.mvvmfx.internal.viewloader.View;

import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.Bean;
import java.io.Serializable;
import java.lang.annotation.Annotation;

public class LoadingScopeContext implements Context, Serializable {


    private LoadingScopeContextHolder contextHolder;


    public LoadingScopeContext() {
        System.out.println("new LoadingScopeContext()");
        contextHolder = LoadingScopeContextHolder.getInstance();

        FxmlViewLoader.addOnBeforeLoadCallback(()-> {
            System.out.println("On Before");
            contextHolder.getBeans().clear();
        });

        FxmlViewLoader.addOnAfterLoadCallback(new FxmlViewLoader.OnAfterLoadCallback() {
            @Override
            public <V extends View<? extends VM>, VM extends ViewModel> void action(V view, VM viewModel) {
                System.out.println("on After v:" + view + " vm:" + viewModel);
                contextHolder.getBeans().clear();
            }
        });
    }


    @Override
    public Class<? extends Annotation> getScope() {
        return LoadingScope.class;
    }

    @Override
    public <T> T get(Contextual<T> contextual, CreationalContext<T> creationalContext) {
        Bean bean = (Bean) contextual;

        if (contextHolder.getBeans().containsKey(bean.getBeanClass())) {
            return (T) contextHolder.getBean(bean.getBeanClass()).instance;
        } else {
            T t = (T) bean.create(creationalContext);

            LoadingScopeInstance customInstance = new LoadingScopeInstance<>();
            customInstance.bean = bean;
            customInstance.ctx = creationalContext;
            customInstance.instance = t;

            contextHolder.putBean(customInstance);
            return t;
        }
    }

    @Override
    public <T> T get(Contextual<T> contextual) {
        Bean bean = (Bean) contextual;

        if (contextHolder.getBeans().containsKey(bean.getBeanClass())) {
            return (T) contextHolder.getBean(bean.getBeanClass()).instance;
        } else {
            return null;
        }
    }

    @Override
    public boolean isActive() {
        return true;
    }

    public void destroy(@Observes KillEvent killEvent) {
        if(contextHolder.getBeans().containsKey(killEvent.getBeanType())) {
            contextHolder.destroyBean(contextHolder.getBean(killEvent.getBeanType()));
        }
    }
}
