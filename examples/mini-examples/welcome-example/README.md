mvvmfx welcome example
======================

This example is slightly more complex example then the other mini-examples. 
It shows a login dialog where users get a welcome message after they where logged in.

The example shows many interesting parts of the mvvmFX framework:

## Dependency-Injection

The code in the application uses the `@Inject` annotation and there are App classes for both **CDI/Weld** and **Guice**. 
It demonstrates that it's possible to use the same code base for both dependency injection frameworks while only using
different starter classes for each framework. 

## Commands

The example uses our [Commands](https://github.com/sialcasa/mvvmFX/wiki/Commands) feature for handling of actions.

