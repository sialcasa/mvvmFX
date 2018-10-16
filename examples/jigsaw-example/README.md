# MvvmFX JigSaw Example

This is a simple application to demonstrate that the mvvmFX framework can be used in applications based on a Java version 
above Java 8 and using the jigsaw module system.

### The use-case

The application consists on a main view, which contains two separate sub views to calculate the areas of a circle 
respectively a rectangle.
The circle view is included via `fx:include`, the rectangle view is loaded via FluentViewLoader when initializing the 
main view.

### Important difference to the other examples in the MvvmFX project

The application is not involved in the build process of the main project because the main project is compiled with Java 8
and the jigsaw-example is compiled with Java 11. Because of differences on the project structures, otherwise building 
the main project would fail. 

### Configuration to run with Java 11

* set JAVA_HOME to an installed jdk-11
* set IDE's runconfiguration to JRE 11
* set properties tag in pom.xml to
 ```xml
    <properties>
       <maven.compiler.source>11</maven.compiler.source>
       <maven.compiler.target>11</maven.compiler.target>

       <java.version>11</java.version>
       <mvvmfx.version>1.8.0-SNAPSHOT</mvvmfx.version>
   </properties>
 ```
 * set the release tag in the maven compiler plugin to 
 ```xml  
    <build>
         <plugins>
             <plugin>
                 <groupId>org.apache.maven.plugins</groupId>
                 <artifactId>maven-compiler-plugin</artifactId>
                 <version>3.8.0</version>
                 <configuration>
                     <release>11</release>
                 </configuration>
             </plugin>
         </plugins>
     </build>
 ```
  
### Interesting parts

#### JavaFX

Since Java 11, JavaFX is no longer part of the JDK. Therefore the packages for javafx-controls, javafx-graphics, 
javafx-base and javafx-fxml from org.openjfx must be added as maven `<dependency>`to the main pom.xml.

#### Modules

Since Java 9, java applications need a `module-info.java` file in the top-level-package of every module.
This module descriptor contains informations on which other packages are required or must be exported respectively opened for 
other packages.

```Java
module de.saxsys.mvvmfx.examples.jigsaw.app{

    requires de.saxsys.mvvmfx.examples.jigsaw.circle;
    requires de.saxsys.mvvmfx.examples.jigsaw.rectangle;
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires mvvmfx;

    exports de.saxsys.mvvmfx.examples.jigsaw.app to javafx.graphics;

    opens de.saxsys.mvvmfx.examples.jigsaw.app to mvvmfx, javafx.fxml;
}
```
##### Instructions

 - `requires <name of the module>` - indicates from which other modules the current module depends
 - `exports <name of the package>` - indicates which packages from the current module are exported outside the module
 - `opens <name of the package>` - provides a package for deep reflection at runtime

##### Further Informations 

[More information about the the Java Platform Module System](https://dzone.com/articles/java-9-modules-introduction-part-1)

 