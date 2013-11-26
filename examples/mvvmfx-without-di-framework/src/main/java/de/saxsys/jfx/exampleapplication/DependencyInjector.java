package de.saxsys.jfx.exampleapplication;

import de.saxsys.jfx.exampleapplication.model.Repository;
import javafx.util.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * In this exampleapplication there is no Dependency Injection Framework. Instead
 * we make dependency injection "by hand". One way of doing this is to
 * create the whole object graph in the code.
 */
public class DependencyInjector implements Callback<Class<?>, Object> {

    private Map<Class<?>,Object> instances = new HashMap<>();

    public DependencyInjector(){

        Repository repository = new Repository();
        instances.put(Repository.class, repository);



    }

    @Override
    public Object call(Class<?> type) {
        if(instances.containsKey(type)){
            return instances.get(type);
        }else if{
            throw new IllegalStateException("Can't inject an Instance of type " + type + ". There is no instance created in the DependencyInjector");
        }
    }
}
