package de.saxsys.mvvmfx.utils.mapping;

import javafx.beans.property.Property;

import java.util.function.Function;

public interface BooleanPropertyAccessor<M> extends Function<M, Property<Boolean>> {
}
