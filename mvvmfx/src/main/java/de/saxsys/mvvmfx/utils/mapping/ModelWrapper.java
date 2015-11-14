/*******************************************************************************
 * Copyright 2015 Alexander Casall, Manuel Mauky
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.saxsys.mvvmfx.utils.mapping;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.BooleanGetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.BooleanPropertyAccessor;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.BooleanSetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.DoubleGetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.DoublePropertyAccessor;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.DoubleSetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.FloatGetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.FloatPropertyAccessor;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.FloatSetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.IntGetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.IntPropertyAccessor;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.IntSetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.ListGetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.ListPropertyAccessor;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.ListSetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.LongGetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.LongPropertyAccessor;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.LongSetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.ObjectGetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.ObjectPropertyAccessor;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.ObjectSetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.StringGetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.StringPropertyAccessor;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.StringSetter;
import eu.lestard.doc.Beta;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;


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
 *             public StringProperty nameProperty(){
 *                 return wrapper.field("name", Person::getName, Person::setName, "");
 *             }
 * 
 *             public StringProperty familyNameProperty(){
 *                 return wrapper.field("familyName", Person::getFamilyName, Person::setFamilyName, "");
 *             }
 * 
 *             public IntegerProperty ageProperty() {
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
 * 
 * @param <M>
 *            the type of the model class.
 */
@Beta
public class ModelWrapper<M> {
	
	private final ReadOnlyBooleanWrapper dirtyFlag = new ReadOnlyBooleanWrapper();
	private final ReadOnlyBooleanWrapper diffFlag = new ReadOnlyBooleanWrapper();
	
	
	/**
	 * This interface defines the operations that are possible for each field of a wrapped class.
	 * 
	 * @param <T>
	 *            target type. The base type of the returned property, f.e. {@link String}.
	 * @param <M>
	 *            model type. The type of the Model class, that is wrapped by this ModelWrapper instance.
	 * @param <R>
	 *            return type. The type of the Property that is returned via {@link #getProperty()}, f.e.
	 *            {@link StringProperty} or {@link Property<String>}.
	 */
	private interface PropertyField<T, M, R extends Property<T>> {
		void commit(M wrappedObject);
		
		void reload(M wrappedObject);
		
		void resetToDefault();
		
		R getProperty();
		
