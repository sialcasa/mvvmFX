# Architecture Checks with AspectJ

AspectJ can be used to check MVVM-Architecture errors.

These errors involve mostly on method calls or class-dependencies to unallowed layers within the MVVM pattern, like the View using Model objects or access to the _javafx.scene_ package in the ViewModel or the Model.

Since every project has different model packages we have built a plugin to ease aspect configurations for the mvvmFX framework.
These aspects will not change any logic on the project and are only used for architecture checks for the MVVM pattern with mvvmFX.

## Setting up Maven for mvvmFX architecture checks

   - add dependency to UI-module or project

```xml
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjrt</artifactId>
    <version>1.8.9</version>
</dependency>
```
   - in the same file add the mvvmFX aspect creator plugin and the AspectJ compiler plugin
   
```xml
<plugins>
    <plugin>
        <groupId>de.saxsys</groupId>
        <artifactId>mvvmfx-aspect-creator-plugin</artifactId>
        <version>1.6.0-SNAPSHOT</version>
        <configuration>
            <modelPackages>
                <package>com.example.dao</package>
                <package>com.example.business</package>
                <package>com.example.mypackage</package>
            </modelPackages>
            <modelClasses>
                <class>com.example.myotherpackage.MyClass</class>
            </modelClasses>
        </configuration>
    </plugin>
    <plugin>
        <groupId>org.codehaus.mojo</groupId>
        <artifactId>aspectj-maven-plugin</artifactId>
        <version>1.8</version>
        <configuration>
            <source>1.8</source>
            <target>1.8</target>
            <complianceLevel>1.8</complianceLevel>
            <verbose>true</verbose>
        </configuration>
        <executions>
            <execution>
                <goals>
                    <goal>compile</goal>
                </goals>
            </execution>
        </executions>
    </plugin>
</plugins>
```

## create the aspects and compile the project
To create the aspects we have to configure it first. To do this we add the packages or the fully qualified name of the classes in the configuration of the plugin.
The View and ViewModel classes don't have to be configured.

After this we execute the plugin to create the aspects with "_mvn mvvmfx-aspect-creator:createaspects_".

Compile the project or UI-module with "_mvn clean install_" or "_mvn aspectj:compile_".

If there are some errors found the compiler will then throw error messages with the class, line of code and reason why there is an error.
If no errors were found, then good job!

## For Multi-Module Maven Projects
If the project has Multi-Modules, where the Layers(View, ViewModel and Model) are in different Modules then the above method won't fully work.
This is because half of the Aspects won't be applied on the modules because the modules are compiled separately. 
 
To solve this issue we have to 

First create a new module for our aspects on the parent module. 
It is important to note the "_\<artifactId>_". For the purpose of this example the module name will be "_aspects_"
In this module we add two plugins to our pom:
```xml
<build>
    <plugins>
        <plugin>
            <groupId>de.saxsys</groupId>
            <artifactId>mvvmfx-aspect-creator-plugin</artifactId>
            <configuration>
                <modelPackages>
                    <package>com.example.dao</package>
                    <package>com.example.business</package>
                    <package>com.example.mypackage</package>
                </modelPackages>
                <modelClasses>
                    <class>com.example.mypackage.SomeClass</class>
                </modelClasses>
            </configuration>
        </plugin>
        <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>aspectj-maven-plugin</artifactId>
            <version>1.9</version>
            <configuration>
                <source>1.8</source>
                <target>1.8</target>
                <complianceLevel>1.8</complianceLevel>
                <verbose>true</verbose>
                <Xlint>ignore</Xlint>
            </configuration>
            <executions>
                <execution>
                    <goals>
                        <goal>compile</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

We then create the aspects using the plugin with "_mvn mvvmfx-aspect-creator:createaspects_". This will create a folder aspect on src/main of the module.
We also have to compile this aspects with "_mvn aspectj:compile_" or "_mvn clean install_".
__NOTE: use this command while inside of the module if not the aspects will be created on the folder where the command was executed and you will have to move them to the aspects module__

After this we just have to add an aspectj plugin on every module that will apply our created aspects to each module.
The aspect library tag has to be directed to the module where the aspects where created

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>aspectj-maven-plugin</artifactId>
            <version>1.9</version>
            <configuration>
                <source>1.8</source>
                <target>1.8</target>
                <complianceLevel>1.8</complianceLevel>
                <verbose>true</verbose>
                <aspectLibraries>
                    <aspectLibrary>
                        <groupId>com.example</groupId>
                        <artifactId>aspects</artifactId>
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
    </plugins>
</build>
```