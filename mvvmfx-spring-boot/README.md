# MvvmFX Spring-Boot

This module is an extension for the [MvvmFX](https://github.com/sialcasa/mvvmFX) framework that adds support for 
[Spring-Boot](http://cdi-spec.org/) as dependency injection framework. 

```xml
<dependency>
		<groupId>de.saxsys</groupId>
		<artifactId>mvvmfx-spring-boot</artifactId>
		<version>${mvvmfx-version}</version>
</dependency>
```


To create an application that is powered by Spring-Boot you have to extend `MvvmfxSpringApplication`:

```java
public class Starter extends MvvmfxSpringApplication{

    public static void main(String...args){
        launch(args);
    }

    @Override
    public void startMvvmfx(Stage stage){
       // your code to initialize the view.
    }
}
```

An example application using Spring-Boot can be found at [helloworld-spring-boot](/examples/mini-examples/helloworld-spring-boot).

* [Guice](/mvvmfx-guice)
* [CDI](/mvvmfx-cdi)
* [EasyDI](/mvvmfx-easydi)