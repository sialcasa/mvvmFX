package de.saxsys.mvvmfx.utils.mapping;

import eu.lestard.doc.Beta;
import javafx.beans.property.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;


/**
 * A helper class that can be used to simplify the mapping between the ViewModel and the Model for use cases where a
 * typical CRUD functionality is needed and there is no big difference between the structure of the model class and the
 * view.
 * <p>
 * A typical workflow would be:
 * <ul>
 * <li>load an existing model instance from the backend and copy all values from the model to the properties of the
 * ViewModel</li>
 * <li>the user changes the values of the viewModel properties (via the UI). The state of the underlying model instance
 * may not be changed at this point in time!</li>
 * <li>when the user clicks an Apply button (and validation is successful) copy all values from the ViewModel fields
 * into the model instance.</li>
 * </ul>
 * <p>
 * Additional requirements:
 * <ul>
 * <li>click "reset" so that the old values are back in the UI. In this case all UI fields should get the current values
 * of the model</li>
 * <li>if we are creating a new model instance and the user clicks "reset" we want all UI fields to be reset to a
 * meaningful default value</li>
 * </ul>
 * 
 * <p>
 * 
 * These requirements are quite common but there is a lot of code needed to copy between the model and the viewModel.
 * Additionally we have a tight coupling because every time the structure of the model changes (for example a field is
 * removed) we have several places in the viewModel that need to be adjusted.
 * <p>
 * This component can be used to simplify use cases like the described one and minimize the coupling between the model
 * and the viewModel. See the following code example. First without and afterwards with the {@link ModelWrapper}.
 * <p>
 * The model class:
 * <p>
 * 
 * <pre>
 * public class Person {
 * 	private String name;
 * 	private String familyName;
 * 	private int age;
 * 	
 * 	public String getName() {
 * 		return name;
 * 	}
 * 	
 * 	public void setName(String name) {
 * 		this.name = name;
 * 	}
 * 	
 * 	public String getFamilyName() {
 * 		return familyName;
 * 	}
 * 	
 * 	public void setFamilyName(String familyName) {
 * 		this.familyName = familyName;
 * 	}
 * 	
 * 	public int getAge() {
 * 		return age;
 * 	}
 * 	
 * 	public void setAge(int age) {
 * 		this.age = age;
 * 	}
 * }
 * </pre>
 * 
 * Without {@link ModelWrapper}:
 * <p>
 * 
 * <pre>
 * public class PersonViewModel implements ViewModel {
 * 	
 * 	private StringProperty name = new SimpleStringProperty();
 * 	private StringProperty familyName = new SimpleStringProperty();
 * 	private IntegerProperty age = new SimpleIntegerProperty();
 * 	
 * 	private Person person;
 * 	
 * 	public void init(Person person) {
 * 		this.person = person;
 * 		reloadFromModel();
 * 	}
 * 	
 * 	public void reset() {
 * 		this.name.setValue(&quot;&quot;);
 * 		this.familyName.setValue(&quot;&quot;);
 * 		this.age.setValue(0);
 * 	}
 * 	
 * 	public void reloadFromModel() {
 * 		this.name.setValue(person.getName());
 * 		this.familyName.setValue(person.getFamilyName());
 * 		this.age.setValue(person.getAge());
 * 	}
 * 	
 * 	public void save() {
 * 		if (someValidation() &amp;&amp; person != null) {
 * 			person.setName(name.getValue());
 * 			person.setFamilyName(familyName.getValue());
 * 			person.setAge(age.getValue());
 * 		}
 * 	}
 * 	
 * 	public StringProperty nameProperty() {
 * 		return name;
 * 	}
 * 	
 * 	public StringProperty familyNameProperty() {
 * 		return familyName;
 * 	}
 * 	
 * 	public IntegerProperty ageProperty() {
 * 		return age;
 * 	}
 * }
 * </pre>
 * 
 * With {@link ModelWrapper}:
 * <p>
 * 
 * <pre>
 *         public class PersonViewModel implements ViewModel {
 *              private ModelWrapper{@code<Person>} wrapper = new ModelWrapper{@code<>}();
 * 
 *             public void init(Person person) {
 *                  wrapper.set(person);
 *                  wrapper.reload();
 *             }
 * 
 *             public void reset() {
 *                 wrapper.reset();
 *             }
 * 
 *             public void reloadFromModel(){
 *                 wrapper.reload();
 *             }
 * 
 *             public void save() {
 *                 if (someValidation()) {
 *                     wrapper.commit();
 *                 }
 *             }
 * 
 *             public Property{@code<String>} nameProperty(){
 *                 return wrapper.field("name", Person::getName, Person::setName, "");
 *             }
 * 
 *             public Property{@code<String>} familyNameProperty(){
 *                 return wrapper.field("familyName", Person::getFamilyName, Person::setFamilyName, "");
 *             }
 * 
 *             public Property{@code<Integer>} ageProperty() {
 *                 return wrapper.field("age", Person::getAge, Person::setAge, 0);
 *             }
 *         }
 * </pre>
 * 
 * In the first example without the {@link ModelWrapper} we have several lines of code that are specific for each field
 * of the model. If we would add a new field to the model (for example "email") then we would have to update several
 * pieces of code in the ViewModel.
 * <p>
 * On the other hand in the example with the {@link ModelWrapper} there is only the definition of the Property accessors
 * in the bottom of the class that is specific to the fields of the Model. For each field we have only one place in the
 * ViewModel that would need an update when the structure of the model changes.
 * 
 * 
 * @param <M>
 *            the type of the model class.
 */
