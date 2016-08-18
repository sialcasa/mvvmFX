package de.saxsys.mvvmfx;

import de.saxsys.mvvmfx.internal.viewloader.PreventGarbageCollectionStore;

public interface PreventGarbageCollection {

	static void release(PreventGarbageCollection instance) {
		PreventGarbageCollectionStore.getInstance().remove(instance);
	}

}
