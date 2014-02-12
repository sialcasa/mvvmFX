package de.saxsys.jfx.mvvm.internal.utils;

import de.saxsys.jfx.mvvm.base.view.View;
import de.saxsys.jfx.mvvm.base.viewmodel.ViewModel;
import org.junit.Test;

import java.net.URL;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class GenericTypeResolverTest {
    private static class ExampleViewModel implements ViewModel {
    }

    private static class ExampleView extends View<ExampleViewModel> {
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
        }
    }

    /**
     * This test case verifies the main usage of the {@link de.saxsys.jfx.mvvm.internal.utils.GenericTypeResolver} in
     * the framework: We like to get the view model type for a given View class.
     */
    @Test
    public void testGetViewModelType() {
        List<Class<?>> typeArguments =
                GenericTypeResolver.getTypeArguments(View.class, ExampleView.class);

        assertThat(typeArguments).isNotNull().isNotEmpty().hasSize(1);

        assertThat(typeArguments.get(0)).isEqualTo(ExampleViewModel.class);
    }


    /**
     * This example class is used to check the behavior for other use cases besides determining the view model type.
     */
    private static class MyMap extends HashMap<Integer, String> {
    }

    /**
     * In this test we check a class with two generic types. As example we use a subclass of {@link java.util.HashMap}
     * but in the method call we are using the interface {@link java.util.Map} as base class.
     * <p/>
     * At the moment the {@link de.saxsys.jfx.mvvm.internal.utils.GenericTypeResolver} can't handle interface types as
     * base class parameter. For this reason an {@link java.lang.IllegalArgumentException} is thrown.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testHashMapWithBaseClassParameterMap() {
        List<Class<?>> typeArguments = GenericTypeResolver.getTypeArguments(Map.class, MyMap.class);

        assertThat(typeArguments).isNotNull().isNotEmpty().hasSize(2);

        assertThat(typeArguments.get(0)).isEqualTo(Integer.class);
        assertThat(typeArguments.get(1)).isEqualTo(String.class);

    }

    /**
     * This test is the same as {@link #testHashMapWithBaseClassParameterMap} except that this time not the {@link
     * java.util.Map} interface is used as base type but {@link java.util.HashMap}.
     */
    @Test
    public void testHashMapWithBaseClassParameterHashMap() {

        List<Class<?>> typeArguments = GenericTypeResolver.getTypeArguments(HashMap.class, MyMap.class);

        assertThat(typeArguments).isNotNull().isNotEmpty().hasSize(2);

        assertThat(typeArguments.get(0)).isEqualTo(Integer.class);
        assertThat(typeArguments.get(1)).isEqualTo(String.class);
    }

    /**
     * This test is the same as {@link #testHashMapWithBaseClassParameterMap} except that this time not the {@link
     * java.util.Map} interface is used as base type but {@link java.util.AbstractMap}.
     * <p/>
     * Unlike the Map interface, AbstractMap is working as base class parameter as it is an abstract class and not an
     * interface.
     */
    @Test
    public void testHashMapWithBaseClassParameterAbstractMap() {
        List<Class<?>> typeArguments = GenericTypeResolver.getTypeArguments(AbstractMap.class, MyMap.class);

        assertThat(typeArguments).isNotNull().isNotEmpty().hasSize(2);

        assertThat(typeArguments.get(0)).isEqualTo(Integer.class);
        assertThat(typeArguments.get(1)).isEqualTo(String.class);
    }

    // This classes are used to verify the behavior for classes with a deeper inheritance tree.

    private static class A<T, P> {
    }

    private static class B<E> extends A<Integer, Double> {
    }

    private static class C extends B<String> {
    }


    /**
     * In this test case we use A as base class. A defines two generic types that are declared as Integer and Double in B.
     */
    @Test
    public void testBaseClassA() {
        List<Class<?>> typeArguments = GenericTypeResolver.getTypeArguments(A.class, C.class);

        assertThat(typeArguments).isNotNull().hasSize(2);


        assertThat(typeArguments.get(0)).isEqualTo(Integer.class);
        assertThat(typeArguments.get(1)).isEqualTo(Double.class);

    }

    /**
     * In this test we use B as base class and because B itself only declares one generic type, we only get
     * this type as result. The resulting type is String because in C the generic type of B is defined as String.
     */
    @Test
    public void testBaseClassB() {
        List<Class<?>> typeArguments = GenericTypeResolver.getTypeArguments(B.class, C.class);

        assertThat(typeArguments).isNotNull().hasSize(1);

        assertThat(typeArguments.get(0)).isEqualTo(String.class);
    }

    /**
     * In this test we use C as base class. C itself doesn't define any generic types so the result list is empty.
     */
    @Test
    public void testBaseClassC() {
        List<Class<?>> typeArguments = GenericTypeResolver.getTypeArguments(C.class, C.class);

        assertThat(typeArguments).isNotNull().isEmpty();
    }


    /**
     * In this test case we use B as base class and as child class.
     * The result is a little bit special: The list of type arguments has one entry because B has one generic type argument.
     * But the entry is <code>null</code> because we don't know the type of this argument.
     */
    @Test
    public void testGenericTypeIsAvailableButUnknown() {
        List<Class<?>> typeArguments = GenericTypeResolver.getTypeArguments(B.class, B.class);

        assertThat(typeArguments).isNotNull().hasSize(1);

        assertThat(typeArguments.get(0)).isNull();
    }


}