@Beta
public class ModelWrapper<M> {
	
	/**
	 * This interface defines the operations that are possible for each field of a wrapped class.
	 * 
	 * @param <T>
	 * @param <M>
	 */
	private interface PropertyField<T, M, R extends Property<T>> {
		void commit(M wrappedObject);
		
		void reload(M wrappedObject);
		
		void resetToDefault();
		
		R getProperty();
	}
	
	/**
	 * An implementation of {@link PropertyField} that is used when the fields of the model class are JavaFX Properties
	 * too.
	 * 
	 * @param <T>
	 */
	private class FxPropertyField<T, R extends Property<T>> implements PropertyField<T, M, R> {
		
		private final T defaultValue;
		private final Function<M, Property<T>> accessor;
		private final R targetProperty;
		
		public FxPropertyField(Function<M, Property<T>> accessor, Supplier<Property<T>> propertySupplier) {
			this(accessor, null, propertySupplier);
		}

		@SuppressWarnings("unchecked")
		public FxPropertyField(Function<M, Property<T>> accessor, T defaultValue, Supplier<Property<T>> propertySupplier) {
			this.accessor = accessor;
			this.defaultValue = defaultValue;
			this.targetProperty = (R)propertySupplier.get();
		}
		
		@Override
		public void commit(M wrappedObject) {
			accessor.apply(wrappedObject).setValue(targetProperty.getValue());
		}
		
		@Override
		public void reload(M wrappedObject) {
			targetProperty.setValue(accessor.apply(wrappedObject).getValue());
		}
		
		@Override
		public void resetToDefault() {
			targetProperty.setValue(defaultValue);
		}
		
		@Override
		public R getProperty() {
			return targetProperty;
		}
	}
	
	/**
	 * An implementation of {@link PropertyField} that is used when the fields of the model class are <b>not</b> JavaFX
	 * Properties but are following the old Java-Beans standard, i.e. there are getter and setter method for each field.
	 *
	 * @param <T>
	 */
	private class BeanPropertyField<T, R extends Property<T>> implements PropertyField<T, M, R> {
		
		private final R targetProperty;
		private final T defaultValue;
		
		private final Function<M, T> getter;
		private final BiConsumer<M, T> setter;
		
		public BeanPropertyField(Function<M, T> getter,
				BiConsumer<M, T> setter, Supplier<R> propertySupplier) {
			this(getter, setter, null, propertySupplier);
		}
		
		public BeanPropertyField(Function<M, T> getter,
				BiConsumer<M, T> setter, T defaultValue, Supplier<R> propertySupplier) {
			this.defaultValue = defaultValue;
			this.getter = getter;
			this.setter = setter;
			this.targetProperty = propertySupplier.get();
		}
		
