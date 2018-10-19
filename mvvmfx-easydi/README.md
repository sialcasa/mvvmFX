# MvvmFX EasyDI

This module is an extension for the [MvvmFX](https://github.com/sialcasa/mvvmFX) framework that adds support for 
[EasyDI](https://github.com/lestard/EasyDI) as dependency injection framework. 


```xml
<dependency>
		<groupId>de.saxsys</groupId>
		<artifactId>mvvmfx-easydi</artifactId>
		<version>${mvvmfx-version}</version>
</dependency>
```


To create an application that is powered by EasyDI you have to extend `MvvmfxEasyDIApplication`:

```java
public class Starter extends MvvmfxEasyDIApplication{

    public static void main(String...args){
        launch(args);
    }

    @Override
    public void startMvvmfx(Stage stage){
       // your code to initialize the view.
    }
}
```

Other supported dependency-injection frameworks are:

* [Guice](/mvvmfx-guice)
* [CDI](/mvvmfx-cdi)
* [Spring-Boot](/mvvmfx-spring-boot)