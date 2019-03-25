
module de.saxsys.mvvmfx.examples.jigsaw.app{

    requires de.saxsys.mvvmfx.examples.jigsaw.circle;
    requires de.saxsys.mvvmfx.examples.jigsaw.rectangle;
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires de.saxsys.mvvmfx;

    exports de.saxsys.mvvmfx.examples.jigsaw.app to javafx.graphics;

    opens de.saxsys.mvvmfx.examples.jigsaw.app to de.saxsys.mvvmfx, javafx.fxml;
}