jfx-mvvm
========

An example how to implement a small project with the MVVM pattern.

**If you want to run it, you have to resolve the JavaFX dependency by your self.**

##Parts of the project##
#### de.saxsys.jfx.mvvm ####
Includes base implementations for 
the mvvm pattern. In addition a Loader is implemented to load the FXML Files and get the referenced code-behind part.

#### de.saxsys.jfx.exampleapplication ####
Contains an application which has the following flow:

- list of persons
- choose a person
- press login button
- see welcome sentence for chosen person

The packages are divided into 

- Model
- ViewModel
- View

For every *ViewModel* is a testclass in the testpackage.