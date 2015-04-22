package de.saxsys.mvvmfx.utils.mapping;

import javafx.beans.property.IntegerProperty;

import java.util.function.Function;

public interface IntPropertyAccessor<M> extends Function<M, IntegerProperty> {
}
