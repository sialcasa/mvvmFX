package de.saxsys.mvvmfx.utils.mapping;

import javafx.beans.property.Property;

import java.util.function.Function;

public interface StringPropertyAccessor<M> extends Function<M, Property<String>> {
}
