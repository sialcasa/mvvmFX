# MvvmFX fx:root example

This example shows how you can create a custom component with the [fx:root](http://docs.oracle.com/javafx/2/fxml_get_started/custom_control.htm) element that uses mvvmFX.

The example control consists of a Label, a TextField and a Button. When the button is pressed the text entered in the textField will be set as new label text. The button is disabled when the textField is empty. Of cause this isn't realy useful in practice but it's a good example of how to encapsulate the view logic of the control into the viewModel.