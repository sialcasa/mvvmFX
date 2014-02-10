package de.saxsys.jfx.mvvm.internal.utils;

import de.saxsys.jfx.mvvm.base.view.View;
import de.saxsys.jfx.mvvm.base.viewmodel.ViewModel;
import org.junit.Test;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static org.assertj.core.api.Assertions.assertThat;

public class GenericTypeResolverTest {
    private static class ExampleViewModel implements ViewModel{
    }

    private static class ExampleView extends View<ExampleViewModel>{
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
        }
    }

    @Test
    public void testExampleView(){
        List<Class<?>> typeArguments =
            GenericTypeResolver.getTypeArguments(View.class,ExampleView.class);

        assertThat(typeArguments).isNotNull().isNotEmpty().hasSize(1);

        assertThat(typeArguments.get(0)).isEqualTo(ExampleViewModel.class);
    }

}
