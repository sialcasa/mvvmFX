package de.saxsys.mvvmfx.guice.interceptiontest;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class InterceptedView implements FxmlView<InterceptedViewModel> {
    public static boolean vmWasInjected = false;
    public static boolean wasInitCalled = false;

    @InjectViewModel
    private InterceptedViewModel viewModel;

    public void initialize() {
        wasInitCalled = true;
        vmWasInjected = viewModel != null;
    }

}
