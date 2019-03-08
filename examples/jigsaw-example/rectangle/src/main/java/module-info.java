
module de.saxsys.mvvmfx.examples.jigsaw.rectangle {

    requires javafx.base;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires de.saxsys.mvvmfx;

    exports de.saxsys.mvvmfx.examples.jigsaw.rectangle;

    opens de.saxsys.mvvmfx.examples.jigsaw.rectangle to de.saxsys.mvvmfx, javafx.fxml;
}