import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.internal.viewloader.example.TestViewModel;

/**
 * The purpose of this class is to reproduce Bug no. 154 (https://github.com/sialcasa/mvvmFX/issues/156).
 * 
 * The problem is that FxmlView's that are located in the default package can't be loaded properly. Instead a 
 * NullPointerException was thrown. 
 */
public class FxmlViewInDefaultPackage implements FxmlView<TestViewModel> {
}