		/**
		 * Determines if the value in the model object and the property field are different or not.
		 * 
		 * This method is used to implement the {@link #differentProperty()} flag.
		 * 
		 * @param wrappedObject
		 *            the wrapped model object
		 * @return <code>false</code> if both the wrapped model object and the property field have the same value,
		 *         otherwise <code>true</code>
		 */
		boolean isDifferent(M wrappedObject);
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
		public FxPropertyField(Function<M, Property<T>> accessor, T defaultValue,
				Supplier<Property<T>> propertySupplier) {
			this.accessor = accessor;
			this.defaultValue = defaultValue;
			this.targetProperty = (R) propertySupplier.get();
			
			this.targetProperty.addListener((observable, oldValue, newValue) -> propertyWasChanged());
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
		
		@Override
		public boolean isDifferent(M wrappedObject) {
			final T modelValue = accessor.apply(wrappedObject).getValue();
			final T wrapperValue = targetProperty.getValue();
			
			return !Objects.equals(modelValue, wrapperValue);
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
			
			this.targetProperty.addListener((observable, oldValue, newValue) -> propertyWasChanged());
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
		
		@Override
		public boolean isDifferent(M wrappedObject) {
			final T modelValue = getter.apply(wrappedObject);
			final T wrapperValue = targetProperty.getValue();
			
			return !Objects.equals(modelValue, wrapperValue);
		}
	}
	
	/**
	 * An implementation of {@link PropertyField} that is used when the field of the model class is a {@link List} and
	 * will be mapped to a JavaFX {@link ListProperty}.
	 *
	 * @param <T>
	 * @param <E>
	 *            the type of the list elements.
	 */
	private class FxListPropertyField<E, T extends ObservableList<E>, R extends Property<T>>
			implements PropertyField<T, M, R> {
			
		private final List<E> defaultValue;
		private final ListPropertyAccessor<M, E> accessor;
		private final ListProperty<E> targetProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
		
		public FxListPropertyField(ListPropertyAccessor<M, E> accessor) {
			this(accessor, Collections.emptyList());
		}
		
		public FxListPropertyField(ListPropertyAccessor<M, E> accessor, List<E> defaultValue) {
			this.accessor = accessor;
			this.defaultValue = defaultValue;
			
			this.targetProperty.addListener((ListChangeListener<E>) change -> ModelWrapper.this.propertyWasChanged());
		}
		
		@Override
		public void commit(M wrappedObject) {
			accessor.apply(wrappedObject).setAll(targetProperty.getValue());
		}
		
		@Override
		public void reload(M wrappedObject) {
			targetProperty.setAll(accessor.apply(wrappedObject).getValue());
		}
		
		@Override
		public void resetToDefault() {
			targetProperty.setAll(defaultValue);
		}
		
		@Override
		public R getProperty() {
			return (R) targetProperty;
		}
		
		@Override
		public boolean isDifferent(M wrappedObject) {
			final List<E> modelValue = accessor.apply(wrappedObject).getValue();
			final List<E> wrapperValue = targetProperty;
			
			return !Objects.equals(modelValue, wrapperValue);
		}
	}
	
	/**
	 * An implementation of {@link PropertyField} that is used when the field of the model class is a {@link List} and
	 * is <b>not</b> a JavaFX ListProperty but is following the old Java-Beans standard, i.e. there is getter and setter
	 * method for the field.
	 *
	 * @param <T>
	 * @param <E>
	 *            the type of the list elements.
	 */
	private class BeanListPropertyField<E, T extends ObservableList<E>, R extends Property<T>>
			implements PropertyField<T, M, R> {
			
		private final ListGetter<M, E> getter;
		private final ListSetter<M, E> setter;
		
		private final List<E> defaultValue;
		private final ListProperty<E> targetProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
		
		public BeanListPropertyField(ListGetter<M, E> getter, ListSetter<M, E> setter) {
			this(getter, setter, Collections.emptyList());
		}
		
		public BeanListPropertyField(ListGetter<M, E> getter, ListSetter<M, E> setter, List<E> defaultValue) {
			this.defaultValue = defaultValue;
			this.getter = getter;
			this.setter = setter;
			
			this.targetProperty.addListener((ListChangeListener<E>) change -> propertyWasChanged());
		}
		
		@Override
		public void commit(M wrappedObject) {
			setter.accept(wrappedObject, targetProperty.getValue());
		}
		
		@Override
		public void reload(M wrappedObject) {
			targetProperty.setAll(getter.apply(wrappedObject));
		}
		
		@Override
		public void resetToDefault() {
			targetProperty.setAll(defaultValue);
		}
		
		@Override
		public R getProperty() {
			return (R) targetProperty;
		}
		
		@Override
		public boolean isDifferent(M wrappedObject) {
			final List<E> modelValue = getter.apply(wrappedObject);
			final List<E> wrapperValue = targetProperty;
			
			return !Objects.equals(modelValue, wrapperValue);
		}
	}
	
	private final Set<PropertyField<?, M, ?>> fields = new HashSet<>();
	private final Map<String, PropertyField<?, M, ?>> identifiedFields = new HashMap<>();
	
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
		
		calculateDifferenceFlag();
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
			
			dirtyFlag.set(false);
			
			calculateDifferenceFlag();
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
			
			dirtyFlag.set(false);
			calculateDifferenceFlag();
		}
	}
	
	
	
	private void propertyWasChanged() {
		dirtyFlag.set(true);
		calculateDifferenceFlag();
	}
	
	private void calculateDifferenceFlag() {
		if (model != null) {
			final Optional<PropertyField<?, M, ?>> optional = fields.stream()
					.filter(field -> field.isDifferent(model))
					.findAny();
					
			diffFlag.set(optional.isPresent());
		}
	}
	
	
	
	/** Field type String **/
	
