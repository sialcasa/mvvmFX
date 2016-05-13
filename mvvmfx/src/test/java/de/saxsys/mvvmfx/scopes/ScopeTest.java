package de.saxsys.mvvmfx.scopes;

import org.junit.Assert;
import org.junit.Test;

import de.saxsys.mvvmfx.FluentViewLoader;

public class ScopeTest {

    @Test
    public void testJavaScopedView() throws Exception {

        // FIXME JAVA TESTS

        // Muss äquivalent zum FXML-Test sein und auch den Concern des Manuell
        // erstellten ViewModels testen

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
                .load()
                .getCodeBehind();

        final ScopedViewModelA viewModelA = parentView.subviewAController.viewModel;
        final ScopedViewModelB viewModelB = parentView.subviewBController.viewModel;

        ScopedViewModelC viewModelCinA = parentView.subviewAController.subviewCController.viewModel;
        ScopedViewModelD viewModelDinA = parentView.subviewAController.subviewCController.subViewDController.viewModel;

        ScopedViewModelC viewModelCinB = parentView.subviewBController.subviewCController.viewModel;
        ScopedViewModelD viewModelDinB = parentView.subviewBController.subviewCController.subViewDController.viewModel;

        ScopedViewModelE viewModel_A_E = parentView.subviewAController.subviewEController.viewModel;
        ScopedViewModelF viewModel_A_E_F = parentView.subviewAController.subviewEController.subviewFController.viewModel;
        ScopedViewModelG viewModel_A_E_G = parentView.subviewAController.subviewEController.subviewGController.viewModel;

        ScopedViewModelE viewModel_B_E = parentView.subviewBController.subviewEController.viewModel;
        ScopedViewModelF viewModel_B_E_F = parentView.subviewBController.subviewEController.subviewFController.viewModel;
        ScopedViewModelG viewModel_B_E_G = parentView.subviewBController.subviewEController.subviewGController.viewModel;

        Assert.assertNotNull(viewModel_A_E);
        Assert.assertNotNull(viewModel_A_E_F);
        Assert.assertNotNull(viewModel_A_E_G);
        Assert.assertNotNull(viewModel_B_E);
        Assert.assertNotNull(viewModel_B_E_F);
        Assert.assertNotNull(viewModel_B_E_G);

        Assert.assertNotEquals(viewModel_A_E.testScope3, viewModel_B_E.testScope3);

        Assert.assertEquals(viewModel_A_E.testScope3, viewModel_A_E_F.testScope3);
        Assert.assertEquals(viewModel_A_E.testScope3, viewModel_A_E_G.testScope3);

        Assert.assertEquals(viewModel_B_E.testScope3, viewModel_B_E_F.testScope3);
        Assert.assertEquals(viewModel_B_E.testScope3, viewModel_B_E_G.testScope3);

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

        Assert.assertNotNull(viewModelA.injectedScope1);
        Assert.assertNotNull(viewModelB.injectedScope1);
        Assert.assertNotNull(viewModelCinA.injectedScope1);
        Assert.assertNotNull(viewModelCinB.injectedScope1);
        Assert.assertNotNull(viewModelDinA.injectedScope1);
        Assert.assertNotNull(viewModelDinA.injectedScope2);
        Assert.assertNotNull(viewModelDinB.injectedScope1);
        Assert.assertNotNull(viewModelDinB.injectedScope2);

        Assert.assertNotEquals(viewModelA.injectedScope1, viewModelB.injectedScope1);

        Assert.assertEquals(viewModelA.injectedScope1, viewModelCinA.injectedScope1);
        Assert.assertEquals(viewModelA.injectedScope1, viewModelDinA.injectedScope1);

        Assert.assertEquals(viewModelB.injectedScope1, viewModelCinB.injectedScope1);
        Assert.assertEquals(viewModelB.injectedScope1, viewModelDinB.injectedScope1);

    }

}
