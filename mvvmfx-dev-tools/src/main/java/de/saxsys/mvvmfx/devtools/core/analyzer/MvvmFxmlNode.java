package de.saxsys.mvvmfx.devtools.core.analyzer;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.Scope;
import de.saxsys.mvvmfx.ViewModel;

import java.util.Collections;
import java.util.List;

public class MvvmFxmlNode implements FxmlNode {

    final private String fxmlPath;
    final private Class<? extends FxmlView> codeBehind;
    final private Class<? extends ViewModel> viewModelClass;
    final private int totalChildren;

    final private List<Class<? extends Scope>> scopes;
    final private List<Class<? extends Scope>> providedScopes;

    final private List<FxmlNode> fxIncludes;

    public MvvmFxmlNode(String fxmlPath, Class<? extends FxmlView> codeBehind, List<FxmlNode> fxIncludes, Class<? extends ViewModel> viewModelClass, List<Class<? extends Scope>> scopes, List<Class<? extends Scope>> providedScopes) {
        this.fxmlPath = fxmlPath;
        this.codeBehind = codeBehind;
        this.viewModelClass = viewModelClass;
        this.fxIncludes = Collections.unmodifiableList(fxIncludes);

        this.totalChildren = fxIncludes.stream()
                .mapToInt(FxmlNode::getTotalChildren)
                .sum() + getDirectChildren();


        this.scopes = Collections.unmodifiableList(scopes);
        this.providedScopes = Collections.unmodifiableList(providedScopes);
    }

    @Override
    public Class<?> getControllerClass() {
        return codeBehind;
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

    public Class<? extends ViewModel> getViewModelClass() {
        return viewModelClass;
    }

    public List<Class<? extends Scope>> getScopes() {
        return scopes;
    }

    public List<Class<? extends Scope>> getProvidedScopes() {
        return providedScopes;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MvvmFxmlNode{");
        sb.append("fxmlPath='").append(fxmlPath).append('\'');
        sb.append(", codeBehind=").append(codeBehind);
        sb.append(", viewModelClass=").append(viewModelClass);

        sb.append(", directChildren=").append(getDirectChildren());
        sb.append(", totalChildren=").append(getTotalChildren());
        sb.append(", used scopes=").append(scopes);
        sb.append(", provided scopes").append(providedScopes);
        sb.append('}');
        return sb.toString();
    }
}
