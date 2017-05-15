package de.saxsys.mvvmfx.devtools.core;

import de.saxsys.mvvmfx.devtools.core.analyzer.Analyzer;
import de.saxsys.mvvmfx.devtools.core.analyzer.FxmlNode;
import de.saxsys.mvvmfx.devtools.core.gui.DevToolGui;
import de.saxsys.mvvmfx.internal.viewloader.View;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DevTool {


    private List<FxmlNode> fxmlRootNodes = new ArrayList<>();

    public DevTool() {
    }


    public void start() {
        DevToolGui devToolGui = new DevToolGui(fxmlRootNodes);
        devToolGui.start(new Stage());
    }

    public void analyze(Class<? extends View<?>> viewClass) {
        FxmlNode rootNode = Analyzer.parseFXML(viewClass);
        fxmlRootNodes.add(rootNode);
    }

    public void analyze(Class<? extends View<?>>... viewClasses) {
        Stream.of(viewClasses)
                .forEach(this::analyze);

    }

}
