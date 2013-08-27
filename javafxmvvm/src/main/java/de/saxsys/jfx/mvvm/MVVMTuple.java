package de.saxsys.jfx.mvvm;

import javafx.scene.Parent;

/**
 * Tuple for carriing view / code-behind pair.
 */
public class MVVMTuple {

    private final MVVMView<?> codeBehind;
    private final Parent view;

    /**
     * @param codeBehind to set
     * @param view to set
     */
    public MVVMTuple(final MVVMView<?> codeBehind, final Parent view) {
        this.codeBehind = codeBehind;
        this.view = view;
    }

    /**
     * @return the viewmodel
     */
    public MVVMView<?> getCodeBehind() {
        return codeBehind;
    }

    /**
     * @return the view
     */
    public Parent getView() {
        return view;
    }
}
