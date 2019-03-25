# MvvmFX CDI

This module is an extension for the [MvvmFX](https://github.com/sialcasa/mvvmFX) framework that adds support for 
[CDI](http://cdi-spec.org/) as dependency injection framework. 


```xml
<dependency>
		<groupId>de.saxsys</groupId>
		<artifactId>mvvmfx-cdi</artifactId>
		<version>${mvvmfx-version}</version>
</dependency>
```


To create an application that is powered by CDI you have to extend `MvvmfxCdiApplication`:

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

Starting with version 1.7.0 this module doesn't depend on JBoss Weld anymore. 
Instead it's now possible to use any CDI 2.0 compatible implementation. 
However, this also means that you have to add an CDI library to your project on your own.

An example application using CDI and JBoss Weld can be found at [contacts-example](/examples/contacts-example) and 
at [welcome-example](/examples/mini-examples/welcome-example).

Other supported dependency-injection frameworks are:

* [Guice](/mvvmfx-guice)
* [Spring-Boot](/mvvmfx-spring-boot)
* [EasyDI](/mvvmfx-easydi)