	/**
	 * Add a new field of type String to this instance of the wrapper. This method is used for model elements that are
	 * following the normal Java-Beans-standard i.e. the model fields are only available via getter and setter methods
	 * and not as JavaFX Properties.
	 *
	 * <p>
	 *
	 * Example:
	 * <p>
	 *
	 * <pre>
	 * ModelWrapper{@code<Person>} personWrapper = new ModelWrapper{@code<>}();
	 * 
	 * StringProperty wrappedNameProperty = personWrapper.field(person -> person.getName(), (person, value)
	 * 	 -> person.setName(value), "empty");
	 * 
	 * // or with a method reference
	 * StringProperty wrappedNameProperty = personWrapper.field(Person::getName, Person::setName, "empty");
	 *
	 * </pre>
	 *
	 *
	 * @param getter
	 *            a function that returns the current value of the field for a given model element. Typically you will
	 *            use a method reference to the getter method of the model element.
	 * @param setter
	 *            a function that sets the given value to the given model element. Typically you will use a method
	 *            reference to the setter method of the model element.
	 * 			
	 * @return The wrapped property instance.
	 */
	public StringProperty field(StringGetter<M> getter, StringSetter<M> setter) {
		return add(new BeanPropertyField<>(getter, setter, SimpleStringProperty::new));
	}
	
	/**
	 * Add a new field of type String to this instance of the wrapper. See {@link #field(StringGetter, StringSetter)}.
	 * This method additionally has a parameter to define the default value that is used when the {@link #reset()}
	 * method is used.
	 *
	 *
	 * @param getter
	 *            a function that returns the current value of the field for a given model element. Typically you will
	 *            use a method reference to the getter method of the model element.
	 * @param setter
	 *            a function that sets the given value to the given model element. Typically you will use a method
	 *            reference to the setter method of the model element.
	 * @param defaultValue
	 *            the default value that is used when {@link #reset()} is invoked.
	 * 			
	 * @return The wrapped property instance.
	 */
	public StringProperty field(StringGetter<M> getter, StringSetter<M> setter, String defaultValue) {
		return add(new BeanPropertyField<>(getter, setter, defaultValue, SimpleStringProperty::new));
	}
	
	/**
	 * Add a new field of type {@link String} to this instance of the wrapper. This method is used for model elements
	 * that are following the enhanced JavaFX-Beans-standard i.e. the model fields are available as JavaFX Properties.
	 * <p>
	 *
	 * Example:
	 * <p>
	 *
	 * <pre>
	 * ModelWrapper{@code<Person>} personWrapper = new ModelWrapper{@code<>}();
	 * 
	 * StringProperty wrappedNameProperty = personWrapper.field(person -> person.nameProperty());
	 * 
	 * // or with a method reference
	 * StringProperty wrappedNameProperty = personWrapper.field(Person::nameProperty);
	 *
	 * </pre>
	 *
	 * @param accessor
	 *            a function that returns the property for a given model instance. Typically you will use a method
	 *            reference to the javafx-property accessor method.
	 * 			
	 * @return The wrapped property instance.
	 */
	public StringProperty field(StringPropertyAccessor<M> accessor) {
		return add(new FxPropertyField<>(accessor::apply, SimpleStringProperty::new));
	}
	
	/**
	 * Add a new field of type String to this instance of the wrapper. See {@link #field(StringGetter, StringSetter)}.
	 * This method additionally has a parameter to define the default value that is used when the {@link #reset()}
	 * method is used.
	 * 
	 * @param accessor
	 *            a function that returns the property for a given model instance. Typically you will use a method
	 *            reference to the javafx-property accessor method.
	 * @param defaultValue
	 *            the default value that is used when {@link #reset()} is invoked.
	 * @return The wrapped property instance.
	 */
	public StringProperty field(StringPropertyAccessor<M> accessor, String defaultValue) {
		return add(new FxPropertyField<>(accessor::apply, SimpleStringProperty::new));
	}
	
	
	
	
	/**
	 * Add a new field of type String to this instance of the wrapper. See {@link #field(StringGetter, StringSetter)}.
	 * This method additionally takes a string identifier as first parameter.
	 *
	 * This identifier is used to return the same property instance even when the method is invoked multiple times.
	 *
	 * @param identifier
	 *            an identifier for the field.
	 * @param getter
	 *            a function that returns the current value of the field for a given model element. Typically you will
	 *            use a method reference to the getter method of the model element.
	 * @param setter
	 *            a function that sets the given value to the given model element. Typically you will use a method
	 *            reference to the setter method of the model element.
	 * @return The wrapped property instance.
	 */
	public StringProperty field(String identifier, StringGetter<M> getter, StringSetter<M> setter) {
		return addIdentified(identifier, new BeanPropertyField<>(getter, setter, SimpleStringProperty::new));
	}
	
