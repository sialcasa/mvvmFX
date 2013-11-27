package de.saxsys.jfx.mvvm.di;

import javafx.util.Callback;

/**
 * This class handles the dependency injection for the mvvmFX framework.
 *
 * The main reason for this class is to make it possible for the user
 * to use her own dependency injection mechanism/framework.
 * The user can define how instances should be retrieved by setting an callback that
 * returns an instance for a given class type (see {@link DependencyInjector#setCustomInjector}.
 *
 */
public class DependencyInjector {

    private Callback<Class<?>, Object> customInjector;

    private static DependencyInjector singleton = new DependencyInjector();

    DependencyInjector(){
    }

    public static DependencyInjector getInstance(){
        return singleton;
    }

    public <T> T getInstanceOf(Class<? extends T> type){
        if(isCustomInjectorDefined()){
            return (T) customInjector.call(type);
        }else{
            try {
                // use default creation
                return type.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("Can't create instance of type " + type.getName() ,e);
            }
        }
    }

    /**
     * See {@link #setCustomInjector(javafx.util.Callback)} for more details.
     * @return the defined custom injector if any
     */
    public Callback<Class<?>, Object> getCustomInjector(){
        return customInjector;
    }

    /**
     * Define a custom injector that is used to retrieve instances. This can be used
     * as a bridge to you dependency injection framework.
     *
     * The callback has to return an instance for the given class type. This is
     * same way as it is done in the {@link javafx.fxml.FXMLLoader#setControllerFactory(javafx.util.Callback)}
     * method.
     *
     * @param callback the callback that returns instances of a specific class type.
     */
    public void setCustomInjector(Callback<Class<?>, Object> callback){
        this.customInjector = callback;
    }

    /**
     * @return true when a custom injector is defined, otherwise false.
     */
    public boolean isCustomInjectorDefined(){
        return customInjector != null;
    }

}
