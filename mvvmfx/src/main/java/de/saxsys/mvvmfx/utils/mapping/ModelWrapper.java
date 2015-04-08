package de.saxsys.mvvmfx.utils.mapping;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.WritableValue;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class ModelWrapper<T> {
    private class AccessorTuple<R> {
        final Function<T, R> getter;
        final BiConsumer<T, R> setter;

        public AccessorTuple(Function<T, R> getter, BiConsumer<T, R> setter) {
            this.getter = getter;
            this.setter = setter;
        }
    }

    private Map<String, ObjectProperty> identifiedFields = new HashMap<>();
    private Map<ObjectProperty, AccessorTuple> fields = new HashMap<>();


    private T wrappedObject;


    public ModelWrapper(T wrappedObject) {
        set(wrappedObject);
    }

    public ModelWrapper(){
    }

    public void set(T object) {
        this.wrappedObject = object;

        reload();
    }

    public T get() {
        return wrappedObject;
    }

    public void reset(){
        fields.forEach(((objectProperty, getterSetterTuple) -> objectProperty.setValue(null)));

    }

    public void commit() {
        if(wrappedObject != null) {
            fields.forEach((objectProperty, accessorTuple)
                    -> accessorTuple.setter.accept(wrappedObject, objectProperty.get()));
        }
    }

    public void reload() {
        if(wrappedObject != null) {
            fields.forEach(((objectProperty, accessorTuple)
                    -> objectProperty.setValue(accessorTuple.getter.apply(wrappedObject))));
        }
    }


    public <R> ObjectProperty<R> field(Function<T, WritableValue<R>> accessor) {
        return field(t -> accessor.apply(t).getValue(), (t, r) -> accessor.apply(t).setValue(r));
    }

    public <R> ObjectProperty<R> field(String fieldName, Function<T, WritableValue<R>> accessor) {
        return field(fieldName, t -> accessor.apply(t).getValue(), (t, r) -> accessor.apply(t).setValue(r));
    }



    public <R> ObjectProperty<R> field(Function<T, R> getter, BiConsumer<T, R> setter) {
        ObjectProperty<R> property = new SimpleObjectProperty<>();
        fields.put(property, new AccessorTuple<>(getter, setter));

        if(wrappedObject != null) {
            property.set(getter.apply(wrappedObject));
        }
        return property;
    }

    public <R> ObjectProperty<R> field(String fieldName, Function<T, R> getter, BiConsumer<T, R> setter) {
        if(!identifiedFields.containsKey(fieldName)) {
            final ObjectProperty<R> property = field(getter, setter);

            identifiedFields.put(fieldName, property);
        }

        return identifiedFields.get(fieldName);
    }



}