	public StringProperty field(String identifier, StringGetter<M> getter, StringSetter<M> setter,
			String defaultValue) {
		return addIdentified(identifier, new BeanPropertyField<>(getter, setter, defaultValue,
				SimpleStringProperty::new));
	}
	
	/**
	 * Add a new field of type String to this instance of the wrapper. See {@link #field(StringPropertyAccessor)}. This
	 * method additionally takes a string identifier as first parameter.
	 *
	 * This identifier is used to return the same property instance even when the method is invoked multiple times.
	 *
	 * @param identifier
	 *            an identifier for the field.
	 * 			
	 * @param accessor
	 *            a function that returns the property for a given model instance. Typically you will use a method
	 *            reference to the javafx-property accessor method.
	 * @return The wrapped property instance.
	 */
	public StringProperty field(String identifier, StringPropertyAccessor<M> accessor) {
		return addIdentified(identifier, new FxPropertyField<>(accessor::apply, SimpleStringProperty::new));
	}
	
	public StringProperty field(String identifier, StringPropertyAccessor<M> accessor, String defaultValue) {
		return addIdentified(identifier,
				new FxPropertyField<>(accessor::apply, defaultValue, SimpleStringProperty::new));
	}
	
	/** Field type Boolean **/
	
	public BooleanProperty field(BooleanGetter<M> getter, BooleanSetter<M> setter) {
		return add(new BeanPropertyField<>(getter, setter, SimpleBooleanProperty::new));
	}
	
	public BooleanProperty field(BooleanGetter<M> getter, BooleanSetter<M> setter, boolean defaultValue) {
		return add(new BeanPropertyField<>(getter, setter, defaultValue, SimpleBooleanProperty::new));
	}
	
	public BooleanProperty field(BooleanPropertyAccessor<M> accessor) {
		return add(new FxPropertyField<>(accessor, SimpleBooleanProperty::new));
	}
	
	public BooleanProperty field(BooleanPropertyAccessor<M> accessor, boolean defaultValue) {
		return add(new FxPropertyField<>(accessor, defaultValue, SimpleBooleanProperty::new));
	}
	
	public BooleanProperty field(String identifier, BooleanGetter<M> getter, BooleanSetter<M> setter) {
		return addIdentified(identifier, new BeanPropertyField<>(getter, setter, SimpleBooleanProperty::new));
	}
	
	public BooleanProperty field(String identifier, BooleanGetter<M> getter, BooleanSetter<M> setter,
			boolean defaultValue) {
		return addIdentified(identifier, new BeanPropertyField<>(getter, setter, defaultValue,
				SimpleBooleanProperty::new));
	}
	
	public BooleanProperty field(String identifier, BooleanPropertyAccessor<M> accessor) {
		return addIdentified(identifier, new FxPropertyField<>(accessor, SimpleBooleanProperty::new));
	}
	
	public BooleanProperty field(String identifier, BooleanPropertyAccessor<M> accessor, boolean defaultValue) {
		return addIdentified(identifier, new FxPropertyField<>(accessor, defaultValue, SimpleBooleanProperty::new));
	}
	
	
	
	/** Field type Double **/
	
	
	public DoubleProperty field(DoubleGetter<M> getter, DoubleSetter<M> setter) {
		final ModelWrapper<M>.BeanPropertyField<Number, SimpleDoubleProperty> beanPropertyField = new BeanPropertyField<>(
				getter::apply, (m, number) -> setter.accept(m, number.doubleValue()),
				SimpleDoubleProperty::new);
		return add(beanPropertyField);
	}
	
