package de.saxsys.mvvmfx.internal.viewloader;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to store references to objects that may not be
 * garbage collected (yet).
 */
public class PreventGarbageCollectionStore {

	private static final PreventGarbageCollectionStore SINGLETON = new PreventGarbageCollectionStore();

	private PreventGarbageCollectionStore() {
	}

	public static PreventGarbageCollectionStore getInstance() {
		return SINGLETON;
	}

	private List<Object> storedObjects = new ArrayList<>();

	public void put(Object o) {
		if(!storedObjects.contains(o)) {
			this.storedObjects.add(o);
		}
	}

	public void remove(Object o) {
		this.storedObjects.removeIf(x -> x == o || x.equals(o));
	}
}

