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

import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.BooleanGetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.BooleanImmutableSetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.BooleanPropertyAccessor;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.BooleanSetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.DoubleGetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.DoubleImmutableSetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.DoublePropertyAccessor;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.DoubleSetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.FloatGetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.FloatImmutableSetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.FloatPropertyAccessor;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.FloatSetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.IntGetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.IntImmutableSetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.IntPropertyAccessor;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.IntSetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.ListGetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.ListImmutableSetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.ListPropertyAccessor;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.ListSetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.LongGetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.LongImmutableSetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.LongPropertyAccessor;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.LongSetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.MapGetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.MapImmutableSetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.MapPropertyAccessor;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.MapSetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.ObjectGetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.ObjectImmutableSetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.ObjectPropertyAccessor;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.ObjectSetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.SetGetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.SetImmutableSetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.SetPropertyAccessor;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.SetSetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.StringGetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.StringImmutableSetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.StringPropertyAccessor;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.StringSetter;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import eu.lestard.doc.Beta;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;


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

	private final Set<PropertyField<?, M, ?>> fields = new LinkedHashSet<>();
	private final Map<String, PropertyField<?, M, ?>> identifiedFields = new HashMap<>();

	private final Set<ImmutablePropertyField<?, M, ?>> immutableFields = new LinkedHashSet<>();

	private final ObjectProperty<M> model;

	/**
	 * This flag is needed to support immutable fields. Without immutables when {@link #commit()} is invoked,
	 * the fields of the model instance are changed. With immutables however on commit the instance of the model itself is
	 * replaced. By default when the model instance is changed a {@link #reload()} is executed.
	 * This is ok when the user changes the model instance. It is not ok when we replace the model instance because of immutable fields.
	 * For this reason we need to distinguish between a change of the model instance due to a commit with immutable fields#
	 * and when the user changes the model instance. Therefore durring commit this flag will switch to <code>true</code>
	 * to indicate that we are currently executing a commit.
	 */
	private boolean inCommitPhase = false;

	/**
	 * Create a new instance of {@link ModelWrapper} that wraps the instance of the Model class wrapped by the property.
	 * Updates all data when the model instance changes.
	 *
	 * @param model
	 *            the property of the model element that will be wrapped.
	 */
	public ModelWrapper(ObjectProperty<M> model) {
		this.model = model;
		reload();
		this.model.addListener((observable, oldValue, newValue) -> {
			/*
			 * Only reload the values from the new model instance when it was changed by the user and not when it was changed
			 * during the commit phase.
			 */
			if(!inCommitPhase) {
				reload();
			}
		});
	}

	/**
	 * Create a new instance of {@link ModelWrapper} that wraps the given instance of the Model class.
	 *
	 * @param model
	 *            the element of the model that will be wrapped.
	 */
	public ModelWrapper(M model) {
		this(new SimpleObjectProperty<>(model));
	}

	/**
	 * Create a new instance of {@link ModelWrapper} that is empty at the moment. You have to define the model element
	 * that should be wrapped afterwards with the {@link #set(Object)} method.
	 */
	public ModelWrapper() {
		this(new SimpleObjectProperty<>());
	}

	/**
	 * Define the model element that will be wrapped by this {@link ModelWrapper} instance.
	 *
	 * @param model
	 *            the element of the model that will be wrapped.
	 */
	public void set(M model) {
		this.model.set(model);
	}

	/**
	 * @return the wrapped model element if one was defined, otherwise <code>null</code>.
	 */
	public M get() {
		return model.get();
	}

	/**
	 * @return property holding the model instance wrapped by this model wrapper instance.
   */
	public ObjectProperty<M> modelProperty() {
		return model;
	}

	/**
	 * Resets all defined fields to their default values.
	 * <p>
	 * Default values can be defined as last argument of the overloaded "field" methods
	 * (see {@link #field(StringGetter, StringSetter, String)})
	 * or by using the {@link #useCurrentValuesAsDefaults()} method.
	 *
	 * <p>
	 *
	 * If no special default value was defined for a field the default value of the actual Property type will be used
	 * (e.g. 0 for {@link IntegerProperty}, <code>null</code> for {@link StringProperty} and {@link ObjectProperty} ...).
	 *
	 *
	 * <p>
	 * <b>Note:</b> This method has no effects on the wrapped model element but will only change the values of the
	 * defined property fields.
	 */
	public void reset() {
		fields.forEach(PropertyField::resetToDefault);
		immutableFields.forEach(PropertyField::resetToDefault);

		calculateDifferenceFlag();
	}

	/**
	 * Use all values that are currently present in the wrapped model object as new default values for respective fields.
	 * This overrides/updates the values that were set during the initialization of the field mappings.
	 * <p>
	 * Subsequent calls to {@link #reset()} will reset the values to this new default values.
	 * <p>
	 * Usage example:
	 * <pre>
	 * ModelWrapper{@code<Person>} wrapper = new ModelWrapper{@code<>}();
	 *
	 * wrapper.field(Person::getName, Person::setName, "oldDefault");
	 *
	 * Person p = new Person();
	 * wrapper.set(p);
	 *
	 *
	 * p.setName("Luise");
	 *
	 * wrapper.useCurrentValuesAsDefaults(); // now "Luise" is the default value for the name field.
	 *
	 *
	 * name.set("Hugo");
	 * wrapper.commit();
	 *
	 * name.get(); // Hugo
	 * p.getName(); // Hugo
	 *
	 *
	 * wrapper.reset(); // reset to the new defaults
	 * name.get(); // Luise
	 *
	 * wrapper.commit(); // put values from properties to the wrapped model object
	 * p.getName(); // Luise
	 *
	 *
	 * </pre>
	 *
	 *
	 * If no model instance is set to be wrapped by the ModelWrapper, nothing will happen when this method is invoked.
	 * Instead the old default values will still be available.
	 *
	 */
	public void useCurrentValuesAsDefaults() {
		M wrappedModelInstance = model.get();
		if(wrappedModelInstance != null) {
			for (final PropertyField<?, M, ?> field : fields) {
				field.updateDefault(wrappedModelInstance);
			}

			for (final ImmutablePropertyField<?, M, ?> field : immutableFields) {
				field.updateDefault(wrappedModelInstance);
			}
		}
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
		if (model.get() != null) {

			inCommitPhase = true;

			fields.forEach(field -> field.commit(model.get()));

			if(! immutableFields.isEmpty()) {

				M tmp = model.get();

				for (ImmutablePropertyField<?, M, ?> immutableField : immutableFields) {
					tmp = immutableField.commitImmutable(tmp);
				}

				model.set(tmp);

			}

			inCommitPhase = false;



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
		M wrappedModelInstance = model.get();
		if (wrappedModelInstance != null) {
			fields.forEach(field -> field.reload(wrappedModelInstance));

			immutableFields.forEach(field -> field.reload(wrappedModelInstance));

			dirtyFlag.set(false);
			calculateDifferenceFlag();
		}
	}


	/**
	 * This method can be used to copy all values of this {@link ModelWrapper} instance
	 * to the model instance provided as argument.
	 * Existing values in the provided model instance will be overwritten.
	 * <p>
	 * This method doesn't change the state of this modelWrapper or the wrapped model instance.
	 *
	 * @param model a non-null instance of a model.
	 */
	public void copyValuesTo(M model) {
		Objects.requireNonNull(model);
		fields.forEach(field -> field.commit(model));
	}


	private void propertyWasChanged() {
		dirtyFlag.set(true);
		calculateDifferenceFlag();
	}

	private void calculateDifferenceFlag() {
		M wrappedModelInstance = model.get();

		if (wrappedModelInstance != null) {
			for (final PropertyField<?, M, ?> field : fields) {
				if (field.isDifferent(wrappedModelInstance)) {
					diffFlag.set(true);
					return;
				}
			}

			for (final ImmutablePropertyField<?, M, ?> field : immutableFields) {
				if(field.isDifferent(wrappedModelInstance)) {
					diffFlag.set(true);
					return;
				}
			}

			diffFlag.set(false);
		}
	}


	private <T, R extends Property<T>> R add(PropertyField<T, M, R> field) {
		fields.add(field);
		if (model.get() != null) {
			field.reload(model.get());
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

	private <T, R extends Property<T>> R addImmutable(ImmutablePropertyField<T, M, R> field) {
		immutableFields.add(field);

		if(model.get() != null) {
			field.reload(model.get());
		}

		return field.getProperty();
	}

	private <T, R extends Property<T>> R addIdentifiedImmutable(String fieldName, ImmutablePropertyField<T, M, R> field) {
		if (identifiedFields.containsKey(fieldName)) {
			final Property<?> property = identifiedFields.get(fieldName).getProperty();
			return (R) property;
		} else {
			identifiedFields.put(fieldName, field);
			return addImmutable(field);
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
	 * @return a read-only property indicating a difference between model and wrapper.
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
	 * <p/>
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



	/* Field type String */

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
	 * 	 -> person.setName(value));
	 *
	 * // or with a method reference
	 * StringProperty wrappedNameProperty = personWrapper.field(Person::getName, Person::setName);
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
		return add(new BeanPropertyField<>(this::propertyWasChanged, getter, setter, SimpleStringProperty::new));
	}

	/**
	 * Add a new immutable field of type String to this instance of the wrapper. This method is used for immutable
	 * model elements that have getters to get values for it's fields but not setters.
	 * Instead, immutables have methods that take a new value for a field and return a new cloned instance of the
	 * model element with only this field updated to the new value. The old model instance isn't changed.
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
	 * 	 -> {
	 * 	     Person clone = person.withName(value);
	 * 	     return clone;
	 * 	 });
	 *
	 * // or with a method reference
	 * StringProperty wrappedNameProperty = personWrapper.field(Person::getName, Person::withName);
	 *
	 * </pre>
	 *
	 *
	 * @param getter
	 *            a function that returns the current value of the field for a given model element. Typically you will
	 *            use a method reference to the getter method of the model element.
	 * @param immutableSetter
	 *            a function that returns a clone of this the given model element that has the given value set. Typically you will use a method
	 *            reference to the immutable setter method of the model element.
	 *
	 * @return The wrapped property instance.
	 */
	public StringProperty immutableField(StringGetter<M> getter, StringImmutableSetter<M> immutableSetter){
		return addImmutable(new ImmutableBeanPropertyField<>(this::propertyWasChanged, getter, immutableSetter, SimpleStringProperty::new));
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
		return add(new BeanPropertyField<>(this::propertyWasChanged, getter, setter, defaultValue,
				SimpleStringProperty::new));
	}

	/**
	 * Ad a new immutable field of type String to this instance of the wrapper. See {@link #immutableField(StringGetter, StringImmutableSetter)}.
	 * This method additionally has a parameter to define the default value that is used when the {@link #reset()}
	 * method is used.
	 *
	 *
	 * @param getter
	 *            a function that returns the current value of the field for a given model element. Typically you will
	 *            use a method reference to the getter method of the model element.
	 * @param immutableSetter
	 *            a function that returns a clone of this the given model element that has the given value set. Typically you will use a method
	 *            reference to the immutable setter method of the model element.
	 * @param defaultValue
	 *            the default value that is used when {@link #reset()} is invoked.
	 *
	 * @return The wrapped property instance.
	 */
	public StringProperty immutableField(StringGetter<M> getter, StringImmutableSetter<M> immutableSetter, String defaultValue){
		return addImmutable(new ImmutableBeanPropertyField<>(this::propertyWasChanged, getter, immutableSetter, defaultValue, SimpleStringProperty::new));
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
		return add(new FxPropertyField<>(this::propertyWasChanged, accessor, SimpleStringProperty::new));
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
		return add(new FxPropertyField<>(this::propertyWasChanged, accessor, defaultValue,
				SimpleStringProperty::new));
	}




	/**
	 * Add a new field of type String to this instance of the wrapper. See {@link #field(StringGetter, StringSetter)}.
	 * This method additionally takes a string identifier as first parameter.
	 * <p/>
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
		return addIdentified(identifier, new BeanPropertyField<>(this::propertyWasChanged, getter, setter,
				() -> new SimpleStringProperty(null, identifier)));
	}

	public StringProperty field(String identifier, StringGetter<M> getter, StringSetter<M> setter,
			String defaultValue) {
		return addIdentified(identifier, new BeanPropertyField<>(this::propertyWasChanged, getter, setter, defaultValue,
				() -> new SimpleStringProperty(null, identifier)));
	}

	/**
	 * Add a new immutable field of type String to this instance of the wrapper. See {@link #immutableField(StringGetter, StringImmutableSetter}).
	 * This method additionally takes a string identifier as first parameter.
	 * <p/>
	 * This identifier is used to return the same property instance even when the method is invoked multiple times.
	 *
	 * @param identifier
	 *            an identifier for the field.
	 * @param getter
	 *            a function that returns the current value of the field for a given model element. Typically you will
	 *            use a method reference to the getter method of the model element.
	 * @param immutableSetter
	 *            a function that returns a clone of this the given model element that has the given value set. Typically you will use a method
	 *            reference to the immutable setter method of the model element.
	 * @return The wrapped property instance.
	 */
	public StringProperty immutableField(String identifier, StringGetter<M> getter, StringImmutableSetter<M> immutableSetter){
		return addIdentifiedImmutable(identifier, new ImmutableBeanPropertyField<>(this::propertyWasChanged, getter, immutableSetter, () -> new SimpleStringProperty(null, identifier)));
	}

	public StringProperty immutableField(String identifier, StringGetter<M> getter, StringImmutableSetter<M> immutableSetter, String defaultValue){
		return addIdentifiedImmutable(identifier, new ImmutableBeanPropertyField<>(this::propertyWasChanged, getter, immutableSetter, defaultValue, () -> new SimpleStringProperty(null, identifier)));
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
		return addIdentified(identifier, new FxPropertyField<>(this::propertyWasChanged, accessor,
				() -> new SimpleStringProperty(null, identifier)));
	}

	public StringProperty field(String identifier, StringPropertyAccessor<M> accessor, String defaultValue) {
		return addIdentified(identifier,
				new FxPropertyField<>(this::propertyWasChanged, accessor, defaultValue,
						() -> new SimpleStringProperty(null, identifier)));
	}

	/* Field type Boolean */

	public BooleanProperty field(BooleanGetter<M> getter, BooleanSetter<M> setter) {
		return add(new BeanPropertyField<>(this::propertyWasChanged, getter, setter, SimpleBooleanProperty::new));
	}

	public BooleanProperty immutableField(BooleanGetter<M> getter, BooleanImmutableSetter<M> immutableSetter){
		return addImmutable(new ImmutableBeanPropertyField<>(this::propertyWasChanged, getter, immutableSetter, SimpleBooleanProperty::new));
	}

	public BooleanProperty field(BooleanGetter<M> getter, BooleanSetter<M> setter, boolean defaultValue) {
		return add(new BeanPropertyField<>(this::propertyWasChanged, getter, setter, defaultValue,
				SimpleBooleanProperty::new));
	}

	public BooleanProperty immutableField(BooleanGetter<M> getter, BooleanImmutableSetter<M> immutableSetter, boolean defaultValue){
		return addImmutable(new ImmutableBeanPropertyField<>(this::propertyWasChanged, getter, immutableSetter, defaultValue, SimpleBooleanProperty::new));
	}

	public BooleanProperty field(BooleanPropertyAccessor<M> accessor) {
		return add(new FxPropertyField<>(this::propertyWasChanged, accessor, SimpleBooleanProperty::new));
	}

	public BooleanProperty field(BooleanPropertyAccessor<M> accessor, boolean defaultValue) {
		return add(new FxPropertyField<>(this::propertyWasChanged, accessor, defaultValue, SimpleBooleanProperty::new));
	}

	public BooleanProperty field(String identifier, BooleanGetter<M> getter, BooleanSetter<M> setter) {
		return addIdentified(identifier, new BeanPropertyField<>(this::propertyWasChanged, getter, setter,
				() -> new SimpleBooleanProperty(null, identifier)));
	}

	public BooleanProperty field(String identifier, BooleanGetter<M> getter, BooleanSetter<M> setter,
			boolean defaultValue) {
		return addIdentified(identifier, new BeanPropertyField<>(this::propertyWasChanged, getter, setter, defaultValue,
				() -> new SimpleBooleanProperty(null, identifier)));
	}

	public BooleanProperty immutableField(String identifier, BooleanGetter<M> getter, BooleanImmutableSetter<M> immutableSetter){
		return addIdentifiedImmutable(identifier, new ImmutableBeanPropertyField<>(this::propertyWasChanged, getter, immutableSetter, () -> new SimpleBooleanProperty(null, identifier)));
	}

	public BooleanProperty immutableField(String identifier, BooleanGetter<M> getter, BooleanImmutableSetter<M> immutableSetter, boolean defaultValue){
		return addIdentifiedImmutable(identifier, new ImmutableBeanPropertyField<>(this::propertyWasChanged, getter, immutableSetter, defaultValue, () -> new SimpleBooleanProperty(null, identifier)));
	}

	public BooleanProperty field(String identifier, BooleanPropertyAccessor<M> accessor) {
		return addIdentified(identifier, new FxPropertyField<>(this::propertyWasChanged, accessor,
				() -> new SimpleBooleanProperty(null, identifier)));
	}

	public BooleanProperty field(String identifier, BooleanPropertyAccessor<M> accessor, boolean defaultValue) {
		return addIdentified(identifier, new FxPropertyField<>(this::propertyWasChanged, accessor, defaultValue,
				() -> new SimpleBooleanProperty(null, identifier)));
	}



	/* Field type Double */


	public DoubleProperty field(DoubleGetter<M> getter, DoubleSetter<M> setter) {
		return add(new BeanPropertyField<>(
				this::propertyWasChanged,
				getter::apply, (m, number) -> setter.accept(m, number.doubleValue()),
				SimpleDoubleProperty::new));
	}


	public DoubleProperty immutableField(DoubleGetter<M> getter, DoubleImmutableSetter<M> immutableSetter){
		return addImmutable(new ImmutableBeanPropertyField<>(
				this::propertyWasChanged,
				getter::apply,
				(m, number) -> immutableSetter.apply(m, number.doubleValue()),
				SimpleDoubleProperty::new
		));
	}

	public DoubleProperty field(DoubleGetter<M> getter, DoubleSetter<M> setter, double defaultValue) {
		return add(new BeanPropertyField<>(
				this::propertyWasChanged,
				getter::apply, (m, number) -> setter.accept(m, number.doubleValue()),
				defaultValue,
				SimpleDoubleProperty::new));
	}

	public DoubleProperty immutableField(DoubleGetter<M> getter, DoubleImmutableSetter<M> immutableSetter, double defaultValue){
		return addImmutable(new ImmutableBeanPropertyField<>(
				this::propertyWasChanged,
				getter::apply,
				(m, number) -> immutableSetter.apply(m, number.doubleValue()),
				defaultValue,
				SimpleDoubleProperty::new));
	}

	public DoubleProperty field(DoublePropertyAccessor<M> accessor) {
		return add(new FxPropertyField<>(this::propertyWasChanged, accessor::apply, SimpleDoubleProperty::new));
	}

	public DoubleProperty field(DoublePropertyAccessor<M> accessor, double defaultValue) {
		return add(new FxPropertyField<>(this::propertyWasChanged, accessor::apply, defaultValue,
				SimpleDoubleProperty::new));
	}

	public DoubleProperty field(String identifier, DoubleGetter<M> getter, DoubleSetter<M> setter) {

		return addIdentified(identifier, new BeanPropertyField<>(
				this::propertyWasChanged,
				getter::apply,
				(m, number) -> setter.accept(m, number.doubleValue()),
				() -> new SimpleDoubleProperty(null, identifier)));
	}

	public DoubleProperty field(String identifier, DoubleGetter<M> getter, DoubleSetter<M> setter,
			double defaultValue) {
		return addIdentified(identifier, new BeanPropertyField<>(
				this::propertyWasChanged,
				getter::apply,
				(m, number) -> setter.accept(m, number.doubleValue()),
				defaultValue,
				() -> new SimpleDoubleProperty(null, identifier)));
	}


	public DoubleProperty immutableField(String identifier, DoubleGetter<M> getter, DoubleImmutableSetter<M> immutableSetter){
		return addIdentifiedImmutable(identifier, new ImmutableBeanPropertyField<>(
				this::propertyWasChanged,
				getter::apply,
				(m, number) -> immutableSetter.apply(m, number.doubleValue()),
				() -> new SimpleDoubleProperty(null, identifier)));
	}

	public DoubleProperty immutableField(String identifier, DoubleGetter<M> getter, DoubleImmutableSetter<M> immutableSetter, double defaultValue){
		return addIdentifiedImmutable(identifier, new ImmutableBeanPropertyField<>(
				this::propertyWasChanged,
				getter::apply,
				(m, number) -> immutableSetter.apply(m, number.doubleValue()),
				defaultValue,
				() -> new SimpleDoubleProperty(null, identifier)));
	}

	public DoubleProperty field(String identifier, DoublePropertyAccessor<M> accessor) {
		return addIdentified(identifier, new FxPropertyField<>(
				this::propertyWasChanged,
				accessor::apply,
				() -> new SimpleDoubleProperty(null, identifier)));
	}

	public DoubleProperty field(String identifier, DoublePropertyAccessor<M> accessor, double defaultValue) {
		return addIdentified(identifier,
				new FxPropertyField<>(this::propertyWasChanged, accessor::apply, defaultValue,
						() -> new SimpleDoubleProperty(null, identifier)));
	}




	/* Field type Float */

	public FloatProperty field(FloatGetter<M> getter, FloatSetter<M> setter) {
		return add(new BeanPropertyField<>(
				this::propertyWasChanged,
				getter::apply, (m, number) -> setter.accept(m, number.floatValue()),
				SimpleFloatProperty::new));
	}

	public FloatProperty immutableField(FloatGetter<M> getter, FloatImmutableSetter<M> immutableSetter){
		return addImmutable(new ImmutableBeanPropertyField<>(
				this::propertyWasChanged,
				getter::apply,
				(m, number) -> immutableSetter.apply(m, number.floatValue()),
				SimpleFloatProperty::new
		));
	}

	public FloatProperty field(FloatGetter<M> getter, FloatSetter<M> setter, float defaultValue) {
		return add(new BeanPropertyField<>(
				this::propertyWasChanged,
				getter::apply, (m, number) -> setter.accept(m, number.floatValue()), defaultValue,
				SimpleFloatProperty::new));
	}

	public FloatProperty immutableField(FloatGetter<M> getter, FloatImmutableSetter<M> immutableSetter, float defaultValue){
		return addImmutable(new ImmutableBeanPropertyField<>(
				this::propertyWasChanged,
				getter::apply,
				(m, number) -> immutableSetter.apply(m, number.floatValue()),
				defaultValue,
				SimpleFloatProperty::new));
	}

	public FloatProperty field(FloatPropertyAccessor<M> accessor) {
		return add(new FxPropertyField<>(this::propertyWasChanged, accessor::apply, SimpleFloatProperty::new));
	}

	public FloatProperty field(FloatPropertyAccessor<M> accessor, float defaultValue) {
		return add(new FxPropertyField<>(this::propertyWasChanged, accessor::apply, defaultValue,
				SimpleFloatProperty::new));
	}

	public FloatProperty field(String identifier, FloatGetter<M> getter, FloatSetter<M> setter) {
		return addIdentified(identifier, new BeanPropertyField<>(
				this::propertyWasChanged,
				getter::apply, (m, number) -> setter.accept(m, number.floatValue()),
				() -> new SimpleFloatProperty(null, identifier)));
	}

	public FloatProperty field(String identifier, FloatGetter<M> getter, FloatSetter<M> setter, float defaultValue) {

		return addIdentified(identifier, new BeanPropertyField<>(
				this::propertyWasChanged,
				getter::apply, (m, number) -> setter.accept(m, number.floatValue()),
				defaultValue,
				() -> new SimpleFloatProperty(null, identifier)));
	}


	public FloatProperty immutableField(String identifier, FloatGetter<M> getter, FloatImmutableSetter<M> immutableSetter){
		return addIdentifiedImmutable(identifier, new ImmutableBeanPropertyField<>(
				this::propertyWasChanged,
				getter::apply,
				(m, number) -> immutableSetter.apply(m, number.floatValue()),
				() -> new SimpleFloatProperty(null, identifier)));
	}

	public FloatProperty immutableField(String identifier, FloatGetter<M> getter, FloatImmutableSetter<M> immutableSetter, float defaultValue){
		return addIdentifiedImmutable(identifier, new ImmutableBeanPropertyField<>(
				this::propertyWasChanged,
				getter::apply,
				(m, number) -> immutableSetter.apply(m, number.floatValue()),
				defaultValue,
				() -> new SimpleFloatProperty(null, identifier)));
	}

	public FloatProperty field(String identifier, FloatPropertyAccessor<M> accessor) {
		return addIdentified(identifier, new FxPropertyField<>(this::propertyWasChanged, accessor::apply,
				() -> new SimpleFloatProperty(null, identifier)));
	}

	public FloatProperty field(String identifier, FloatPropertyAccessor<M> accessor, float defaultValue) {
		return addIdentified(identifier,
				new FxPropertyField<>(this::propertyWasChanged, accessor::apply, defaultValue,
						() -> new SimpleFloatProperty(null, identifier)));
	}


	/* Field type Integer */


	public IntegerProperty field(IntGetter<M> getter, IntSetter<M> setter) {
		return add(new BeanPropertyField<>(
				this::propertyWasChanged,
				getter::apply, (m, number) -> setter.accept(m, number.intValue()),
				SimpleIntegerProperty::new));
	}


	public IntegerProperty immutableField(IntGetter<M> getter, IntImmutableSetter<M> immutableSetter){
		return addImmutable(new ImmutableBeanPropertyField<>(
				this::propertyWasChanged,
				getter::apply,
				(m, number) -> immutableSetter.apply(m, number.intValue()),
				SimpleIntegerProperty::new
		));
	}

	public IntegerProperty field(IntGetter<M> getter, IntSetter<M> setter, int defaultValue) {
		return add(new BeanPropertyField<>(
				this::propertyWasChanged,
				getter::apply, (m, number) -> setter.accept(m, number.intValue()),
				defaultValue,
				SimpleIntegerProperty::new));
	}


	public IntegerProperty immutableField(IntGetter<M> getter, IntImmutableSetter<M> immutableSetter, int defaultValue){
		return addImmutable(new ImmutableBeanPropertyField<>(
				this::propertyWasChanged,
				getter::apply,
				(m, number) -> immutableSetter.apply(m, number.intValue()),
				defaultValue,
				SimpleIntegerProperty::new));
	}


	public IntegerProperty field(IntPropertyAccessor<M> accessor) {
		return add(new FxPropertyField<>(this::propertyWasChanged, accessor::apply, SimpleIntegerProperty::new));
	}

	public IntegerProperty field(IntPropertyAccessor<M> accessor, int defaultValue) {
		return add(new FxPropertyField<>(this::propertyWasChanged, accessor::apply, defaultValue,
				SimpleIntegerProperty::new));
	}

	public IntegerProperty field(String identifier, IntGetter<M> getter, IntSetter<M> setter) {
		return addIdentified(identifier, new BeanPropertyField<>(
				this::propertyWasChanged,
				getter::apply, (m, number) -> setter.accept(m, number.intValue()),
				() -> new SimpleIntegerProperty(null, identifier)));
	}

	public IntegerProperty field(String identifier, IntGetter<M> getter, IntSetter<M> setter, int defaultValue) {
		return addIdentified(identifier, new BeanPropertyField<>(
				this::propertyWasChanged,
				getter::apply, (m, number) -> setter.accept(m, number.intValue()),
				defaultValue,
				() -> new SimpleIntegerProperty(null, identifier)));
	}



	public IntegerProperty immutableField(String identifier, IntGetter<M> getter, IntImmutableSetter<M> immutableSetter){
		return addIdentifiedImmutable(identifier, new ImmutableBeanPropertyField<>(
				this::propertyWasChanged,
				getter::apply,
				(m, number) -> immutableSetter.apply(m, number.intValue()),
				() -> new SimpleIntegerProperty(null, identifier)));
	}

	public IntegerProperty immutableField(String identifier, IntGetter<M> getter, IntImmutableSetter<M> immutableSetter, int defaultValue){
		return addIdentifiedImmutable(identifier, new ImmutableBeanPropertyField<>(
				this::propertyWasChanged,
				getter::apply,
				(m, number) -> immutableSetter.apply(m, number.intValue()),
				defaultValue,
				() -> new SimpleIntegerProperty(null, identifier)));
	}

	public IntegerProperty field(String identifier, IntPropertyAccessor<M> accessor) {
		return addIdentified(identifier, new FxPropertyField<>(this::propertyWasChanged, accessor::apply,
				() -> new SimpleIntegerProperty(null, identifier)));
	}

	public IntegerProperty field(String identifier, IntPropertyAccessor<M> accessor, int defaultValue) {
		return addIdentified(identifier, new FxPropertyField<>(this::propertyWasChanged, accessor::apply, defaultValue,
				() -> new SimpleIntegerProperty(null, identifier)));
	}



	/* Field type Long */

	public LongProperty field(LongGetter<M> getter, LongSetter<M> setter) {
		return add(new BeanPropertyField<>(
				this::propertyWasChanged,
				getter::apply, (m, number) -> setter.accept(m, number.longValue()),
				SimpleLongProperty::new));
	}

	public LongProperty immutableField(LongGetter<M> getter, LongImmutableSetter<M> immutableSetter){
		return addImmutable(new ImmutableBeanPropertyField<>(
				this::propertyWasChanged,
				getter::apply,
				(m, number) -> immutableSetter.apply(m, number.longValue()),
				SimpleLongProperty::new
		));
	}

	public LongProperty field(LongGetter<M> getter, LongSetter<M> setter, long defaultValue) {
		return add(new BeanPropertyField<>(
				this::propertyWasChanged,
				getter::apply, (m, number) -> setter.accept(m, number.longValue()),
				defaultValue,
				SimpleLongProperty::new));
	}

	public LongProperty immutableField(LongGetter<M> getter, LongImmutableSetter<M> immutableSetter, long defaultValue){
		return addImmutable(new ImmutableBeanPropertyField<>(
				this::propertyWasChanged,
				getter::apply,
				(m, number) -> immutableSetter.apply(m, number.longValue()),
				defaultValue,
				SimpleLongProperty::new));
	}

	public LongProperty field(LongPropertyAccessor<M> accessor) {
		return add(new FxPropertyField<>(this::propertyWasChanged, accessor::apply, SimpleLongProperty::new));
	}

	public LongProperty field(LongPropertyAccessor<M> accessor, long defaultValue) {
		return add(new FxPropertyField<>(this::propertyWasChanged, accessor::apply, defaultValue,
				SimpleLongProperty::new));
	}


	public LongProperty field(String identifier, LongGetter<M> getter, LongSetter<M> setter) {
		return addIdentified(identifier, new BeanPropertyField<>(
				this::propertyWasChanged,
				getter::apply, (m, number) -> setter.accept(m, number.longValue()),
				() -> new SimpleLongProperty(null, identifier)));
	}

	public LongProperty field(String identifier, LongGetter<M> getter, LongSetter<M> setter, long defaultValue) {
		return addIdentified(identifier,
				new BeanPropertyField<>(
						this::propertyWasChanged,
						getter::apply, (m, number) -> setter.accept(m, number.longValue()),
						defaultValue,
						() -> new SimpleLongProperty(null, identifier)));
	}


	public LongProperty immutableField(String identifier, LongGetter<M> getter, LongImmutableSetter<M> immutableSetter){
		return addIdentifiedImmutable(identifier, new ImmutableBeanPropertyField<>(
				this::propertyWasChanged,
				getter::apply,
				(m, number) -> immutableSetter.apply(m, number.longValue()),
				() -> new SimpleLongProperty(null, identifier)));
	}

	public LongProperty immutableField(String identifier, LongGetter<M> getter, LongImmutableSetter<M> immutableSetter, long defaultValue){
		return addIdentifiedImmutable(identifier, new ImmutableBeanPropertyField<>(
				this::propertyWasChanged,
				getter::apply,
				(m, number) -> immutableSetter.apply(m, number.longValue()),
				defaultValue,
				() -> new SimpleLongProperty(null, identifier)));
	}


	public LongProperty field(String identifier, LongPropertyAccessor<M> accessor) {
		return addIdentified(identifier, new FxPropertyField<>(this::propertyWasChanged, accessor::apply,
				() -> new SimpleLongProperty(null, identifier)));
	}

	public LongProperty field(String identifier, LongPropertyAccessor<M> accessor, long defaultValue) {
		return addIdentified(identifier, new FxPropertyField<>(this::propertyWasChanged, accessor::apply, defaultValue,
				() -> new SimpleLongProperty(null, identifier)));
	}



	/* Field type generic */


	public <T> ObjectProperty<T> field(ObjectGetter<M, T> getter, ObjectSetter<M, T> setter) {
		return add(new BeanPropertyField<>(this::propertyWasChanged, getter, setter, SimpleObjectProperty::new));
	}


	public <T> ObjectProperty<T> immutableField(ObjectGetter<M, T> getter, ObjectImmutableSetter<M, T> immutableSetter){
		return addImmutable(new ImmutableBeanPropertyField<>(
				this::propertyWasChanged,
				getter,
				immutableSetter,
				SimpleObjectProperty::new
		));
	}

	public <T> ObjectProperty<T> field(ObjectGetter<M, T> getter, ObjectSetter<M, T> setter, T defaultValue) {
		return add(new BeanPropertyField<>(this::propertyWasChanged, getter, setter, defaultValue,
				SimpleObjectProperty::new));
	}

	public <T> ObjectProperty<T> immutableField(ObjectGetter<M,T> getter, ObjectImmutableSetter<M, T> immutableSetter, T defaultValue){
		return addImmutable(new ImmutableBeanPropertyField<>(
				this::propertyWasChanged,
				getter,
				immutableSetter,
				defaultValue,
				SimpleObjectProperty::new));
	}

	public <T> ObjectProperty<T> field(ObjectPropertyAccessor<M, T> accessor) {
		return add(new FxPropertyField<>(this::propertyWasChanged, accessor, SimpleObjectProperty::new));
	}

	public <T> ObjectProperty<T> field(ObjectPropertyAccessor<M, T> accessor, T defaultValue) {
		return add(new FxPropertyField<>(this::propertyWasChanged, accessor, defaultValue, SimpleObjectProperty::new));
	}


	public <T> ObjectProperty<T> field(String identifier, ObjectGetter<M, T> getter, ObjectSetter<M, T> setter) {
		return addIdentified(identifier, new BeanPropertyField<>(this::propertyWasChanged, getter, setter,
				() -> new SimpleObjectProperty<T>(null, identifier)));
	}

	public <T> ObjectProperty<T> field(String identifier, ObjectGetter<M, T> getter, ObjectSetter<M, T> setter,
			T defaultValue) {
		return addIdentified(identifier, new BeanPropertyField<>(this::propertyWasChanged, getter, setter, defaultValue,
				() -> new SimpleObjectProperty<T>(null, identifier)));
	}



	public <T> ObjectProperty<T> immutableField(String identifier, ObjectGetter<M, T> getter, ObjectImmutableSetter<M, T> immutableSetter){
		return addIdentifiedImmutable(identifier, new ImmutableBeanPropertyField<>(
				this::propertyWasChanged,
				getter,
				immutableSetter,
				() -> new SimpleObjectProperty<T>(null, identifier)));
	}

	public <T> ObjectProperty<T> immutableField(String identifier, ObjectGetter<M, T> getter, ObjectImmutableSetter<M, T> immutableSetter, T defaultValue){
		return addIdentifiedImmutable(identifier, new ImmutableBeanPropertyField<>(
				this::propertyWasChanged,
				getter,
				immutableSetter,
				defaultValue,
				() -> new SimpleObjectProperty<T>(null, identifier)));
	}

	public <T> ObjectProperty<T> field(String identifier, ObjectPropertyAccessor<M, T> accessor) {
		return addIdentified(identifier, new FxPropertyField<>(this::propertyWasChanged, accessor,
				() -> new SimpleObjectProperty<T>(null, identifier)));
	}

	public <T> ObjectProperty<T> field(String identifier, ObjectPropertyAccessor<M, T> accessor, T defaultValue) {
		return addIdentified(identifier,
				new FxPropertyField<>(this::propertyWasChanged, accessor, defaultValue,
						() -> new SimpleObjectProperty<T>(null, identifier)));
	}


	/* Field type list */

	public <E> ListProperty<E> field(ListGetter<M, E> getter, ListSetter<M, E> setter) {
		return add(new BeanListPropertyField<>(this::propertyWasChanged, getter,
				(m, list) -> setter.accept(m, FXCollections.observableArrayList(list)), SimpleListProperty::new));
	}


	public <E> ListProperty<E> immutableField(ListGetter<M, E> getter, ListImmutableSetter<M, E> immutableSetter){
		return addImmutable(new ImmutableListPropertyField<>(
				this::propertyWasChanged,
				getter,
				(m, list) -> immutableSetter.apply(m, FXCollections.observableArrayList(list)),
				SimpleListProperty::new
		));
	}

	public <E> ListProperty<E> field(ListGetter<M, E> getter, ListSetter<M, E> setter, List<E> defaultValue) {
		return add(new BeanListPropertyField<>(this::propertyWasChanged, getter,
				(m, list) -> setter.accept(m, FXCollections.observableArrayList(list)), SimpleListProperty::new,
				defaultValue));
	}


	public <E> ListProperty<E> immutableField(ListGetter<M, E> getter, ListImmutableSetter<M, E> immutableSetter, List<E> defaultValue){
		return addImmutable(new ImmutableListPropertyField<>(
				this::propertyWasChanged,
				getter,
				(m, list) -> immutableSetter.apply(m, FXCollections.observableArrayList(list)),
				SimpleListProperty::new,
				defaultValue));
	}

	public <E> ListProperty<E> field(ListPropertyAccessor<M, E> accessor) {
		return add(new FxListPropertyField<>(this::propertyWasChanged, accessor, SimpleListProperty::new));
	}

	public <E> ListProperty<E> field(ListPropertyAccessor<M, E> accessor, List<E> defaultValue) {
		return add(
				new FxListPropertyField<>(this::propertyWasChanged, accessor, SimpleListProperty::new, defaultValue));
	}


	public <E> ListProperty<E> field(String identifier, ListGetter<M, E> getter, ListSetter<M, E> setter) {
		return addIdentified(identifier, new BeanListPropertyField<>(this::propertyWasChanged, getter,
				(m, list) -> setter.accept(m, FXCollections.observableArrayList(list)),
				() -> new SimpleListProperty<>(null, identifier)));
	}

	public <E> ListProperty<E> field(String identifier, ListGetter<M, E> getter, ListSetter<M, E> setter,
			List<E> defaultValue) {
		return addIdentified(identifier, new BeanListPropertyField<>(this::propertyWasChanged, getter,
				(m, list) -> setter.accept(m, FXCollections.observableArrayList(list)),
				() -> new SimpleListProperty<>(null, identifier), defaultValue));
	}

	public <E> ListProperty<E> immutableField(String identifier, ListGetter<M, E> getter, ListImmutableSetter<M, E> immutableSetter){
		return addIdentifiedImmutable(identifier, new ImmutableListPropertyField<>(
				this::propertyWasChanged,
				getter,
				(m, list) -> immutableSetter.apply(m, FXCollections.observableArrayList(list)),
				() -> new SimpleListProperty<>(null, identifier)));
	}

	public <E> ListProperty<E> immutableField(String identifier, ListGetter<M, E> getter, ListImmutableSetter<M, E> immutableSetter, List<E> defaultValue){
		return addIdentifiedImmutable(identifier, new ImmutableListPropertyField<>(
				this::propertyWasChanged,
				getter,
				(m, list) -> immutableSetter.apply(m, FXCollections.observableArrayList(list)),
				() -> new SimpleListProperty<>(null, identifier),
				defaultValue));
	}

	public <E> ListProperty<E> field(String identifier, ListPropertyAccessor<M, E> accessor) {
		return addIdentified(identifier, new FxListPropertyField<>(this::propertyWasChanged, accessor,
				() -> new SimpleListProperty<>(null, identifier)));
	}

	public <E> ListProperty<E> field(String identifier, ListPropertyAccessor<M, E> accessor, List<E> defaultValue) {
		return addIdentified(identifier, new FxListPropertyField<>(this::propertyWasChanged, accessor,
				() -> new SimpleListProperty<>(null, identifier), defaultValue));
	}


	/* Field type set */

	/**
	 * This helper method is needed because there is no equivalent of {@link FXCollections#observableArrayList(Collection)}
	 * for {@link Set}.
	 * The only factory method available for sets is {@link FXCollections#observableSet(Set)} which creates an observable set
	 * that is backed by the given set. However, this would mean that changes to the observable set are directly synchronized back to the
	 * source set. In contrast to this {@link FXCollections#observableArrayList(Collection)} creates an observable list that has
	 * initially all values of the source list but changes aren't synchronized back because internally a new ArrayList is created.
	 * We need exactly this behaviour for Sets and therefore are using this helper method.
	 */
	private static <T> ObservableSet<T> observableHashSet(Set<T> source) {
		return FXCollections.observableSet(new HashSet<>(source));
	}

	public <E> SetProperty<E> field(SetGetter<M, E> getter, SetSetter<M, E> setter) {
		return add(new BeanSetPropertyField<>(this::propertyWasChanged, getter,
				(m, set) -> setter.accept(m, observableHashSet(set)), SimpleSetProperty::new));
	}

	public <E> SetProperty<E> immutableField(SetGetter<M, E> getter, SetImmutableSetter<M, E> immutableSetter) {
		return addImmutable(new ImmutableSetPropertyField<>(
				this::propertyWasChanged,
				getter,
				(m, set) -> immutableSetter.apply(m, observableHashSet(set)),
				SimpleSetProperty::new
		));
	}

	public <E> SetProperty<E> field(SetGetter<M, E> getter, SetSetter<M, E> setter, Set<E> defaultValue) {
		return add(new BeanSetPropertyField<>(this::propertyWasChanged, getter,
				(m, set) -> setter.accept(m, observableHashSet(set)), SimpleSetProperty::new,
				defaultValue));
	}

	public <E> SetProperty<E> immutableField(SetGetter<M, E> getter, SetImmutableSetter<M, E> immutableSetter, Set<E> defaultValue) {
		return addImmutable(new ImmutableSetPropertyField<>(
				this::propertyWasChanged,
				getter,
				(m, set) -> immutableSetter.apply(m, observableHashSet(set)),
				SimpleSetProperty::new,
				defaultValue));
	}

	public <E> SetProperty<E> field(SetPropertyAccessor<M, E> accessor) {
		return add(new FxSetPropertyField<>(this::propertyWasChanged, accessor, SimpleSetProperty::new));
	}

	public <E> SetProperty<E> field(SetPropertyAccessor<M, E> accessor, Set<E> defaultValue) {
		return add(
				new FxSetPropertyField<>(this::propertyWasChanged, accessor, SimpleSetProperty::new, defaultValue));
	}

	public <E> SetProperty<E> field(String identifier, SetGetter<M, E> getter, SetSetter<M, E> setter) {
		return addIdentified(identifier, new BeanSetPropertyField<>(this::propertyWasChanged, getter,
				(m, set) -> setter.accept(m, observableHashSet(set)),
				() -> new SimpleSetProperty<>(null, identifier)));
	}

	public <E> SetProperty<E> field(String identifier, SetGetter<M, E> getter, SetSetter<M, E> setter,
			Set<E> defaultValue) {
		return addIdentified(identifier, new BeanSetPropertyField<>(this::propertyWasChanged, getter,
				(m, set) -> setter.accept(m, observableHashSet(set)),
				() -> new SimpleSetProperty<>(null, identifier), defaultValue));
	}

	public <E> SetProperty<E> immutableField(String identifier, SetGetter<M, E> getter, SetImmutableSetter<M, E> immutableSetter) {
		return addIdentifiedImmutable(identifier, new ImmutableSetPropertyField<>(
				this::propertyWasChanged,
				getter,
				(m, set) -> immutableSetter.apply(m, observableHashSet(set)),
				() -> new SimpleSetProperty<>(null, identifier)));
	}

	public <E> SetProperty<E> immutableField(String identifier, SetGetter<M, E> getter, SetImmutableSetter<M, E> immutableSetter,
			Set<E> defaultValue) {
		return addIdentifiedImmutable(identifier, new ImmutableSetPropertyField<>(
				this::propertyWasChanged,
				getter,
				(m, set) -> immutableSetter.apply(m, observableHashSet(set)),
				() -> new SimpleSetProperty<>(null, identifier),
				defaultValue));
	}

	public <E> SetProperty<E> field(String identifier, SetPropertyAccessor<M, E> accessor) {
		return addIdentified(identifier, new FxSetPropertyField<>(this::propertyWasChanged, accessor,
				() -> new SimpleSetProperty<>(null, identifier)));
	}

	public <E> SetProperty<E> field(String identifier, SetPropertyAccessor<M, E> accessor, Set<E> defaultValue) {
		return addIdentified(identifier, new FxSetPropertyField<>(this::propertyWasChanged, accessor,
				() -> new SimpleSetProperty<>(null, identifier), defaultValue));
	}

	/* Field type map */

	public <K, V> MapProperty<K, V> field(MapGetter<M, K, V> getter, MapSetter<M, K, V> setter) {
		return add(new BeanMapPropertyField<>(this::propertyWasChanged, getter,
				(m, map) -> setter.accept(m, FXCollections.observableMap(map)), SimpleMapProperty::new));
	}

	public <K, V> MapProperty<K, V> immutableField(MapGetter<M, K, V> getter,
			MapImmutableSetter<M, K, V> immutableSetter) {
		return addImmutable(new ImmutableMapPropertyField<>(
				this::propertyWasChanged,
				getter,
				(m, map) -> immutableSetter.apply(m, FXCollections.observableMap(map)),
				SimpleMapProperty::new
		));
	}

	public <K, V> MapProperty<K, V> field(MapGetter<M, K, V> getter, MapSetter<M, K, V> setter,
			Map<K, V> defaultValue) {
		return add(new BeanMapPropertyField<>(this::propertyWasChanged, getter,
				(m, map) -> setter.accept(m, FXCollections.observableMap(map)), SimpleMapProperty::new,
				defaultValue));
	}

	public <K, V> MapProperty<K, V> immutableField(MapGetter<M, K, V> getter,
			MapImmutableSetter<M, K, V> immutableSetter, Map<K, V> defaultValue) {
		return addImmutable(new ImmutableMapPropertyField<>(
				this::propertyWasChanged,
				getter,
				(m, map) -> immutableSetter.apply(m, FXCollections.observableMap(map)),
				SimpleMapProperty::new,
				defaultValue));
	}

	public <K, V> MapProperty<K, V> field(MapPropertyAccessor<M, K, V> accessor) {
		return add(new FxMapPropertyField<>(this::propertyWasChanged, accessor, SimpleMapProperty::new));
	}

	public <K, V> MapProperty<K, V> field(MapPropertyAccessor<M, K, V> accessor, Map<K, V> defaultValue) {
		return add(
				new FxMapPropertyField<>(this::propertyWasChanged, accessor, SimpleMapProperty::new, defaultValue));
	}

	public <K, V> MapProperty<K, V> field(String identifier, MapGetter<M, K, V> getter, MapSetter<M, K, V> setter) {
		return addIdentified(identifier, new BeanMapPropertyField<>(this::propertyWasChanged, getter,
				(m, map) -> setter.accept(m, FXCollections.observableMap(map)),
				() -> new SimpleMapProperty<>(null, identifier)));
	}

	public <K, V> MapProperty<K, V> field(String identifier, MapGetter<M, K, V> getter, MapSetter<M, K, V> setter,
			Map<K, V> defaultValue) {
		return addIdentified(identifier, new BeanMapPropertyField<>(this::propertyWasChanged, getter,
				(m, map) -> setter.accept(m, FXCollections.observableMap(map)),
				() -> new SimpleMapProperty<>(null, identifier), defaultValue));
	}

	public <K, V> MapProperty<K, V> immutableField(String identifier, MapGetter<M, K, V> getter,
			MapImmutableSetter<M, K, V> immutableSetter) {
		return addIdentifiedImmutable(identifier, new ImmutableMapPropertyField<>(
				this::propertyWasChanged,
				getter,
				(m, map) -> immutableSetter.apply(m, FXCollections.observableMap(map)),
				() -> new SimpleMapProperty<>(null, identifier)));
	}

	public <K, V> MapProperty<K, V> immutableField(String identifier, MapGetter<M, K, V> getter,
			MapImmutableSetter<M, K, V> immutableSetter,
			Map<K, V> defaultValue) {
		return addIdentifiedImmutable(identifier, new ImmutableMapPropertyField<>(
				this::propertyWasChanged,
				getter,
				(m, map) -> immutableSetter.apply(m, FXCollections.observableMap(map)),
				() -> new SimpleMapProperty<>(null, identifier),
				defaultValue));
	}

	public <K, V> MapProperty<K, V> field(String identifier, MapPropertyAccessor<M, K, V> accessor) {
		return addIdentified(identifier, new FxMapPropertyField<>(this::propertyWasChanged, accessor,
				() -> new SimpleMapProperty<>(null, identifier)));
	}

	public <K, V> MapProperty<K, V> field(String identifier, MapPropertyAccessor<M, K, V> accessor,
			Map<K, V> defaultValue) {
		return addIdentified(identifier, new FxMapPropertyField<>(this::propertyWasChanged, accessor,
				() -> new SimpleMapProperty<>(null, identifier), defaultValue));
	}
}
