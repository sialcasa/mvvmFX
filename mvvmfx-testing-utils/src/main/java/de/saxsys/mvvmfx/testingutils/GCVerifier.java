package de.saxsys.mvvmfx.testingutils;

import java.lang.ref.WeakReference;


/**
 * This is a small testing helper to verify that a given object is available for Garbage Collection.
 * 
 * The typical usage is:
 * 
 * <pre>
 * Object testObject = new Object();
 * 
 * GCVerifier verifier = GCVerifier.create(testObject);
 * 
 * 
 * testObject = null;  // now there is no reference to the object anymore so it should be available for GC
 * 
 * 
 * verifier.verify();  // this will throw an AssertionError if there is still a reference to the object somewhere
 * </pre>
 * 
 */
public class GCVerifier {
	private final WeakReference reference;
	
	private final String objectName;
	
	GCVerifier(WeakReference reference, String objectName) {
		this.reference = reference;
		this.objectName = objectName;
	}
	
	/**
	 * Create a Verifier instance for the given Object. No hard reference to this object is stored internally.
	 * 
	 * @param instance
	 *            the instance that is verified.
	 * 			
	 * @return an instance of the {@link GCVerifier} that can be used to verify the garbage collection of the given
	 *         instance.
	 */
	@SuppressWarnings("unchecked")
	public static GCVerifier create(Object instance) {
		return new GCVerifier(new WeakReference(instance), instance.toString());
	}

	/**
	 * Verify that the wrapped object can be garbage collected.
	 * If the object is not available for garbage collection
	 * an assertion error with the given message will be thrown.
	 *
	 * @param message the message to be used when the object can't be garbage collected.
	 */
	public void verify(String message) {
		forceGC();

		if (!isAvailableForGC()) {
			throw new AssertionError(message);
		}
	}

	/**
	 * This method can be used to check if a wrapped object is available
	 * for garbage collection or not.
	 * <p/>
	 * This method performs a garbage collection. This means that the wrapped
	 * object (and potentially all other objects) may be collected and therefore aren't
	 * available afterwards.
	 *
	 * @return <code>true</code> if the object is available for garbage collection.
	 */
	public boolean isAvailableForGC() {
		forceGC();

		return reference.get() == null;
	}

	/**
	 * Returns the wrapped object if it wasn't garbage collected yet.
	 * If the object was already collected, this method returns <code>null</code>
	 */
	public Object get() {
		return reference.get();
	}

	/**
	 * Verify that the wrapped object can be garbage collected.
	 * If the object is not available for garbage collection
	 * an assertion error with the given message will be thrown.
	 */
	public void verify() {
		verify("Expected the given object [" + objectName + "] to be available for Garbage Collection but it isn't");
	}
	
	/**
	 * This method can be used to "force" the garbage collection.
	 * 
	 * This can be useful because with "System.gc()" you can't tell if the GC was actually done.
	 * 
	 * 
	 * This method will only return when the GC was done. <strong>Be careful</strong>: This method will block until the
	 * GC was done so when for some reason the java runtime doesn't run the GC your application won't continue.
	 */
	@SuppressWarnings("unchecked")
	public static void forceGC() {
		Object o = new Object();
		
		WeakReference ref = new WeakReference(o);
		
		o = null;
		
		while (ref.get() != null) {
			System.gc();
		}
	}
}
