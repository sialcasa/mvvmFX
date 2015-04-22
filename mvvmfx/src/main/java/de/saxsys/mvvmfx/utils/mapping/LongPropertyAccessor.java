package de.saxsys.mvvmfx.utils.mapping;

import javafx.beans.property.LongProperty;

import java.util.function.Function;

public interface LongPropertyAccessor<M> extends Function<M, LongProperty> {
}
