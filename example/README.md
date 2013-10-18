jfx-mvvm
========

An example how to implement a small project with the MVVM pattern. 

This example uses my open source library [mvvmFX](https://github.com/sialcasa/mvvmFX) which is currently in development. It provides following features:

- Guice Support
- Custom FXML Loader
- MVVM Base Implementations
- Notification Mechanism

**If you want to run it, you have to set the propper JAVA_HOME that the JavaFX dependency can be resolved.**

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