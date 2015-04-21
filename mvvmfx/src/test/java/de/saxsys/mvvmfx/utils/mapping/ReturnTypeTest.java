package de.saxsys.mvvmfx.utils.mapping;

import org.junit.Before;

/**
 * This test is used to check the return values when fields are mapped.
 * See Isseu 211 <a href="https://github.com/sialcasa/mvvmFX/issues/211">https://github.com/sialcasa/mvvmFX/issues/211</a>
 */
public class ReturnTypeTest {

    private ModelWrapper<ExampleModel> wrapper;

    private ExampleModel model;

    @Before
    public void setup(){
        wrapper = new ModelWrapper<>();
        model = new ExampleModel();
    }


    // The following code is commented out because it doesn't compile at the moment
    // Its a target description on how we would like the API to be.
    
//    @Test
//    public void integerProperty(){
//        final IntegerProperty beanField = wrapper.field(ExampleModel::getInteger, ExampleModel::setInteger);
//        final IntegerProperty fxField = wrapper.field(ExampleModel::integerProperty);
//    }
//
//    @Test
//    public void doubleProperty(){
//        final DoubleProperty beanField = wrapper.field(ExampleModel::getDouble, ExampleModel::setDouble);
//        final DoubleProperty fxField = wrapper.field(ExampleModel::doubleProperty);
//    }
//
//
//    @Test
//    public void longProperty(){
//        final LongProperty beanField = wrapper.field(ExampleModel::getLong, ExampleModel::setLong);
//        final LongProperty fxField = wrapper.field(ExampleModel::longProperty);
//    }
//
//
//    @Test
//    public void floatProperty(){
//        final FloatProperty beanField = wrapper.field(ExampleModel::getFloat, ExampleModel::setFloat);
//        final FloatProperty fxField = wrapper.field(ExampleModel::floatProperty);
//    }
//
//
//    @Test
//    public void booleanProperty(){
//        final BooleanProperty beanField = wrapper.field(ExampleModel::getBoolean, ExampleModel::setBoolean);
//        final BooleanProperty fxField = wrapper.field(ExampleModel::booleanProperty);
//    }
//
//
//    @Test
//    public void stringProperty(){
//        final StringProperty beanField = wrapper.field(ExampleModel::getString, ExampleModel::setString);
//        final StringProperty fxField = wrapper.field(ExampleModel::stringProperty);
//    }
//
//    @Test
//    public void objectProperty(){
//        final ObjectProperty<Person> beanField = wrapper.field(ExampleModel::getObject, ExampleModel::setObject);
//        final ObjectProperty<Person> fxField = wrapper.field(ExampleModel::objectProperty);
//    }

}