	public DoubleProperty field(DoubleGetter<M> getter, DoubleSetter<M> setter, double defaultValue) {
		final ModelWrapper<M>.BeanPropertyField<Number, SimpleDoubleProperty> beanPropertyField = new BeanPropertyField<>(
				getter::apply, (m, number) -> setter.accept(m, number.doubleValue()),
				defaultValue,
				SimpleDoubleProperty::new);
		return add(beanPropertyField);
	}
	
	public DoubleProperty field(DoublePropertyAccessor<M> accessor) {
		return add(new FxPropertyField<>(accessor::apply, SimpleDoubleProperty::new));
	}
	
	public DoubleProperty field(DoublePropertyAccessor<M> accessor, double defaultValue) {
		return add(new FxPropertyField<>(accessor::apply, defaultValue, SimpleDoubleProperty::new));
	}
	
	public DoubleProperty field(String identifier, DoubleGetter<M> getter, DoubleSetter<M> setter) {
		final ModelWrapper<M>.BeanPropertyField<Number, SimpleDoubleProperty> beanPropertyField = new BeanPropertyField<>(
				getter::apply, (m, number) -> setter.accept(m, number.doubleValue()),
				SimpleDoubleProperty::new);
				
		return addIdentified(identifier, beanPropertyField);
	}
	
	public DoubleProperty field(String identifier, DoubleGetter<M> getter, DoubleSetter<M> setter,
			double defaultValue) {
		ModelWrapper<M>.BeanPropertyField<Number, SimpleDoubleProperty> beanPropertyField = new BeanPropertyField<>(
				getter::apply, (m, number) -> setter.accept(m, number.doubleValue()),
				defaultValue,
				SimpleDoubleProperty::new);
		return addIdentified(identifier, beanPropertyField);
	}
	
	public DoubleProperty field(String identifier, DoublePropertyAccessor<M> accessor) {
		return addIdentified(identifier, new FxPropertyField<>(accessor::apply, SimpleDoubleProperty::new));
	}
	
	public DoubleProperty field(String identifier, DoublePropertyAccessor<M> accessor, double defaultValue) {
		return addIdentified(identifier,
				new FxPropertyField<>(accessor::apply, defaultValue, SimpleDoubleProperty::new));
	}
	
	
	
	
	/** Field type Float **/
	
	public FloatProperty field(FloatGetter<M> getter, FloatSetter<M> setter) {
		final ModelWrapper<M>.BeanPropertyField<Number, SimpleFloatProperty> beanPropertyField = new BeanPropertyField<>(
				getter::apply, (m, number) -> setter.accept(m, number.floatValue()),
				SimpleFloatProperty::new);
		return add(beanPropertyField);
	}
	
	public FloatProperty field(FloatGetter<M> getter, FloatSetter<M> setter, float defaultValue) {
		ModelWrapper<M>.BeanPropertyField<Number, SimpleFloatProperty> beanPropertyField = new BeanPropertyField<>(
				getter::apply, (m, number) -> setter.accept(m, number.floatValue()), defaultValue,
				SimpleFloatProperty::new);
		return add(beanPropertyField);
	}
	
	public FloatProperty field(FloatPropertyAccessor<M> accessor) {
		return add(new FxPropertyField<>(accessor::apply, SimpleFloatProperty::new));
	}
	
	public FloatProperty field(FloatPropertyAccessor<M> accessor, float defaultValue) {
		return add(new FxPropertyField<>(accessor::apply, defaultValue, SimpleFloatProperty::new));
	}
	
	public FloatProperty field(String identifier, FloatGetter<M> getter, FloatSetter<M> setter) {
		ModelWrapper<M>.BeanPropertyField<Number, SimpleFloatProperty> beanPropertyField = new BeanPropertyField<>(
				getter::apply, (m, number) -> setter.accept(m, number.floatValue()),
				SimpleFloatProperty::new);
		return addIdentified(identifier, beanPropertyField);
	}
	
