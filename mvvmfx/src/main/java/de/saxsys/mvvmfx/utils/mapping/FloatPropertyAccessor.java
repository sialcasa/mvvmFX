package de.saxsys.mvvmfx.utils.mapping;

import javafx.beans.property.FloatProperty;

import java.util.function.Function;

public interface FloatPropertyAccessor<M> extends Function<M, FloatProperty> {
}
