package de.saxsys.mvvmfx.utils.mapping;

import javafx.beans.property.DoubleProperty;

import java.util.function.Function;

public interface DoublePropertyAccessor<M> extends Function<M, DoubleProperty> {
}
