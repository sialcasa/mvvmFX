# MvvmFX CDI

This module is an extension for the [MvvmFX](https://github.com/sialcasa/mvvmFX) framework that adds support for 
[CDI](http://cdi-spec.org/) as dependency injection framework. It uses [JBoss Weld](http://weld.cdi-spec.org/) as implementation for CDI.


```xml
<dependency>
		<groupId>de.saxsys</groupId>
		<artifactId>mvvmfx-cdi</artifactId>
		<version>${mvvmfx-version}</version>
</dependency>
```


To create an application that is powered by CDI / Weld you have to extend `MvvmfxCdiApplication`:

```java
public class Starter extends MvvmfxCdiApplication{

    public static void main(String...args){
        launch(args);
    }

    @Override
    public void startMvvmfx(Stage stage){
       // your code to initialize the view.
    }
}
```

An example application using CDI can be found at [contacts-example](/examples/contacts-example) and 
at [welcome-example](/examples/mini-examples/welcome-example).

If you prefer Guice as dependency injection framework you can use [mvvnfx-guice](/mvvmfx-guice).