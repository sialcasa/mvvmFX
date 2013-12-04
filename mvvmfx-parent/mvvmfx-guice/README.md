# MvvmFX Guice

This Module is an extension for the [MvvmFX](https://github.com/sialcasa/mvvmFX) framework that adds support for Guice as a dependency injection framework.

It is base on [fx-guice](https://github.com/cathive/fx-guice).

To create an application that is powered by Guice you have to extend `MvvmfxGuiceApplication`:

    public class Starter extends MvvmfxGuiceApplication {

        public static void main(final String[] args) {
            launch(args);
        }

        @Inject
        private ViewLoader viewLoader;

        @Override
        public void startMvvmfx(final Stage stage) throws Exception {
            // your code to initialize the view
        }

        @Override
        public void initGuiceModules(List<Module> modules) throws Exception {
            // add your guice modules here
        }
    }


A simple example for this is available at [mvvmfx-guice-starter](https://github.com/sialcasa/mvvmFX/tree/develop/examples/mvvmfx-guice-starter)

If you prefer CDI as a dependency injection framework you can use [mvvnfx-cdi](https://github.com/sialcasa/mvvmFX/tree/develop/mvvmfx-parent/mvvmfx-cdi)