		@Override
		public void commit(M wrappedObject) {
			setter.accept(wrappedObject, targetProperty.getValue());
		}
		
		@Override
		public void reload(M wrappedObject) {
			targetProperty.setValue(getter.apply(wrappedObject));
		}
		
		@Override
		public void resetToDefault() {
			targetProperty.setValue(defaultValue);
		}
		
		@Override
		public R getProperty() {
			return targetProperty;
		}
	}
	
	
	private Set<PropertyField<?, M, ?>> fields = new HashSet<>();
	private Map<String, PropertyField<?, M, ?>> identifiedFields = new HashMap<>();
	
	private M model;
	
	
	/**
	 * Create a new instance of {@link ModelWrapper} that wraps the given instance of the Model class.
	 * 
	 * @param model
	 *            the element of the model that will be wrapped.
	 */
	public ModelWrapper(M model) {
		set(model);
		reload();
	}
	
	/**
	 * Create a new instance of {@link ModelWrapper} that is empty at the moment. You have to define the model element
	 * that should be wrapped afterwards with the {@link #set(Object)} method.
	 */
	public ModelWrapper() {
	}
	
	/**
	 * Define the model element that will be wrapped by this {@link ModelWrapper} instance.
	 * 
	 * @param model
	 *            the element of the model that will be wrapped.
	 */
	public void set(M model) {
		this.model = model;
	}
	
	/**
	 * @return the wrapped model element if one was defined, otherwise <code>null</code>.
	 */
	public M get() {
		return model;
	}
	
	/**
	 * Resets all defined fields to their default values. If no default value was defined <code>null</code> will be used
	 * instead.
	 * <p>
	 * <b>Note:</b> This method has no effects on the wrapped model element but will only change the values of the
	 * defined property fields.
	 */
	public void reset() {
		fields.forEach(field -> field.resetToDefault());
	}
	
	/**
	 * Take the current value of each property field and write it into the wrapped model element.
	 * <p>
	 * If no model element is defined then nothing will happen.
	 * <p>
	 * <b>Note:</b> This method has no effects on the values of the defined property fields but will only change the
	 * state of the wrapped model element.
	 */
	public void commit() {
		if (model != null) {
			fields.forEach(field -> field.commit(model));
		}
	}
	
	/**
	 * Take the current values from the wrapped model element and put them in the corresponding property fields.
	 * <p>
	 * If no model element is defined then nothing will happen.
	 * <p>
	 * <b>Note:</b> This method has no effects on the wrapped model element but will only change the values of the
	 * defined property fields.
	 */
	public void reload() {
		if (model != null) {
			fields.forEach(field -> field.reload(model));
		}
	}

	public BooleanProperty field(BooleanGetter<M> getter, BooleanSetter<M> setter) {
		return add(new BeanPropertyField<>(getter, setter, SimpleBooleanProperty::new));
	}

	public BooleanProperty field(BooleanPropertyAccessor<M> accessor) {
		return add(new FxPropertyField<>(accessor, SimpleBooleanProperty::new));
	}

	public BooleanProperty field(String identifier, BooleanGetter<M> getter, BooleanSetter<M> setter) {
		return addIdentified(identifier, new BeanPropertyField<>(getter, setter, SimpleBooleanProperty::new));
	}

	public BooleanProperty field(String identifier, BooleanPropertyAccessor<M> accessor) {
		return addIdentified(identifier, new FxPropertyField<>(accessor, SimpleBooleanProperty::new));
	}


	public DoubleProperty field(DoubleGetter<M> getter, DoubleSetter<M> setter) {
		return add(new BeanPropertyField<>(getter::apply, (m, number) -> setter.accept(m, number.doubleValue()), SimpleDoubleProperty::new));
	}

	public DoubleProperty field(DoublePropertyAccessor<M> accessor) {
		return add(new FxPropertyField<>(accessor::apply, SimpleDoubleProperty::new));
	}

