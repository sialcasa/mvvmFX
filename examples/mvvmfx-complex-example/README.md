mvvmfx complex example
======================

This module contains the business logic and user interface implementation of the example.
Not part of this maven module is the startup code to run the application itself. This can be done with either
[mvvmfx-cdi-starter](/examples/mvvmfx-cdi-starter) or
[mvvmfx-guice-starter](/examples/mvvmfx-guice-starter) depending on the dependency injection framework you want to use.

This separation is possible because both guice and cdi can use the `@Inject` annotation (which is used in this module)
for dependency injection configuration.

To see this example in action you need to:

1. first build this module with `mvn clean install`
2. use the `Starter` class of either [mvvmfx-cdi-example](/examples/mvvmfx-cdi-starter/src/main/java/de/saxsys/jfx/Starter.java)
 or [mvvmfx-guice-example](/examples/mvvmfx-guice-starter/src/main/java/de/saxsys/jfx/Starter.java).
3. If are using java 7 you need to have the `JAVA_HOME` property defined correctly because the JavaFX runtime is resolved this directory.

##Parts of the project##

#### de.saxsys.jfx.exampleapplication ####
Contains an application which has the following flow:

- list of persons
- choose a person
- press login button
- see welcome sentence for chosen person

The packages are divided into 

- Model
	- __Person.java__ Bean for a Person
	- __Repository.java__ Service Class for __Person.java__
- ViewModel
	- __PersonLoginViewModel.java__ creates the information which should be displayed by __PersonLoginView.java__ / __PersonLoginView.fxml__
	- __PersonWelcomeViewModel.java__ creates the information which should be displayed by __PersonWelcomeView.java__ / __PersonWelcomeView.fxml__
- View 
	- __PersonLoginView.java__ and __PersonLoginView.fxml__
	- __PersonWelcomeView.java__ and __PersonWelcomeView.fxml__

For every __ViewModel__ is a testclass in the testpackage to give an impression how to develop testdriven.