	public FloatProperty field(String identifier, FloatGetter<M> getter, FloatSetter<M> setter, float defaultValue) {
		
		ModelWrapper<M>.BeanPropertyField<Number, SimpleFloatProperty> beanPropertyField = new BeanPropertyField<>(
				getter::apply, (m, number) -> setter.accept(m, number.floatValue()),
				defaultValue,
				SimpleFloatProperty::new);
		return addIdentified(identifier, beanPropertyField);
	}
	
	public FloatProperty field(String identifier, FloatPropertyAccessor<M> accessor) {
		return addIdentified(identifier, new FxPropertyField<>(accessor::apply, SimpleFloatProperty::new));
	}
	
	public FloatProperty field(String identifier, FloatPropertyAccessor<M> accessor, float defaultValue) {
		return addIdentified(identifier,
				new FxPropertyField<>(accessor::apply, defaultValue, SimpleFloatProperty::new));
	}
	
	
	/** Field type Integer **/
	
	
	public IntegerProperty field(IntGetter<M> getter, IntSetter<M> setter) {
		ModelWrapper<M>.BeanPropertyField<Number, SimpleIntegerProperty> beanPropertyField = new BeanPropertyField<>(
				getter::apply, (m, number) -> setter.accept(m, number.intValue()),
				SimpleIntegerProperty::new);
		return add(beanPropertyField);
	}
	
	public IntegerProperty field(IntGetter<M> getter, IntSetter<M> setter, int defaultValue) {
		ModelWrapper<M>.BeanPropertyField<Number, SimpleIntegerProperty> beanPropertyField = new BeanPropertyField<>(
				getter::apply, (m, number) -> setter.accept(m, number.intValue()),
				defaultValue,
				SimpleIntegerProperty::new);
		return add(beanPropertyField);
	}
	
	
	public IntegerProperty field(IntPropertyAccessor<M> accessor) {
		return add(new FxPropertyField<>(accessor::apply, SimpleIntegerProperty::new));
	}
	
	public IntegerProperty field(IntPropertyAccessor<M> accessor, int defaultValue) {
		return add(new FxPropertyField<>(accessor::apply, defaultValue, SimpleIntegerProperty::new));
	}
	
	public IntegerProperty field(String identifier, IntGetter<M> getter, IntSetter<M> setter) {
		ModelWrapper<M>.BeanPropertyField<Number, SimpleIntegerProperty> beanPropertyField = new BeanPropertyField<>(
				getter::apply, (m, number) -> setter.accept(m, number.intValue()),
				SimpleIntegerProperty::new);
		return addIdentified(identifier, beanPropertyField);
	}
	
	public IntegerProperty field(String identifier, IntGetter<M> getter, IntSetter<M> setter, int defaultValue) {
		ModelWrapper<M>.BeanPropertyField<Number, SimpleIntegerProperty> beanPropertyField = new BeanPropertyField<>(
				getter::apply, (m, number) -> setter.accept(m, number.intValue()),
				defaultValue,
				SimpleIntegerProperty::new);
		return addIdentified(identifier, beanPropertyField);
	}
	
	
	public IntegerProperty field(String identifier, IntPropertyAccessor<M> accessor) {
		return addIdentified(identifier, new FxPropertyField<>(accessor::apply, SimpleIntegerProperty::new));
	}
	
	public IntegerProperty field(String identifier, IntPropertyAccessor<M> accessor, int defaultValue) {
		return addIdentified(identifier, new FxPropertyField<>(accessor::apply, defaultValue,
				SimpleIntegerProperty::new));
	}
	
	
	
	/** Field type Long **/
	
	public LongProperty field(LongGetter<M> getter, LongSetter<M> setter) {
		ModelWrapper<M>.BeanPropertyField<Number, SimpleLongProperty> beanPropertyField = new BeanPropertyField<>(
				getter::apply, (m, number) -> setter.accept(m, number.longValue()),
				SimpleLongProperty::new);
		return add(beanPropertyField);
	}
	
	public LongProperty field(LongGetter<M> getter, LongSetter<M> setter, long defaultValue) {
		ModelWrapper<M>.BeanPropertyField<Number, SimpleLongProperty> beanPropertyField = new BeanPropertyField<>(
				getter::apply, (m, number) -> setter.accept(m, number.longValue()),
				defaultValue,
				SimpleLongProperty::new);
		return add(beanPropertyField);
	}
	