	public DoubleProperty field(String identifier, DoubleGetter<M> getter, DoubleSetter<M> setter) {
		return addIdentified(identifier, new BeanPropertyField<>(getter::apply, (m, number) -> setter.accept(m, number.doubleValue()), SimpleDoubleProperty::new));
	}

	public DoubleProperty field(String identifier, DoublePropertyAccessor<M> accessor) {
		return addIdentified(identifier, new FxPropertyField<>(accessor::apply, SimpleDoubleProperty::new));
	}






	public FloatProperty field(FloatGetter<M> getter, FloatSetter<M> setter) {
		return add(new BeanPropertyField<>(getter::apply, (m, number) -> setter.accept(m, number.floatValue()), SimpleFloatProperty::new));
	}

	public FloatProperty field(FloatPropertyAccessor<M> accessor) {
		return add(new FxPropertyField<>(accessor::apply, SimpleFloatProperty::new));
	}

	public FloatProperty field(String identifier, FloatGetter<M> getter, FloatSetter<M> setter) {
		return addIdentified(identifier, new BeanPropertyField<>(getter::apply, (m, number) -> setter.accept(m, number.floatValue()), SimpleFloatProperty::new));
	}

	public FloatProperty field(String identifier, FloatPropertyAccessor<M> accessor) {
		return addIdentified(identifier, new FxPropertyField<>(accessor::apply, SimpleFloatProperty::new));
	}




	public IntegerProperty field(IntGetter<M> getter, IntSetter<M> setter) {
		return add(new BeanPropertyField<>(getter::apply, (m, number) -> setter.accept(m, number.intValue()), SimpleIntegerProperty::new));
	}

	public IntegerProperty field(IntPropertyAccessor<M> accessor) {
		return add(new FxPropertyField<>(accessor::apply, SimpleIntegerProperty::new));
	}

	public IntegerProperty field(String identifier, IntGetter<M> getter, IntSetter<M> setter) {
		return addIdentified(identifier, new BeanPropertyField<>(getter::apply, (m, number) -> setter.accept(m, number.intValue()), SimpleIntegerProperty::new));
	}

	public IntegerProperty field(String identifier, IntPropertyAccessor<M> accessor) {
		return addIdentified(identifier, new FxPropertyField<>(accessor::apply, SimpleIntegerProperty::new));
	}




	public LongProperty field(LongGetter<M> getter, LongSetter<M> setter) {
		return add(new BeanPropertyField<>(getter::apply, (m, number) -> setter.accept(m, number.longValue()), SimpleLongProperty::new));
	}

	public LongProperty field(LongPropertyAccessor<M> accessor) {
		return add(new FxPropertyField<>(accessor::apply, SimpleLongProperty::new));
	}

	public LongProperty field(String identifier, LongGetter<M> getter, LongSetter<M> setter) {
		return addIdentified(identifier, new BeanPropertyField<>(getter::apply, (m, number) -> setter.accept(m, number.longValue()), SimpleLongProperty::new));
	}

	public LongProperty field(String identifier, LongPropertyAccessor<M> accessor) {
		return addIdentified(identifier, new FxPropertyField<>(accessor::apply, SimpleLongProperty::new));
	}





	public <T> ObjectProperty<T> field(ObjectGetter<M, T> getter, ObjectSetter<M, T> setter) {
		return add(new BeanPropertyField<>(getter, setter, SimpleObjectProperty::new));
	}

	public <T> ObjectProperty<T> field(ObjectPropertyAccessor<M, T> accessor) {
		return add(new FxPropertyField<>(accessor::apply, SimpleObjectProperty::new));
	}

	public <T> ObjectProperty<T> field(String identifier, ObjectGetter<M, T> getter, ObjectSetter<M, T> setter) {
		return addIdentified(identifier, new BeanPropertyField<>(getter, setter, SimpleObjectProperty::new));
	}

	public <T> ObjectProperty<T> field(String identifier, ObjectPropertyAccessor<M, T> accessor) {
		return addIdentified(identifier, new FxPropertyField<>(accessor::apply, SimpleObjectProperty::new));
	}





	public StringProperty field(StringGetter<M> getter, StringSetter<M> setter) {
		return add(new BeanPropertyField<>(getter, setter, SimpleStringProperty::new));
	}

