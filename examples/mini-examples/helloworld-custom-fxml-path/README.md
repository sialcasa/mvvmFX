# MvvmFX HelloWorld with custom FXML path

This module is an example of a simple HelloWorld app that shows
how to develop a View with a CodeBehind class that has another location then
the FXML file. Normally both files should have the same name to be found by mvvmFX
by following a set of [naming conventions](https://github.com/sialcasa/mvvmFX/wiki/Naming-Conventions).

However, it is also possible to use the `@FxmlPath` annotation
to define a custom path for the FXML file. See [the Wiki](https://github.com/sialcasa/mvvmFX/wiki/Deviating-FXML-location)
for a detailed description.