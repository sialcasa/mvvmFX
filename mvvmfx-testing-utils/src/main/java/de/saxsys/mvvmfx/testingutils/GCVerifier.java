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
	
	
	public void verify(String message) {
		forceGC();
		
		if (reference.get() != null) {
			throw new AssertionError(message);
		}
	}
	
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
