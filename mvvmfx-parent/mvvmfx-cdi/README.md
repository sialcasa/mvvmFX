# MvvmFX CDI

This Module is an extension for the [MvvmFX](https://github.com/sialcasa/mvvmFX) framework that adds support for CDI as a dependency injection framework.

To create an application that is powered by CDI / Weld you have to extend `MvvmfxCdiApplication`:

    public class Starter extends MvvmfxCdiApplication{

    	public static void main(String...args){
            launch(args);
    	}

        @Inject
        private ViewLoader viewLoader;

        @Override
        public void start(Stage stage){
           // your code to initialize the view.
        }
    }

A simple example for this is available at [mvvmfx-cdi-starter](https://github.com/sialcasa/mvvmFX/tree/develop/examples/mvvmfx-cdi-starter)

If you prefer Guice as a dependency injection framework you can use [mvvnfx-guice](https://github.com/sialcasa/mvvmFX/tree/develop/mvvmfx-parent/mvvmfx-guice)