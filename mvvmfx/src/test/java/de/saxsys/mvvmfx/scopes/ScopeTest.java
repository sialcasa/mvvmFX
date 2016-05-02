package de.saxsys.mvvmfx.scopes;

import org.junit.Assert;
import org.junit.Test;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.internal.viewloader.example.TestScope1;

public class ScopeTest {

    @Test
    public void testJavaScopedView() throws Exception {

        // FIXME JAVA TESTS

        // final ScopedViewModelA viewModelA =
        // FluentViewLoader.javaView(ScopedJavaViewA.class).load().getViewModel();
        // final ScopedViewModelB viewModelB =
        // FluentViewLoader.javaView(ScopedJavaViewB.class).load().getViewModel();
        //
        // verifyScopes(viewModelA, viewModelB);
    }

    @Test
    public void testFxmlScopedView() throws Exception {

        final ScopesFxmlParentView parentView = FluentViewLoader.fxmlView(ScopesFxmlParentView.class)
                .providedScopes(new TestScope1())
                .load()
                .getCodeBehind();

        final ScopedViewModelA viewModelA = parentView.subviewAController.viewModel;
        final ScopedViewModelB viewModelB = parentView.subviewBController.viewModel;

        ScopedViewModelC viewModelCinA = parentView.subviewAController.subviewCController.viewModel;
        ScopedViewModelD viewModelDinA = parentView.subviewAController.subviewCController.subViewDController.viewModel;

        ScopedViewModelC viewModelCinB = parentView.subviewBController.subviewCController.viewModel;
        ScopedViewModelD viewModelDinB = parentView.subviewBController.subviewCController.subViewDController.viewModel;

        verifyScopes(viewModelA, viewModelB, viewModelCinA, viewModelCinB, viewModelDinA, viewModelDinB);
    }

    @Test(expected = Exception.class)
    public void testErrorWhenNoScopeProviderFound() {

        final ScopesFxmlParentView parentView = FluentViewLoader.fxmlView(ScopesFxmlParentView.class)
                .load()
                .getCodeBehind();

        parentView.subviewAController.subviewCController.loadWrongScopedView();
    }

    private void verifyScopes(ScopedViewModelA viewModelA, ScopedViewModelB viewModelB, ScopedViewModelC viewModelCinA,
            ScopedViewModelC viewModelCinB, ScopedViewModelD viewModelDinA, ScopedViewModelD viewModelDinB) {

        Assert.assertNotNull(viewModelA);
        Assert.assertNotNull(viewModelB);
        Assert.assertNotNull(viewModelCinA);
        Assert.assertNotNull(viewModelCinB);
        Assert.assertNotNull(viewModelDinA);
        Assert.assertNotNull(viewModelDinB);

        Assert.assertEquals(viewModelA.injectedScope1, viewModelCinA.injectedScope1);
        Assert.assertEquals(viewModelA.injectedScope1, viewModelDinA.injectedScope1);

        Assert.assertEquals(viewModelB.injectedScope1, viewModelCinB.injectedScope1);
        Assert.assertEquals(viewModelB.injectedScope1, viewModelDinB.injectedScope1);

        Assert.assertNotEquals(viewModelA.injectedScope1, viewModelCinB);
        Assert.assertNotEquals(viewModelB.injectedScope1, viewModelCinA);

        Assert.assertNotNull(viewModelDinB.injectedScope2);

    }

}
