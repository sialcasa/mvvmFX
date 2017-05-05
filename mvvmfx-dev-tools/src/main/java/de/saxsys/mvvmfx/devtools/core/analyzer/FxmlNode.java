package de.saxsys.mvvmfx.devtools.core.analyzer;

import java.util.List;

public interface FxmlNode {


    Class<?> getControllerClass();

    List<FxmlNode> getFxIncludes();

    int getDirectChildren();

    int getTotalChildren();

    String getFxmlPath();


}