	public LongProperty field(LongPropertyAccessor<M> accessor) {
		return add(new FxPropertyField<>(accessor::apply, SimpleLongProperty::new));
	}
	
	public LongProperty field(LongPropertyAccessor<M> accessor, long defaultValue) {
		return add(new FxPropertyField<>(accessor::apply, defaultValue, SimpleLongProperty::new));
	}
	
	
	public LongProperty field(String identifier, LongGetter<M> getter, LongSetter<M> setter) {
		ModelWrapper<M>.BeanPropertyField<Number, SimpleLongProperty> beanPropertyField = new BeanPropertyField<>(
				getter::apply, (m, number) -> setter.accept(m, number.longValue()),
				SimpleLongProperty::new);
		return addIdentified(identifier, beanPropertyField);
	}
	
	public LongProperty field(String identifier, LongGetter<M> getter, LongSetter<M> setter, long defaultValue) {
		ModelWrapper<M>.BeanPropertyField<Number, SimpleLongProperty> beanPropertyField = new BeanPropertyField<>(
				getter::apply, (m, number) -> setter.accept(m, number.longValue()),
				defaultValue,
				SimpleLongProperty::new);
		return addIdentified(identifier,
				beanPropertyField);
	}
	
	public LongProperty field(String identifier, LongPropertyAccessor<M> accessor) {
		return addIdentified(identifier, new FxPropertyField<>(accessor::apply, SimpleLongProperty::new));
	}
	
	public LongProperty field(String identifier, LongPropertyAccessor<M> accessor, long defaultValue) {
		return addIdentified(identifier, new FxPropertyField<>(accessor::apply, defaultValue, SimpleLongProperty::new));
	}
	
	
	
	/** Field type generic **/
	
	
	public <T> ObjectProperty<T> field(ObjectGetter<M, T> getter, ObjectSetter<M, T> setter) {
		return add(new BeanPropertyField<>(getter, setter, SimpleObjectProperty::new));
	}
	
	public <T> ObjectProperty<T> field(ObjectGetter<M, T> getter, ObjectSetter<M, T> setter, T defaultValue) {
		return add(new BeanPropertyField<>(getter, setter, defaultValue, SimpleObjectProperty::new));
	}
	
	public <T> ObjectProperty<T> field(ObjectPropertyAccessor<M, T> accessor) {
		return add(new FxPropertyField<>(accessor::apply, SimpleObjectProperty::new));
	}
	
	public <T> ObjectProperty<T> field(ObjectPropertyAccessor<M, T> accessor, T defaultValue) {
		return add(new FxPropertyField<>(accessor::apply, defaultValue, SimpleObjectProperty::new));
	}
	
	
	public <T> ObjectProperty<T> field(String identifier, ObjectGetter<M, T> getter, ObjectSetter<M, T> setter) {
		return addIdentified(identifier, new BeanPropertyField<>(getter, setter, SimpleObjectProperty::new));
	}
	
	public <T> ObjectProperty<T> field(String identifier, ObjectGetter<M, T> getter, ObjectSetter<M, T> setter,
			T defaultValue) {
		return addIdentified(identifier, new BeanPropertyField<>(getter, setter, defaultValue,
				SimpleObjectProperty::new));
	}
	
	public <T> ObjectProperty<T> field(String identifier, ObjectPropertyAccessor<M, T> accessor) {
		return addIdentified(identifier, new FxPropertyField<>(accessor::apply, SimpleObjectProperty::new));
	}
	
	public <T> ObjectProperty<T> field(String identifier, ObjectPropertyAccessor<M, T> accessor, T defaultValue) {
		return addIdentified(identifier,
				new FxPropertyField<>(accessor::apply, defaultValue, SimpleObjectProperty::new));
	}
	
	
	/** Field type list **/
	
	public <E> ListProperty<E> field(ListGetter<M, E> getter, ListSetter<M, E> setter) {
		return add(new BeanListPropertyField<>(getter::apply,
				(m, list) -> setter.accept(m, FXCollections.observableArrayList(list))));
	}
	
