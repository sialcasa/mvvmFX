package de.saxsys.mvvmfx.devtools.core.analyzer;

import java.util.Collections;
import java.util.List;

public class FxmlNodeImpl implements FxmlNode {

    final private String fxmlPath;
    final private Class<?> controller;
    final private int totalChildren;
    final private List<FxmlNode> fxIncludes;

    public FxmlNodeImpl(String fxmlPath, Class<?> controller, List<FxmlNode> fxIncludes) {

        this.fxmlPath = fxmlPath;
        this.controller = controller;
        this.fxIncludes = Collections.unmodifiableList(fxIncludes);
        this.totalChildren = fxIncludes.stream()
                .mapToInt(FxmlNode::getTotalChildren)
                .sum() + getDirectChildren();
    }

    public Class<?> getControllerClass() {
        return controller;
    }

    @Override
    public List<FxmlNode> getFxIncludes() {
        return fxIncludes;
    }

    @Override
    public int getDirectChildren() {
        return fxIncludes.size();
    }

    @Override
    public int getTotalChildren() {
        return totalChildren;

    }

    @Override
    public String getFxmlPath() {
        return fxmlPath;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FxmlNode{");
        sb.append("fxmlPath='").append(fxmlPath).append('\'');
        sb.append(", controller=").append(controller);

        sb.append(", getDirectChildren=").append(getDirectChildren());
        sb.append(", totalChildren=").append(getTotalChildren());
        sb.append('}');
        return sb.toString();
    }
}