	public StringProperty field(StringPropertyAccessor<M> accessor) {
		return add(new FxPropertyField<>(accessor::apply, SimpleStringProperty::new));
	}

	public StringProperty field(String identifier, StringGetter<M> getter, StringSetter<M> setter) {
		return addIdentified(identifier, new BeanPropertyField<>(getter, setter, SimpleStringProperty::new));
	}

	public StringProperty field(String identifier, StringPropertyAccessor<M> accessor) {
		return addIdentified(identifier, new FxPropertyField<>(accessor::apply, SimpleStringProperty::new));
	}


//
//	/**
//	 * Add a new field to this instance of the wrapper. This method is used for model elements that are following the
//	 * enhanced JavaFX-Beans-standard i.e. the model fields are available as JavaFX Properties.
//	 * <p>
//	 * Example:
//	 * <p>
//	 *
//	 * <pre>
//	 *     ModelWrapper{@code<Person>} personWrapper = new ModelWrapper{@code<>}();
//	 *
//	 *     Property{@code<String>} wrappedNameProperty = personWrapper.field(person -> person.nameProperty());
//	 *
//	 *     // or with a method reference
//	 *     Property{@code<String>} wrappedNameProperty = personWrapper.field(Person::nameProperty);
//	 *
//	 * </pre>
//	 *
//	 *
//	 * @param accessor
//	 *            a function that returns the property for a given model instance. Typically you will use a method
//	 *            reference to the javafx-property accessor method.
//	 *
//	 * @param <T>
//	 *            the type of the field.
//	 *
//	 * @return The wrapped property instance.
//	 */
//	public <T> Property<T> field(Function<M, WritableValue<T>> accessor) {
//		return add(new FxPropertyField<>(accessor));
//	}
//
//	/**
//	 * Add a new field to this instance of the wrapper. This method is used for model elements that are following the
//	 * enhanced JavaFX-Beans-standard i.e. the model fields are available as JavaFX Properties.
//	 * <p>
//	 * Additionally you can define a default value that is used as when {@link #reset()} is invoked.
//	 *
//	 * <p>
//	 *
//	 * Example:
//	 * <p>
//	 *
//	 * <pre>
//	 *     ModelWrapper{@code<Person>} personWrapper = new ModelWrapper{@code<>}();
//	 *
//	 *     Property{@code<String>} wrappedNameProperty = personWrapper.field(person -> person.nameProperty(), "empty");
//	 *
//	 *     // or with a method reference
//	 *     Property{@code<String>} wrappedNameProperty = personWrapper.field(Person::nameProperty, "empty");
//	 *
//	 * </pre>
//	 *
//	 *
//	 * @param accessor
//	 *            a function that returns the property for a given model instance. Typically you will use a method
//	 *            reference to the javafx-property accessor method.
//	 * @param defaultValue
//	 *            the default value for the field.
//	 * @param <T>
//	 *            the type of the field.
//	 *
//	 * @return The wrapped property instance.
//	 */
//	public <T> Property<T> field(Function<M, WritableValue<T>> accessor, T defaultValue) {
//		return add(new FxPropertyField<>(accessor, defaultValue));
//	}
//
//	/**
//	 * Add a new field to this instance of the wrapper. This method is used for model elements that are following the
//	 * normal Java-Beans-standard i.e. the model fields are only available via getter and setter methods and not as
//	 * JavaFX Properties.
//	 *
//	 * <p>
//	 *
//	 * Example:
//	 * <p>
//	 *
//	 * <pre>
//	 *     ModelWrapper{@code<Person>} personWrapper = new ModelWrapper{@code<>}();
//	 *
//	 *     Property{@code<String>} wrappedNameProperty = personWrapper.field(person -> person.getName(), (person, value) -> person.setName(value), "empty");
//	 *
//	 *     // or with a method reference
//	 *     Property{@code<String>} wrappedNameProperty = personWrapper.field(Person::getName, Person::setName, "empty");
//	 *
//	 * </pre>
//	 *
//	 *
//	 * @param getter
//	 *            a function that returns the current value of the field for a given model element. Typically you will
//	 *            use a method reference to the getter method of the model element.
//	 * @param setter
//	 *            a function that sets the given value to the given model element. Typically you will use a method
//	 *            reference to the setter method of the model element.
//	 * @param <T>
//	 *            the type of the field.
//	 *
//	 * @return The wrapped property instance.
//	 */
//	public <T> Property<T> field(Function<M, T> getter, BiConsumer<M, T> setter) {
//		return add(new BeanPropertyField<>(getter, setter));
//	}
//
//
//
//
//
//	/**
//	 * Add a new field to this instance of the wrapper. This method is used for model elements that are following the
//	 * normal Java-Beans-standard i.e. the model fields are only available via getter and setter methods and not as
//	 * JavaFX Properties.
//	 * <p>
//	 * Additionally you can define a default value that is used as when {@link #reset()} is invoked.
//	 *
//	 * <p>
//	 *
//	 * Example:
//	 * <p>
//	 *
//	 * <pre>
//	 *     ModelWrapper{@code<Person>} personWrapper = new ModelWrapper{@code<>}();
//	 *
//	 *     Property{@code<String>} wrappedNameProperty = personWrapper.field(person -> person.getName(), (person, value) -> person.setName(value), "empty");
//	 *
//	 *     // or with a method reference
//	 *     Property{@code<String>} wrappedNameProperty = personWrapper.field(Person::getName, Person::setName, "empty");
//	 *
//	 * </pre>
//	 *
//	 *
//	 * @param getter
//	 *            a function that returns the current value of the field for a given model element. Typically you will
//	 *            use a method reference to the getter method of the model element.
//	 * @param setter
//	 *            a function that sets the given value to the given model element. Typically you will use a method
//	 *            reference to the setter method of the model element.
//	 * @param defaultValue
//	 *            the default value for the field.
//	 * @param <T>
//	 *            the type of the field.
//	 *
//	 * @return The wrapped property instance.
//	 */
//	public <T> Property<T> field(Function<M, T> getter, BiConsumer<M, T> setter, T defaultValue) {
//		return add(new BeanPropertyField<>(getter, setter, defaultValue));
//	}
//
//
//	/**
//	 * Add a new field to this instance of the wrapper that is identified by the given string. This method is basically
//	 * the same as {@link #field(Function)} with one difference: This method can be invoked multiply times but will only
//	 * create a single field instance for every given string identifier. This means that the returned property will be
//	 * the <b>same</b> instance for each call with the same identifier.
//	 * <p>
//	 * This behaviour can be useful when you don't keep a reference to the returned property but directly call this
//	 * method in a property accessor method in your viewModel. This way only a single field wrapping will be defined
//	 * even when the accessor method is called multiple times. See the following example of a typical use case:
//	 * <p>
//	 *
//	 * <pre>
//	 *
//	 *     public class PersonViewModel extends ViewModel {
//	 *
//	 *         // you only need a reference to the model wrapper itself but no additional references to each wrapped property.
//	 *         private ModelWrapper{@code<Person>} wrapper = new ModelWrapper{@code<>}();
//	 *
//	 *
//	 *         // This method will be used from the view.
//	 *         // The view can call this method multiple times but will always get the same property instance.
//	 *         public Property{@code<String>} nameProperty() {
//	 *              return wrapper.field("name", Person::nameProperty);
//	 *         }
//	 *     }
//	 * </pre>
//	 *
//	 *
//	 * @param fieldName
//	 *            the identifier for this field. Typically you will use the name of the field in the model.
//	 * @param accessor
//	 *            a function that returns the property for a given model instance. Typically you will use a method
//	 *            reference to the javafx-property accessor method.
//	 * @param <T>
//	 *            the type of the field.
//	 * @return The wrapped property instance.
//	 */
//	public <T> Property<T> field(String fieldName, Function<M, WritableValue<T>> accessor) {
//		return addIdentified(fieldName, new FxPropertyField<>(accessor));
//	}
//
//	/**
//	 * See {@link #field(String, Function)}. The difference is that this method accepts an additional parameter to
//	 * define the default value that will be used when {@link #reset()} is invoked.
//	 *
//	 * @param fieldName
//	 *            the identifier for this field. Typically you will use the name of the field in the model.
//	 * @param accessor
//	 *            a function that returns the property for a given model instance. Typically you will use a method
//	 *            reference to the javafx-property accessor method.
//	 * @param <T>
//	 *            the type of the field.
//	 * @return The wrapped property instance.
//	 */
//	public <T> Property<T> field(String fieldName, Function<M, WritableValue<T>> accessor, T defaultValue) {
//		return addIdentified(fieldName, new FxPropertyField<>(accessor, defaultValue));
//	}
//
//	/**
//	 * Add a new field to this instance of the wrapper that is identified by the given string. This method is basically
//	 * the same as {@link #field(Function, BiConsumer)} with one difference: This method can be invoked multiply times
//	 * but will only create a single field instance for every given string identifier. This means that the returned
//	 * property will be the <b>same</b> instance for each call with the same identifier.
//	 * <p>
//	 * This behaviour can be useful when you don't keep a reference to the returned property but directly call this
//	 * method in a property accessor method in your viewModel. This way only a single field wrapping will be defined
//	 * even when the accessor method is called multiple times. See the following example of a typical use case:
//	 * <p>
//	 *
//	 * <pre>
//	 *
//	 *     public class PersonViewModel extends ViewModel {
//	 *
//	 *         // you only need a reference to the model wrapper itself but no additional references to each wrapped property.
//	 *         private ModelWrapper{@code<Person>} wrapper = new ModelWrapper{@code<>}();
//	 *
//	 *
//	 *         // This method will be used from the view.
//	 *         // The view can call this method multiple times but will always get the same property instance.
//	 *         public Property{@code<String>} nameProperty() {
//	 *              return wrapper.field("name", Person::getName, Person::setName);
//	 *         }
//	 *     }
//	 * </pre>
//	 *
//	 *
//	 * @param fieldName
//	 *            the identifier for this field. Typically you will use the name of the field in the model.
//	 * @param getter
//	 *            a function that returns the current value of the field for a given model element. Typically you will
//	 *            use a method reference to the getter method of the model element.
//	 * @param setter
//	 *            a function that sets the given value to the given model element. Typically you will use a method
//	 *            reference to the setter method of the model element.
//	 * @param <T>
//	 *            the type of the field.
//	 * @return The wrapped property instance.
//	 */
//	public <T> Property<T> field(String fieldName, Function<M, T> getter, BiConsumer<M, T> setter) {
//		return addIdentified(fieldName, new BeanPropertyField<>(getter, setter));
//	}
//
//	/**
//	 * See {@link #field(String, Function, BiConsumer)}. The difference is that this method accepts an additional
//	 * parameter to define the default value that will be used when {@link #reset()} is invoked.
//	 *
//	 * @param fieldName
//	 *            the identifier for this field. Typically you will use the name of the field in the model.
//	 * @param getter
//	 *            a function that returns the current value of the field for a given model element. Typically you will
//	 *            use a method reference to the getter method of the model element.
//	 * @param setter
//	 *            a function that sets the given value to the given model element. Typically you will use a method
//	 *            reference to the setter method of the model element.
//	 * @param <T>
//	 *            the type of the field.
//	 * @return The wrapped property instance.
//	 */
//	public <T> Property<T> field(String fieldName, Function<M, T> getter, BiConsumer<M, T> setter, T defaultValue) {
//		return addIdentified(fieldName, new BeanPropertyField<>(getter, setter, defaultValue));
//	}
	
	private <T, R extends Property<T>> R add(PropertyField<T, M, R> field) {
		fields.add(field);
		if (model != null) {
			field.reload(model);
		}
		return field.getProperty();
	}
	
	@SuppressWarnings("unchecked")
	private <T, R extends Property<T>> R addIdentified(String fieldName, PropertyField<T, M, R> field) {
		if (identifiedFields.containsKey(fieldName)) {
			final Property<?> property = identifiedFields.get(fieldName).getProperty();
			return (R) property;
		} else {
			identifiedFields.put(fieldName, field);
			return add(field);
		}
	}
	
}