	public <E> ListProperty<E> field(ListGetter<M, E> getter, ListSetter<M, E> setter, List<E> defaultValue) {
		return add(new BeanListPropertyField<>(getter::apply,
				(m, list) -> setter.accept(m, FXCollections.observableArrayList(list)), defaultValue));
	}
	
	public <E> ListProperty<E> field(ListPropertyAccessor<M, E> accessor) {
		return add(new FxListPropertyField<>(accessor::apply));
	}
	
	public <E> ListProperty<E> field(ListPropertyAccessor<M, E> accessor, List<E> defaultValue) {
		return add(new FxListPropertyField<>(accessor::apply, defaultValue));
	}
	
	
	public <E> ListProperty<E> field(String identifier, ListGetter<M, E> getter, ListSetter<M, E> setter) {
		return addIdentified(identifier, new BeanListPropertyField<>(getter::apply,
				(m, list) -> setter.accept(m, FXCollections.observableArrayList(list))));
	}
	
	public <E> ListProperty<E> field(String identifier, ListGetter<M, E> getter, ListSetter<M, E> setter,
			List<E> defaultValue) {
		return addIdentified(identifier, new BeanListPropertyField<>(getter::apply,
				(m, list) -> setter.accept(m, FXCollections.observableArrayList(list)), defaultValue));
	}
	
	public <E> ListProperty<E> field(String identifier, ListPropertyAccessor<M, E> accessor) {
		return addIdentified(identifier, new FxListPropertyField<>(accessor::apply));
	}
	
	public <E> ListProperty<E> field(String identifier, ListPropertyAccessor<M, E> accessor, List<E> defaultValue) {
		return addIdentified(identifier, new FxListPropertyField<>(accessor::apply, defaultValue));
	}
	
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
	
	/**
	 * This boolean flag indicates whether there is a difference of the data between the wrapped model object and the
	 * properties provided by this wrapper.
	 * <p>
	 * Note the difference to {@link #dirtyProperty()}: This property will be <code>true</code> if the data of the
	 * wrapped model is different to the properties of this wrapper. If you change the data back to the initial state so
	 * that the data is equal again, this property will change back to <code>false</code> while the
	 * {@link #dirtyProperty()} will still be <code>true</code>.
	 *
	 * Simply speaking: This property indicates whether there is a difference in data between the model and the wrapper.
	 * The {@link #dirtyProperty()} indicates whether there was a change done.
	 *
	 *
	 * Note: Only those changes are observed that are done through the wrapped property fields of this wrapper. If you
	 * change the data of the model instance directly, this property won't turn to <code>true</code>.
	 *
	 *
	 * @return a reay-only property indicating a difference between model and wrapper.
	 */
	public ReadOnlyBooleanProperty differentProperty() {
		return diffFlag.getReadOnlyProperty();
	}
	
	/**
	 * See {@link #differentProperty()}.
	 */
	public boolean isDifferent() {
		return diffFlag.get();
	}
	
	/**
	 * This boolean flag indicates whether there was a change to at least one wrapped property.
	 * <p>
	 * Note the difference to {@link #differentProperty()}: This property will turn to <code>true</code> when the value
	 * of one of the wrapped properties is changed. It will only change back to <code>false</code> when either the
	 * {@link #commit()} or {@link #reload()} method is called. This property will stay <code>true</code> even if
	 * afterwards another change is done so that the data is equal again. In this case the {@link #differentProperty()}
	 * will switch back to <code>false</code>.
	 *
	 * Simply speaking: This property indicates whether there was a change done to the wrapped properties or not. The
	 * {@link #differentProperty()} indicates whether there is a difference in data at the moment.
	 *
	 * @return a read only boolean property indicating if there was a change done.
	 */
	public ReadOnlyBooleanProperty dirtyProperty() {
		return dirtyFlag.getReadOnlyProperty();
	}
	
	/**
	 * See {@link #dirtyProperty()}.
	 */
	public boolean isDirty() {
		return dirtyFlag.get();
	}
	
	
}
