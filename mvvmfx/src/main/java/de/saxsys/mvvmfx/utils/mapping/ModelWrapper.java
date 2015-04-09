package de.saxsys.mvvmfx.utils.mapping;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.WritableValue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class ModelWrapper<T> {
    
    private interface PropertyField<R, T> {
        void commit(T wrappedObject);
        void reload(T wrappedObject);
        void resetToDefault();
        ObjectProperty<R> getProperty();
    }
    
    private class FxPropertyField<R> implements PropertyField<R, T>{
        
        private final R defaultValue;
        private final Function<T, WritableValue<R>> accessor;
        private final ObjectProperty<R> targetProperty;

        public FxPropertyField(Function<T, WritableValue<R>> accessor){
            this(accessor, null);
        }

        public FxPropertyField(Function<T, WritableValue<R>> accessor, R defaultValue) {
            this.accessor = accessor;
            this.defaultValue = defaultValue;
            this.targetProperty = new SimpleObjectProperty<>();
        }

        @Override
        public void commit(T wrappedObject) {
            accessor.apply(wrappedObject).setValue(targetProperty.get());
        }

        @Override
        public void reload(T wrappedObject) {
            targetProperty.setValue(accessor.apply(wrappedObject).getValue());
        }

        @Override
        public void resetToDefault() {
            targetProperty.setValue(defaultValue);
        }

        @Override
        public ObjectProperty<R> getProperty() {
            return targetProperty;
        }
    }
    
    private class BeanPropertyField<R> implements PropertyField<R, T> {

        private final ObjectProperty<R> targetProperty;
        private final R defaultValue;

        private final Function<T, R> getter;
        private final BiConsumer<T, R> setter;

        public BeanPropertyField(Function<T, R> getter,
                BiConsumer<T, R> setter) {
            this(getter, setter, null);
        }
        
        public BeanPropertyField(Function<T, R> getter,
                BiConsumer<T, R> setter, R defaultValue) {
            this.defaultValue = defaultValue;
            this.getter = getter;
            this.setter = setter;
            this.targetProperty = new SimpleObjectProperty<>();
        }

        @Override
        public void commit(T wrappedObject) {
            setter.accept(wrappedObject, targetProperty.get());
        }

        @Override
        public void reload(T wrappedObject) {
            targetProperty.setValue(getter.apply(wrappedObject));
        }

        @Override
        public void resetToDefault() {
            targetProperty.setValue(defaultValue);
        }

        @Override
        public ObjectProperty<R> getProperty() {
            return targetProperty;
        }
    }

    
    private Set<PropertyField<?, T>> fields = new HashSet<>();
    private Map<String, PropertyField<?, T>> identifiedFields = new HashMap<>();

    private T wrappedObject;


    public ModelWrapper(T wrappedObject) {
        set(wrappedObject);
        reload();
    }

    public ModelWrapper(){
    }

    public void set(T object) {
        this.wrappedObject = object;
    }

    public T get() {
        return wrappedObject;
    }

    public void reset(){
        fields.forEach(field -> field.resetToDefault());
    }

    public void commit() {
        if(wrappedObject != null) {
            fields.forEach(field -> field.commit(wrappedObject));
        }
    }

    public void reload() {
        if(wrappedObject != null) {
            fields.forEach(field -> field.reload(wrappedObject));
        }
    }

    private <R> ObjectProperty<R> add(PropertyField<R, T> field) {
        fields.add(field);
        if(wrappedObject != null) {
            field.reload(wrappedObject);
        }
        return field.getProperty();
    }

    public <R> ObjectProperty<R> field(Function<T, WritableValue<R>> accessor) {
        return add(new FxPropertyField<>(accessor));
    }
    public <R> ObjectProperty<R> field(Function<T, WritableValue<R>> accessor, R defaultValue){
        return add(new FxPropertyField<>(accessor, defaultValue));
    }

    public <R> ObjectProperty<R> field(Function<T, R> getter, BiConsumer<T, R> setter) {
        return add(new BeanPropertyField<>(getter, setter));
    }

    public <R> ObjectProperty<R> field(Function<T, R> getter, BiConsumer<T, R> setter, R defaultValue){
        return add(new BeanPropertyField<>(getter, setter, defaultValue));
    }
    
    @SuppressWarnings("unchecked")
    private <R> ObjectProperty<R> addIdentified(String fieldName, PropertyField<R, T> field) {
        if(identifiedFields.containsKey(fieldName)) {
            final ObjectProperty<?> property = identifiedFields.get(fieldName).getProperty();
            return (ObjectProperty<R>) property;
        } else {
            identifiedFields.put(fieldName, field);
            return add(field);
        }
    }

    public <R> ObjectProperty<R> field(String fieldName, Function<T, WritableValue<R>> accessor) {
        return addIdentified(fieldName, new FxPropertyField<>(accessor));
    }

    public <R> ObjectProperty<R> field(String fieldName, Function<T, WritableValue<R>> accessor, R defaultValue) {
        return addIdentified(fieldName, new FxPropertyField<>(accessor, defaultValue));
    }

    public <R> ObjectProperty<R> field(String fieldName, Function<T, R> getter, BiConsumer<T, R> setter) {
        return addIdentified(fieldName, new BeanPropertyField<>(getter, setter));
    }

    public <R> ObjectProperty<R> field(String fieldName, Function<T, R> getter, BiConsumer<T, R> setter, R defaultValue) {
        return addIdentified(fieldName, new BeanPropertyField<>(getter, setter, defaultValue));
    }

}
