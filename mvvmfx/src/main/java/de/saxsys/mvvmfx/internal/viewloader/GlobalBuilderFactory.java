package de.saxsys.mvvmfx.internal.viewloader;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javafx.fxml.JavaFXBuilderFactory;
import javafx.util.Builder;
import javafx.util.BuilderFactory;

/**
 * A {@link BuilderFactory} that can manage multiple custom builder factories.
 * <br/>
 * This class is part of the internal API of mvvmFX and may be subject to changes. 
 * Don't use this class directly. Instead add new builder factories by using public API:
 * {@link de.saxsys.mvvmfx.MvvmFX#addGlobalBuilderFactory(BuilderFactory)}.
 */
public class GlobalBuilderFactory implements BuilderFactory {

	private List<BuilderFactory> factories = new ArrayList<>();

	private static final GlobalBuilderFactory SINGLETON = new GlobalBuilderFactory();

	private BuilderFactory defaultBuilderFactory = new JavaFXBuilderFactory();

	private GlobalBuilderFactory() {
	}

	public static GlobalBuilderFactory getInstance() {
		return SINGLETON;
	}

	@Override
	public Builder<?> getBuilder(Class<?> type) {
		// if no factories are defined yet, use the default factory.
		// this is an optimization for performance reasons to prevent unnecessary iterator handling
		// for a very common usage scenario
		if(factories.isEmpty()) {
			return defaultBuilderFactory.getBuilder(type);
		}

		Builder<?> builder = null;

		final ListIterator<BuilderFactory> listIterator = factories.listIterator(factories.size());

		// iterate builder list in reverse order
		while(listIterator.hasPrevious() && builder == null) {
			final BuilderFactory factory = listIterator.previous();

			builder = factory.getBuilder(type);
		}

		// if no custom builderFactory is suitable for this type, use the default one.
		if(builder == null) {
			builder = defaultBuilderFactory.getBuilder(type);
		}

		return builder;
	}

	public void addBuilderFactory(BuilderFactory factory) {
		this.factories.add(factory);
	}
}
