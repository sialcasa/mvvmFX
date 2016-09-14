# Architecture Checks with AspectJ

AspectJ can be used to check MVVM-Architecture errors.

These errors involve mostly on dependencies to unallowed layers within the MVVM pattern, like the View using Model objects or access to the _javafx.scene.*_ package in the ViewModel or the Model.

## setting up Maven for AspectJ Architecture checks

   - add dependency to UI-module or Project

```xml
<dependency>
    <groupId>de.saxsys</groupId>
	<artifactId>mvvmfx-aspectj</artifactId>
</dependency>
```

   - also add this AspectJ compiler plugin in UI-module or Project
```xml
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>aspectj-maven-plugin</artifactId>
    <version>1.8</version>
    <configuration>
    	<source>1.8</source>
    	<target>1.8</target>
    	<complianceLevel>1.8</complianceLevel>
    	<verbose>true</verbose>
    	<aspectLibraries>
    	    <aspectLibrary>
    	        <groupId>de.saxsys</groupId>
    	        <artifactId>mvvmfx-aspectj</artifactId>
    	    </aspectLibrary>
    	</aspectLibraries>
    </configuration>
    <executions>
    	<execution>
    	    <goals>
    	        <goal>compile</goal>
    	    </goals>
    	</execution>
    </executions>
</plugin>
```

## compile project
Compile the project or UI-module with "mvn install" or "mvn aspectj:compile".

If there are some errors found the compiler will then throw error messasges with the class, line of code and reason why there is an error.
If no errors were found, then good job!

These aspects will not change any logic on the project and are only used for architecture checks for the MVVM pattern with mvvmFX.